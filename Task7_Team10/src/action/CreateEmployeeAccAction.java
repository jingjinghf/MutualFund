package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;

import bean.EmployeeBean;
import dao.EmployeeDAO;
import dao.Model;
import formbean.CreateEmployeeAccForm;

/**
 * employee use this action to create employee account.
 * @author jingjinghuangfu
 *
 */
public class CreateEmployeeAccAction extends Action {
    private EmployeeDAO employeeDAO;
    
    public CreateEmployeeAccAction(Model model) {
        employeeDAO = model.getEmployeeDAO();
    }

    public String getName() { return "createemployeeacc.do"; }

    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        // check whether the user is a loged-in employee
        EmployeeBean employee = (EmployeeBean) request.getSession().getAttribute("employee");
        if (employee == null) {
            return "employeelogin.do";
        }

        try {
            CreateEmployeeAccForm form = new CreateEmployeeAccForm(request);
            request.setAttribute("form", form);
            // presented (we assume for the first time).
            if (!form.isPresent()) {
                return "employee_create_employee_acc.jsp";
            }
            
            // validate input errors
            errors.addAll(form.getValidationErrors());
            
            if (errors.size() != 0) {
                return "employee_create_employee_acc.jsp";
            }
            
            // check if the employee exists
            synchronized (errors) {
                if (employeeDAO.read(form.getUserName()) != null) {
                    errors.add("User name already exists");
                    return "employee_create_employee_acc.jsp";
                }                
            }            

            // Create the user bean
            EmployeeBean newemployee = new EmployeeBean();
            newemployee.setUserName(form.getUserName());
            newemployee.setFirstName(form.getFirstName());
            newemployee.setLastName(form.getLastName());
            newemployee.createPassword(form.getPassword());
            
            employeeDAO.create(newemployee);
            
            request.setAttribute("message", "Create new account for " + newemployee.getUserName());

            return "success_employee.jsp";
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "employee_create_employee_acc.jsp";
        }
    }
}


