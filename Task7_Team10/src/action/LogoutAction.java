package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import dao.*;

/*
 * Logs out by setting the "user" session attribute to null.
 * (Actions don't be much simpler than this.)
 */
public class LogoutAction extends Action {
	//private CustomerDAO customerDAO;
    //ssssss
	public LogoutAction(Model model) {
		//customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "logout.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		//?????name of attribute
		session.setAttribute("employee", null);
		session.setAttribute("customer", null);
		return "login.jsp";
	}
}
