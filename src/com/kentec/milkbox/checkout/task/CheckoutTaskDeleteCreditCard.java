package com.kentec.milkbox.checkout.task;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskDeleteCreditCard extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private String mId;
	private String mMsg = "";

	public CheckoutTaskDeleteCreditCard(CheckoutActivity activity, String id, OnDeleteCreditCardOkListener listener) {
		this.mActivity = activity;
		this.mId = id;
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
			Boolean success = client.checkoutexdCreditCardDelete(mId);
			if(success)			
				return OK;
		} catch (Exception e) {
			DEBUG.c(e);
			mMsg = mActivity.getText(R.string.error).toString();
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			listener.OK();
		} else if (result == ERROR) {
			listener.Error(mMsg);
		}
	}

	private OnDeleteCreditCardOkListener listener;

	public interface OnDeleteCreditCardOkListener {
		public void OK();

		public void Error(String msg);
	}

}
