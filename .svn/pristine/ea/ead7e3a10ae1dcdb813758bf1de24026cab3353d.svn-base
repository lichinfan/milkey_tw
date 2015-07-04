package com.kentec.milkbox.homedine.method;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.api.API;
import com.kentec.milkbox.homedine.utils.BitmapUtil;

public class HomeDineCacheLoader {

	private HomeDineCacheListener listener;

	public interface HomeDineCacheListener {
		public void imageCallback(String id, Bitmap imageBitmap);
	}

	public void setOnStoreImgListener(HomeDineCacheListener listener) {
		this.listener = listener;
	}

	private Context mContext;

	private HashMap<String, SoftReference<Bitmap>> imageCache = new HashMap<String, SoftReference<Bitmap>>();
	private HashMap<String, Boolean> isDownloading = new HashMap<String, Boolean>();

	private int imgW;
	private int imgH;

	public HomeDineCacheLoader(Context context, int imgW, int imgH) {
		this.mContext = context;
		this.imgW = imgW;
		this.imgH = imgH;
	}

	public Bitmap loadBitmap(String id, String imageUrl) {
		if (imageUrl != null && !"".equals(imageUrl)) {
			if (!imageCache.containsKey(imageUrl) && isDownloading.get(imageUrl) == null) {
				loadUrlImgStart(id, imageUrl);
			} else if (isDownloading.get(imageUrl) != null) {
				return getImg(id, imageUrl);
			}
		}
		return null;
	}

	private Bitmap getImg(String id, String imageUrl) {
		if (!isDownloading.get(imageUrl) && imageCache.get(imageUrl) != null) {
			if (imageCache.get(imageUrl).get() != null) {
				return imageCache.get(imageUrl).get();
			} else {
				loadUrlImgStart(id, imageUrl);
			}
		}
		return null;
	}

	private void loadUrlImgStart(final String id, final String imageUrl) {

		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				if (listener != null) {
					listener.imageCallback(id, (Bitmap) message.obj);
				}
			}
		};

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					isDownloading.put(imageUrl, true);
					Bitmap bmp = null;
					if (imageUrl == null || "".equals(imageUrl)) {
						bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.dine_transparent);
					} else {
						bmp = API.getPic(imageUrl, imgW, imgH);
					}

					if (bmp != null) {

						if (imgW > 0 && imgH > 0) {
							bmp = BitmapUtil.ImageCrop(bmp);
							bmp = BitmapUtil.zoomImage(bmp, imgW, imgH);
						}

						imageCache.put(imageUrl, new SoftReference<Bitmap>(bmp));
						handler.sendMessage(handler.obtainMessage(0, bmp));
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					isDownloading.put(imageUrl, false);
				}

			}
		}).start();

	}
}