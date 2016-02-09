package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class DepositCheckForm {

	private String inputAmount;
	private String inputConfirm;
	private String inputId;
	private int customerId;
	private double amount;
	private String confirmAmount;
	private double confirmamount;
	private String action;
	private double max = 10000000;
	List<String> errors = new ArrayList<String>();

	public String getConfirmAmount() {
	    return confirmAmount;
	}
	public void setConfirmAmount(double confirmamount) {
        this.confirmAmount = String.valueOf(confirmamount);
    }
	public DepositCheckForm(HttpServletRequest request) {
		inputAmount = request.getParameter("amount");
		inputConfirm = request.getParameter("confirmAmount");
		inputId = request.getParameter("id");
		if (inputId != null) {
			try {
				customerId = Integer.parseInt(sanitize(inputId));
			} catch (NumberFormatException e) {
				errors.add("customer id is not integer");
			}
		}

		if (inputAmount != null && inputAmount.length() != 0) {
			try {
				amount = Double.parseDouble(sanitize(inputAmount));
			} catch (NumberFormatException e) {
				errors.add("Amount is not a number");
			}
		}

		if (inputConfirm != null && inputConfirm.length() != 0) {
			try {
				confirmamount = Double.parseDouble(sanitize(inputConfirm));
			} catch (NumberFormatException e) {
				errors.add("Confirm amount is not a number");
			}
		}

		action = sanitize(request.getParameter("depositbutton"));
	}

	public double getAmount() {
		return amount;
	}
	
	public String getInputAmount() {
		return inputAmount;
	}
	
	public void setInputAmount(double amount) {
		this.inputAmount = String.valueOf(amount);
	}
	

	public boolean isPresent() {
		return !(action == null);
	}

	public List<String> getValidationErrors() {
		if (!errors.isEmpty()) {
			return errors;
		}

		if (inputAmount == null || inputAmount.length() == 0) {
			errors.add("Amount is required");
		}

		if (inputConfirm == null || inputConfirm.length() == 0) {
			errors.add("Please confirm the amount");
		}
		if (!errors.isEmpty()) {
			return errors;
		}

		if (inputConfirm != null && !inputConfirm.equals(inputAmount)) {
			errors.add("amount not equal");
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

		if (action != null && !action.equals("Deposit")) {
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
