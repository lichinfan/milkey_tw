package com.kentec.milkbox.homedine.task;

import java.util.ArrayList;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.api.APIParser;
import com.kentec.milkbox.homedine.data.FoodItem;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineTaskGetShopCartFoods extends AsyncTask<String, Integer, ArrayList<FoodItem>> {

	private String cartId;

	public HomeDineTaskGetShopCartFoods(HomeDineDeliveryActivity context, String cartId, OnLoadShoppingCartListener listener) {
		this.listener = listener;
		this.cartId = cartId;
		this.execute();
	}

	@Override
	protected ArrayList<FoodItem> doInBackground(String... params) {
		try {
			JSONObject tmpObject = new JSONObject(API.getServerShopCart(cartId));
			if (APIParser.isOK(tmpObject)) {
				String tmpString = tmpObject.getString("data").replaceAll(":null", ":\"0\"");
				DEBUG.e("tmpString", tmpString);
				return APIParser.parserShopCartFoods(tmpString);
			} 
		} catch (Exception e) {
			DEBUG.c(e);
		}
		return null;
	}

	@Override
	protected void onPostExecute(ArrayList<FoodItem> shopCardFood) {
		if (listener != null)
			listener.complete(shopCardFood);
	}

	private OnLoadShoppingCartListener listener;

	public interface OnLoadShoppingCartListener {
		public void complete(ArrayList<FoodItem> shopCardFood);
	}
}
