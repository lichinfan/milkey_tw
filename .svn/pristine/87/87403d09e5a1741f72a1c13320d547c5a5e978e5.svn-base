package com.kentec.milkbox.checkout.task;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.Country;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskLoadCountry extends AsyncTask<String, Void, ArrayList<Country>> implements AsyncTaskCode {

	private CheckoutActivity mActivity;

	public CheckoutTaskLoadCountry(CheckoutActivity activity, OnLoadCountryListener listener) {
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
	protected ArrayList<Country> doInBackground(String... arg0) {
		try {
			Object[] list = CFG.getRpcClient().getCountryList();
			return APIParser.parserCountryList(list);
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<Country> countryList) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (countryList != null && countryList.size() > 0) {
			listener.OK(countryList);
		} else {
			listener.Error(mActivity.getText(R.string.error).toString());
		}
	}

	private OnLoadCountryListener listener;

	public interface OnLoadCountryListener {
		public void OK(ArrayList<Country> countryList);

		public void Error(String msg);
	}

}
