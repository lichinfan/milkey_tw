package com.kentec.milkbox.homedine.task;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.OrderInfoItems;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskGetFoodThumbnail extends AsyncTask<String, Boolean, ArrayList<OrderInfoItems>> {

	private Context mContext;
	private String msg = "";
	private ArrayList<OrderInfoItems> itemList;

	public HomeDineTaskGetFoodThumbnail(Context context, ArrayList<OrderInfoItems> itemList, OnFoodThumbnailListener listener) {
		this.mContext = context;
		this.msg = mContext.getText(R.string.error).toString();
		this.itemList = itemList;
		this.listener = listener;
		this.execute();
	}

	@Override
	protected ArrayList<OrderInfoItems> doInBackground(String... params) {
		try {
			if (NetworkUtil.haveInternet(mContext)) {

				JSONObject tmpObject = new JSONObject(API.getFoodsThumbnail(itemList));
				if (APIParser.isOK(tmpObject)) {
					String tmpString = tmpObject.getString("data").replaceAll(":null", ":\"0\"");
					DEBUG.e("tmpString", tmpString);
					return APIParser.parserOrderInfoItems(itemList, new JSONObject(tmpString));
				} else {
					msg = tmpObject.getString("msg");
				}
			} else {
				msg = mContext.getText(R.string.error_internet).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<OrderInfoItems> itemList) {

		if (itemList != null) {
			if (listener != null) {
				listener.OK(itemList);
			}
		} else {
			if (listener != null) {
				listener.Error(msg);
			}
		}
	}

	private OnFoodThumbnailListener listener;

	public interface OnFoodThumbnailListener {
		public void OK(ArrayList<OrderInfoItems> itemList);

		public void Error(String msg);
	}
}
