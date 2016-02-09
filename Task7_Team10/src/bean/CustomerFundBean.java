/**
 * @author Chuhui Zhang
 * Date: Jan 22, 2016
 */

package bean;

public class CustomerFundBean {
	private String fundName;
	private String fundSymbol;
	private long shares;
	private long amount;
	private double price;
	
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getFundSymbol() {
		return fundSymbol;
	}
	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}
	public long getShares() {
		return shares;
	}
	public void setShares(long shares) {
		this.shares = shares;
	}
	public void setPrice(double p) {
	    this.price = p;
	}
	public double getPrice() {
        return this.price;
    }
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public void writeShares(double shares) {
	    this.shares = (long) (shares * 1000);
	}
	public void writeAmount(double amount) {
	    this.amount = (long) (amount * 100);
	}
	public double readShares() {
		return (double) (shares) / 1000;
	}
	public double readAmount() {
	    return (double) (amount) / 100;
	}
	
}
