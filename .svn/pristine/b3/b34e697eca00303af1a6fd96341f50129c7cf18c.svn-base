package com.kentec.milkbox.utils;

import java.net.UnknownHostException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.kentec.milkbox.CFG;

public class MyHttpClient {
	
	public static String post(String url , List<NameValuePair> param) throws Exception  {
		
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(param, "UTF-8");
		HttpPost httppost = new HttpPost(url);
		httppost.setEntity(entity);

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpParams hp = httpclient.getParams();
		HttpConnectionParams.setConnectionTimeout(hp, CFG.CONNECT_TIMEOUT);
		HttpConnectionParams.setSoTimeout(hp, CFG.SOCKET_TIMEOUT);

		HttpResponse response = httpclient.execute(httppost);
		if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			throw new UnknownHostException();				
		}
		
		return EntityUtils.toString(response.getEntity());
	}
	
}
