package com.kentec.milkbox.checkout.api;

import java.util.HashMap;

import org.json.JSONException;

import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.utils.DEBUG;

public class APIMake {

	/**************************************************************************
	 ************************* 組合Customer Address **************************
	 **************************************************************************/
	public static HashMap<String, Object> customerAddress(Address mAddress) throws Exception {
		HashMap<String, Object> address = new HashMap<String, Object>();
		try {
			address.put("firstname", mAddress.getFirstName());
			address.put("lastname", mAddress.getLastName());
			address.put("street", new String[] { mAddress.getStreet1(), mAddress.getStreet2() });
			address.put("city", mAddress.getCity());
			address.put("country_id", mAddress.getCountryId());
			// address.put("region", "Texas");
			// address.put("region_id", 3);
			address.put("postcode", mAddress.getPostCode());
			address.put("telephone", mAddress.getTelePhone());
			address.put("is_default_billing", false);
			address.put("is_default_shipping", false);

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return address;
	}

	/**************************************************************************
	 ************************* 組合Billing Address ****************************
	 **************************************************************************/
	public static HashMap<String, String> billingAddress(Address mAddress) throws JSONException {
		HashMap<String, String> addressMap = new HashMap<String, String>();
		try {
			String street = "";
			if( mAddress.getStreet1() != null ) {
				street = mAddress.getStreet1();
			}
			if( mAddress.getStreet2() != null ) {
				street = street + " " +mAddress.getStreet2();
			}						
			addressMap.put("mode", CheckoutActivity.MODE_BILLING);
			addressMap.put("firstname", mAddress.getFirstName());
			addressMap.put("lastname", mAddress.getLastName());
			addressMap.put("city", mAddress.getCity());
			addressMap.put("street", street );
			addressMap.put("postcode", mAddress.getPostCode());
			addressMap.put("telephone", mAddress.getTelePhone());
			addressMap.put("country_id", mAddress.getCountryId());
			
			
			
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return addressMap;
	}

	/**************************************************************************
	 ************************* 組合Shipping Address ****************************
	 **************************************************************************/
	public static HashMap<String, String> shippingAddress(Address mAddress) throws JSONException {
		HashMap<String, String> addressMap = new HashMap<String, String>();
		try {
			addressMap.put("mode", CheckoutActivity.MODE_SHIPPING);
			addressMap.put("firstname", mAddress.getFirstName());
			addressMap.put("lastname", mAddress.getLastName());
			addressMap.put("city", mAddress.getCity());
			addressMap.put("street", mAddress.getStreet1() + " " + mAddress.getStreet2());
			addressMap.put("postcode", mAddress.getPostCode());
			addressMap.put("telephone", mAddress.getTelePhone());
			addressMap.put("country_id", mAddress.getCountryId());
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return addressMap;
	}

	/**************************************************************************
	 *************************** 組合一般付款方式 ******************************
	 **************************************************************************/
	public static HashMap<String, String> paymentMethod(PaymentMethod paymentMethod) throws JSONException {
		HashMap<String, String> tmpPaymentMethod = new HashMap<String, String>();
		try {
			tmpPaymentMethod.put("po_number", "");
			tmpPaymentMethod.put("method", paymentMethod.getCode());
			tmpPaymentMethod.put("cc_cid", "");
			tmpPaymentMethod.put("cc_owner", "");
			tmpPaymentMethod.put("cc_number", "");
			tmpPaymentMethod.put("cc_type", "");
			tmpPaymentMethod.put("cc_exp_year", "");
			tmpPaymentMethod.put("cc_exp_month", "");
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return tmpPaymentMethod;
	}

	/**************************************************************************
	 ************************** 組合信用卡付款方式 ******************************
	 **************************************************************************/
	public static HashMap<String, String> paymentMethod(PaymentMethod paymentMethod, CreditCard creditCard) throws JSONException {
		HashMap<String, String> tmpPaymentMethod = new HashMap<String, String>();
		try {
			tmpPaymentMethod.put("po_number", "");
			tmpPaymentMethod.put("method", paymentMethod.getCode());
			tmpPaymentMethod.put("cc_cid", creditCard.getCid());
			tmpPaymentMethod.put("cc_owner", creditCard.getOwner());
			tmpPaymentMethod.put("cc_number", creditCard.getNumber());
			tmpPaymentMethod.put("cc_type", creditCard.getType());
			tmpPaymentMethod.put("cc_exp_year", creditCard.getExpiryYear());
			tmpPaymentMethod.put("cc_exp_month", creditCard.getExpiryMonth());
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return tmpPaymentMethod;
	}
}
