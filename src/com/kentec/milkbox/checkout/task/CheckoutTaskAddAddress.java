package com.kentec.milkbox.checkout.task;

import org.xmlrpc.android.XMLRPCFault;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIMake;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskAddAddress extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private Address mAddress;
	private String msg = "";

	public CheckoutTaskAddAddress(CheckoutActivity activity, Address address, OnAddAddressOkListener listener) {
		this.mActivity = activity;
		this.mAddress = address;
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
			Object code = CFG.getRpcClient().customerAddressCreate(mActivity.getData().getCustomerId(), APIMake.customerAddress(mAddress));

			DEBUG.e("address code", String.valueOf(code));

			return OK;
		} catch (XMLRPCFault e) {
			DEBUG.c(e);
			msg = e.getFaultString();
		} catch (Exception e) {
			DEBUG.c(e);
			msg = mActivity.getText(R.string.error).toString();
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			listener.OK();
		} else if (result == ERROR) {
			listener.Error(msg);
		}
	}

	private OnAddAddressOkListener listener;

	public interface OnAddAddressOkListener {
		public void OK();

		public void Error(String msg);
	}

}
