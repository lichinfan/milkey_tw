package com.kentec.milkbox.homedine.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;

import com.kentec.milkbox.utils.DEBUG;

public class HttpConnection {

	public static byte[] getAPIresult(String url, MultipartEntity mpEntity) {
		byte[] strResult = null;
		try {
			HttpClient httpclient = SSLConnect.createMyHttpClient();
			HttpResponse response;
			if (mpEntity != null) {
				url = url.replaceAll(" ", "%20");
				DEBUG.e("url", url);
				HttpPost httpRequest = new HttpPost(url.replaceAll(" ", "%20"));
				httpRequest.setEntity(mpEntity);
				response = httpclient.execute(httpRequest);

				DEBUG.e("executing request ", String.valueOf(httpRequest.getRequestLine()));
			} else {
				HttpGet httpRequest = new HttpGet(url.replaceAll(" ", "%20"));
				response = httpclient.execute(httpRequest);

				DEBUG.e("executing request ", String.valueOf(httpRequest.getRequestLine()));
			}

			DEBUG.e("response.getStatusLine()", String.valueOf(response.getStatusLine()));

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				HttpEntity resEntity = response.getEntity();

				long length = resEntity.getContentLength();

				DEBUG.e("length", String.valueOf(length));

				InputStream is = resEntity.getContent();
				if (is != null) {
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					byte[] buf = new byte[1024];

					int ch = -1;
					while ((ch = is.read(buf)) != -1) {
						out.write(buf, 0, ch);
					}
					out.flush();
					strResult = out.toByteArray();

					out.close();
					is.close();

					DEBUG.e("Result", new String(strResult, "UTF-8"));

					resEntity.consumeContent();
				}
			}
			httpclient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strResult;
	}
}