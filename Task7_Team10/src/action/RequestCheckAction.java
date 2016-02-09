package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.TransactionBean;
import dao.Model;
import dao.TransactionDAO;
import utilities.TransactionType;
import dao.CustomerDAO;

/*
 * Request check action.
 */
public class RequestCheckAction extends Action {
	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	public RequestCheckAction(Model model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "requestcheck.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		System.out.println("requestcheck");
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if (customer == null) {
			return "customerlogin.do";
		}
		int id = customer.getCustomerId();
		try {
			customer = customerDAO.read(id);
		} catch (RollbackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		session.setAttribute("customer", customer);

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		/*
		 * Check the request form.
		 */
		String action = sanitize(request.getParameter("requestbutton"));
		if (action == null) {
			return "customer_request_check.jsp";
		}

		String check = sanitize(request.getParameter("amount"));
		request.setAttribute("check", check);
		if (check == null || check.length() == 0) {
			errors.add("Please input the cash value!");
			return "customer_request_check.jsp";
		}
		double amount;

		try {
			amount = Double.parseDouble(check);

		} catch (Exception e) {
			errors.add("Please input the correct cash value!");
			return "customer_request_check.jsp";
		}

		if (amount <= 0) {
			errors.add("Request amount can not be 0 or negtive!");
			return "customer_request_check.jsp";
		}

		if (amount > customer.readCashAvailable()) {
			errors.add("Insufficient available balance!");
			return "customer_request_check.jsp";
		}

		double max = 10000000;
		if (amount > max) {
			errors.add("Sorry, amount exceed trading limit, please contact XXX@cmu.edu for assistance.");
			return "customer_request_check.jsp";
		}
		//////////////////////
		// Transaction START //
		//////////////////////
		try {
			Transaction.begin();
			TransactionBean transaction = new TransactionBean();
			// what kind of transaction
			transaction.setTransactionType(TransactionType.REQUEST.toString());
			// who request the check
			transaction.setCustomerId(customer.getCustomerId());
			// how much money(in long)
			transaction.writeAmount(amount);
			// Create transaction
			transactionDAO.create(transaction);

			// update balance&available immediately.
			amount = -amount;
			customer.writeCashAvailable(amount);
			customer.writeCashBalance(amount);
			customerDAO.update(customer);
			Transaction.commit();
		} catch (RollbackException e) {
			e.printStackTrace();
		}
		////////////////////
		// Transaction END//
		////////////////////
		session.setAttribute("customer", customer);
		session.setAttribute("message", "Thank you! You have successfully sent your check request!");
		return "success_customer.jsp";
	}

	private String sanitize(String s) {
		if (s != null) {
			return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
					.replace("\'", "&apos");
		} else
			return null;
	}
}
