package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import bean.ViewFundBean;
import dao.Model;
import formbean.SearchFundForm;
import dao.CustomerDAO;
import dao.FundDAO;
import dao.FundPriceHistoryDAO;

public class ResearchFundAction extends Action {
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundpricehistoryDAO;
	private CustomerDAO customerDAO;

	public ResearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		fundpricehistoryDAO = model.getFundPriceHistoryDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "researchfund.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		CustomerBean customer = (CustomerBean) session.getAttribute("customer");
		List<String> errors = new ArrayList<String>();

		List<FundBean> fundlist = new ArrayList<FundBean>();

		List<ViewFundBean> viewfundlist = new ArrayList<ViewFundBean>();
		
		if (customer == null) {
		    return "customerlogin.do";
		}
		
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
	        }

		/*
		 * Show the fund list.
		 */
		try {
			fundlist = fundDAO.getAllFunds();
			for (int i = 0; i < fundlist.size(); i++) {
				ViewFundBean view = new ViewFundBean();
				int fundId = fundlist.get(i).getFundId();
				List<FundPriceHistoryBean> prices = new ArrayList<FundPriceHistoryBean>();
				prices = fundpricehistoryDAO.getAllPriceByFundId(fundId);

				if (prices.size() != 0) {
					int last = prices.size() - 1;
					double price = prices.get(last).readPrice();
					view.setPrice(price);

					if (last != 0) {
						double price1 = prices.get(last).readPrice();
						double price2 = prices.get(last - 1).readPrice();
						double change = (price1 - price2) / price2 * 100;
						view.setChange(change);
					} else {
						view.setChange(0);
					}

				}
				view.setFundId(fundId);
				String symbol = fundlist.get(i).getSymbol();
				view.setSymbol(symbol);
				String fundname = fundlist.get(i).getFundName();
				view.setFundName(fundname);
				viewfundlist.add(view);

			}

		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		session.setAttribute("viewfundlist", viewfundlist);

		/*
		 * Check the search form.
		 */
		SearchFundForm searchfundform = new SearchFundForm(request);
		if (!searchfundform.isPresent()) {
			return "customer_research_fund.jsp";
		}
		errors.addAll(searchfundform.getValidationErrors());
		if (errors.size() != 0) {
			return "customer_research_fund.jsp";
		}

		/*
		 * Show the search result.
		 */
		String symbol = searchfundform.getSymbol();
		FundBean fund = new FundBean();
		viewfundlist.removeAll(viewfundlist);
		try {

			ViewFundBean view = new ViewFundBean();

			fund = fundDAO.getFundIdBySymbol(symbol);
			int fundId = fund.getFundId();
			List<FundPriceHistoryBean> prices = new ArrayList<FundPriceHistoryBean>();

			prices = fundpricehistoryDAO.getAllPriceByFundId(fundId);
			if (prices.size() != 0) {
				int last = prices.size() - 1;
				double price1 = prices.get(last).readPrice();
				double change;
				if (last > 0) {
					double price2 = prices.get(last - 1).readPrice();
					change = (price1 - price2) / price2 * 100;
				} else {
					change = 0;
				}

				view.setChange(change);
				view.setPrice(price1);
			}

			view.setFundId(fundId);
			view.setSymbol(symbol);
			String fundname = fund.getFundName();
			view.setFundName(fundname);
			viewfundlist.add(view);

			session.setAttribute("viewfundlist", viewfundlist);

		} catch (RollbackException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
			errors.add("Sorry, we don't find your fund!");
		}

		return "customer_research_fund.jsp";
	}
}
