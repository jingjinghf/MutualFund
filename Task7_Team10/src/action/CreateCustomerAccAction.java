package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import dao.CustomerDAO;
import dao.Model;
import formbean.CreateCustomerAccForm;

/**
 * Create customer account action by employee
 * @author jingjinghuangfu
 *
 */
public class CreateCustomerAccAction extends Action{

    private CustomerDAO customerDAO;
    
    public CreateCustomerAccAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    public String getName() { return "createcustomeracc.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        // check whether the user is a loged-in employee
        EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
        if (employee == null) {
            return "employeelogin.do";
        }
        
        try {
            CreateCustomerAccForm form = new CreateCustomerAccForm(request);
            request.setAttribute("form", form);
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "employee_create_customer_acc.jsp";
            }
            
            // validate input errors
            errors.addAll(form.getValidationErrors());
            
            if (errors.size() != 0) {
                return "employee_create_customer_acc.jsp";
            }
            
            // check if the customer exists
            synchronized (errors) {
            if (customerDAO.getCustomer(form.getUserName()) != null) {
                errors.add("User name already exists");
                return "employee_create_customer_acc.jsp";
            }
            }

            // Create the customer bean
            CustomerBean newcustomer = new CustomerBean();
            newcustomer.setCustomerName(form.getUserName());
            newcustomer.setFirstName(form.getFirstName());
            newcustomer.setLastName(form.getLastName());
            newcustomer.setAddrLine1(form.getAddrLine1());
            newcustomer.setAddrLine2(form.getAddrLine2());
            newcustomer.setState(form.getState());
            newcustomer.setCity(form.getCity());
            newcustomer.setZip(form.getZip());
            newcustomer.createPassword(form.getPassword());
            
            customerDAO.create(newcustomer);
            
            request.setAttribute("message", "Create new customer account for " + newcustomer.getCustomerName());

            return "success_employee.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "employee_create_customer_acc.jsp";
        }
    }

}
