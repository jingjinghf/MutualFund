package action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import bean.EmployeeBean;
import bean.FundBean;
import dao.FundDAO;
import dao.Model;
import formbean.CreateFundForm;

public class CreateFundAction extends Action {
    private FundDAO fundDAO;

    public CreateFundAction(Model model) {
        fundDAO = model.getFundDAO();       
    }

    public String getName() {
        return "createfund.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        
        //judge if user is login employee
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");        
        if (employee == null) {
            return "employeelogin.do";
        }
        
        CreateFundForm form = new CreateFundForm(request);
        request.setAttribute("form", form);
        if (!form.isPresent()) {
            return "employee_create_fund.jsp";
        }
        errors.addAll(form.getValidationErrors());
		if (errors.size() != 0) {
			 return "employee_create_fund.jsp";
		}

        try {
            //check duplicate fund symbol
            synchronized (errors) {
                FundBean fundintable = fundDAO.getFundIdBySymbol(form.getSymbol());
                if (fundintable != null) {
                    errors.add("The fund symbol already exists");
                    return "employee_create_fund.jsp";
                }
            }
            
            
            FundBean fund = new FundBean();
            fund.setFundName(form.getFundName());
            fund.setSymbol(form.getSymbol());
            fundDAO.create(fund);
            
        } catch (RollbackException e2) {
            
            return "error.jsp";
        } finally {
            
        }
        
        session.setAttribute("message", "Thank you! You have successfully create the fund!");
        return "success_employee.jsp";
    }
}
