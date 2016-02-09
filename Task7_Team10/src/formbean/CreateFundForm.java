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
public class CreateFundForm {
    private String fundName;
    private String symbol;
    private String action;

    public CreateFundForm(HttpServletRequest request) {
        fundName = sanitize(request.getParameter("fundName"));
        symbol = sanitize(request.getParameter("symbol"));
        action = sanitize(request.getParameter("createfundbutton"));
    }

    public String getFundName() {
        return fundName;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAction() {
        return action;
    }

    public boolean isPresent() {
        return !(action == null);
    }

    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<String>();

        if (fundName == null || fundName.length() == 0) {
            errors.add("Fund name is required");
        }

        if (symbol == null || symbol.length() == 0) {
            errors.add("Fund symbol is required");
        }
        if (!errors.isEmpty()) {
            return errors;
        }
        if (!fundName.matches("^[a-zA-Z ]+$")) {
            errors.add("Sorry, the fund name must be all letters!");
        }

        if (!symbol.matches("^[a-zA-Z]{1,5}$")) {
            errors.add(
                    "Sorry, symbol should be a short one to five character identifier!");
        }
        
        if (!errors.isEmpty()) {
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
