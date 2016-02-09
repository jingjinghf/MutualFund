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
public class CustomerChangePasswordAction extends Action {
    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    public CustomerChangePasswordAction(Model model) {
        customerDAO = model.getCustomerDAO();
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() {
        return "customerchangepassword.do";
    }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        CustomerBean customer1 = (CustomerBean) request.getSession().getAttribute("customer");
        if (customer1 == null) {
            return "customerlogin.do";
        }

        try {
            // get current user from session
            CustomerBean customer = (CustomerBean) request.getSession()
                    .getAttribute("customer");
            
            // if not login, direct the user to login page
            if (customer == null) {
                return "index.jsp";
            }
            
            // Load the form parameters into a form bean
            ChangePasswordForm form = new ChangePasswordForm(request);

            if (customer != null) {
                int id = customer.getCustomerId();
                try {
                    customer = customerDAO.read(id);
                } catch (RollbackException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                request.getSession().setAttribute("customer", customer);
                if (!form.isPresent()) {
                    return "customer_change_pwd.jsp";
                }

                // Check for any validation errors
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "customer_change_pwd.jsp";
                }

                // check old password
                synchronized (errors) {
                    if (!customer.checkPassword(form.getOriginalPassword())) {
                        errors.add("Old password wrong.");
                        return "customer_change_pwd.jsp";
                    }

                    // Change password for customer
                    customerDAO.updatePassword(customer.getCustomerId(),
                            form.getNewPassword());
                    request.setAttribute("message",
                            "Password changed for " + customer.getFirstName() + " "
                                    + customer.getLastName());
                }
                
            }
            return "success_customer.jsp";

        } catch (RollbackException e) {
            errors.add(e.toString());
            return "error_list.jsp";
        }
    }
}
