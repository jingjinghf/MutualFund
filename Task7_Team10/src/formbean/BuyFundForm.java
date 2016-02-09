package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class BuyFundForm {

	private String symbol;
	private String action;
	private double amount;
	private String amounts;
	private double max = 10000000;
	List<String> errors = new ArrayList<String>();

	public BuyFundForm(HttpServletRequest request) {

		symbol = sanitize(request.getParameter("symbol"));
		action = sanitize(request.getParameter("buybutton"));
		amounts = sanitize(request.getParameter("amount"));
		if (amounts != null && amounts.length() != 0) {
			try {
				amount = Double.parseDouble(amounts);
			} catch (Exception e) {
				errors.add("Please input the number!");
			}
		}
	}

	public Double getAmount() {
		return amount;
	}
	public String getAmounts() {
		return amounts;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setAmount(Double s) {
		amount = s;
	}

	public void setSymbol(String s) {
		symbol = s.trim();
	}

	public boolean isPresent() {
		return !(action == null);
	}

	public List<String> getValidationErrors() {
		if (amounts == null || amounts.length() == 0) {
			errors.add("Please input the amount!");
		}
		if (!errors.isEmpty()) {
			return errors;
		}
		if (amount <= 0) {
			errors.add("Please input positive number!");
		}
		if (!errors.isEmpty()) {
			return errors;
		}
		if (amount > max) {
			errors.add("Sorry, amount exceed trading limit, please contact XXX@cmu.edu for assistance.");
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
