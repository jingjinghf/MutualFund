/**
 * @author Chuhui Zhang
 * Date: Jan 19, 2016
 */
package dao;

import java.util.ArrayList;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import bean.PositionBean;

public class PositionDAO extends GenericDAO<PositionBean> {
	public PositionDAO(ConnectionPool connectionPool)
			throws DAOException {
		super(PositionBean.class, "position", connectionPool);
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<PositionBean> getAllPositionByUserId(int customerId) throws RollbackException {
		PositionBean[] pbArray = match(MatchArg.equals("customerId", customerId));
		if (pbArray == null) {
			return null;
		}
		ArrayList<PositionBean> list = new ArrayList<PositionBean>();
		for (int i = 0; i < pbArray.length; i++) {
			list.add(pbArray[i]);
		}
		return list;
	}
	
	// This is to delete the whole entry, meaning the customer sell all of the share of that specific fund
	public void deleteShares(PositionBean position) throws RollbackException {
		try {
			Transaction.begin();
			//PositionBean position = read(transaction.getCustomerId(), transaction.getFundId());
			delete(position.getCustomerId(), position.getFundId());
			Transaction.commit();
		} finally {
			if (Transaction.isActive()) 
				Transaction.rollback();
		}
	}
}

