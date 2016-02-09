package action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import action.Action;
import bean.CustomerBean;
import bean.DetailTransactionBean;
import bean.EmployeeBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.TransactionBean;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;
import dao.Model;
import dao.TransactionDAO;

/**
 * get transaction history list from a specific customer
 * @author jingjinghuangfu
 *
 */
public class CustomerViewTransactionHistoryAction extends Action {
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public CustomerViewTransactionHistoryAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }

    public String getName() { return "customerviewtransactionhistory.do"; }
    
    public String perform(HttpServletRequest request)  {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        //get current user from session
        CustomerBean customer = (CustomerBean) request.getSession().getAttribute("customer");
        
        //if not an employee or customer, direct to login page
        if (customer == null) {
            return "customerlogin.do";
        }
        
        //if a customer login
        if (customer != null) {
            int id = customer.getCustomerId();
            try {
                customer = customerDAO.read(id);
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            request.getSession().setAttribute("customer", customer);
            
            try {
                List<TransactionBean> translist = transactionDAO.getAllTransactionsByCustomerId(customer.getCustomerId());
                
                List<DetailTransactionBean> transactionList = new ArrayList<DetailTransactionBean>();
                request.setAttribute("transactionList", transactionList);
                //get last day
                FundPriceHistoryBean fph = fundPriceHistoryDAO.getLastTradingDate();
                Date lasttradingday;
                if (fph == null) {
                    lasttradingday = transactionDAO.getLastTransactionDate();
                }
                else  {
                    lasttradingday = fph.getPriceDate();
                }
                //transfer transactionbean to join bean
                TransactionBean trans;
                for (int i = translist.size()-1; i >= 0; i--) {
                	trans = translist.get(i);
                    DetailTransactionBean detail = new DetailTransactionBean();

                    if (trans.getTransactionType().equals("buy") || trans.getTransactionType().equals("sell")) {
                        FundBean fb = fundDAO.read(trans.getFundId());
                        //date,type,name,share,price,amount
                        System.out.println(fb.getFundName());
                        detail.setTransactionId(trans.getTransactionId());
                        detail.setTransactionType(trans.getTransactionType());
                        detail.setExecutionDate(trans.getExecutionDate());
                        detail.setFundName(fb.getFundName());
                        detail.setShares(trans.readShares());
                        detail.setAmount(trans.readAmount());
                        if (trans.getExecutionDate() != null) {
                            FundPriceHistoryBean fphb = fundPriceHistoryDAO.read(fb.getFundId(), trans.getExecutionDate());
                            detail.setPrice_perShare(fphb.readPrice());
                        }
//                        if (lasttradingday != null) {
//                            FundPriceHistoryBean fphb = fundPriceHistoryDAO.read(fb.getFundId(), lasttradingday);
//                            detail.setPrice_perShare(fphb.readPrice());
//                        }             
                    }
                    else {
                        detail.setTransactionId(trans.getTransactionId());
                        detail.setTransactionType(trans.getTransactionType());
                        detail.setAmount(trans.readAmount());
                        detail.setExecutionDate(trans.getExecutionDate());
                    }
                    
                    transactionList.add(detail);
                }
                
               // Collections.reverse(transactionList); 
                request.setAttribute("transactionList", transactionList);
                return "customer_transactionhistory.jsp";                
            } catch (RollbackException e) {
                e.printStackTrace();
                
            }
        }
        return "error.jsp";
    }
}
