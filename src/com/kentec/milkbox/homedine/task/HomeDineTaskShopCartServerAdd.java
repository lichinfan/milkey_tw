package com.kentec.milkbox.homedine.task;

import org.xmlrpc.android.XMLRPCFault;

import android.os.AsyncTask;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskShopCartServerAdd extends AsyncTask<String, Integer, Boolean> {

	private HomeDineDeliveryActivity mActivity;
	private String cartId;
	private String storeId;
	private String msg = "";

	public HomeDineTaskShopCartServerAdd(HomeDineDeliveryActivity context, String cartId, String storeId, OnAddShoppingCartListener listener) {
		this.mActivity = context;
		this.cartId = cartId;
		this.storeId = storeId;
		this.listener = listener;
		this.execute();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			if (API.clearServerShopCart(cartId)) {
				API.addServerCustomer(mActivity.getProperty().getCustomerId(), cartId, storeId);
				return API.addServerShopCart(cartId, mActivity.getMealsList().getStoreMealsList(mActivity.getProperty().getNowStoreId()));
			}
		} catch (XMLRPCFault e) {
			DEBUG.c(e);
			msg = e.getFaultString();
		} catch (Exception e) {
			DEBUG.c(e);
			msg = mActivity.getText(R.string.error).toString();
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		if (listener != null)
			listener.complete(result, msg);
	}

	private OnAddShoppingCartListener listener;

	public interface OnAddShoppingCartListener {
		public void complete(boolean success, String msg);
	}
}