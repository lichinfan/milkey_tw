package com.kentec.milkbox.data;

import java.util.ArrayList;

import com.kentec.milkbox.grocery.data.Product;

public class OrderInfo {
	
	private String id;	
	private String createDate;	
	private String shippingMethod;
	private ShippingAddress shippAddr;	
	private String payment;
	private OrderInfoCreditCard creditCard;
	private BillingAddress billingAddr;		
	private ArrayList<Product> productList;
	
	private double subtotal;
	private double shippingPrice;
	private double total;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getShippingMethod() {
		return shippingMethod;
	}
	public void setShippingMethod(String shippingMethod) {
		this.shippingMethod = shippingMethod;
	}
	public ShippingAddress getShippAddr() {
		return shippAddr;
	}
	public void setShippAddr(ShippingAddress shippAddr) {
		this.shippAddr = shippAddr;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public OrderInfoCreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(OrderInfoCreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public BillingAddress getBillingAddr() {
		return billingAddr;
	}
	public void setBillingAddr(BillingAddress billingAddr) {
		this.billingAddr = billingAddr;
	}
	public ArrayList<Product> getProductList() {
		return productList;
	}
	public void setProductList(ArrayList<Product> productList) {
		this.productList = productList;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	public double getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
	
}
