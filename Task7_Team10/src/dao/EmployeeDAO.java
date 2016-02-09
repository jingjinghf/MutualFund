/**
 * @author Chuhui Zhang
 * Date: Jan 18, 2016
 */

package dao;
import java.util.ArrayList;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.CustomerBean;
import bean.EmployeeBean;

public class EmployeeDAO extends GenericDAO<EmployeeBean> {

	public EmployeeDAO(ConnectionPool connectionPool)
			throws DAOException {
		super(EmployeeBean.class, "Employee", connectionPool);
		// TODO Auto-generated constructor stub
	}

	
	public void updatePassword(String userName, String password) throws RollbackException {
        try {
        	Transaction.begin();
			EmployeeBean employee = read(userName);
			
			if (employee == null) {
				throw new RollbackException("User "+userName+" no longer exists");
			}
			
			employee.createPassword(password);
			update(employee);
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) Transaction.rollback();
		}
	}
	
	public ArrayList<EmployeeBean> getAllEmployee() throws RollbackException {
        EmployeeBean[] employeelist = match(MatchArg.notEquals("userName", ""));
        ArrayList<EmployeeBean> list = new ArrayList<EmployeeBean>();
        for (int i = 0; i < employeelist.length; i++) {
        	list.add(employeelist[i]);
        }
        return list;
    }
}
