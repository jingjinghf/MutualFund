package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

import bean.CustomerBean;
/**
 * Form bean to get and validate input from login.jsp
 * @author jingjinghuangfu
 *
 */
public class ResetPasswordForm {
    private String customerId;
    private String newPassword;
    private String confirmNewPassword;
    private String action;
    private String digest;
    
    public ResetPasswordForm(HttpServletRequest request) {
        customerId = sanitize(request.getParameter("id"));
        newPassword = sanitize(request.getParameter("newPassword"));
        confirmNewPassword = sanitize(request.getParameter("confirmNewPassword"));
        action = sanitize(request.getParameter("resetbutton"));
        CustomerBean customer = (CustomerBean)request.getSession().getAttribute("modifyCustomer");
        if(customer != null) {
            digest = customer.getHashedPassword();
        }
    }
    public String getDigest(){return digest;}
    public String getCustomerId() {return customerId;}
    public String getNewPassword()  { return newPassword; }
    public String getConfirmNewPassword()  { return confirmNewPassword; }
    public String getAction() {return action;}
    
    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (newPassword == null || newPassword.length() == 0) {
            errors.add("New password is required");
        }
        
        if (confirmNewPassword == null || confirmNewPassword.length() == 0) {
            errors.add("Please confirm new password is required");
        }
        
        if (newPassword != null && confirmNewPassword != null && !newPassword.equals(confirmNewPassword)) {
            errors.add("Confirm password and new password do not match");
        }
        
        if (action != null && !action.equals("Reset")) {
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
