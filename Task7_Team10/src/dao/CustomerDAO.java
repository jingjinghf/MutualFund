package dao;
import java.util.ArrayList;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import bean.*;

/**
 * @author Yuheng Li
 * @version 1.0
 * @since Jan 14, 2016
 */
public class CustomerDAO extends GenericDAO<CustomerBean> {
    public CustomerDAO(ConnectionPool connectionPool) throws DAOException {
        super(CustomerBean.class, "Customer", connectionPool);
    }

    /**
     * Update customer(id)'s pwd to new Password.
     * 
     * @param customerId
     * @param newPassword
     */
    public void updatePassword(int customerId, String newPassword)
            throws RollbackException {
        try {
            Transaction.begin();
            CustomerBean customer = read(customerId);

            if (customer == null) {
                throw new RollbackException(
                        "Customer " + customerId + " no longer exists");
            }

            customer.createPassword(newPassword);
            update(customer);
            Transaction.commit();
        } finally {
            if (Transaction.isActive())
                Transaction.rollback();
        }
    }

    /**
     * Use customerId to get customer bean
     * 
     * @param customerId
     * @return
     */
    public CustomerBean getCustomer(int customerId) throws RollbackException {
        return read(customerId);
    }

    /**
     * Use customerName to get customer bean
     * 
     * @param customerId
     * @return
     */
    public CustomerBean getCustomer(String customerName)
            throws RollbackException {
        CustomerBean[] customer = match(
                MatchArg.equals("customerName", customerName));
        if (customer != null && customer.length > 0) {
            return customer[0];
        }
        return null;
    }

    /**
     * deprecated, please use setCashBalance + update(customerBean) to perform updateCash Balance
     * update customer(id)'s balance to new amount it write a double($123.45) to
     * database in long(12345 cent)
     * 
     * @param customerId
     * @param amount
     */
    @Deprecated
    public void updateBalance(CustomerBean customer, double amount)
            throws RollbackException {
            customer.setCashBalance((long) (amount * 100));
            update(customer);
    }

    /**
     * deprecated, please use setCashAvailable + update(customerBean) to perform update cash available
     * update customer's available to new available it write a double($123.45)
     * to database in long(12345 cent)
     * 
     * @param customerId
     * @param amount
     */
    @Deprecated
    public void updateAvailable(CustomerBean customer, double amount)
            throws RollbackException {
            customer.setCashAvailable((long) (amount * 100));
            update(customer);
    }
    
    public ArrayList<CustomerBean> getAllCustomer() throws RollbackException {
        CustomerBean[] customerlist = match(MatchArg.notEquals("customerId", -1));
        ArrayList<CustomerBean> list = new ArrayList<CustomerBean>();
        for (int i = 0; i < customerlist.length; i++) {
            list.add(customerlist[i]);
        }
        return list;
    }

}
