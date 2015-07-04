package com.kentec.milkbox.tv.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.api.BaseAPIParser;
import com.kentec.milkbox.tv.data.TvChannel;


public class TvApiParser extends BaseAPIParser {

	public static ArrayList<TvChannel> getChannelArray(String jsonStr) throws JSONException {		
		return getChannelArray(new JSONArray(jsonStr));		
	}

	public static ArrayList<TvChannel> getChannelArray(JSONArray jsonArr) throws JSONException {
		ArrayList<TvChannel> list = new ArrayList<TvChannel>();
		TvChannel row;
		JSONObject obj;		
		for(int i=0; i<jsonArr.length(); i++) {
			obj = jsonArr.getJSONObject(i);
			row = getTvChannel(obj);
			list.add(row);
		}		
		return list;
	}
	
	private static TvChannel getTvChannel(JSONObject jsonObj) throws JSONException {
		TvChannel category = new TvChannel();
		category.setId(jsonObj.getInt("id"));
		category.setName(jsonObj.getString("name"));
		category.setUrl(jsonObj.getString("url"));
		return category; 
	}
	
}
