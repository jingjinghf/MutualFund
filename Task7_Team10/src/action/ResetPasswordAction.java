package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import dao.CustomerDAO;
import dao.Model;
import formbean.ResetPasswordForm;

/**
 * change password action to allow user change password.
 * @author jingjinghuangfu
 *
 */
public class ResetPasswordAction extends Action {
    private CustomerDAO customerDAO;

    public ResetPasswordAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "resetpassword.do"; }
    
    public String perform(HttpServletRequest request)  {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        //get current user from session
        EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
        
        //if not login, direct the user to login page
        if (employee == null) {
            return "employeelogin.do";
        }
        
        ResetPasswordForm form = new ResetPasswordForm(request);
        if (!form.isPresent()) {
            System.out.println("reset form to show");
            return "employee_reset_customer_pwd.jsp";
        }
        
        // Load the form parameters into a form bean
        
        errors.addAll(form.getValidationErrors());        
        if (errors.size() != 0) {
            System.out.println("has error");
            return "employee_reset_customer_pwd.jsp";
        }

        
        try {
            
            //check if there is customer parameter
                int customerId = Integer.parseInt(form.getCustomerId());
                CustomerBean customer = customerDAO.read(customerId);
                if (customer == null) {
                    errors.add("There is no customer with id=" + customerId);
                    return "customerlist.do";
                } else {
                    if (!customer.getHashedPassword().equals(form.getDigest())) {
                        errors.add("Current customer password has been modified!Please reopen this page!");
                        return "employee_reset_customer_pwd.jsp";
                    }
                }
                System.out.println(customer.getCustomerName());
                customerDAO.updatePassword(customer.getCustomerId(), form.getNewPassword());
                request.setAttribute("message", "Password successfully updata for"+customer.getCustomerName());
                          
            } catch(NumberFormatException e3) {
                errors.add("Illegel customerId");
                return "customerlist.jap";                
            }catch (RollbackException e2) {
                return "error.jsp";
            }     
        
            return "success_employee.jsp";
                  
                
    }
}
