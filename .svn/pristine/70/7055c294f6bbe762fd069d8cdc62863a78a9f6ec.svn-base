package com.kentec.milkbox.checkout.task;

import java.util.HashMap;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskAddCreditCard extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;
	private CreditCard mCreditCard;
	private String mCustomId;
	private String msg = "";

	public CheckoutTaskAddCreditCard(CheckoutActivity activity, String customId, CreditCard creditCard, OnAddCreditCardOkListener listener) {
		this.mActivity = activity;
		this.mCreditCard = creditCard;
		this.mCustomId = customId;
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
			HashMap<String,String> cc = new HashMap<String,String>();
			cc.put("customer_id", mCustomId);
			cc.put("name", mCreditCard.getOwner());
			cc.put("type", mCreditCard.getType());
			cc.put("number", mCreditCard.getNumber());
			cc.put("exp_month", mCreditCard.getExpiryMonth());
			cc.put("exp_year", mCreditCard.getExpiryYear());
			cc.put("verify_number", mCreditCard.getCid());
			cc.put("comment", "create");
			RpcClient client = CFG.getRpcClient();
			client.checkoutexdCreditCardCreate(cc);
			return OK;
			
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

	private OnAddCreditCardOkListener listener;

	public interface OnAddCreditCardOkListener {
		public void OK();

		public void Error(String msg);
	}

}
