package com.kentec.milkbox.checkout.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.os.Bundle;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.DEBUG;
import com.kentec.milkbox.utils.Format;

public class CheckoutData {

	private CheckoutActivity mActivity;

	private String mCartId;
	private String mCouponCartId;
	private Bundle mBundle;
	private String mCustomerId;

	private ArrayList<Address> mAddrList;
	private ArrayList<ShippingMethod> mShippingMethodList;
	private ArrayList<PaymentMethod> mPaymentMethodList;
	private ArrayList<CreditCard> mCreditCardList;
	private ArrayList<Coupon> mCouponList;

	private ArrayList<Product> mProductList;
	private ArrayList<Total> mTotalList;

	private CheckoutIndex mCheckoutIndex;

	// private CreditCard mCustomerCreditCard;

	public CheckoutData(CheckoutActivity activity) {
		this.mActivity = activity;
		this.mAddrList = new ArrayList<Address>();
		this.mProductList = new ArrayList<Product>();
		this.mTotalList = new ArrayList<Total>();
		this.mPaymentMethodList = new ArrayList<PaymentMethod>();
		this.mCreditCardList = new ArrayList<CreditCard>();
		this.mShippingMethodList = new ArrayList<ShippingMethod>();
		this.mCheckoutIndex = new CheckoutIndex();

		this.mBundle = activity.getIntent().getExtras();
		if (mBundle == null) {
			activity.showMsg(R.string.msg_no_shopping_cart_id);
			return;
		}

		this.mCartId = mBundle.getString("cartId");
		if (mCartId == null) {
			activity.showMsg(R.string.msg_no_shopping_cart_id);
			return;
		}

		DEBUG.e("mCartId", mCartId);

		this.mCustomerId = activity.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
		if (mCustomerId == null) {
			activity.showMsg(R.string.msg_illegal_client);
			return;
		}

		this.mCouponCartId = activity.getSP().getString(CFG.PREF_COUPON_CART_ID, null);

	}

	/***************************************
	 **************** 轉換地址 ***************
	 ***************************************/
	public List<HashMap<String, String>> getAddressSimpleList() {
		ArrayList<HashMap<String, String>> tmpList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		if (mAddrList.size() > 0) {
			String tmpText = "";
			for (int i = 0; i < mAddrList.size(); i++) {
				map = new HashMap<String, String>();
				
				if(mAddrList.get(i).getStreet1()!=null) {
					tmpText = mAddrList.get(i).getStreet1();
				}
				if(mAddrList.get(i).getStreet2()!=null) {
					tmpText = tmpText+" "+mAddrList.get(i).getStreet2();
				}				
				tmpText = tmpText+" " + mAddrList.get(i).getCity() + " " + mAddrList.get(i).getPostCode();
				map.put("address", tmpText);
				tmpList.add(map);
			}
		} else {
			map = new HashMap<String, String>();
			map.put("address", mActivity.getString(R.string.no_data));
			tmpList.add(map);
		}
		return tmpList;
	}

	/***************************************
	 ************** 轉換運送方式 *************
	 ***************************************/
	public List<HashMap<String, String>> getShippingMethodSimpleList() {
		ArrayList<HashMap<String, String>> tmpList = new ArrayList<HashMap<String, String>>();

		HashMap<String, String> map = new HashMap<String, String>();

		if (mShippingMethodList.size() > 0) {
			for (int i = 0; i < mShippingMethodList.size(); i++) {
				map = new HashMap<String, String>();
				String tmpText = mShippingMethodList.get(i).getCarrierTitle() + " " + mShippingMethodList.get(i).getMethodTitle() + " "
						+ Format.price(mShippingMethodList.get(i).getPrice());

				map.put("shippingMethod", tmpText);
				tmpList.add(map);
			}
		} else {
			map = new HashMap<String, String>();
			map.put("shippingMethod", mActivity.getString(R.string.no_data));
			tmpList.add(map);
		}
		return tmpList;
	}

	/********************************************
	 * 轉換付款方式 List<HashMap<String, String>> *
	 ********************************************/
	public List<HashMap<String, String>> getPaymentMethodSimpleList() {
		ArrayList<HashMap<String, String>> tmpList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map = new HashMap<String, String>();
		if (mPaymentMethodList.size() > 0) {
			for (int i = 0; i < mPaymentMethodList.size(); i++) {
				map = new HashMap<String, String>();
				String tmpText = mPaymentMethodList.get(i).getTitle();

				map.put("paymentMethod", tmpText);
				tmpList.add(map);
			}
		} else {
			map = new HashMap<String, String>();
			map.put("paymentMethod", mActivity.getString(R.string.no_data));
			tmpList.add(map);
		}
		return tmpList;
	}

