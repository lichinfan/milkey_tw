package com.kentec.milkbox.checkout.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.CCType;
import com.kentec.milkbox.checkout.data.Country;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.checkout.data.ShippingMethod;
import com.kentec.milkbox.checkout.data.Total;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.DEBUG;

public class APIParser {

	/**************************************************************************
	 ******************************* 解析地址 **********************************
	 **************************************************************************/
	public static ArrayList<Address> parserAddress(Object[] list) {
		ArrayList<Address> addressList = new ArrayList<Address>();

		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				Address address = new Address();
				address.setId(row.get("customer_address_id").toString());
				address.setStreet1(row.get("street").toString());
				address.setCountryId(row.get("country_id").toString());
				address.setCity(row.get("city").toString());
				address.setPostCode(row.get("postcode").toString());
				address.setTelePhone(row.get("telephone").toString());
				address.setFirstName(row.get("firstname").toString());
				address.setLastName(row.get("lastname").toString());
				address.setBilling((Boolean) row.get("is_default_billing"));
				address.setShipping((Boolean) row.get("is_default_shipping"));
				addressList.add(address);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}

		return addressList;
	}

	/**************************************************************************
	 ***************************** 解析運送方式 ********************************
	 **************************************************************************/
	public static ArrayList<ShippingMethod> parserShippingMethod(Object[] list) {
		ArrayList<ShippingMethod> shippingMethodList = new ArrayList<ShippingMethod>();
		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				ShippingMethod sm = new ShippingMethod();
				sm.setCode(row.get("code").toString());
				sm.setCarrierTitle(row.get("carrier_title").toString());
				sm.setMethodTitle(row.get("method_title").toString());
				sm.setPrice(row.get("price").toString());
				shippingMethodList.add(sm);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return shippingMethodList;
	}

	/**************************************************************************
	 ***************************** 解析付款方式 ********************************
	 **************************************************************************/
	public static ArrayList<PaymentMethod> parserPaymentMethod(Object[] list) {
		ArrayList<PaymentMethod> paymentMethodList = new ArrayList<PaymentMethod>();
		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				PaymentMethod pm = new PaymentMethod();
				pm.setCode(row.get("code").toString());
				pm.setTitle(row.get("title").toString());
				if (CheckoutActivity.CC_SAVE.equals(pm.getCode())) {
					@SuppressWarnings("unchecked")
					HashMap<String, String> type = (HashMap<String, String>) row.get("cc_types");
					ArrayList<CCType> typeList = getTypeList(type);
					pm.setCcType(typeList);
				}
				paymentMethodList.add(pm);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return paymentMethodList;
	}

	/***************************************
	 ************** 轉換付款類型 *************
	 ***************************************/
	public static ArrayList<CCType> getTypeList(HashMap<String, String> hm) {
		List<String> keys = new ArrayList<String>(hm.keySet());
		ArrayList<CCType> list = new ArrayList<CCType>();
		CCType type;
		for (String key : keys) {
			type = new CCType();
			type.setId(key);
			type.setName(hm.get(key).toString());
			list.add(type);
		}
		return list;
	}

	/**************************************************************************
	 ***************************** 解析信用卡 ********************************
	 **************************************************************************/
//	public static ArrayList<CreditCard> parserCreditCard(HashMap<?, ?> creditCardMap) {
	public static ArrayList<CreditCard> parserCreditCard(Object[] list) {
	
		ArrayList<CreditCard> creditCardList = new ArrayList<CreditCard>();
		try {
			HashMap<?,?> creditCardMap;			
			for( Object row : list ) {				
				creditCardMap = (HashMap<?,?>) row;				
				CreditCard creditCard = new CreditCard();
				creditCard.setId(creditCardMap.get("entity_id").toString());
				creditCard.setOwner(creditCardMap.get("name").toString());
				creditCard.setNumber(creditCardMap.get("number").toString().trim().replaceAll("-", "").replaceAll(" ", ""));
				creditCard.setType(creditCardMap.get("type").toString());
				creditCard.setExpiryMonth(creditCardMap.get("exp_month").toString());
				creditCard.setExpiryYear(creditCardMap.get("exp_year").toString());
				creditCard.setCid(creditCardMap.get("verify_number").toString());
				creditCardList.add(creditCard);
			}

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return creditCardList;
	}

	/**************************************************************************
	 ***************************** 解析購物車產品 *******************************
	 **************************************************************************/
	public static ArrayList<Product> parserProduct(Object[] list) {
		ArrayList<Product> productList = new ArrayList<Product>();
		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				Product product = new Product();
				product.setId(row.get("product_id").toString());
				product.setName(row.get("name").toString());
				product.setPrice(Double.parseDouble(row.get("price").toString()));
				product.setQty(Double.parseDouble(row.get("qty").toString()));
				product.setSku(row.get("sku").toString());
				product.setSubtotal(Double.parseDouble(row.get("row_total").toString()));
				productList.add(product);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return productList;
	}

	/**************************************************************************
	 ********************************* 解析總計 ********************************
	 **************************************************************************/
	public static ArrayList<Total> parserTotals(Object[] list) {
		ArrayList<Total> totalList = new ArrayList<Total>();
		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				Total total = new Total();
				total.setTitle(row.get("title").toString());
				total.setAmount(row.get("amount").toString());
				totalList.add(total);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return totalList;
	}

	/**************************************************************************
	 ********************************* 解析國家 ********************************
	 **************************************************************************/
	public static ArrayList<Country> parserCountryList(Object[] list) {

		ArrayList<Country> countryList = new ArrayList<Country>();
		try {
			for (int i = 0; i < list.length; i++) {
				HashMap<?, ?> row = (HashMap<?, ?>) list[i];
				Country country = new Country();
				country.setCountryId(row.get("country_id").toString());
				country.setIso2Code(row.get("iso2_code").toString());
				country.setIso3Code(row.get("iso3_code").toString());
				country.setName(row.get("name").toString());

				countryList.add(country);
			}
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return countryList;
	}

}