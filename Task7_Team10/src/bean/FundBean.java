/**
 * @author Chuhui Zhang
 * Date: Jan 19, 2016
 */
package bean;

import org.genericdao.PrimaryKey;

@PrimaryKey("fundId")
public class FundBean {
	private int fundId;
	private String fundName;
	private String symbol;
	
	public int getFundId() {
		return fundId;
	}
	
	public String getFundName() {
		return fundName;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setFundId(int s) {
		this.fundId = s;
	}
	
	public void setFundName(String s) {
		this.fundName = s;
	}
	
	public void setSymbol(String s) {
		this.symbol = s;
	}
}
