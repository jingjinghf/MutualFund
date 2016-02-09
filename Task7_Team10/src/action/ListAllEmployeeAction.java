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

public class ListAllEmployeeAction extends Action {
    private EmployeeDAO employeeDAO;

    public ListAllEmployeeAction(Model model) {
    	employeeDAO = model.getEmployeeDAO();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "listallemployee.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);  
     // authority judge
        if((EmployeeBean)request.getSession().getAttribute("employee") == null) {
            return "employeelogin.do";
        }
        try {
            List<EmployeeBean> employeeList = employeeDAO.getAllEmployee();
            request.setAttribute("employeeList", employeeList);
            System.out.println(employeeList);
            
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error_list.jsp";
        }
        
        return "employeelist.jsp";
    }
}
