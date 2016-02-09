package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import dao.CustomerDAO;
import dao.EmployeeDAO;
import dao.Model;
import formbean.ChangePasswordForm;

/**
 * change password action to allow user change password.
 * 
 * @author jingjinghuangfu
 *
 */
public class EmployeeChangePasswordAction extends Action {
    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    public EmployeeChangePasswordAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() {
        return "employeechangepassword.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            // get current user from session
            EmployeeBean employee = (EmployeeBean) request.getSession()
                    .getAttribute("employee");

            // if not login, direct the user to login page
            if (employee == null) {
                return "employeelogin.do";
            }

            // Load the form parameters into a form bean
            ChangePasswordForm form = new ChangePasswordForm(request);

            // if login user is an employee
            if (employee != null) {
                // presented (we assume for the first time).
                if (!form.isPresent()) {
                    return "employee_change_pwd.jsp";
                }
                // Check for any validation errors
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "employee_change_pwd.jsp";
                }

                // check old password
                synchronized(errors) {
                    if (!employee.checkPassword(form.getOriginalPassword())) {
                        errors.add("Old password wrong.");
                        return "employee_change_pwd.jsp";
                    }

                    // Change password for employee
                    employeeDAO.updatePassword(employee.getUserName(),
                            form.getNewPassword());
                    request.setAttribute("message",
                            "Password changed for " + employee.getUserName());
                }
                
            }
            return "success_employee.jsp";
            
        } catch (RollbackException e) {
            errors.add(e.toString());
            return "error_list.jsp";
        }
    }
}
