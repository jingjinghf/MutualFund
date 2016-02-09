/**
 * @author Chuhui Zhang
 * Date: Jan 19, 2016
 */

package bean;

import org.genericdao.PrimaryKey;

@PrimaryKey("customerId,fundId")
public class PositionBean {
	private int customerId;
	private int fundId;
	private long shares;
	
	public int getCustomerId() {
		return customerId;
	}
	
	public int getFundId() {
		return fundId;
	}
	
	public long getShares() {
		return shares;
	}
	
	public void setCustomerId(int s) {
		this.customerId = s;
	}
	
	public void setFundId(int s) {
		this.fundId = s;
	}
	
	public void setShares(long s) {
		this.shares = s;
	}
	
	public void writeShares(double s) {
		this.shares = (long) (s * 1000);
	}
	
	public double readShares() {
		return (double) (shares) / 1000;
	}
}
