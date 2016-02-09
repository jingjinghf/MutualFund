/**
 * @author Chuhui Zhang
 */
package dao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;

public class Model {
    private EmployeeDAO employeeDAO;
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

    public Model(ServletConfig config) throws ServletException {
    	String jdbcDriver = "com.mysql.jdbc.Driver";
		String jdbcURL    = "jdbc:mysql:///task7";

        try {
//            String jdbcDriver = config.getInitParameter("jdbcDriverName");
//            String jdbcURL    = config.getInitParameter("jdbcURL");
            ConnectionPool pool = new ConnectionPool(jdbcDriver, jdbcURL);
            employeeDAO = new EmployeeDAO(pool);
            customerDAO = new CustomerDAO(pool);
            transactionDAO = new TransactionDAO(pool);
            positionDAO = new PositionDAO(pool);
	    	fundDAO = new FundDAO(pool);
	    	fundPriceHistoryDAO = new FundPriceHistoryDAO(pool);
        } catch (DAOException e) {
            throw new ServletException(e);
        }
    }

    public EmployeeDAO getEmployeeDAO() {
        return employeeDAO;
    }

    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    public TransactionDAO getTransactionDAO() {
        return transactionDAO;
    }
    
    public PositionDAO getPositionDAO() { 
    	return positionDAO;       
    }
    
	public FundDAO getFundDAO() { 
		return fundDAO;           
	}
	
	public FundPriceHistoryDAO getFundPriceHistoryDAO() { 
		return fundPriceHistoryDAO;
	}
}
