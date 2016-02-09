package formbean;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Form bean to get input and validate input from changepassword.jsp
 * 
 * @author jingjinghuangfu
 *
 */
public class ChangePasswordForm {
    private String originalPassword;
    private String newPassword;
    private String confirmNewPassword;
    private String action;

    public String getOriginalPassword() {
        return originalPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setOriginalPassword(String s) {
        originalPassword = sanitize(s.trim());
    }

    public void setConfirmNewPassword(String s) {
        confirmNewPassword = sanitize(s.trim());
    }

    public void setNewPassword(String s) {
        newPassword = sanitize(s.trim());
    }

    public ChangePasswordForm(HttpServletRequest request) {
        originalPassword = sanitize(request.getParameter("originalPassword"));
        newPassword = sanitize(request.getParameter("newPassword"));
        confirmNewPassword = sanitize(
                request.getParameter("confirmNewPassword"));
        action = sanitize(request.getParameter("button"));
    }

    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (originalPassword == null || originalPassword.length() == 0) {
            errors.add("Original Password is required");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (newPassword == null || newPassword.length() == 0) {
            errors.add("New Password is required");
        }
        if (!errors.isEmpty()) {
            return errors;
        }

        if (confirmNewPassword == null || confirmNewPassword.length() == 0) {
            errors.add("Confirm Password is required");
        }

        if (!errors.isEmpty()) {
            return errors;
        }

        if (!newPassword.equals(confirmNewPassword)) {
            errors.add("New password does not match with confirm password");
        }

        if (action != null && !action.equals("Submit")) {
            errors.add("Action not exists");
        }

        if (errors.size() > 0) {
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
