package com.kentec.milkbox.homedine.task;

import java.util.ArrayList;

import android.content.Context;
import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.utils.NetworkUtil;

public class HomeDineTaskGetOrderStatus extends AsyncTask<String, Boolean, ArrayList<OrderData>> {

	private Context mContext;
	private String storeId;
	private String customerId;
	private String msg = "";

	public HomeDineTaskGetOrderStatus(Context context, String storeId, OnOrderOkListener listener) {
		this.mContext = context;
		this.storeId = storeId;
		this.customerId = mContext.getSharedPreferences("preference", 0).getString(CFG.PREF_CUSTOMER_ID, "");
		this.msg = mContext.getText(R.string.error).toString();
		this.listener = listener;
		this.execute();
	}

	@Override
	protected ArrayList<OrderData> doInBackground(String... params) {
		try {
			if (NetworkUtil.haveInternet(mContext) && !"".equals(customerId) && !"".equals(storeId)) {
				Object[] order = API.getServerOrder(customerId, storeId);
				return APIParser.parserOrderList(order);
			} else {
				msg = mContext.getText(R.string.error_internet).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<OrderData> storeList) {
		if (storeList != null) {
			if (listener != null) {
				listener.OK(storeList);
			}
		} else {
			if (listener != null) {
				listener.Error(msg);
			}
		}
	}

	private OnOrderOkListener listener;

	public interface OnOrderOkListener {
		public void OK(ArrayList<OrderData> storeList);

		public void Error(String msg);
	}
}
