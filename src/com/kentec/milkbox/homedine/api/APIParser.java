package com.kentec.milkbox.homedine.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.homedine.data.FoodData;
import com.kentec.milkbox.homedine.data.FoodItem;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.data.OrderInfo;
import com.kentec.milkbox.homedine.data.OrderInfoItems;
import com.kentec.milkbox.homedine.data.OrderInfoStatusHistory;
import com.kentec.milkbox.homedine.data.StoreData;

public class APIParser {

	public static boolean isOK(JSONObject tmpObject) throws JSONException {
		if (tmpObject.getInt("code") == 0) {
			return true;
		} else {
			return false;
		}
	}

	/******************************************************************************
	 ********************************* 解析店家 ************************************
	 ******************************************************************************/
	public static ArrayList<StoreData> parserStore(JSONArray jsonArray) throws JSONException {
		ArrayList<StoreData> tmpList = new ArrayList<StoreData>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject tmpObject = jsonArray.getJSONObject(i);

			StoreData tmpData = new StoreData();
			tmpData.setStoreID(tmpObject.getString("storeID"));
			tmpData.setStoreCategoryId(tmpObject.getString("storeCategoryId"));
			tmpData.setStoreName(tmpObject.getString("storeName"));
			tmpData.setStoreAbout(tmpObject.getString("storeAbout"));
			tmpData.setStoreThumbnail(tmpObject.getString("storeThumbnail"));
			tmpData.setStorePic(tmpObject.getString("storePic"));

			tmpList.add(tmpData);
		}

