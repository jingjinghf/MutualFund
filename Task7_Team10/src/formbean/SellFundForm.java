package formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class SellFundForm {
	private String symbol;
	private String action;
	private double shares;
	private String share;
	double maxshares = 100000;
	List<String> errors = new ArrayList<String>();

	public SellFundForm(HttpServletRequest request) {
		symbol = sanitize(request.getParameter("symbol"));

		action = sanitize(request.getParameter("sellbutton"));
		share = sanitize(request.getParameter("shares"));
		if (share != null && share.length() != 0) {
			try {
				shares = Double.parseDouble(share);
			} catch (Exception e) {
				errors.add("Please input the number!");
			}
		}

	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String s) {
		symbol = s;
	}

	public double getShares() {
		return shares;
	}
	
	public String getShare() {
		return share;
	}

	public void setShares(Double s) {
		shares = s;
	}
	
	public void setShare(String s) {
		share = s;
	}

	public boolean isPresent() {
		return !(action == null);
	}

	public List<String> getValidationErrors() {
		if (!errors.isEmpty()) {
			return errors;
		}
		if (share == null || share.length() == 0) {
			errors.add("Please input the shares!");
		}
		if (symbol.length() > 5) {
			errors.add("Sorry, the length of the symbol name must be less than 5 letters!");
		}
		if (!errors.isEmpty()) {
			return errors;
		}

		String REGEX = "^[A-Za-z]+$";
		boolean result = Pattern.compile(REGEX).matcher(symbol).find();
		if (!result) {
			errors.add("Sorry, the symbol name must be all letters!");
		}

		if (shares <= 0) {
			errors.add("Please input positive number!");
		}
		if (!errors.isEmpty()) {
			return errors;
		}
		if (shares > maxshares) {
			errors.add("Sorry, shares exceed trading limit, please contact XXX@cmu.edu for assistance.");
		}
		if (!errors.isEmpty()) {
			return errors;
		}

		if (action != null && !action.equals("Place Order")) {
			errors.add("Action not exists");
		}

		return errors;
	}

	private String sanitize(String s) {
		if (s != null) {
			return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;")
					.replace("\'", "&apos");
		} else
			return null;
	}
}
