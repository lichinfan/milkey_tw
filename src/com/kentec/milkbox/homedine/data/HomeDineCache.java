package com.kentec.milkbox.homedine.data;

import java.util.ArrayList;
import java.util.HashMap;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.method.HomeDineCacheLoader;
import com.kentec.milkbox.homedine.method.HomeDineCacheLoader.HomeDineCacheListener;
import com.kentec.milkbox.homedine.utils.DisplayUtil;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineCache {

	private HomeDineDeliveryActivity mActivity;

	private HomeDineCacheLoader storePic;
	private HomeDineCacheLoader storeThumb;
	private HomeDineCacheLoader foodPic;
	private HomeDineCacheLoader foodThumb;

	private ArrayList<StoreData> storeList;
	private FoodData fooddata;
	private HashMap<String, Coupon> couponMap = new HashMap<String, Coupon>();

	public HomeDineCache(HomeDineDeliveryActivity context) {
		this.mActivity = context;

		this.storePic = new HomeDineCacheLoader(context, DisplayUtil.srcWPxToPx(context, 400), DisplayUtil.srcHPxToPx(context, 400));
		this.storeThumb = new HomeDineCacheLoader(context, DisplayUtil.srcWPxToPx(context, 200), DisplayUtil.srcHPxToPx(context, 200));
		this.foodPic = new HomeDineCacheLoader(context, DisplayUtil.srcWPxToPx(context, 400), DisplayUtil.srcHPxToPx(context, 400));
		this.foodThumb = new HomeDineCacheLoader(context, DisplayUtil.srcWPxToPx(context, 200), DisplayUtil.srcHPxToPx(context, 200));

		this.storeThumb.setOnStoreImgListener(StoreThumbListener);
		this.storePic.setOnStoreImgListener(StorePicListener);
		// this.foodThumb.setOnStoreImgListener(foodThumbListener);
		this.foodPic.setOnStoreImgListener(foodPicListener);
	}

	/***************************************
	 ************** 顯示店家縮圖 *************
	 ***************************************/
	public void showStoreThumb(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		showBitmap(imageView, storeThumb.loadBitmap(id, url));
	}

	private HomeDineCacheListener StoreThumbListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			try {
				ImageView imageViewByTag = (ImageView) mActivity.getViewGroup().getStoreGridView().findViewWithTag(idTag);
				if (imageViewByTag != null && imageBitmap != null) {
					imageViewByTag.setImageBitmap(imageBitmap);
				}
			} catch (Exception e) {
				DEBUG.c(e);
			}
		}
	};

	/***************************************
	 ************** 顯示店家大圖 *************
	 ***************************************/
	public void showStorePic(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		showBitmap(imageView, storePic.loadBitmap(id, url));
	}

	private HomeDineCacheListener StorePicListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			try {
				if (idTag.equals(mActivity.getProperty().getNowStoreId())) {
					ImageView imageView = (ImageView) mActivity.getViewGroup().getStoreImageView();
					if (imageView != null && imageBitmap != null) {
						imageView.setImageBitmap(imageBitmap);
					}
				}
			} catch (Exception e) {
				DEBUG.c(e);
			}
		}
	};

	/***************************************
	 ************** 顯示食物縮圖 *************
	 ***************************************/
	public void showfoodThumb(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		foodThumb.setOnStoreImgListener(foodThumbListener);
		showBitmap(imageView, foodThumb.loadBitmap(id, url));
	}

	private HomeDineCacheListener foodThumbListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			try {
				ImageView imageViewByTag = (ImageView) mActivity.getViewGroup().getFoodTwoWaygridview().findViewWithTag(idTag);

				if (imageViewByTag != null && imageBitmap != null) {
					imageViewByTag.setImageBitmap(imageBitmap);
				}
			} catch (Exception e) {
				DEBUG.c(e);
			}

		}
	};

	/***************************************
	 ************** 顯示食物大圖 *************
	 ***************************************/
	public void showFoodPic(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		showBitmap(imageView, foodPic.loadBitmap(id, url));
	}

	private HomeDineCacheListener foodPicListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			ImageView imageView = (ImageView) mActivity.getViewGroup().getFoodImageView();
			if (imageView != null && imageBitmap != null) {
				imageView.setImageBitmap(imageBitmap);
			}
		}
	};

	/***************************************
	 **************** 顯示圖片 ***************
	 ***************************************/
	private void showBitmap(ImageView imageView, Bitmap bitmap) {
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
		} else {
			imageView.setImageBitmap(null);
		}
	}

	/***************************************
	 ************** 取得店家清單 *************
	 ***************************************/
	public ArrayList<StoreData> getStoreList() {
		return storeList;
	}

	public void setStoreList(ArrayList<StoreData> storeList) {
		this.storeList = storeList;
	}

	/***************************************
	 ************** 取得店家資料 *************
	 ***************************************/
	public StoreData getStore(String storeId) {
		for (int i = 0; i < storeList.size(); i++) {
			if (storeList.get(i).getStoreID().equals(storeId)) {
				return storeList.get(i);
			}
		}
		return null;
	}

	/***************************************
	 ************** 取得食物清單 *************
	 ***************************************/
	public FoodData getFoodData() {
		return fooddata;
	}

	public void setFoodData(FoodData fooddata) {
		this.fooddata = fooddata;
	}

	public void addFoodData(FoodData fooddata) {
		this.fooddata.addFoodList(fooddata.getFoodList());
	}

	/***************************************
	 ************ 顯示購物車縮圖 *************
	 ***************************************/
	public void showCartThumb(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		foodThumb.setOnStoreImgListener(cartThumbListener);
		showBitmap(imageView, foodThumb.loadBitmap(id, url));
	}

	private HomeDineCacheListener cartThumbListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			try {
//				ImageView imageViewByTag = (ImageView) mActivity.getViewGroup().getProgressDialogFragment().getMainViewPager().findViewWithTag(idTag);
				ImageView imageViewByTag = (ImageView) mActivity.getViewGroup().getMealListDioLog().getFoodListView().findViewWithTag(idTag);

				if (imageViewByTag != null && imageBitmap != null) {
					imageViewByTag.setImageBitmap(imageBitmap);
				}
			} catch (Exception e) {
				DEBUG.c(e);
			}
		}
	};

	/***************************************
	 ************** 顯示進度縮圖 *************
	 ***************************************/
	public void showfoodProgressThumb(String id, String url, ImageView imageView) {
		imageView.setTag(id);
		foodThumb.setOnStoreImgListener(foodThumbProgressListener);
		showBitmap(imageView, foodThumb.loadBitmap(id, url));
	}

	private HomeDineCacheListener foodThumbProgressListener = new HomeDineCacheListener() {
		@Override
		public void imageCallback(String idTag, Bitmap imageBitmap) {
			try {
				ImageView imageViewByTag = (ImageView) mActivity.getViewGroup().getProgressDialogFragment().getMainViewPager().findViewWithTag(idTag);

				if (imageViewByTag != null && imageBitmap != null) {
					imageViewByTag.setImageBitmap(imageBitmap);
				}
			} catch (Exception e) {
				DEBUG.c(e);
			}
		}
	};

	public HashMap<String, Coupon> getCouponMap() {
		return couponMap;
	}

	public void setCouponMap(HashMap<String, Coupon> couponMap) {
		this.couponMap = couponMap;
	}
}
