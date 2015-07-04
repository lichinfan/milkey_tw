package com.kentec.milkbox.checkout.task;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.ShippingMethod;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskSetShippingMethod extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private ShippingMethod shippingMethod;

	public CheckoutTaskSetShippingMethod(CheckoutActivity activity, ShippingMethod shippingMethod) {
		this.mActivity = activity;
		this.shippingMethod = shippingMethod;
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
			Boolean result = client.cartShippingMethod(cartID, shippingMethod.getCode());

			if (!result) {
				return ERROR;
			}

			mActivity.getOrder().setShippingMethod(shippingMethod);

			return OK;

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			mActivity.getMethod().nextPage();
		} else if (result == ERROR) {
			mActivity.showMsg(R.string.error);
		}
	}

}