package formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class SearchFundForm {

	private String symbol;
	private String action;;
	List<String> errors = new ArrayList<String>();

	public SearchFundForm(HttpServletRequest request) {

		symbol = sanitize(request.getParameter("symbol"));
		action = sanitize(request.getParameter("buybutton"));
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String s) {
		symbol = s.trim();
	}

	public boolean isPresent() {
		return !(action == null);
	}

	public List<String> getValidationErrors() {
		if (symbol == null || symbol.length() == 0) {
			errors.add("Please input the symbol of the fund!");
		}
		if (!errors.isEmpty()) {
			return errors;
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

		if (action != null && !action.equals("BuyFund")) {
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
