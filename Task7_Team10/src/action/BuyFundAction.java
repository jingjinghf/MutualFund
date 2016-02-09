package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.FundBean;
import bean.TransactionBean;
import dao.Model;
import dao.TransactionDAO;
import formbean.BuyFundForm;
import utilities.TransactionType;
import dao.CustomerDAO;
import dao.FundDAO;

/*
 * Buy fund action.
 * 
 */
public class BuyFundAction extends Action {
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;

	public BuyFundAction(Model model) {
		;
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "buyfund.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		String symbol = request.getParameter("symbol");
		FundBean fund = null;
		try {
			fund = fundDAO.getFundIdBySymbol(symbol);
			if (fund == null) {
				errors.add("Sorry,we don't find this fund.");
				return "customer_view_acc.jsp";
			}
		} catch (RollbackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();

		}
		session.setAttribute("modifyFund", fund);

		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if (customer == null) {
			return "customerlogin.do";
		}

		if (customer != null) {
			int id = customer.getCustomerId();
			try {
				customer = customerDAO.read(id);
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			request.getSession().setAttribute("customer", customer);
		}

		/*
		 * Check the buy fund form.
		 */
		BuyFundForm buyfundform = new BuyFundForm(request);
		request.setAttribute("buyfundform", buyfundform);

		if (!buyfundform.isPresent()) {
			return "customer_buy_fund.jsp";
		}
		errors.addAll(buyfundform.getValidationErrors());
		if (errors.size() != 0) {
			return "customer_buy_fund.jsp";
		}

		double avaliable = customer.readCashAvailable();
		double amount = buyfundform.getAmount();

		if (amount > avaliable) {
			errors.add("Sorry, you don't have enough money!");

			return "customer_buy_fund.jsp";
		}

		int fundId = fund.getFundId();
		//////////////////////
		// Transaction START //
		//////////////////////
		try {
			Transaction.begin();
			TransactionBean transaction = new TransactionBean();
			// what kind of transaction
			transaction.setTransactionType(TransactionType.BUY.toString());
			// who request the buy
			transaction.setCustomerId(customer.getCustomerId());
			// how much money(in long)
			transaction.writeAmount(amount);
			transaction.setFundId(fundId);
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
		session.setAttribute("message", "Thank you! You have successfully sent your buy fund request!");
		return "success_customer.jsp";
	}

}
