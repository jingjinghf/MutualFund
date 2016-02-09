package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.EmployeeBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.PositionBean;
import bean.TransactionBean;
import bean.ViewFundBean;
import dao.Model;
import dao.PositionDAO;
import dao.TransactionDAO;
import formbean.TransitionForm;
import utilities.TransactionType;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;
import java.util.Date;

/*
 * Transition action.
 */
public class TransitionAction extends Action {
    private PositionDAO positionDAO;
    private FundPriceHistoryDAO fundpricehistoryDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private CustomerDAO customerDAO;

    public TransitionAction(Model model) {
        positionDAO = model.getPositionDAO();
        fundpricehistoryDAO = model.getFundPriceHistoryDAO();
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
        customerDAO = model.getCustomerDAO();
    }

    public String getName() {
        return "transition.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        System.out.println("succeed1");
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        if (employee == null) {
            return "employeelogin.do";
        }
        // get last day
        FundPriceHistoryBean fph;
        Date lasttradingday = new Date();
        try {
            fph = fundpricehistoryDAO.getLastTradingDate();

            if (fph == null) {
                lasttradingday = transactionDAO.getLastTransactionDate();
            } else {
                lasttradingday = fph.getPriceDate();
            }
        } catch (RollbackException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
        }

        session.setAttribute("lasttradingday", lasttradingday);

        
        /*
         * Get the fund list.
         */
        List<FundBean> fundlist = new ArrayList<FundBean>();

        List<ViewFundBean> viewfundlist = new ArrayList<ViewFundBean>();

        try {
            fundlist = fundDAO.getAllFunds();
            // System.out.println(fundlist.size());
            for (int i = 0; i < fundlist.size(); i++) {
                ViewFundBean view = new ViewFundBean();
                int fundId = fundlist.get(i).getFundId();
                List<FundPriceHistoryBean> prices = new ArrayList<FundPriceHistoryBean>();
                prices = fundpricehistoryDAO.getAllPriceByFundId(fundId);

                if (prices.size() != 0) {
                    int last = prices.size() - 1;
                    double price = prices.get(last).readPrice();
                    view.setPrice(price);

                    if (last != 0) {
                        double price1 = prices.get(last).readPrice();
                        double price2 = prices.get(last - 1).readPrice();
                        double change = (price1 - price2) / price2 * 100;
                        view.setChange(change);
                    } else {
                        view.setChange(0);
                    }

                }
                view.setFundId(fundId);
                String symbol = fundlist.get(i).getSymbol();
                view.setSymbol(symbol);
                String fundname = fundlist.get(i).getFundName();
                view.setFundName(fundname);
                viewfundlist.add(view);

            }

        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        session.setAttribute("viewfundlist", viewfundlist);

       

        TransitionForm transitionform = new TransitionForm(request);
        request.setAttribute("form", transitionform);
        if (!transitionform.isPresent()) {
            return "employee_transition.jsp";
        }
        
        errors.addAll(transitionform.getValidationErrors());
       
        if (errors.size() != 0) {
            return "employee_transition.jsp";
        }

        /*
         * Check the transition date.
         */
        Date newdate = transitionform.getDate();

        if (lasttradingday != null && lasttradingday.getTime() >= newdate.getTime()) {
            errors.add("Sorry, the new trading day must be larger than the last trading day!");
            return "employee_transition.jsp";
        }

        // change the price of all the funds
        List<FundBean> newfundlist = new ArrayList<FundBean>();
        try {
            newfundlist = fundDAO.getAllFunds();
            for (int i = 0; i < newfundlist.size(); i++) {
                int id = newfundlist.get(i).getFundId();
                String id1 = String.valueOf(id);
                String price = request.getParameter(id1);
                double newprice;

                if (price == null || price.length() == 0) {
                    errors.add("Please input the price for the fund " + id + " !");
                    return "employee_transition.jsp";
                } else {
                    try {
                        newprice = Double.parseDouble(price);
                        if (newprice <= 0) {
                            errors.add("Sorry, only positive fund price is allowed(id = " + id + ")");
                            return "employee_transition.jsp";
                        }
                        
                    } catch (Exception e) {
                        errors.add("Sorry, the format of the price for the fund " + id + " is not right!");
                        return "employee_transition.jsp";
                    }
                }

            }
            Transaction.begin();
            for (int i = 0; i < newfundlist.size(); i++) {

                int id = newfundlist.get(i).getFundId();
                String id1 = String.valueOf(id);
                String price = request.getParameter(id1);
                double newprice = Double.parseDouble(price);

                int fundId = id;
                FundPriceHistoryBean pricehistory = new FundPriceHistoryBean();
                pricehistory.setFundId(fundId);
                pricehistory.writePrice(newprice);
                pricehistory.setPriceDate(transitionform.getDate());
                fundpricehistoryDAO.create(pricehistory);

            }
            Transaction.commit();

        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        /*
         * Begin transition.
         */
        List<TransactionBean> transactionlist = new ArrayList<TransactionBean>();
        Date date = transitionform.getDate();
        try {
            transactionlist = transactionDAO.getAllPendingTransactions();
            for (int i = 0; i < transactionlist.size(); i++) {
                TransactionBean transaction = transactionlist.get(i);
                // first type
                if (transaction.readType() == TransactionType.REQUEST) {
                    Transaction.begin();
                    transaction.setExecutionDate(date);
                    transactionDAO.update(transaction);
                    Transaction.commit();
                }

                // second type
                if (transaction.readType() == TransactionType.DEPOSIT) {
                    Transaction.begin();
                    int customerId = transaction.getCustomerId();
                    double amount = transaction.readAmount();
                    CustomerBean customer = customerDAO.getCustomer(customerId);
                    transaction.writeAmount(amount);
                    transaction.setExecutionDate(date);
                    customer.writeCashAvailable(amount);
                    customerDAO.update(customer);
                    transactionDAO.update(transaction);
                    Transaction.commit();
                }

                // third type
                if (transaction.readType() == TransactionType.SELL) {
                    Transaction.begin();
                    int customerId = transaction.getCustomerId();
                    CustomerBean customer = customerDAO.getCustomer(customerId);
                    int fundId = transaction.getFundId();
                    FundPriceHistoryBean newhistory = fundpricehistoryDAO.read(fundId, date);
                    double newprice = newhistory.readPrice();

                    double shares = transaction.readShares();
                    double amount = shares * newprice;
                    transaction.writeAmount(amount);

                    
                    customer.writeCashAvailable(amount);
                    customer.writeCashBalance(amount);
                    customerDAO.update(customer);

                    // request.setAttribute("customer", customer);
                    transaction.setExecutionDate(date);
                    transactionDAO.update(transaction);
                    Transaction.commit();
                }

                // last type
                if (transaction.readType() == TransactionType.BUY) {
                    Transaction.begin();
                    int customerId = transaction.getCustomerId();
                    int fundId = transaction.getFundId();
                    FundPriceHistoryBean newhistory = fundpricehistoryDAO.read(fundId, date);
                    double newprice = newhistory.readPrice();
                    double amount = transaction.readAmount();
                    double shares = amount / newprice;
                    transaction.writeShares(shares);

                    List<PositionBean> positions = new ArrayList<PositionBean>();
                    positions = positionDAO.getAllPositionByUserId(customerId);
                    int flag = 0;
                    for (int j = 0; j < positions.size(); j++) {
                        PositionBean position = positions.get(j);
                        if (position.getFundId() == fundId) {
                            double newshares = shares + position.readShares();
                            position.writeShares(newshares);
                            positionDAO.update(position);
                            flag = 1;
                            break;
                        }
                    }
                    if (flag == 0) {
                        PositionBean position = new PositionBean();
                        position.setCustomerId(customerId);
                        position.setFundId(fundId);
                        position.writeShares(shares);
                        positionDAO.create(position);
                    }

                    transaction.setExecutionDate(date);
                    transactionDAO.update(transaction);
                    Transaction.commit();
                }

            }

        } catch (RollbackException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        session.setAttribute("message", "The transitions have been finished!");
        return "success_employee.jsp";
    }

}
