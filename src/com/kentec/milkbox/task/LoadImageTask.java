package com.kentec.milkbox.task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.homedine.network.HttpConnection;
import com.kentec.milkbox.homedine.utils.BitmapUtil;

public class LoadImageTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {
	
	private final static String TAG = "LoadImageTask"; 
	
	private String mUrl;
	private int mWidth;
	private int mHeight;
	private Bitmap mBitmap;
	private ImageView mImageView;

	public LoadImageTask(ImageView imageView, String url, int width, int height) {
		this.mImageView = imageView;
		this.mUrl = url;
		this.mWidth = width;
		this.mHeight = height;
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		try {
			byte[] imgResult = HttpConnection.getAPIresult(mUrl, null);
		    mBitmap = BitmapFactory.decodeByteArray(imgResult, 0, imgResult.length, BitmapUtil.getBestOption(imgResult, mWidth, mHeight));
			mBitmap = BitmapUtil.zoomImage(mBitmap, mWidth, mHeight);
			return OK;
		} catch (Exception e) {
			Log.e(TAG, e.toString());
			return ERROR;
		}	
//		} finally {
//			Log.e(TAG, "return ERROR");
//			return ERROR;
//		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (isCancelled()) {
			return;
		}
		if (result == OK) {
			mImageView.setImageBitmap(mBitmap);
		}
	}

}
