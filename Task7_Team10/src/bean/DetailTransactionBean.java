/**
 * @author Chuhui Zhang
 * Date: Jan 25, 2016
 */

package bean;

import java.util.Date;

import utilities.TransactionType;

public class DetailTransactionBean {
	private int transactionId;
	private Date executionDate;
	private String transactionType;
	private String fundName;
	private double shares;
	private double amount;
	private double price_perShare;
	
	
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public Date getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public double getShares() {
		return shares;
	}
	public void setShares(double shares) {
		this.shares = shares;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getPrice_perShare() {
		return price_perShare;
	}
	public void setPrice_perShare(double price_perShare) {
		this.price_perShare = price_perShare;
	}
	
}
