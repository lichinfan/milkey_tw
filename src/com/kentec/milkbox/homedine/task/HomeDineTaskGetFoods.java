package com.kentec.milkbox.homedine.task;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.FoodData;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskGetFoods extends AsyncTask<String, Boolean, FoodData> {

	private HomeDineDeliveryActivity mActivity;
	private String msg = "";
	private String storeCategoryId;
	private int page;

	public HomeDineTaskGetFoods(HomeDineDeliveryActivity context, String storeId, int page, OnFoodsOkListener listener) {
		this.mActivity = context;
		this.storeCategoryId = mActivity.getCache().getStore(storeId).getStoreCategoryId();
		this.page = page;
		this.listener = listener;
		this.msg = mActivity.getText(R.string.error).toString();
		this.execute();
	}

	@Override
	protected FoodData doInBackground(String... as) {
		try {
			if (NetworkUtil.haveInternet(mActivity)) {
				JSONObject tmpObject = new JSONObject(API.getFoods(storeCategoryId, mActivity.getProperty().isUnder200Calores(), page));
				if (APIParser.isOK(tmpObject)) {
					String tmpString = tmpObject.getString("data").replaceAll(":null", ":\"0\"");
					DEBUG.e("tmpString", tmpString);
					return APIParser.parserFood(tmpString);
				} else {
					msg = tmpObject.getString("msg");
				}
			} else {
				msg = mActivity.getText(R.string.error_internet).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(FoodData fooddata) {
		if (fooddata != null) {
			if (listener != null) {
				listener.OK(fooddata);
			}
		} else {
			if (listener != null) {
				listener.Error(msg);
			}
		}
	}

	private OnFoodsOkListener listener;

	public interface OnFoodsOkListener {
		public void OK(FoodData fooddata);

		public void Error(String msg);
	}
}
