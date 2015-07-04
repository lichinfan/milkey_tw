package com.kentec.milkbox.homedine.data;

import java.util.ArrayList;

public class ShopCartData {

	private double subtotal;
	private String taxvat;
	private String quoteCurrencyCode;
	private String shippingDescription;
	private ArrayList<FoodItem> foodList;

	public double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public String getTaxvat() {
		return taxvat;
	}

	public void setTaxvat(String taxvat) {
		this.taxvat = taxvat;
	}

	public String getQuoteCurrencyCode() {
		return quoteCurrencyCode;
	}

	public void setQuoteCurrencyCode(String quoteCurrencyCode) {
		this.quoteCurrencyCode = quoteCurrencyCode;
	}

	public String getShippingDescription() {
		return shippingDescription;
	}

	public void setShippingDescription(String shippingDescription) {
		this.shippingDescription = shippingDescription;
	}

	public ArrayList<FoodItem> getFoodList() {
		return foodList;
	}

	public void setFoodList(ArrayList<FoodItem> foodList) {
		this.foodList = foodList;
	}
}
