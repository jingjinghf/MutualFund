package bean;

import java.util.*;

import org.genericdao.PrimaryKey;

import utilities.TransactionType;

@PrimaryKey("transactionId")
public class TransactionBean {
    private int transactionId;
    private int customerId;
    private int fundId;
    private Date executionDate;
    private long shares;
    private String transactionType;
    private long amount;

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public Date getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(Date executionDate) {
        this.executionDate = executionDate;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    /**
     * To get the Transaction Type, please use readType().
     * 
     * @return
     */
    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String type) {
        this.transactionType = type;
    }
    // public void setTransactionType(TransactionType type) {
    // String typeStr;
    // switch (type) {
    // case BUY:
    // typeStr = "buy";
    // break;
    // case SELL:
    // typeStr = "sell";
    // break;
    // case DEPOSIT:
    // typeStr = "deposit";
    // break;
    // case REQUEST:
    // typeStr = "request";
    // break;
    // case UNKNOWN:
    // typeStr = "request";
    // break;
    // default:
    // typeStr = "unkown";
    // break;
    // }
    // this.transactionType = typeStr;
    // }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    /**
     * translate the fund share in long in DB to double.
     * 
     * @return
     */
    public double readShares() {
        return (double) this.shares / 1000;

    }

    /**
     * translate the cash amount in long in DB to double.
     * 
     * @return
     */
    public double readAmount() {
        return (double) this.amount / 100;

    }

    public void writeShares(double share) {
        this.shares = (long) (share * 1000);
    }

    public void writeAmount(double amount) {
        this.amount = (long) (amount * 100);
    }

    /**
     * 
     */
    public TransactionBean() {
    }

    /**
     * return the transaction type.
     * 
     * @return TransactionType
     */
    public TransactionType readType() {
        switch (transactionType) {
        case "buy":
            return TransactionType.BUY;
        case "sell":
            return TransactionType.SELL;
        case "request":
            return TransactionType.REQUEST;
        case "deposit":
            return TransactionType.DEPOSIT;
        default:
            return TransactionType.UNKNOWN;
        }
    }
}