		return tmpList;
	}

	/******************************************************************************
	 ********************************* 解析餐點 ************************************
	 ******************************************************************************/
	public static FoodData parserFood(String tmpString) throws JSONException {

		JSONObject tmpObj = new JSONObject(tmpString);

		ArrayList<FoodItem> tmpList = new ArrayList<FoodItem>();

		JSONArray tmpArray = tmpObj.getJSONArray("foodData");

		for (int i = 0; i < tmpArray.length(); i++) {
			JSONObject tmpItemObj = tmpArray.getJSONObject(i);

			FoodItem tmpData = new FoodItem();
			tmpData.setId(tmpItemObj.getString("Id"));
			tmpData.setSKU(tmpItemObj.getString("SKU"));
			tmpData.setPic(tmpItemObj.getString("Pic"));
			tmpData.setThumbnail(tmpItemObj.getString("Thumbnail"));
			tmpData.setName(tmpItemObj.getString("Name"));
			tmpData.setRemainingQuantity(Double.parseDouble(tmpItemObj.getString("Quantity")));
			tmpData.setPrice(Double.parseDouble(tmpItemObj.getString("Price")));
			tmpData.setAbout(tmpItemObj.getString("About"));
			tmpData.setCalorie(Float.parseFloat(tmpItemObj.getString("Calorie")));
			tmpData.setCarbohydrates(Integer.parseInt(tmpItemObj.getString("Carbohydrates")));
			tmpData.setCholesterol(Integer.parseInt(tmpItemObj.getString("Cholesterol")));
			tmpData.setFat(Integer.parseInt(tmpItemObj.getString("Fat")));
			tmpData.setProtein(Integer.parseInt(tmpItemObj.getString("Protein")));
			tmpData.setSaturatedFat(Integer.parseInt(tmpItemObj.getString("Saturated_Fat")));
			tmpData.setSodlum(Integer.parseInt(tmpItemObj.getString("Sodlum")));
			tmpData.setSugar(Integer.parseInt(tmpItemObj.getString("Sugar")));
			tmpData.setTrandFat(Integer.parseInt(tmpItemObj.getString("Trand_Fat")));

			tmpList.add(tmpData);
		}

		FoodData tmpData = new FoodData();
		tmpData.setFoodCount(tmpObj.getInt("foodCount"));
		tmpData.setFoodPageCount(tmpObj.getInt("lastPageSize"));
		tmpData.setFoodList(tmpList);

		return tmpData;
	}

	/******************************************************************************
	 ******************************* 解析購物車食物 *******************************
	 ******************************************************************************/
	public static ArrayList<FoodItem> parserShopCartFoods(String tmpString) throws JSONException {
		ArrayList<FoodItem> tmpList = new ArrayList<FoodItem>();

		JSONArray tmpArray = new JSONArray(tmpString);
		for (int i = 0; i < tmpArray.length(); i++) {
			JSONObject tmpItemObj = tmpArray.getJSONObject(i);

			FoodItem tmpData = new FoodItem();
			tmpData.setId(tmpItemObj.getString("Id"));
			tmpData.setSKU(tmpItemObj.getString("SKU"));
			tmpData.setPic(tmpItemObj.getString("Pic"));
			tmpData.setThumbnail(tmpItemObj.getString("Thumbnail"));
			tmpData.setName(tmpItemObj.getString("Name"));
			tmpData.setRemainingQuantity(Double.parseDouble(tmpItemObj.getString("Quantity")));
			tmpData.setPrice(Double.parseDouble(tmpItemObj.getString("Price")));
			tmpData.setAbout(tmpItemObj.getString("About"));
			tmpData.setCalorie(Float.parseFloat(tmpItemObj.getString("Calorie")));
			tmpData.setCarbohydrates(Integer.parseInt(tmpItemObj.getString("Carbohydrates")));
			tmpData.setCholesterol(Integer.parseInt(tmpItemObj.getString("Cholesterol")));
			tmpData.setFat(Integer.parseInt(tmpItemObj.getString("Fat")));
			tmpData.setProtein(Integer.parseInt(tmpItemObj.getString("Protein")));
			tmpData.setSaturatedFat(Integer.parseInt(tmpItemObj.getString("Saturated_Fat")));
			tmpData.setSodlum(Integer.parseInt(tmpItemObj.getString("Sodlum")));
			tmpData.setSugar(Integer.parseInt(tmpItemObj.getString("Sugar")));
			tmpData.setTrandFat(Integer.parseInt(tmpItemObj.getString("Trand_Fat")));
			tmpData.setQuantity(tmpItemObj.getInt("cartQty"));
			tmpList.add(tmpData);
		}
		return tmpList;
	}

	/******************************************************************************
	 ******************************** 解析訂單清單 **********************************
	 ******************************************************************************/
	public static ArrayList<OrderData> parserOrderList(Object[] order) {
		ArrayList<OrderData> list = new ArrayList<OrderData>();
		for (int i = order.length - 1; i >= 0; i--) {
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = (HashMap<String, String>) order[i];
			OrderData tmpData = new OrderData();
			tmpData.setIncrementId(map.get("increment_id"));
			tmpData.setCreatedAt(map.get("created_at"));
			tmpData.setGrandTotal(map.get("grand_total"));
			tmpData.setStatus(map.get("status"));
			list.add(tmpData);
		}
		return list;
	}

	/******************************************************************************
	 ******************************** 解析詳細訂單 **********************************
	 ******************************************************************************/
	public static OrderInfo parserOrderInfo(HashMap<?, ?> order) {

		HashMap<?, ?> baMap = (HashMap<?, ?>) order.get("billing_address");
		String tmpAddress = (String) baMap.get("street");
		tmpAddress += "," + (String) baMap.get("postcode");
		tmpAddress += "," + (String) baMap.get("city");
		tmpAddress += "," + (String) baMap.get("country_id");

		ArrayList<OrderInfoStatusHistory> shList = new ArrayList<OrderInfoStatusHistory>();
		Object[] shObj = (Object[]) order.get("status_history");
		for (int i = 0; i < shObj.length; i++) {
			HashMap<?, ?> shMap = (HashMap<?, ?>) shObj[i];
			OrderInfoStatusHistory sh = new OrderInfoStatusHistory();
			sh.setParentId((String) shMap.get("parent_id"));
			sh.setCreatedAt((String) shMap.get("created_at"));
			sh.setUpdatedAt((String) shMap.get("updated_at"));
			sh.setStatus((String) shMap.get("status"));
			sh.setComment((String) shMap.get("comment"));
			shList.add(sh);
		}

		ArrayList<OrderInfoItems> itemList = new ArrayList<OrderInfoItems>();
		Object[] itemObj = (Object[]) order.get("items");
		for (int i = 0; i < itemObj.length; i++) {
			HashMap<?, ?> itmeMap = (HashMap<?, ?>) itemObj[i];
			OrderInfoItems item = new OrderInfoItems();
			item.setQuoteItemId((String) itmeMap.get("quote_item_id"));
			item.setItemId((String) itmeMap.get("item_id"));
			item.setOrderId((String) itmeMap.get("order_id"));
			item.setProductId((String) itmeMap.get("product_id"));
			item.setSku((String) itmeMap.get("sku"));
			item.setProductType((String) itmeMap.get("product_type"));
			item.setName((String) itmeMap.get("name"));
			item.setWeight((String) itmeMap.get("weight"));
			item.setPrice((String) itmeMap.get("price"));
			item.setCreatedAt((String) itmeMap.get("created_at"));
			item.setUpdatedAt((String) itmeMap.get("updated_at"));
			item.setQtyOrdered((String) itmeMap.get("qty_ordered"));
			item.setRowTotal((String) itmeMap.get("row_total"));
			item.setRowWeight((String) itmeMap.get("row_weight"));
			itemList.add(item);
		}

		OrderInfo info = new OrderInfo();
		info.setOrderId((String) order.get("order_id "));
		info.setCustomerFirstname((String) baMap.get("firstname"));
		info.setCustomerLastname((String) baMap.get("lastname"));
		info.setBillingAddres(tmpAddress);
		info.setPhone((String) baMap.get("lastname"));
		info.setStatusHistoryList(shList);
		info.setItemList(itemList);
		return info;
	}

	/******************************************************************************
	 ******************************** 解析食物縮圖 **********************************
	 ******************************************************************************/
	public static ArrayList<OrderInfoItems> parserOrderInfoItems(ArrayList<OrderInfoItems> itemList, JSONObject jsonObject) throws JSONException {
		for (int i = 0; i < itemList.size(); i++) {
			itemList.get(i).setImgThumbnail(jsonObject.getString(itemList.get(i).getProductId()));
		}
		return itemList;
	}
}