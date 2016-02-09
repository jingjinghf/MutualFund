package action;


import javax.servlet.http.HttpServletRequest;

import dao.Model;

public class JudgeAction extends Action {
    public JudgeAction(Model model) {       
    }

    public String getName() { return "judge.do"; }
    
    public String perform(HttpServletRequest request) {
        
        String judge = (String) request.getParameter("for");
        System.out.println(judge);
        if (judge.equals("customer")) {
            request.setAttribute("judge", "customer");
        }
        else if (judge.equals("employee")) {
            request.setAttribute("judge", "employee");
        }
        return "login.jsp";
    }
        

}
