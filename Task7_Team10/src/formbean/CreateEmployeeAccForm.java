package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean to get input and validate from createemployeeacc.jsp
 * 
 * @author jingjinghuangfu
 *
 */
public class CreateEmployeeAccForm {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String confirmPassword;
    private String action;

    public CreateEmployeeAccForm(HttpServletRequest request) {
        userName = sanitize(request.getParameter("userName"));
        firstName = sanitize(request.getParameter("firstName"));
        lastName = sanitize(request.getParameter("lastName"));
        password = sanitize(request.getParameter("password"));
        confirmPassword = sanitize(request.getParameter("confirmPassword"));
        action = sanitize(request.getParameter("button"));
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public String getComfirmPassword() {
        return confirmPassword;
    }

    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (userName == null || userName.length() == 0) {
            errors.add("User name is required");
            return errors;
        }

        if (firstName == null || firstName.length() == 0) {
            errors.add("First name is required");
            return errors;
        }
        
        if(!firstName.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid firstname");
            return errors;
        }
        
        if (lastName == null || lastName.length() == 0) {
            errors.add("Last name is required");
            return errors;
        }
        if(!lastName.matches("^[a-zA-Z]*$")) {
            errors.add("Invalid lastname");
            return errors;
        }

        if (password == null || password.length() == 0) {
            errors.add("Password is required");
            return errors;
        }

        if (confirmPassword == null || confirmPassword.length() == 0) {
            errors.add("You should confirm your password");
            return errors;
        }

        if (!confirmPassword.equals(password)) {
            errors.add("Password does not match the confirm password");
            return errors;
        }

        if (action != null && !action.equals("Create")) {
            errors.add("Action not exists");
        }

        return errors;
    }

    private String sanitize(String s) {
        if (s != null) {
            return s.replace("&", "&amp;").replace("<", "&lt;")
                    .replace(">", "&gt;").replace("\"", "&quot;")
                    .replace("\'", "&apos");
        } else
            return null;
    }
}
