package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.CustomerFundBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.PositionBean;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;
import dao.Model;
import dao.PositionDAO;
import dao.TransactionDAO;
import formbean.LoginForm;

/**
 * Customer login function
 * @author jingjinghuangfu
 *
 */
public class CustomerLoginAction extends Action {
    
    private CustomerDAO customerDAO;
    private PositionDAO positionDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private FundDAO fundDAO;
    private TransactionDAO transactionDAO;

    public CustomerLoginAction(Model model) {
        customerDAO = model.getCustomerDAO();
        positionDAO = model.getPositionDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
        fundDAO = model.getFundDAO();
        transactionDAO = model.getTransactionDAO();        
    }

    public String getName() { return "customerlogin.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
            LoginForm form = new LoginForm(request);
            //request.setAttribute("form",form);
            
            //get and set last trading day
            FundPriceHistoryBean FPH = fundPriceHistoryDAO.getLastTradingDate();
            Date ltd = FPH.getPriceDate();
            if (ltd == null) {
                ltd = transactionDAO.getLastTransactionDate();
            }
            request.setAttribute("lasttradingday", ltd);

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login.jsp";
            }

            // Look up the user by emailAddress
            CustomerBean customer = customerDAO.getCustomer(form.getUserName());
            
            
            if (customer == null) {               
                errors.add("User name not found");
                return "login.jsp";
            }
            

            // Check the password
            //String hashpassword = hash(form.getPassword(), customer.getSalt());
            
            if (!customer.checkPassword(form.getPassword())) {
                errors.add("Incorrect password");
                return "login.jsp";
            }
    
            // Attach (this copy of) the user bean to the session
            HttpSession session = request.getSession();
            session.setAttribute("customer", customer);
            
            //generate own fund list

            ArrayList<PositionBean> positionList = positionDAO.getAllPositionByUserId(customer.getCustomerId());
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
            
            request.setAttribute("customerFundList", customerFundList);
            return "customer_view_acc.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }
}

