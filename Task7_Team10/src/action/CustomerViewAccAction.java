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
public class CustomerViewAccAction extends Action {
    
    private CustomerDAO customerDAO;
    private PositionDAO positionDAO;
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;
    private TransactionDAO transactionDAO;
    

    public CustomerViewAccAction(Model model) {
        customerDAO = model.getCustomerDAO();
        positionDAO = model.getPositionDAO();
        fundDAO = model.getFundDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();     
        transactionDAO = model.getTransactionDAO();
    }

    public String getName() { return "customerviewacc.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        HttpSession session = request.getSession();
        //check wether the user is a login employee
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");
        
        if (customer == null) {
            return "customerlogin.do";
        }

        //if customer login
        if (customer != null) {
            
            
            //update customer
            int id = customer.getCustomerId();
            try {
                customer = customerDAO.read(id);
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            session.setAttribute("customer", customer);
            
            try {
                //get and set last trading day
                FundPriceHistoryBean FPH = fundPriceHistoryDAO.getLastTradingDate();
                Date ltd = FPH.getPriceDate();
                if (ltd == null) {
                    ltd = transactionDAO.getLastTransactionDate();
                }
                request.setAttribute("lasttradingday", ltd);
                //get position list
            ArrayList<PositionBean> positionList = positionDAO.getAllPositionByUserId(customer.getCustomerId());
            //get customer fund list
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

            } catch (RollbackException e2) {
                e2.printStackTrace();
            }            
        } 
        return "error.jsp";
    }
}