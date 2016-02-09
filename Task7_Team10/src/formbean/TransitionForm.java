package formbean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class TransitionForm {

	private String action;
	private Date date;
	private String dates;
	List<String> errors = new ArrayList<String>();

	public TransitionForm(HttpServletRequest request) {
		dates = sanitize(request.getParameter("newdate"));
		action = sanitize(request.getParameter("button"));
		if (dates != null && dates.length() != 0) {
			try {
				DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
				date = df.parse(dates);

				int month = Integer.parseInt(dates.substring(0, 2));
				int day = Integer.parseInt(dates.substring(3, 5));
				int year = Integer.parseInt(dates.substring(6, 10));
				int flag = 0;
				if (month > 12 || month <= 0) {

					errors.add("Sorry, the format of the date is not right!");
					flag = 1;
				}
				
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, month);

				int daymax = calendar.getActualMaximum(Calendar.DATE);
				if (flag == 0) {
					if (day > daymax || day <= 0) {

						errors.add("Sorry, the format of the date is not right!");
					}
				}

			} catch (Exception e) {
				errors.add("Sorry, the format of the date is not right!");
			}
		}
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date s) {
		date = s;
	}

	public boolean isPresent() {
		return !(action == null);
	}

	public List<String> getValidationErrors() {
		if (!errors.isEmpty()) {
			return errors;
		}
		if (dates == null || dates.length() == 0) {
			errors.add("Please input the date!");
		}
		if (!errors.isEmpty()) {
			return errors;
		}

		if (action != null && !action.equals("Submit")) {
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
