package dao;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;


import bean.*;

/**
 * @author Yuheng Li
 * @version 1.0
 * @since Jan 14, 2016
 */
public class TransactionDAO extends GenericDAO<TransactionBean> {


    /**
     * Create transactionDAO using connection pool
     * 
     * @param connectionPool
     * @throws DAOException
     */
    public TransactionDAO(ConnectionPool connectionPool)
                    throws DAOException {
        super(TransactionBean.class, "Transaction", connectionPool);
    }

    /**
     * return a descending sorted list(by date) of user's transaction bean.
     * 
     * @param customerId
     * @return
     * @throws RollbackException
     */
    public List<TransactionBean> getAllTransactionsByCustomerId(int customerId)
            throws RollbackException {
        List<TransactionBean> list = Arrays
                .asList(match(MatchArg.equals("customerId", customerId)));
        // list.sort((b,a)->{
        // return a.getExecutionDate().compareTo(b.getExecutionDate());
        // });
        return list;
    }
    
    /**
     * get the latest commited transaction.
     * @return
     */
    public TransactionBean getLastCommitedTransaction() {
        try {
            TransactionBean[] array = match(MatchArg.notEquals("executionDate", null));
            return array[array.length - 1];
        } catch (RollbackException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * return the last transaction of the customer, suppose all 4 type of
     * transactions are trading.
     * 
     * @param customerId
     * @return
     */
    public TransactionBean getLastTransactionByUserId(int customerId) {
        TransactionBean[] list;
        try {
            list = match(MatchArg.equals("customerId", customerId));
            return list[list.length - 1];
        } catch (RollbackException e) {
            e.printStackTrace();
            return null;
        }
        
    }

    /**
     * get all pending transactions.
     * 
     * @return
     * @throws RollbackException
     */
    public List<TransactionBean> getAllPendingTransactions()
            throws RollbackException {
        TransactionBean[] list = match(MatchArg.equals("executionDate", null));
        return Arrays.asList(list);
    }
    
    public Date getLastTransactionDate() throws RollbackException {
    	TransactionBean[] tb = match(MatchArg.max("executionDate"));
    	if (tb == null || tb.length == 0) {
    		return null;
    	}
    	Date date = tb[tb.length - 1].getExecutionDate();
    	return date;
    }
}
