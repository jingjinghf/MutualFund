package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.EmployeeBean;
import dao.EmployeeDAO;
import dao.Model;
import formbean.LoginForm;

/**
 * Customer login function
 * @author jingjinghuangfu
 *
 */
public class EmployeeLoginAction extends Action {
    
    private EmployeeDAO employeeDAO;

    public EmployeeLoginAction(Model model) {
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() { return "employeelogin.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {
            LoginForm form = new LoginForm(request);

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "login.jsp";
            }
            

            // Look up the user by emailAddress
            //EmployeeBean employee = employeeDAO.getEmployee(form.getUserName());
            EmployeeBean employee = employeeDAO.read(form.getUserName());

            if (employee == null) {               
                errors.add("User name not found");
                return "login.jsp";
            }
            
            // Check the password            
            if (!employee.checkPassword(form.getPassword())) {
                errors.add("Incorrect user name or password");
                return "login.jsp";
            }
    
            // Attach (this copy of) the user bean to the session
            HttpSession session = request.getSession();
            session.setAttribute("employee", employee);

            request.setAttribute("message", "Employee successfully login!");          
            return "success_employee.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }
    }

}