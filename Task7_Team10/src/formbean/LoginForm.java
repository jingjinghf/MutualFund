package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
/**
 * Form bean to get and validate input from login.jsp
 * @author jingjinghuangfu
 *
 */
public class LoginForm {
    private String userName;
    private String password;
    private String action;
    
    public LoginForm(HttpServletRequest request) {
        userName = sanitize(request.getParameter("userName"));
        password = sanitize(request.getParameter("password"));
        action = sanitize(request.getParameter("button"));
    }
    
    public String getUserName()  { return userName; }
    public String getPassword()  { return password; }
    
    public void setUserName(String s) { userName = s.trim();  }
    public void setPassword(String s) { password = s.trim();  }
    
    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (userName == null || userName.length() == 0) {
            errors.add("User name is required");
        }
        
        if (password == null || password.length() == 0) {
            errors.add("Password is required");
        }
        
        if (action != null && !action.equals("Login")) {            
            errors.add("Action not exists");
        }
        
        return errors;
    }
    
    private String sanitize(String s) {
        if (s != null) {
            return s.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\"", "&quot;").replace("\'", "&apos");
        }
        else return null;        
    }
}
