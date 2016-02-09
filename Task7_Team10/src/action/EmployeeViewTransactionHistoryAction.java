package action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

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
public class EmployeeViewTransactionHistoryAction extends Action {
    private TransactionDAO transactionDAO;
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public EmployeeViewTransactionHistoryAction(Model model) {
        transactionDAO = model.getTransactionDAO();
        fundDAO = model.getFundDAO();
        fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
    }

    public String getName() { return "employeeviewtransactionhistory.do"; }
    
    public String perform(HttpServletRequest request)  {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        //get current user from session
        EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
     // authority judge
        if(employee == null) {
            return "employeelogin.do";
        }
        //if an employee login
        if (employee != null) {
            
            CustomerBean modifyCustomer = (CustomerBean) request.getSession().getAttribute("modifyCustomer");
            if (modifyCustomer == null) {
                errors.add("Not indicate which customer you want to check");
                return "customerlist.jsp";
            }
            
            try {
            List<TransactionBean> translist = transactionDAO.getAllTransactionsByCustomerId(modifyCustomer.getCustomerId());
            List<DetailTransactionBean> transactionList = new ArrayList<DetailTransactionBean>();
            //transfer transactionbean to join bean
            FundPriceHistoryBean fph = fundPriceHistoryDAO.getLastTradingDate();
            
            TransactionBean trans;
            
            
            for (int i = translist.size()-1; i>=0; --i) {
            	trans = translist.get(i);
                DetailTransactionBean detail = new DetailTransactionBean();

                if (trans.getTransactionType().equals("buy") || trans.getTransactionType().equals("sell")) {
                    FundBean fb = fundDAO.read(trans.getFundId());
                    //date,type,name,share,price,amount
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
//                    if (lasttradingday != null) {
//                        FundPriceHistoryBean fphb = fundPriceHistoryDAO.read(fb.getFundId(), lasttradingday);
//                        detail.setPrice_perShare(fphb.readPrice());
//                    }             
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
            return "employee_view_trans_history.jsp";
            } catch (RollbackException e) {
                e.printStackTrace();                
            } 
        }
        
        return "error.jsp";
    }
}
