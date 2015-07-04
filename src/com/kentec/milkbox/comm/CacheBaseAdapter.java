package com.kentec.milkbox.comm;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.kentec.milkbox.homedine.network.HttpConnection;
import com.kentec.milkbox.homedine.utils.BitmapUtil;
import com.kentec.milkbox.utils.DEBUG;

public abstract class CacheBaseAdapter extends BaseAdapter {

	protected HashMap<String, SoftReference<Bitmap>> mImageCache = new HashMap<String, SoftReference<Bitmap>>();
		
	protected class LoadImageAsyncTask extends AsyncTask<String, Bitmap, Bitmap> implements AsyncTaskCode {
		
		private String mUrl;
		private WeakReference<ImageView> mImageView;
		
		private int mWidth;
		private int mHeight;
		
		public LoadImageAsyncTask(String url, ImageView imageView, int width, int height) {
			mUrl = url;
			mImageView = new WeakReference<ImageView>(imageView);
			mWidth = width;
			mHeight = height;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				Bitmap bitmap;
				
				if(mImageCache.containsKey(mUrl)) {
					bitmap = mImageCache.get(mUrl).get();
				} else {
					bitmap = null;
				}
				
				if (bitmap == null) {
					DEBUG.e("mUrl", mUrl);
					byte[] imgResult = HttpConnection.getAPIresult(mUrl, null);
				    Bitmap b = BitmapFactory.decodeByteArray(imgResult, 0, imgResult.length, BitmapUtil.getBestOption(imgResult, mWidth, mHeight));
				    b = BitmapUtil.zoomImage(b, mWidth, mHeight);
					mImageCache.put(mUrl, new SoftReference<Bitmap>(b));
				}
			} catch (Exception e) {
				e.printStackTrace() ;
				cancel(true);
				return null;
			}
			
			return mImageCache.get(mUrl).get();
		}
				
		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (isCancelled()) {
				bitmap = null;
			}
			if (mImageView != null) {
				ImageView imageView = mImageView.get();
				AsyncTask asyncTask = getAsyncTask(imageView);
				if (this == asyncTask) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}				
	}	

	public class DownloadedDrawable extends ColorDrawable {
		private final WeakReference<AsyncTask> asyncTaskReference;
		public DownloadedDrawable(AsyncTask bitmapDownloaderTask) {
			super(Color.TRANSPARENT);
			asyncTaskReference = new WeakReference<AsyncTask>(bitmapDownloaderTask);
		}

		public AsyncTask getAsyncTask() {
			return asyncTaskReference.get();
		}
	}
	
    protected AsyncTask getAsyncTask(ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadedDrawable = (DownloadedDrawable)drawable;
                return downloadedDrawable.getAsyncTask();
            }
        }
        return null;
    }	
	
	public void clearCache() {	
		Iterator<Entry<String, SoftReference<Bitmap>>> itr = mImageCache.entrySet().iterator();
		Map.Entry<String, SoftReference<Bitmap>> entry;
		while(itr.hasNext()) {
			entry = (Map.Entry<String, SoftReference<Bitmap>>) itr.next();
			Bitmap b = (Bitmap)entry.getValue().get();
			if(b!=null) {
				b.recycle();
			}
		}
		mImageCache.clear();
	}

}
