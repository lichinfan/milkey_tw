package com.kentec.milkbox.checkout.view;

import android.view.View;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.task.CheckoutTaskSetShippingAddress;

public class ShippingAddressView extends BillingAddressView {

	public ShippingAddressView(CheckoutActivity activity) {
		super(activity);
		titleTextView.setText(activity.getText(R.string.checkout_select_shipping_address).toString());
	}

	@Override
	public void setOrderAddress(Address address) {
		new CheckoutTaskSetShippingAddress(mActivity, address).execute();
	}
	
	@Override
	protected void showCurrent() {
		Address current = mActivity.getOrder().getShippingAddress();
		if(current!=null){
			String tmpText = current.getStreet1() + " " + current.getStreet2() + " " + current.getCity() + " " + current.getPostCode();
			currentTextView.setText(tmpText);
			mActivity.getViewGroup().getNextButton().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getNextButton().requestFocus();
		}else{
			mActivity.getViewGroup().getNextButton().setVisibility(View.GONE);			
		}
	}
}
