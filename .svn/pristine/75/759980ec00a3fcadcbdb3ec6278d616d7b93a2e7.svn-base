package com.kentec.milkbox.homedine.task;

import java.util.ArrayList;

import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.data.StoreData;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskCheckCart extends AsyncTask<String, Integer, Boolean> {

	private HomeDineDeliveryActivity mActivity;

	public HomeDineTaskCheckCart(HomeDineDeliveryActivity context) {
		this.mActivity = context;

	}

	@Override
	protected Boolean doInBackground(String... params) {
		try {
			ArrayList<StoreData> storeList = mActivity.getCache().getStoreList();
			String customerId = mActivity.getProperty().getCustomerId();
			for (int i = 0; i < storeList.size(); i++) {
				String cartId = mActivity.getSP().getString(storeList.get(i).getStoreID(), "");
				String storeId = storeList.get(i).getStoreID();
				DEBUG.e("Cart ID", cartId);
				if ("".equals(cartId)) {
					cartId = CFG.getRpcClient().cartCreate(storeId).toString();
					API.addServerCustomer(customerId, cartId, storeId);

					Editor edit = mActivity.getSP().edit();
					edit.putString(storeId, cartId);
					edit.commit();
				}
			}
			return true;
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {

	}
}