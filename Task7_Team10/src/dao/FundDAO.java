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


import bean.FundBean;

public class FundDAO extends GenericDAO<FundBean> {
	public FundDAO(ConnectionPool connectionPool) throws DAOException {
		super(FundBean.class, "Fund", connectionPool);
	}
	
	public ArrayList<FundBean> getAllFunds() throws RollbackException {
		FundBean[] fund = match(MatchArg.notEquals("fundId", -1));
		if (fund == null) {
			return null;
		}
		ArrayList<FundBean> list = new ArrayList<FundBean>();
		for (int i = 0; i < fund.length; i++) {
			list.add(fund[i]);
		}
		return list;
	}
	
	public FundBean getFundIdBySymbol(String symbol) throws RollbackException {
		FundBean[] fund = match(MatchArg.equals("symbol", symbol));
		if (fund == null || fund.length == 0) {
			return null;
		}
		else {
		    return fund[0];
		}
	}
}
