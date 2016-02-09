package controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import action.Action;
import action.BuyFundAction;
import action.EmployeeChangePasswordAction;
import action.CreateCustomerAccAction;
import action.CreateEmployeeAccAction;
import action.CreateFundAction;
import action.CustomerChangePasswordAction;
import action.CustomerLoginAction;
import action.CustomerViewAccAction;
import action.CustomerViewTransactionHistoryAction;
import action.DepositCheckAction;
import action.EmployeeLoginAction;
import action.FundDetailAction;
import action.GetCustomerFromIdAction;
import action.JudgeAction;
import action.ListAllCustomerAction;
import action.ListAllEmployeeAction;
import action.LogoutAction;
import action.RequestCheckAction;
import action.ResearchFundAction;
import action.ResetPasswordAction;
import action.SellFundAction;
import action.TransitionAction;
import action.EmployeeViewCustomerAccAction;
import action.EmployeeViewTransactionHistoryAction;
import bean.CustomerBean;
import bean.EmployeeBean;
import bean.FundBean;
import bean.FundPriceHistoryBean;
import dao.Model;

/**
 * This is the controller for the other actions.
 **/
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Date lastTradingDay;
    
    public void init() throws ServletException {
        //initial all actions here
        Model model = new Model(getServletConfig());
        
        Action.add(new EmployeeLoginAction(model));
        Action.add(new CustomerLoginAction(model));
        Action.add(new LogoutAction(model));
        Action.add(new RequestCheckAction(model));
        Action.add(new ResearchFundAction(model));
        Action.add(new SellFundAction(model));
        Action.add(new EmployeeChangePasswordAction(model));
        Action.add(new CustomerChangePasswordAction(model));
        Action.add(new CreateEmployeeAccAction(model));
        Action.add(new CreateCustomerAccAction(model));
        Action.add(new ListAllCustomerAction(model));
        Action.add(new EmployeeViewCustomerAccAction(model));
        Action.add(new CustomerViewAccAction(model));
        Action.add(new ResetPasswordAction(model));
        Action.add(new GetCustomerFromIdAction(model));
        Action.add(new DepositCheckAction(model));
        Action.add(new CreateFundAction(model));
        Action.add(new FundDetailAction(model));
        Action.add(new BuyFundAction(model));
        Action.add(new TransitionAction(model));
        Action.add(new EmployeeViewTransactionHistoryAction(model));
        Action.add(new CustomerViewTransactionHistoryAction(model));
        Action.add(new JudgeAction(model));
        Action.add(new ListAllEmployeeAction(model));


        
        //create default admin account
        //create a default fund and price
        try {
            EmployeeBean admin = new EmployeeBean();
            admin.setFirstName("Jeff");
            admin.setLastName("Eppinger");
            admin.setUserName("jeffe");
            admin.createPassword("admin");
            
            model.getEmployeeDAO().create(admin);
            
            FundBean fund = new FundBean();
            fund.setFundName("CMU-ebusiness");
            fund.setSymbol("EBIZ");
            model.getFundDAO().create(fund);
            FundPriceHistoryBean fph = new FundPriceHistoryBean();
            fph.setFundId(1);
            fph.writePrice(10);
            String str = "2016-01-01";
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fd = df.parse(str);
            fph.setPriceDate(fd);
            model.getFundPriceHistoryDAO().create(fph);
            
        } catch (RollbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }        
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nextPage = performTheAction(request);
        System.out.println(nextPage);
        sendToNextPage(nextPage, request, response);
    }

    /*
     * Extracts the requested action and (depending on whether the user is
     * logged in) perform it (or make the user login).
     * 
     * @param request
     * 
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        CustomerBean customer = (CustomerBean) session.getAttribute("customer");
        EmployeeBean employee = (EmployeeBean) session.getAttribute("employee");
        String judge = (String) request.getAttribute("judge");
        String action = getActionName(servletPath);

        System.out.println(action);
        System.out.println("ababab");
        
        if (action.equals("employeelogin.do") || action.equals("customerlogin.do")) {
            return Action.perform(action, request);
        }
        
        
//        if (employee == null && judge != null && judge.equals("employee")) {
//            return Action.perform("employeelogin.do", request);
//        }
//        if (customer == null && judge != null && judge.equals("customer")) {
//            return Action.perform("customerlogin.do", request);
//        }
        

//        if (employee == null && customer == null) {
//            // If the user hasn't logged in, so login is the only option
//            System.out.println("aaaaaaa");
//            //return Action.perform("employeelogin.do", request);
//            return "login.jsp";
//        }

        // Let the logged in user run his chosen action
        return Action.perform(action, request);        
    }

    /*
     * If nextPage is null, send back 404 If nextPage ends with ".do", redirect
     * to this page. If nextPage ends with ".jsp", dispatch (forward) to the
     * page (the view) This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND,
                    request.getServletPath());
            return;
        }

        if (nextPage.endsWith(".do")) {
            response.sendRedirect(nextPage);
            return;
        }

        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/"
                    + nextPage);
            d.forward(request, response);
            return;
        }

        throw new ServletException(Controller.class.getName()
                + ".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

    /*
     * Returns the path component after the last slash removing any "extension"
     * if present.
     */
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}

