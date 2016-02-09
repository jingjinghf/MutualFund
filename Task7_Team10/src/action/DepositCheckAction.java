package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.EmployeeBean;
import bean.TransactionBean;
import dao.CustomerDAO;
import dao.Model;
import dao.TransactionDAO;
import formbean.DepositCheckForm;
import utilities.TransactionType;

public class DepositCheckAction extends Action {

    private TransactionDAO transactionDAO;
    private CustomerDAO customerDAO;

    public DepositCheckAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transactionDAO = model.getTransactionDAO();
    }

    public String getName() {
        return "depositcheck.do";
    }

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		// authority judge
		if((EmployeeBean)session.getAttribute("employee") == null) {
		    return "employeelogin.do";
		}
		
		request.setAttribute("errors", errors);
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        if (employee == null) {
            return "employeelogin.do";
        }

        /*
         * Check the deposit form.
         */
        DepositCheckForm depositcheckform = new DepositCheckForm(request);
        request.setAttribute("depositcheckform", depositcheckform);
        if (!depositcheckform.isPresent()) {
            return "employee_deposit_check.jsp";
        }
        errors.addAll(depositcheckform.getValidationErrors());
        if (errors.size() != 0) {
            return "employee_deposit_check.jsp";
        }

        CustomerBean customer = (CustomerBean) session.getAttribute("modifyCustomer");
        int id = customer.getCustomerId();
        try {
            customer = customerDAO.read(id);
        } catch (RollbackException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        double amount = depositcheckform.getAmount();

        //////////////////////
        // Transaction START //
        //////////////////////
        try {
            Transaction.begin();
            TransactionBean transaction = new TransactionBean();
            // what kind of transaction
            transaction.setTransactionType(TransactionType.DEPOSIT.toString());
            // who request the deposit
            transaction.setCustomerId(customer.getCustomerId());
            // how much money(in long)
            transaction.writeAmount(amount);
            // Create transaction
            transactionDAO.create(transaction);

            // update balance&available immediately.
            customer.writeCashBalance(amount);
            customerDAO.update(customer);

            Transaction.commit();
        } catch (RollbackException e) {
            e.printStackTrace();
        }
        ////////////////////
        // Transaction END//
        ////////////////////

        session.setAttribute("modifyCustomer", customer);
        // session.setAttribute("customer", customer);

        session.setAttribute("message", "Thank you! You have successfully sent your deposit check request!");
        return "success_employee.jsp";
    }

}