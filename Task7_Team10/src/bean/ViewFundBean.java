package bean;

import java.text.DecimalFormat;
import java.util.Date;

public class ViewFundBean {
	private int fundId;
	private String fundName;
	private String symbol;
	private double price;
	private double change;
	private Date priceDate = null;
	DecimalFormat df = new DecimalFormat("######0.00");

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date s) {
		this.priceDate = s;
	}

	public double getPrice() {
		return price;
	}

	public double getChange() {
		return change;
	}

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

	public void setPrice(double s) {
		 this.price = s;
	}

	public void setChange(double s) {
		 this.change = s;
	}

	public void setFundName(String s) {
		this.fundName = s;
	}

	public void setSymbol(String s) {
		this.symbol = s;
	}
}
