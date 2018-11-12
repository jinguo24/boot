package com.simple.ali.pay;

import java.math.BigDecimal;

public class PayRequest {
	
	public PayRequest(String out_trade_no, BigDecimal total_amount, String subject, String product_code) {
		super();
		this.out_trade_no = out_trade_no;
		this.total_amount = total_amount;
		this.subject = subject;
		this.product_code = product_code;
	}
	private String out_trade_no;
	private BigDecimal total_amount;
	private String subject;
	private String product_code;
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public BigDecimal getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(BigDecimal total_amount) {
		this.total_amount = total_amount;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
}
