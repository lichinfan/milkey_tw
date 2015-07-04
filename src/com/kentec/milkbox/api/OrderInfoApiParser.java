package com.kentec.milkbox.api;

import java.util.ArrayList;
import java.util.HashMap;

import com.kentec.milkbox.data.BillingAddress;
import com.kentec.milkbox.data.OrderInfo;
import com.kentec.milkbox.data.OrderInfoCreditCard;
import com.kentec.milkbox.data.ShippingAddress;
import com.kentec.milkbox.grocery.data.Product;

public class OrderInfoApiParser {
	
	public static final String CC_SAVE = "ccsave";
	
	public static OrderInfo parserOrderInfo(HashMap<?,?> row, Object[] paymentMethodList ) {
		OrderInfo oi = new OrderInfo();
		
		oi.setId(row.get("increment_id").toString());
		oi.setCreateDate(row.get("created_at").toString());
		
		// shipping view
		oi.setShippingMethod(row.get("shipping_description").toString());		
		oi.setShippAddr(parseShippingAddress(row.get("shipping_address")));
				
		// paymeny view
		oi.setBillingAddr(parseBillingAddress(row.get("billing_address")));		
		String payment = ((HashMap)row.get("payment")).get("method").toString(); 
		oi.setPayment(getPaymentMethodTitle(payment,paymentMethodList));
		if( CC_SAVE.equals(payment) ) {
			oi.setCreditCard(parseCreditCard(row.get("payment")));
		}
		
		// overview 
		oi.setProductList(OrderInfoApiParser.parserProductLst((Object[])row.get("items")));		
		oi.setSubtotal(Double.parseDouble(row.get("subtotal").toString()));
		oi.setShippingPrice(Double.parseDouble(row.get("base_shipping_amount").toString()));
		oi.setTotal(Double.parseDouble(row.get("grand_total").toString()));
		
		return oi;
	}
	
	public static ArrayList<Product> parserProductLst(Object[] list) {
		Product product;
		HashMap<String,String> row;
		ArrayList<Product> productList = new ArrayList<Product>();
		for(int i=0; i<list.length; i++) {
			row = (HashMap<String,String>) list[i];
			product = new Product();
			product.setName(row.get("name"));
			product.setPrice(Double.parseDouble(row.get("base_price")));
			product.setQty(Double.parseDouble(row.get("qty_ordered")));
			product.setSubtotal(Double.parseDouble(row.get("row_total")));
			productList.add(product);
		} 		
		return productList;
	}
	
	private static ShippingAddress parseShippingAddress(Object obj) {
		HashMap<String,String> row = (HashMap<String,String>) obj;
		ShippingAddress sa = new ShippingAddress();
		sa.setId(row.get("address_id"));
		sa.setStreet(row.get("street"));
		sa.setCity(row.get("city"));
		sa.setCountryId(row.get("country_id"));
		sa.setFirstname(row.get("firstname"));
		sa.setLastname(row.get("lastname"));
		sa.setPostCode(row.get("postcode"));
		sa.setTelePhone(row.get("telephone"));
		return sa;
	}

	
	private static BillingAddress parseBillingAddress(Object obj) {
		HashMap<String,String> row = (HashMap<String,String>) obj;
		BillingAddress ba = new BillingAddress();
		ba.setId(row.get("address_id"));
		ba.setStreet(row.get("street"));
		ba.setCity(row.get("city"));
		ba.setCountryId(row.get("country_id"));
		ba.setFirstname(row.get("firstname"));
		ba.setLastname(row.get("lastname"));
		ba.setPostCode(row.get("postcode"));
		ba.setTelePhone(row.get("telephone"));		
		return ba;
	}
	
	private static OrderInfoCreditCard parseCreditCard(Object obj) {
		HashMap<String,String> row = (HashMap<String,String>) obj;
		OrderInfoCreditCard oicc = new OrderInfoCreditCard();		
		oicc.setCc_owner(row.get("cc_owner"));
		oicc.setCcType(row.get("cc_type"));
		oicc.setMethod(row.get("method"));
		oicc.setLast4Num(row.get("cc_last4"));
		return oicc;
	}
		
	private static String getPaymentMethodTitle(String code, Object[] list) {
		HashMap<String,String> row;
		for(int i =0; i<list.length; i++) {
			row = (HashMap<String,String>)list[i];
			if(code.equals(row.get("code"))) {
				return row.get("title");
			}
		}		
		return code;
	}
}
