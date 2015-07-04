package com.kentec.milkbox.utils;

import java.text.DecimalFormat;

public class Format {
	
	public static String price(double price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(price);
	}
	
	public static String price(String price) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
		return df.format(Double.parseDouble(price));
	}
	
}
