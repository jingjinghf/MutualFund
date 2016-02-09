/**
 * @author Chuhui Zhang
 * Date: Jan 19, 2016
 */
package bean;

import java.util.Date;
import org.genericdao.PrimaryKey;

@PrimaryKey("fundId,priceDate")
public class FundPriceHistoryBean {
	private int fundId;
	private Date priceDate;
	private long price;
	
	public int getFundId() {
		return fundId;
	}
	
	public Date getPriceDate() {
		return priceDate;
	}
	
	public long getPrice() {
		return price;
	}
	
	public void setFundId(int s) {
		this.fundId = s;
	}
	
	public void setPriceDate(Date s) {
		this.priceDate = s;
	}
	
	public void setPrice(long s) {
		this.price = s;
	}
	
	public void writePrice(double s) {
		this.price = (long) (s * 100);
	}
	
	public double readPrice() {
		return (double) (price) / 100;
	}
}
