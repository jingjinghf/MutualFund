package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean used to get and validate input from createcustomeracc.jsp
 * 
 * @author jingjinghuangfu
 *
 */
public class CreateCustomerAccForm {

    private String userName;
    private String firstName;
    private String lastName;
    private String addrLine1;
    private String addrLine2;
    private String state;
    private String zip;
    private String city;
    private String password;
    private String confirmPassword;
    private String action;

    public CreateCustomerAccForm(HttpServletRequest request) {
        userName = sanitize(request.getParameter("userName"));
        firstName = sanitize(request.getParameter("firstName"));
        lastName = sanitize(request.getParameter("lastName"));
        addrLine1 = sanitize(request.getParameter("addrLine1"));
        addrLine2 = sanitize(request.getParameter("addrLine2"));
        state = sanitize(request.getParameter("state"));
        city = sanitize(request.getParameter("city"));
        zip = sanitize(request.getParameter("zip"));
        password = sanitize(request.getParameter("password"));
        confirmPassword = sanitize(request.getParameter("confirmPassword"));
        action = sanitize(request.getParameter("button"));
    }

    public String getUserName() {
        return userName;
    }
    
    public String getCity() {
        return city;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public String getAddrLine2() {
        return addrLine2;
    }

    public String getState() {
        return state;
    }

    public String getZip() {
        return zip;
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
        
        if (addrLine1 == null || addrLine1.length() == 0) {
            errors.add("Address is required");
            return errors;
        }
        if (state == null || state.length() == 0) {
            errors.add("State is required");
            return errors;
        }
        
        if (city == null || city.length() == 0) {
            errors.add("City is required");
            return errors;
        }
        
        if (zip == null || zip.length() == 0) {
            errors.add("Zip is required");
            return errors;
        }
        if(!zip.matches("^[0-9]{5}$")) {
            errors.add("Invalid zip code");
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
            return errors;
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
