package com.kentec.milkbox.utils;

import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;

public class ShopCartUtil extends AsyncTask<String, String, String> {

	private BaseActivity mContext;
	private String storeId;

	public ShopCartUtil(BaseActivity context, String storeId, OnCartListener listener) {
		this.mContext = context;
		this.storeId = storeId;
		this.listener = listener;

		this.execute();
	}

	@Override
	protected String doInBackground(String... arg0) {

		String cartId = mContext.getSP().getString(storeId, "");
		DEBUG.e("Cart ID", cartId);

		if (!"".equals(cartId)) {
			return cartId;
		} else {
			try {
				RpcClient client = CFG.getRpcClient();
				Object obj = client.cartCreate(storeId);
				cartId = obj.toString();
				DEBUG.e("New Cart ID", cartId);
				return cartId;
			} catch (Exception e) {
				DEBUG.c(e);
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null && !"".equals(result)) {
			DEBUG.e(String.valueOf(storeId) + " Cart ID", String.valueOf(result));
			Editor edit = mContext.getSP().edit();
			edit.putString(storeId, result);
			edit.commit();

			if (listener != null)
				listener.ok(result);
		} else {
			if (listener != null)
				listener.error();
		}
	}

	private OnCartListener listener;

	public interface OnCartListener {
		public void ok(String cartId);

		public void error();

	}
}