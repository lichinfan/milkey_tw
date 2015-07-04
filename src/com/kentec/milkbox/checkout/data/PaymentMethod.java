package com.kentec.milkbox.checkout.data;

import java.util.ArrayList;

public class PaymentMethod {

	private String code;
	private String title;
	private ArrayList<CCType> ccType;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<CCType> getCcType() {
		return ccType;
	}

	public void setCcType(ArrayList<CCType> ccType) {
		this.ccType = ccType;
	}

}
