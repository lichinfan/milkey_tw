package com.kentec.milkbox.api;

import org.json.JSONException;
import org.json.JSONObject;

import com.kentec.milkbox.data.RestResult;

public class BaseAPIParser {

	public static RestResult parserRestResult(String jsonStr) throws JSONException {
		JSONObject obj = new JSONObject(jsonStr);
		RestResult rs = new RestResult();
		rs.setCode(obj.getInt("code"));
		rs.setMsg(obj.getString("msg"));
		rs.setData(obj.getString("data"));
		return rs;
	}
}
