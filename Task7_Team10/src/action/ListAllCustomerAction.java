package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import bean.CustomerBean;
import bean.EmployeeBean;
import dao.CustomerDAO;
import dao.Model;

public class ListAllCustomerAction extends Action{
    private CustomerDAO customerDAO;

    public ListAllCustomerAction(Model model) {
        customerDAO = model.getCustomerDAO();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return "listallcustomer.do";
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
            List<CustomerBean> customerList = customerDAO.getAllCustomer();
            request.setAttribute("customerList", customerList);
            System.out.println(customerList);
            
        } catch (RollbackException e) {
            errors.add(e.getMessage());
            return "error_list.jsp";
        }
        
        return "customerlist.jsp";
    }


}
