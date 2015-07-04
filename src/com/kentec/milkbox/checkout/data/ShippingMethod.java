package com.kentec.milkbox.checkout.data;

public class ShippingMethod {

	private String code;
	private String carrierTitle;
	private String methodTitle;
	private String price;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCarrierTitle() {
		return carrierTitle;
	}

	public void setCarrierTitle(String carrierTitle) {
		this.carrierTitle = carrierTitle;
	}

	public String getMethodTitle() {
		return methodTitle;
	}

	public void setMethodTitle(String methodTitle) {
		this.methodTitle = methodTitle;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

}
