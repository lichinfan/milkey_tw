package com.kentec.milkbox.checkout.task;

import java.util.HashMap;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskOrder extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;

	public CheckoutTaskOrder(CheckoutActivity activity) {
		this.mActivity = activity;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		try {

			String cartID = mActivity.getData().getCartId();
			RpcClient client = CFG.getRpcClient();
			String result = client.cartOrder(cartID);
			DEBUG.e("Order result", result);

			API.clearServerShopCart(cartID);

			Coupon coupon = mActivity.getOrder().getCoupon();
			if (coupon != null && coupon.getId() != null) {
				String couponCartId = mActivity.getData().getCouponCartId();
				HashMap<String, String> product = new HashMap<String, String>();
				product.put("product_id", coupon.getId());
				product.put("qty", "1");
				client.cartProductUpdate(couponCartId, product);
			}

			return OK;

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == ERROR) {
			mActivity.showMsg(R.string.error);
		} else if (result == OK) {
			mActivity.getMethod().okBackActivity();
		}
	}
}