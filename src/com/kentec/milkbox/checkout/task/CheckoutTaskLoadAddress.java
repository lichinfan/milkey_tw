package com.kentec.milkbox.checkout.task;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskLoadAddress extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;

	public CheckoutTaskLoadAddress(CheckoutActivity activity, OnLoadAddressListener listener) {
		this.mActivity = activity;
		this.listener = listener;
		this.execute();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Integer doInBackground(String... arg0) {

		try {
			RpcClient client = CFG.getRpcClient();
			Object[] list = client.customerAddressList(mActivity.getData().getCustomerId());
			mActivity.getData().setAddrList(APIParser.parserAddress(list));
			return OK;

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (listener != null) {
			if (result == OK) {
				listener.OK();
			} else if (result == ERROR) {
				listener.Error(mActivity.getString(R.string.error).toString());
			}
		}
	}

	private OnLoadAddressListener listener;

	public interface OnLoadAddressListener {
		public void OK();

		public void Error(String msg);
	}
}