	/***************************************
	 ********* 轉換付款方式 String[] *********
	 ***************************************/
	public String[] getPaymentMethodStringArrary() {
		String[] itemList;
		if (mPaymentMethodList.size() == 0) {
			itemList = new String[1];
			itemList[0] = mActivity.getString(R.string.no_data);
			return itemList;
		}

		itemList = new String[mPaymentMethodList.size()];
		for (int i = 0; i < mPaymentMethodList.size(); i++) {
			PaymentMethod pm = mPaymentMethodList.get(i);
			itemList[i] = String.valueOf(i + 1) + ". " + pm.getTitle();
		}
		return itemList;
	}

	/***************************************
	 ******** 轉換信用卡清單 String[] *********
	 ***************************************/
	public String[] getCreditCardStringArrary() {
		String[] itemList;
		if (mCreditCardList.size() == 0) {
			itemList = new String[1];
			itemList[0] = mActivity.getString(R.string.no_data);
			return itemList;
		}

		itemList = new String[mCreditCardList.size()];
		for (int i = 0; i < mCreditCardList.size(); i++) {
			String tmpNumber = mCreditCardList.get(i).getNumber();
			if ((tmpNumber.length() - 8) > 0) {
				itemList[i] = tmpNumber.substring(0, 4);
				for (int j = 4; j < tmpNumber.length() - 4; j++) {
					itemList[i] += "x";
				}
				itemList[i] += tmpNumber.substring(tmpNumber.length() - 4, tmpNumber.length());
			} else {
				itemList[i] = tmpNumber;
			}
			itemList[i] = String.valueOf(i + 1) + ". " + itemList[i] + "(" + mCreditCardList.get(i).getOwner() + ")";
		}
		return itemList;
	}

	public void setAddress(Address address) {

	}

	public String getCartId() {
		return mCartId;
	}

	public void setCartId(String mCartId) {
		this.mCartId = mCartId;
	}

	public Bundle getBundle() {
		return mBundle;
	}

	public void setBundle(Bundle mBundle) {
		this.mBundle = mBundle;
	}

	public String getCustomerId() {
		return mCustomerId;
	}

	public void setCustomerId(String mCustomerId) {
		this.mCustomerId = mCustomerId;
	}

	public ArrayList<Address> getAddrList() {
		return mAddrList;
	}

	public void setAddrList(ArrayList<Address> mAddrList) {
		this.mAddrList = mAddrList;
	}

	public ArrayList<ShippingMethod> getShippingMethodList() {
		return mShippingMethodList;
	}

	public void setShippingMethodList(ArrayList<ShippingMethod> mShippingMethodList) {
		this.mShippingMethodList = mShippingMethodList;
	}

	public ArrayList<PaymentMethod> getPaymentMethodList() {
		return mPaymentMethodList;
	}

	public void setPaymentMethodList(ArrayList<PaymentMethod> mPaymentMethodList) {
		this.mPaymentMethodList = mPaymentMethodList;
	}

	public ArrayList<Product> getProductList() {
		return mProductList;
	}

	public void setProductList(ArrayList<Product> mProductList) {
		this.mProductList = mProductList;
	}

	public ArrayList<Total> getTotalList() {
		return mTotalList;
	}

	public void setTotalList(ArrayList<Total> mTotalList) {
		this.mTotalList = mTotalList;
	}

	public ArrayList<CreditCard> getCreditCardList() {
		return mCreditCardList;
	}

	public CheckoutIndex getCheckoutIndex() {
		return mCheckoutIndex;
	}

	public void setCreditCardList(ArrayList<CreditCard> mCreditCardList) {
		this.mCreditCardList = mCreditCardList;
	}

	public ArrayList<Coupon> getCouponList() {
		return mCouponList;
	}

	public void setCouponList(ArrayList<Coupon> couponList) {
		this.mCouponList = couponList;
	}

	public String getCouponCartId() {
		return mCouponCartId;
	}

	public void setCouponCartId(String couponCartId) {
		this.mCouponCartId = couponCartId;
	}

}
