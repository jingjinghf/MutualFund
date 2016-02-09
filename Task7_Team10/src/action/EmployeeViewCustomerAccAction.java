package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.CustomerFundBean;
import bean.EmployeeBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.PositionBean;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;
import dao.Model;
import dao.PositionDAO;
import dao.TransactionDAO;

/**
 * employee view a customer account, set customer and the fund list he owns.
 * @author jingjinghuangfu
 *
 */
public class EmployeeViewCustomerAccAction extends Action {
    
    private CustomerDAO customerDAO;
    private PositionDAO positionDAO;
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionDAO transactionDAO;
    

    public EmployeeViewCustomerAccAction(Model model) {
        customerDAO = model.getCustomerDAO();
        positionDAO = model.getPositionDAO();
        fundDAO = model.getFundDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();     
        transactionDAO = model.getTransactionDAO();
    }

    public String getName() { return "employeeviewcustomeracc.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);        
        HttpSession session = request.getSession();
        
        //check wether the user is a login employee
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        
        if (employee == null) {
            return "employeelogin.do";
        }     
        
        //if an employee login
        if (employee != null) {
            String customerId = request.getParameter("id");
            try {
               //get last trading day
                FundPriceHistoryBean FPH = fundPriceHistoryDAO.getLastTradingDate();
                Date ltd = FPH.getPriceDate();
                if (ltd == null) {
                    ltd = transactionDAO.getLastTransactionDate();
                }
                request.setAttribute("lasttradingday", ltd);
                //get modify customer
                int cusid = Integer.parseInt(customerId);
                CustomerBean modifycus = customerDAO.read(cusid);

                if (modifycus == null) {
                    errors.add("There is no modify customer");
                    return "customerlist.jsp";
                }
                
                if (errors.size() != 0) {
                    return "customerlist.jsp";
                }
                
                ArrayList<PositionBean> positionList = positionDAO.getAllPositionByUserId(modifycus.getCustomerId());
                List<CustomerFundBean> customerFundList = new ArrayList<CustomerFundBean>();
                
                for (PositionBean position:positionList) {
                    CustomerFundBean cusfund = new CustomerFundBean();
                    FundBean fund = fundDAO.read(position.getFundId());
                    String fundName = fund.getFundName();
                    String fundSymbol = fund.getSymbol();
                    double share = position.readShares();
                    FundPriceHistoryBean fph = fundPriceHistoryDAO.getLastTradingDate();
                    Date lasttradingday = fph.getPriceDate();
                    if (lasttradingday == null) {
                        lasttradingday = transactionDAO.getLastTransactionDate();
                    }
                    FundPriceHistoryBean fphb = fundPriceHistoryDAO.read(fund.getFundId(),lasttradingday);
                    double price = fphb.readPrice();
                    double amount = price * position.readShares();
                    //set
                    cusfund.setFundName(fundName);
                    cusfund.setFundSymbol(fundSymbol);
                    cusfund.writeShares(share);
                    cusfund.writeAmount(amount);
                    cusfund.setPrice(price);
                    
                    customerFundList.add(cusfund);
                }
                
                session.setAttribute("modifyCustomer", modifycus);
                request.setAttribute("customerFundList", customerFundList);
                return "employee_view_customer_acc.jsp";

            } catch (RollbackException e) {
                e.printStackTrace();
            }
        }

        return "error.jsp";
    }
}