package com.kentec.milkbox.grocery.data;

public class Product extends GroceryCellView {
	
	private String description;
	
	private double price;
	private double qty;
	private double subtotal;
	private String sku;
	private boolean isCoupon;
		
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
		this.subtotal = qty * price;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public boolean isCoupon() {
		return isCoupon;
	}
	public void setCoupon(boolean isCoupon) {
		this.isCoupon = isCoupon;
	}		
	
}
