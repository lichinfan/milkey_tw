package com.kentec.milkbox.grocery.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.api.BaseAPIParser;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.grocery.data.OrderHistory;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;


public class GroceryApiParser extends BaseAPIParser {

	public static ArrayList<Category> getCategoryArray(String jsonStr) throws JSONException {		
		return getCategoryArray(new JSONArray(jsonStr));		
	}

	public static ArrayList<Category> getCategoryArray(JSONArray jsonArr) throws JSONException {
		ArrayList<Category> list = new ArrayList<Category>();
		Category row;
		JSONObject obj;		
		for(int i=0; i<jsonArr.length(); i++) {
			obj = jsonArr.getJSONObject(i);
			row = getCategory(obj);
			list.add(row);
		}		
		return list;
	}

	public static String getCategoryString(ArrayList<Category> list) throws JSONException {
		JSONObject obj;		
		JSONArray jList = new JSONArray();
		for(Category row : list) {
			obj = new JSONObject();			
			obj.put("id", row.getId());
			obj.put("name", row.getName());
			obj.put("thumbnail", row.getThumbnail());
			obj.put("image", row.getImage());
			obj.put("productCount", row.getProductCount());
			jList.put(obj);
		}		
		return jList.toString();
	}
	
	private static Category getCategory(JSONObject jsonObj) throws JSONException {
		Category category = new Category();
		category.setId(jsonObj.getString("id"));
		category.setName(jsonObj.getString("name"));
		category.setThumbnail(jsonObj.getString("thumbnail"));
		category.setImage(jsonObj.getString("image"));
		category.setProductCount(jsonObj.getInt("productCount"));
		return category; 
	}

	
	public static ProductResult getProduct(String jsonStr) throws JSONException {
		ProductResult pr = new ProductResult();
		JSONObject obj = new JSONObject(jsonStr);
		pr.setPageSize(obj.getInt("pageSize"));
		pr.setTotalNum(obj.getInt("totalNum"));		
		ArrayList<Product> list = getProductArray(new JSONArray(obj.getString("list")));
		pr.setList(list);		
		return pr;
	}
		
	private static ArrayList<Product> getProductArray(JSONArray jsonArr) throws JSONException {
		ArrayList<Product> list = new ArrayList<Product>();
		Product row;		
		JSONObject obj;
		for(int i=0; i<jsonArr.length(); i++) {
			obj = jsonArr.getJSONObject(i);
			row = parserProduct(obj);
			list.add(row);
		}		
		return list;
	}
		
	public static Product parserProduct(JSONObject jsonObj) throws JSONException {
		Product product = new Product();
		product.setId(jsonObj.getString("id"));
		product.setName(jsonObj.getString("name"));
		product.setDescription(jsonObj.getString("description"));
		product.setThumbnail(jsonObj.getString("thumbnail"));
		product.setImage(jsonObj.getString("image"));		
		product.setPrice(Double.parseDouble(jsonObj.getString("price")));
		product.setSku(jsonObj.getString("sku"));
		return product; 
	}
	
	public static OrderHistory parserOrderHistory(HashMap<?,?> hm) throws JSONException {
		OrderHistory oh = new OrderHistory();
		oh.setNum(hm.get("increment_id").toString());		
		oh.setDate(hm.get("created_at").toString());
		oh.setShipTo(hm.get("shipping_firstname").toString()+hm.get("shipping_lastname").toString());
		oh.setTotal(Double.parseDouble(hm.get("grand_total").toString()));	
		oh.setStatus(hm.get("status").toString());
		return oh; 
	}
	
	public static String getStoreId(String jsonStr) throws JSONException {		
		JSONObject jobj = new JSONObject(jsonStr);
		return jobj.getString("storeId");
	}
	
}
