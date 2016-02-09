package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import dao.CustomerDAO;
import dao.Model;

/**
 * change password action to allow user change password.
 * @author jingjinghuangfu
 *
 */
public class GetCustomerFromIdAction extends Action {
    private CustomerDAO customerDAO;

    public GetCustomerFromIdAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "getcustomerfromid.do"; }
    
    public String perform(HttpServletRequest request)  {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);

        try {
            //get current user from session
            EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
            
            //if not login, direct the user to login page
            if (employee == null) {
                return "employeelogin.do";
            }
            //check if there is customer parameter
                int customerId = Integer.parseInt(request.getParameter("id"));
                CustomerBean customer = customerDAO.read(customerId);
                if (customer == null) {
                    errors.add("There is no customer with id=" + customerId);
                    System.out.println("no cus id");
                    return "customerlist.do";
                }
                request.setAttribute("customer", customer);
                System.out.println(customer.getCustomerName());
                return "resetpassword.do";
            
           
            } catch(NumberFormatException e3) {
                errors.add("Illegel customerId");
                return "customerlist.jap";                
            }catch (RollbackException e2) {
                return "error.jsp";
            }               
    }
}
