package com.kentec.milkbox.radio.api;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.api.BaseAPIParser;
import com.kentec.milkbox.radio.data.RadioChannel;


public class RadioApiParser extends BaseAPIParser {

	public static ArrayList<RadioChannel> getChannelArray(String jsonStr) throws JSONException {		
		return getChannelArray(new JSONArray(jsonStr));		
	}

	public static ArrayList<RadioChannel> getChannelArray(JSONArray jsonArr) throws JSONException {
		ArrayList<RadioChannel> list = new ArrayList<RadioChannel>();
		RadioChannel row;
		JSONObject obj;		
		for(int i=0; i<jsonArr.length(); i++) {
			obj = jsonArr.getJSONObject(i);
			row = getRadioChannel(obj);
			list.add(row);
		}		
		return list;
	}
	
	private static RadioChannel getRadioChannel(JSONObject jsonObj) throws JSONException {
		RadioChannel category = new RadioChannel();
		category.setId(jsonObj.getInt("id"));
		category.setType(jsonObj.getString("type"));
		category.setName(jsonObj.getString("name"));
		category.setUrl(jsonObj.getString("url"));
		category.setImage(jsonObj.getString("image"));
		category.setInformation(jsonObj.getString("information"));
		return category; 
	}
	
}
