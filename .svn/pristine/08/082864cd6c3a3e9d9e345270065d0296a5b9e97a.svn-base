package com.kentec.milkbox.coupon.api;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.api.BaseAPIParser;
import com.kentec.milkbox.coupon.data.Coupon;


public class CouponApiParser extends BaseAPIParser {
	
	public static ArrayList<Coupon> getCouponList(String jsonStr) throws JSONException {
		JSONObject obj = new JSONObject(jsonStr);
		JSONArray jsonArr = obj.getJSONArray("list");
		
		Coupon row;				
		ArrayList<Coupon> list = new ArrayList<Coupon>();
		for(int i=0; i<jsonArr.length(); i++ ) {
			obj = jsonArr.getJSONObject(i);
			row = parserCoupon(obj);
			list.add(row);
		}
		
		return list;
	}	
	
	
	private static Coupon parserCoupon(JSONObject json) throws JSONException {
		Coupon coupon = new Coupon();
		coupon.setId(json.getString("id"));
		coupon.setName(json.getString("name"));
		coupon.setDesc(json.getString("description"));											
		coupon.setShortDesc(json.getString("shortDescription"));
		coupon.setImage(json.getString("image"));
		coupon.setThumbnail(json.getString("thumbnail"));
		coupon.setExpDate(json.getString("expDate"));
		coupon.setCode(json.getString("couponCode"));
		coupon.setLinkSku(json.getString("linkSku"));
		coupon.setLinkStoreId(json.getString("linkStoreId"));
		return coupon; 
	}
	
	
	public static double getProductQty(String jsonStr) throws JSONException {
		JSONObject row = new JSONObject(jsonStr);
		return Double.parseDouble(row.getString("qty"));
	}
	
	
	public static String getCouponIdStr(HashMap<?,?> hm) throws Exception {		
		Object[] list = (Object[]) hm.get("items");
		HashMap<?,?> row;
		StringBuffer sb = new StringBuffer();		
		for (int i = 0; i < list.length; i++) {
			row = (HashMap<?,?>) list[i];
			if(i!=0) {
				sb.append(",");
			}
			sb.append(row.get("product_id").toString());			
		}				
		return sb.toString();
	}
	
	public static HashMap<String,Double> getCouponQty(HashMap<?,?> hm) throws Exception {
		Object[] list = (Object[]) hm.get("items");		
		HashMap<?,?> row;
		HashMap<String,Double> result = new HashMap<String,Double>();		
		for (int i = 0; i < list.length; i++) {
			row = (HashMap<?,?>) list[i];
			result.put(row.get("product_id").toString(), Double.valueOf(row.get("qty").toString()));
		}				
		return result;
	}
	
}
