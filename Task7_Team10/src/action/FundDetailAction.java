package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.ViewFundBean;
import dao.Model;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;

public class FundDetailAction extends Action {
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundpricehistoryDAO;
	private CustomerDAO customerDAO;

	public FundDetailAction(Model model) {
		fundDAO = model.getFundDAO();
		fundpricehistoryDAO = model.getFundPriceHistoryDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "funddetail.do";
	}

	public String perform(HttpServletRequest request) {
		System.out.println("succedd");
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();

		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		// authority judge
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

		List<FundPriceHistoryBean> pricelist = new ArrayList<FundPriceHistoryBean>();

		String symbol = request.getParameter("symbol");

		/*
		 * Check the fund form.
		 */
		FundBean fund = new FundBean();
		try {
			fund = fundDAO.getFundIdBySymbol(symbol);
			if (fund == null) {
				errors.add("Sorry,we don't find this fund.");
				return "customer_research_fund.jsp";
			}
		} catch (RollbackException e1) {
			// TODO Auto-generated catch block

			e1.printStackTrace();
		}
		session.setAttribute("modifyFund", fund);

		/*
		 * Show the fund detail list.
		 */
		List<ViewFundBean> viewfundlist = new ArrayList<ViewFundBean>();

		try {
			fund = fundDAO.getFundIdBySymbol(symbol);
			int fundId = fund.getFundId();
			pricelist = fundpricehistoryDAO.getAllPriceByFundId(fundId);
			ViewFundBean view = new ViewFundBean();
			view.setFundId(fundId);
			view.setFundName(fund.getFundName());
			view.setSymbol(symbol);
			if (pricelist.size() == 0) {
				viewfundlist.add(view);
			} else {
				for (int i = pricelist.size() - 1; i >= 0; i--) {
					Date date1 = pricelist.get(i).getPriceDate();
					ViewFundBean view1 = new ViewFundBean();
					view1.setPrice(pricelist.get(i).readPrice());
					view1.setFundId(fundId);
					view1.setFundName(fund.getFundName());
					view1.setSymbol(symbol);
					view1.setPriceDate(date1);
					viewfundlist.add(view1);
				}
			}

		} catch (

		RollbackException e)

		{
			// TODO Auto-generated catch block
			errors.add("Sorry,we don't find this fund's detail.");
			// e.printStackTrace();
		}

		session.setAttribute("details", viewfundlist);

		return "customer_fund_detail.jsp";
	}
}
