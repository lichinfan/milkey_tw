package com.kentec.milkbox.homedine.task;

import java.util.HashMap;

import android.content.Context;
import android.os.AsyncTask;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.OrderInfo;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskGetOrderInfo extends AsyncTask<String, Boolean, OrderInfo> {

	private Context mContext;
	private String orderId;
	private String msg = "";

	public HomeDineTaskGetOrderInfo(Context context, String orderId, OnOrderOkListener listener) {
		this.mContext = context;
		this.orderId = orderId;
		this.msg = mContext.getText(R.string.error).toString();
		this.listener = listener;
		this.execute();
	}

	@Override
	protected OrderInfo doInBackground(String... params) {
		try {
			if (NetworkUtil.haveInternet(mContext) && !"".equals(orderId)) {
				HashMap<?, ?> order = API.getServerOrderInfo(orderId);
				DEBUG.e("order", order.toString());
				return APIParser.parserOrderInfo(order);
			} else {
				msg = mContext.getText(R.string.error_internet).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(OrderInfo orderInfo) {
		if (orderInfo != null) {
			if (listener != null) {
				listener.OK(orderInfo);
			}
		} else {
			if (listener != null) {
				listener.Error(msg);
			}
		}
	}

	private OnOrderOkListener listener;

	public interface OnOrderOkListener {
		public void OK(OrderInfo orderInfo);

		public void Error(String msg);
	}
}
