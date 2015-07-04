package com.kentec.milkbox.checkout.task;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskDeleteAddress extends AsyncTask<String, Void, Boolean> {

	private CheckoutActivity mActivity;
	private String addressId;

	public CheckoutTaskDeleteAddress(CheckoutActivity activity, String addressId, OnDeleteAddressListener listener) {
		this.mActivity = activity;
		this.addressId = addressId;
		this.listener = listener;
		this.execute();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Boolean doInBackground(String... arg0) {
		try {
			return CFG.getRpcClient().customerAddressDelete(addressId);
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (listener != null) {
			if (result) {
				listener.OK();
			} else {
				listener.Error(mActivity.getString(R.string.error).toString());
			}
		}
	}

	private OnDeleteAddressListener listener;

	public interface OnDeleteAddressListener {
		public void OK();

		public void Error(String msg);
	}
}