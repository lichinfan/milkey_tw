package com.kentec.milkbox.checkout.method;

import android.view.View;
import android.view.View.OnClickListener;

import com.kentec.milkbox.checkout.CheckoutActivity;

public class CheckoutListener {

	private CheckoutActivity mActivity;

	public CheckoutListener(CheckoutActivity activity) {
		this.mActivity = activity;

		mActivity.getViewGroup().getBackButton().setOnClickListener(backClickEvent);
		mActivity.getViewGroup().getNextButton().setOnClickListener(nextClickEvent);
	}

	private OnClickListener backClickEvent = new OnClickListener() {
		@Override
		public void onClick(View view) {
			mActivity.getMethod().backPage();
			mActivity.reTimeOut();
		}
	};

	private OnClickListener nextClickEvent = new OnClickListener() {
		@Override
		public void onClick(View view) {
			if (mActivity.getViewGroup().getMainViewFlipper().getDisplayedChild() == 3) {
				mActivity.getMethod().setPaymentMethod();
			} else {
				mActivity.getMethod().nextPage();
				mActivity.reTimeOut();
			}
		}
	};

}
