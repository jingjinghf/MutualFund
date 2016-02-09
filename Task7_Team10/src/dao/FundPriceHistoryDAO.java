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
import bean.FundPriceHistoryBean;

public class FundPriceHistoryDAO extends GenericDAO<FundPriceHistoryBean> {
	public FundPriceHistoryDAO(ConnectionPool connectionPool)
			throws DAOException {
		super(FundPriceHistoryBean.class, "Fund_Price_History", connectionPool);
	}
	
	public ArrayList<FundPriceHistoryBean> getAllPriceByFundId(int fundId) throws RollbackException {
		FundPriceHistoryBean[] fph = match(MatchArg.equals("fundId", fundId));
		if (fph == null) {
			return null;
		}
		ArrayList<FundPriceHistoryBean> list = new ArrayList<FundPriceHistoryBean>();
		for (int i = 0; i < fph.length; i++) {
			list.add(fph[i]);
		}
		
		return list;
	}
	
	public FundPriceHistoryBean getLastTradingDate() throws RollbackException {
		FundPriceHistoryBean[] fpH = match(MatchArg.max("priceDate"));
		if (fpH == null || fpH.length == 0) {
			return null;
		}
		return fpH[fpH.length - 1];
	}
	
}
