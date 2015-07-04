package com.kentec.milkbox.checkout.task;

import java.util.ArrayList;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.utils.DEBUG;

public class CheckoutTaskLoadPaymentMethod extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private CheckoutActivity mActivity;

	public CheckoutTaskLoadPaymentMethod(CheckoutActivity activity, OnLoadPaymentMethodListener listener) {
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
			String cartID = mActivity.getData().getCartId();
			String customerId = mActivity.getData().getCustomerId();
			Object[] list = CFG.getRpcClient().cartPaymentList(cartID);
			ArrayList<PaymentMethod> paymentMethodList = APIParser.parserPaymentMethod(list);
			mActivity.getData().setPaymentMethodList(paymentMethodList);

			try {
				list = CFG.getRpcClient().checkoutexdGetCards(customerId);
				ArrayList<CreditCard> creditCardMethodList = APIParser.parserCreditCard(list);
				mActivity.getData().setCreditCardList(creditCardMethodList);
			} catch (Exception e) {
				DEBUG.c(e);
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
		if (listener != null) {
			if (result == OK) {
				listener.OK();
			} else if (result == ERROR) {
				listener.Error(mActivity.getString(R.string.error).toString());
			}
		}
	}

	private OnLoadPaymentMethodListener listener;

	public interface OnLoadPaymentMethodListener {
		public void OK();

		public void Error(String msg);
	}
}