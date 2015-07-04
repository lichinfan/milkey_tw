package com.kentec.milkbox.homedine.task;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.StoreData;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskGetStore extends AsyncTask<String, Boolean, ArrayList<StoreData>> {

	private Context mContext;
	private String msg = "";

	public HomeDineTaskGetStore(Context context, OnStoreOkListener listener) {
		this.mContext = context;
		this.listener = listener;
		this.msg = mContext.getText(R.string.error).toString();
		this.execute();
	}

	@Override
	protected ArrayList<StoreData> doInBackground(String... params) {
		try {
			if (NetworkUtil.haveInternet(mContext)) {

				JSONObject tmpObject = new JSONObject(API.getStore());
				if (APIParser.isOK(tmpObject)) {
					String tmpString = tmpObject.getString("data").replaceAll(":null", ":\"0\"");
					DEBUG.e("tmpString", tmpString);
					return APIParser.parserStore(new JSONArray(tmpString));
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
	protected void onPostExecute(ArrayList<StoreData> storeList) {

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

	private OnStoreOkListener listener;

	public interface OnStoreOkListener {
		public void OK(ArrayList<StoreData> storeList);

		public void Error(String msg);
	}
}
