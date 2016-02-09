package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.CustomerFundBean;
import bean.FundBean;
import bean.PositionBean;
import bean.TransactionBean;
import dao.Model;
import dao.PositionDAO;
import dao.TransactionDAO;
import formbean.SellFundForm;
import utilities.TransactionType;
import dao.CustomerDAO;
import dao.FundDAO;

/*
 * Sell fund action.
 */
public class SellFundAction extends Action {
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;

	public SellFundAction(Model model) {
		positionDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
		fundDAO = model.getFundDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "sellfund.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		if (customer == null) {
		    return "customerlogin.do";
		}
		int id1 = customer.getCustomerId();
		try {
			customer = customerDAO.read(id1);
		} catch (RollbackException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		session.setAttribute("customer", customer);
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		String sym = request.getParameter("symbol");
		
		/*
		 * Check the sell fund form.
		 */
        FundBean modifund;
        CustomerFundBean customerFund = new CustomerFundBean();
        try {
            modifund = fundDAO.getFundIdBySymbol(sym);
            if (modifund == null) {
                errors.add("Sorry,we don't find this fund.");
                return "customer_fund_detail.jsp";               
            }
            int cusid = customer.getCustomerId();
            int fundid = modifund.getFundId(); 
            PositionBean pb = positionDAO.read(cusid, fundid);
            customerFund.setFundSymbol(sym);
            customerFund.writeShares(pb.readShares());
        } catch (RollbackException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
            errors.add("Sorry,we don't find this fund.");
            return "customer_fund_detail.jsp";
        } catch (NumberFormatException e2) {
            errors.add("Sorry,share is not a number.");
            return "customer_fund_detail.jsp";
        }
        session.setAttribute("customerFund", customerFund);

		SellFundForm sellfundform = new SellFundForm(request);
		request.setAttribute("sellfundform", sellfundform);
		
		if (!sellfundform.isPresent()) {
			return "customer_sell_fund.jsp";
		}
		errors.addAll(sellfundform.getValidationErrors());
		if (errors.size() != 0) {
			return "customer_sell_fund.jsp";
		}

		/*
		 * Sell fund.
		 */
		int customerId = customer.getCustomerId();
		String symbol = sellfundform.getSymbol();
		double shares = sellfundform.getShares();
		FundBean fund = new FundBean();
		
		try {
			fund = fundDAO.getFundIdBySymbol(symbol);
			int fundId = fund.getFundId();
			List<PositionBean> positions = new ArrayList<PositionBean>();
			positions = positionDAO.getAllPositionByUserId(customerId);
			int flag = 0;
			for (int j = 0; j < positions.size(); j++) {
				int id = positions.get(j).getFundId();
				if (id == fundId) {
					flag = 1;
					double oldshares = positions.get(j).readShares();
					if (shares > oldshares) {
						errors.add("Sorry, you don't have enough shares!");
						return "customer_sell_fund.jsp";
					}
					sellfund(customerId, shares, positions.get(j));
					break;
				}
			}
			if (flag == 0) {
				errors.add("Sorry, you don't have this fund!");
				return "customer_view_acc.jsp";
			}

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			errors.add("Sorry, you don't have this kind of fund!");
			return "customer_sell_fund.jsp";
		}

		session.setAttribute("message", "Thank you! You have successfully sent your sell fund request!");
		return "success_customer.jsp";
	}

	public TransactionBean sellfund(int customerId, double shares, PositionBean position) throws RollbackException {
		if (shares < 0) {
			return null;
		}
		try {
			Transaction.begin();
			TransactionBean transaction = new TransactionBean();
			// what kind of transaction
			transaction.setTransactionType(TransactionType.SELL.toString());
			// who sell the fund
			transaction.setCustomerId(customerId);
			int fundId = position.getFundId();

			transaction.writeShares(shares);
			transaction.setFundId(fundId);
			transactionDAO.create(transaction);

			double oldshares = position.readShares();
			double newshares = oldshares - shares;
			position.writeShares(newshares);
			if (newshares > 0) {
				positionDAO.update(position);
			} else {
				positionDAO.delete(customerId, fundId);
			}
			Transaction.commit();

			return transaction;
		} finally {
			if (Transaction.isActive()) {
				Transaction.rollback();
			}
		}
	}
}
