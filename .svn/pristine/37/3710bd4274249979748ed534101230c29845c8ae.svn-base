package com.kentec.milkbox.homedine.method;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.coupon.task.MyCouponTask;
import com.kentec.milkbox.coupon.task.MyCouponTask.OnSucessListener;
import com.kentec.milkbox.data.ActivityResultCode;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.adapter.FoodAdapter;
import com.kentec.milkbox.homedine.adapter.StoreAdapter;
import com.kentec.milkbox.homedine.data.FoodData;
import com.kentec.milkbox.homedine.data.FoodItem;
import com.kentec.milkbox.homedine.data.HomeDineCode;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.data.StoreData;
import com.kentec.milkbox.homedine.task.HomeDineTaskCheckCart;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetFoods;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetFoods.OnFoodsOkListener;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetOrderStatus;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetOrderStatus.OnOrderOkListener;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetShopCartFoods;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetShopCartFoods.OnLoadShoppingCartListener;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetStore;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetStore.OnStoreOkListener;
import com.kentec.milkbox.homedine.task.HomeDineTaskShopCartServerAdd;
import com.kentec.milkbox.homedine.task.HomeDineTaskShopCartServerAdd.OnAddShoppingCartListener;
import com.kentec.milkbox.homedine.utils.AnimUtil;
import com.kentec.milkbox.homedine.view.HomeDineAwesomeDialog;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog;
import com.kentec.milkbox.homedine.view.HomeDineMealsDeleteDioLog.OnDeleteListener;
import com.kentec.milkbox.homedine.view.homedineprogress.HomeDineProgressDialogFragment;
import com.kentec.milkbox.utils.DEBUG;
import com.kentec.milkbox.utils.Format;
import com.kentec.milkbox.utils.ShopCartUtil;
import com.kentec.milkbox.utils.ShopCartUtil.OnCartListener;
import com.kentec.milkbox.view.OrderHistoryDialog;

public class HomeDineMethod {

	private HomeDineDeliveryActivity mActivity;
	private AnimUtil mAnimUtil;

	public HomeDineMethod(HomeDineDeliveryActivity context) {
		this.mActivity = context;
		this.mAnimUtil = new AnimUtil(context);
	}

	public AnimUtil getAnimUtil() {
		return mAnimUtil;
	}

	/***************************************
	 ******** 讀取優惠券以及店家清單 *********
	 ***************************************/
	public void loadCoupon() {
		String couponCartId = mActivity.getCouponCartId();
		if (couponCartId != null) {
			new MyCouponTask(mActivity, new OnSucessListener() {
				@Override
				public void success(ArrayList<Coupon> list) {
					for (int i = 0; i < list.size(); i++) {
						try {
							if (list.get(i).getStatus() != Coupon.USE) {
								mActivity.getCache().getCouponMap().put(list.get(i).getLinkSku(), list.get(i));
							}
						} catch (Exception e) {
							DEBUG.c(e);
						}
					}
					loadStore();
				}

				@Override
				public void noData() {
					loadStore();
				}
			}, couponCartId);
		} else {
			loadStore();
		}
	}

	/***************************************
	 ************** 讀取店家清單 *************
	 ***************************************/
	public void loadStore() {
		new HomeDineTaskGetStore(mActivity, new OnStoreOkListener() {
			@Override
			public void OK(ArrayList<StoreData> storeList) {
				mActivity.getViewGroup().getLoadShopTextView().setText("");
				mActivity.getViewGroup().getLoadShopTextView().setVisibility(View.GONE);
				mActivity.getCache().setStoreList(storeList);
				mActivity.getAdapterGroup().setStoreAdapter(new StoreAdapter(mActivity));
				mActivity.getViewGroup().getStoreGridView().setAdapter(mActivity.getAdapterGroup().getStoreAdapter());
				mAnimUtil.TopIn(mActivity.getViewGroup().getStoreGridView(), 1000);
				mActivity.getViewGroup().getStoreGridView().requestFocus();

				new HomeDineTaskCheckCart(mActivity).execute();
			}

			@Override
			public void Error(String msg) {
				mActivity.getViewGroup().getLoadShopTextView().setText("");
				mActivity.getViewGroup().getLoadShopTextView().setVisibility(View.GONE);
				mActivity.showMsg(msg);
			}
		});
		mActivity.getViewGroup().getRelayout().storeInfo();
		mActivity.getViewGroup().getLoadShopTextView().setText(R.string.homedine_loading_shop);
		mActivity.getViewGroup().getLoadShopTextView().setVisibility(View.VISIBLE);
	}

	/***************************************
	 ************** 重新排序店家 *************
	 ***************************************/
	public void reStoreList(int index) {
		ArrayList<StoreData> tmpList = new ArrayList<StoreData>();
		ArrayList<StoreData> nowList = mActivity.getCache().getStoreList();
		for (int i = index; i < nowList.size(); i++) {
			tmpList.add(nowList.get(i));
		}
		for (int i = 0; i < index; i++) {
			tmpList.add(nowList.get(i));
		}
		mActivity.getCache().setStoreList(tmpList);
		mActivity.getAdapterGroup().getStoreAdapter().notifyDataSetChanged();
	}

	/***************************************
	 ************** 顯示店家資訊 *************
	 ***************************************/
	public void showStore(final StoreData storeInfo) {
		if (mActivity.getProperty().getNowStoreId() == storeInfo.getStoreID()) {
			return;
		}

		mActivity.getProperty().setNowStoreId(storeInfo.getStoreID());

		if (mActivity.getViewGroup().getFoodTwoWaygridview().getVisibility() == View.VISIBLE) {
			mActivity.getViewGroup().getFoodTwoWaygridview().setVisibility(View.GONE);
		}

		if (mActivity.getViewGroup().getFoodInfo().getVisibility() == View.VISIBLE) {
			TranslateAnimation topOut = new TranslateAnimation(0, 0, 0, -mActivity.getProperty().getDisH());
			topOut.setDuration(500);
			topOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					mActivity.getViewGroup().getFoodInfo().setVisibility(View.GONE);
					if (mActivity.getViewGroup().getStoreInfo().getVisibility() != View.VISIBLE)
						mActivity.getViewGroup().getStoreInfo().setVisibility(View.VISIBLE);

					showStoreInfo(storeInfo);
				}
			});
			mActivity.getViewGroup().getFoodInfo().startAnimation(topOut);
		} else if (mActivity.getViewGroup().getStoreInfo().getVisibility() == View.VISIBLE) {
			TranslateAnimation leftOut = new TranslateAnimation(0, -mActivity.getProperty().getDisW(), 0, 0);
			leftOut.setDuration(500);
			leftOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					mActivity.getViewGroup().getFoodInfo().setVisibility(View.GONE);
					if (mActivity.getViewGroup().getStoreInfo().getVisibility() != View.VISIBLE)
						mActivity.getViewGroup().getStoreInfo().setVisibility(View.VISIBLE);

					showStoreInfo(storeInfo);
				}
			});
			mActivity.getViewGroup().getStoreInfo().startAnimation(leftOut);
		} else {
			showStoreInfo(storeInfo);
		}
	}

	private void showStoreInfo(StoreData storeInfo) {
		mActivity.getCache().showStorePic(storeInfo.getStoreID(), storeInfo.getStorePic(), mActivity.getViewGroup().getStoreImageView());

		mActivity.getViewGroup().getStoreNameTextView().setText(storeInfo.getStoreName());
		mActivity.getViewGroup().getStoreAboutTextView().setText(storeInfo.getStoreAbout());
		mAnimUtil.LeftIn(mActivity.getViewGroup().getStoreInfo(), 500);
	}

	/***************************************
	 ************** 搜尋卡路里 *************
	 ***************************************/
	public void changCalores() {
		mActivity.getProperty().setUnder200Calores(!mActivity.getProperty().isUnder200Calores());

		if (mActivity.getProperty().isUnder200Calores()) {
			mActivity.getViewGroup().getUnderTextView().setText(R.string.homedine_allcalores);
		} else {
			mActivity.getViewGroup().getUnderTextView().setText(R.string.homedine_under200calores);
		}

		if (!"".equals(mActivity.getProperty().getNowStoreId())) {
			mActivity.getMethod().loadFoods(mActivity.getProperty().getNowStoreId());
		}
	}

	/***************************************
	 **** 讀取目前購物車數量及食物清單 ****
	 ***************************************/
	public void loadCartAndFoods(final String storeId) {
		new ShopCartUtil(mActivity, storeId, new OnCartListener() {
			@Override
			public void ok(String cartId) {
				new HomeDineTaskGetShopCartFoods(mActivity, cartId, new OnLoadShoppingCartListener() {
					@Override
					public void complete(ArrayList<FoodItem> shopCardFood) {
						addServerCartToCacheCart(storeId, shopCardFood);
						loadFoods(storeId);
					}
				});
			}

			@Override
			public void error() {
			}
		});
	}

	/***************************************
	 *** Server購物車 塞入暫存Meal list ***
	 ***************************************/
	private void addServerCartToCacheCart(String storeId, ArrayList<FoodItem> shopCardFood) {
		if (shopCardFood != null) {
			for (FoodItem foodItem : shopCardFood) {
				mActivity.getMealsList().addFood(storeId, foodItem, (long) foodItem.getQuantity());
			}
		}
	}

	/***************************************
	 ************** 讀取食物清單 *************
	 ***************************************/
	public void loadFoods(String storeId) {
		try {
			mActivity.getViewGroup().getFoodTwoWaygridview().setVisibility(View.GONE);

			mActivity.getViewGroup().getRelayout().foodInfo();
			mActivity.getViewGroup().getLoadFoodTextView().setText(R.string.homedine_loading_food);
			mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.VISIBLE);
			new HomeDineTaskGetFoods(mActivity, storeId, 1, new OnFoodsOkListener() {
				@Override
				public void OK(FoodData fooddata) {
					mActivity.getCache().setFoodData(fooddata);

					mActivity.getViewGroup().getLoadFoodTextView().setText("");
					mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.GONE);

					mActivity.getAdapterGroup().setFoodAdapter(new FoodAdapter(mActivity));

					mActivity.getViewGroup().getRelayout().foodList(fooddata.getFoodList().size());

					mActivity.getViewGroup().getFoodTwoWaygridview().setAdapter(mActivity.getAdapterGroup().getFoodAdapter());
					mActivity.getViewGroup().getFoodTwoWaygridview().setVisibility(View.VISIBLE);
					mAnimUtil.LeftIn(mActivity.getViewGroup().getFoodTwoWaygridview(), 1000);

					mActivity.getProperty().setLoading(false);
					mActivity.getViewGroup().getFoodTwoWaygridview().requestFocus();
				}

				@Override
				public void Error(String msg) {
					mActivity.getViewGroup().getLoadFoodTextView().setText("");
					mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.GONE);
					mActivity.showMsg(msg);
				}
			});
		} catch (Exception e) {
			DEBUG.c(e);
		}
	}

	/***************************************
	 ************** 顯示歷史訂單 *************
	 ***************************************/
	public void showOrderHistory() {
		final String storeId = mActivity.getProperty().getNowStoreId();
		new ShopCartUtil(mActivity, mActivity.getProperty().getNowStoreId(), new OnCartListener() {
			@Override
			public void ok(String cartId) {
				String cid = mActivity.getProperty().getCustomerId();
				new OrderHistoryDialog(mActivity, cid, cartId, storeId).show();
			}

			@Override
			public void error() {
				mActivity.showMsg(R.string.error);
			}
		});
	}

	/***************************************
	 ************** 顯示食物資訊 *************
	 ***************************************/
	public void showFood(int index) {
		mActivity.getProperty().setNowFoodItemId(index);

		mActivity.getMethod().showFoodMore(index);

		final FoodItem food = mActivity.getAdapterGroup().getFoodAdapter().getItem(index);
		if (mActivity.getProperty().getNowFoodId() == food.getId()) {
			return;
		}

		mActivity.getProperty().setNowFoodId(food.getId());
		mActivity.getAdapterGroup().getFoodAdapter().setNowItem(index);

		try {
			if (mActivity.getViewGroup().getStoreInfo().getVisibility() == View.VISIBLE) {
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						TranslateAnimation leftOut = new TranslateAnimation(0, -mActivity.getProperty().getDisW(), 0, 0);
						leftOut.setDuration(500);
						leftOut.setAnimationListener(new AnimationListener() {

							@Override
							public void onAnimationStart(Animation animation) {

							}

							@Override
							public void onAnimationRepeat(Animation animation) {

							}

							@Override
							public void onAnimationEnd(Animation animation) {
								showFoodInfo(food);
							}
						});

						mActivity.getViewGroup().getStoreInfo().startAnimation(leftOut);
					}
				}, 1000);

			} else if (mActivity.getViewGroup().getFoodInfo().getVisibility() == View.VISIBLE) {
				TranslateAnimation topOut = new TranslateAnimation(0, 0, 0, -mActivity.getProperty().getDisH());
				topOut.setDuration(500);
				topOut.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation arg0) {

					}

					@Override
					public void onAnimationRepeat(Animation arg0) {

					}

					@Override
					public void onAnimationEnd(Animation arg0) {
						showFoodInfo(food);
					}
				});
				mActivity.getViewGroup().getFoodInfo().startAnimation(topOut);
			} else {
				showFoodInfo(food);
			}

		} catch (Exception e) {
			DEBUG.c(e);
		}
	}

	private void showFoodInfo(FoodItem food) {
		mActivity.getCache().showFoodPic(food.getId(), food.getPic(), mActivity.getViewGroup().getFoodImageView());

		mActivity.getViewGroup().getFoodNameTextView().setText(food.getName());
		mActivity.getViewGroup().getFoodAboutTextView().setText(food.getAbout());
		mActivity.getViewGroup().getFoodPriceTextView().setText("$" + Format.price(food.getPrice()));

		mActivity.getViewGroup().getProteinTextView().setText(String.valueOf(food.getProtein()) + "g");
		mActivity.getViewGroup().getCarbohydratesTextView().setText(String.valueOf(food.getCarbohydrates()) + "g");
		mActivity.getViewGroup().getSugarTextView().setText(String.valueOf(food.getSugar()) + "g");
		mActivity.getViewGroup().getFatTextView().setText(String.valueOf(food.getFat()) + "g");
		mActivity.getViewGroup().getSaturatedFatTextView().setText(String.valueOf(food.getSaturatedFat()) + "g");
		mActivity.getViewGroup().getTransFatTextView().setText(String.valueOf(food.getTrandFat()) + "g");

		mActivity.getViewGroup().getCholesterolTextView().setText(String.valueOf(food.getCholesterol()) + "mg");
		mActivity.getViewGroup().getSodlumTextView().setText(String.valueOf(food.getSodlum()) + "mg");

		mActivity.getViewGroup().getCaloresTextView().setText(String.valueOf(food.getCalorie()) + " ");
		mActivity.getViewGroup().getRunMinTextView().setText(String.valueOf(food.getCalorie() / 10) + " ");

		mActivity.getViewGroup().getTotalPriceTextView().setText("$ " + String.valueOf(food.getPrice() * 3));

		long quantity = mActivity.getMealsList().getQuantity(mActivity.getProperty().getNowStoreId(), mActivity.getProperty().getNowFoodId());
		mActivity.getViewGroup().getMealsTextView().setText(String.valueOf(quantity));
		mActivity.getViewGroup().getTotalPriceTextView().setText("$ " + String.valueOf(quantity * food.getPrice()));

		mActivity.getViewGroup().getStoreInfo().setVisibility(View.GONE);
		mAnimUtil.TopIn(mActivity.getViewGroup().getFoodInfo(), 500);
		mActivity.getViewGroup().getFoodInfo().setVisibility(View.VISIBLE);
	}

	private void updateFoodInfoMeals() {
		String tmpStore = mActivity.getProperty().getNowStoreId();
		String tmpFood = mActivity.getProperty().getNowFoodId();
		long quantity = mActivity.getMealsList().getQuantity(tmpStore, tmpFood);
		double price = mActivity.getMealsList().getPrice(tmpStore, tmpFood);
		mActivity.getViewGroup().getMealsTextView().setText(String.valueOf(quantity));
		mActivity.getViewGroup().getTotalPriceTextView().setText("$ " + String.valueOf(quantity * price));
	}

	/***************************************
	 ************** 交換食物顯示 *************
	 ***************************************/
	public void changeDN() {
		if (mActivity.getProperty().isDescription()) {
			mActivity.getViewGroup().getDescription().setVisibility(View.INVISIBLE);
			mActivity.getViewGroup().getNutrition().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getSwitchimageView().setImageResource(R.drawable.dine_nutrition);
			mActivity.getProperty().setDescription(false);
		} else {
			mActivity.getViewGroup().getNutrition().setVisibility(View.INVISIBLE);
			mActivity.getViewGroup().getDescription().setVisibility(View.VISIBLE);
			mActivity.getViewGroup().getSwitchimageView().setImageResource(R.drawable.dine_description);
			mActivity.getProperty().setDescription(true);
		}
	}

	/***************************************
	 ************* 讀取更多食物清單 ***********
	 ***************************************/
	public void showFoodMore(int position) {
		if (!mActivity.getProperty().isLoading()) {
			FoodData foodData = mActivity.getCache().getFoodData();
			boolean isOverBuff = (foodData.getFoodList().size() - position) < CFG.FOOD_BUFF_SIZE;
			boolean isHaveMore = foodData.getFoodList().size() < foodData.getFoodCount();

			if (isOverBuff && isHaveMore) {
				mActivity.getProperty().setLoading(true);

				int page = (foodData.getFoodList().size() / CFG.FOOD_PAGE_SIZE) + 1;
				DEBUG.e("showFoodMore", "page=" + String.valueOf(page));

				new HomeDineTaskGetFoods(mActivity, mActivity.getProperty().getNowStoreId(), page, new OnFoodsOkListener() {
					@Override
					public void OK(FoodData fooddata) {
						mActivity.getViewGroup().getLoadFoodTextView().setText("");
						mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.GONE);

						mActivity.getCache().addFoodData(fooddata);
						mActivity.getViewGroup().getRelayout().foodList(mActivity.getCache().getFoodData().getFoodList().size());
						mActivity.getAdapterGroup().getFoodAdapter().notifyDataSetChanged();
						mActivity.getViewGroup().getFoodTwoWaygridview().setSelection(mActivity.getAdapterGroup().getFoodAdapter().getNowItem());

						mActivity.getProperty().setLoading(false);
					}

					@Override
					public void Error(String msg) {
						mActivity.getViewGroup().getLoadFoodTextView().setText("");
						mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.GONE);
						mActivity.showMsg(msg);
					}
				});
				mActivity.getViewGroup().getLoadFoodTextView().setText(R.string.homedine_loading_food_more);
				mActivity.getViewGroup().getLoadFoodTextView().setVisibility(View.VISIBLE);
			}
		}
	}

	/***************************************
	 ************* 顯示選擇數量畫面 ***********
	 ***************************************/
	public void showMeals() {
		mActivity.getViewGroup().getMealsDialog().show();
		mActivity.getViewGroup().getRelayout().foodMeals(mActivity.getViewGroup().getMealsDialog());
		mActivity.getListener().startMealsDialogListener();
	}

	/***************************************
	 ************* 增加Meals數量 ***********
	 ***************************************/
	public void mealsAdd() {
		mActivity.getViewGroup().getMealsDialog().addQuantity();
		updateMeals();
	}

	/***************************************
	 ************* 減少Meals數量 ***********
	 ***************************************/
	public void mealsMinus() {
		mActivity.getViewGroup().getMealsDialog().minusQuantity();
		updateMeals();
	}

	/***************************************
	 ************* 更新Meals數量 ***********
	 ***************************************/
	public void updateMeals() {
		mActivity.getViewGroup().getMealsDialog().updateMeals();
	}

	/***************************************
	 ************ 加入Meal List一筆 **********
	 ***************************************/
	public void addMealList() {
		String store = mActivity.getProperty().getNowStoreId();
		FoodItem food = mActivity.getCache().getFoodData().getFoodList().get(mActivity.getProperty().getNowFoodItemId());
		long quantity = mActivity.getViewGroup().getMealsDialog().getQuantity();
		mActivity.getMealsList().addFood(store, food, quantity);
		mActivity.getViewGroup().getMealsDialog().dismiss();
		mActivity.getViewGroup().getMealsTextView().setText(String.valueOf(quantity));
		mActivity.getViewGroup().getTotalPriceTextView().setText("$ " + String.valueOf(quantity * food.getPrice()));
	}

	/***************************************
	 ************ 刪除Meal List一筆 **********
	 ***************************************/
	public void delMealList(final String store, final String foodId) {
		final HomeDineMealsDeleteDioLog mdd = new HomeDineMealsDeleteDioLog(mActivity);
		mdd.setListener(new OnDeleteListener() {
			@Override
			public void rightButton() {
			}

			@Override
			public void leftButton() {
				String msg = mActivity.getMealsList().getMealsFood(store, foodId).getFoodItem().getName();
				msg += " " + mActivity.getString(R.string.homedine_meals_delete_ok).toString();
				mActivity.getMealsList().delFood(store, foodId);
				mActivity.getViewGroup().getMealListDioLog().update();
				updateFoodInfoMeals();

				mActivity.showNoImgMsg(msg);
			}
		});
		mdd.show();
	}

	/***************************************
	 ************ 清空 Meal List **********
	 ***************************************/
	public void clearMealList(final String store) {
		mActivity.getMealsList().clearStoreFood(store);
		updateFoodInfoMeals();
	}

	/***************************************
	 ************ 顯示Meal List畫面 **********
	 ***************************************/
	public void showMealList() {
		if (mActivity.getProperty().getNowStoreId() != null && !"".equals(mActivity.getProperty().getNowStoreId())) {
			mActivity.getViewGroup().getMealListDioLog().show();
			mActivity.getViewGroup().getRelayout().MealList(mActivity.getViewGroup().getMealListDioLog());
			mActivity.getListener().startMealListDialogListener();
		} else {
			mActivity.showMsg(mActivity.getText(R.string.homedine_no_select_store).toString());
		}
	}

	/***************************************
	 *************** 檢 查 訂 單 *************
	 ***************************************/
	public void checkoutOrder(int resultCode, Intent data) {
		switch(resultCode){
			case ActivityResultCode.OK:
				if (data != null) {
					Bundle b = data.getExtras();
					if (b != null) {
						String storeId = b.getString("storeId");
						if (storeId != null) {
							clearMealList(storeId);
							new HomeDineAwesomeDialog(mActivity).show();
						} else {
							mActivity.showMsg(R.string.error);
						}
					} else {
						mActivity.showMsg(R.string.error);
					}
				} else {
					mActivity.showMsg(R.string.error);
				}
				break;

			case ActivityResultCode.ERROR:
				mActivity.showMsg(R.string.error);
				break;

			case ActivityResultCode.CANCEL:
				mActivity.showMsg(R.string.cancel);
				break;

		}

	}

	/***************************************
	 ************ 加入server購物車 **********
	 ***************************************/
	public void addServerShoppingCart(final String storeId) {
		mActivity.getViewGroup().getMealListDioLog().checkoutLoading(true);
		// 取得shopping cart ID
		new ShopCartUtil(mActivity, storeId, new OnCartListener() {
			@Override
			public void ok(final String cartId) {
				DEBUG.e("cartId", cartId);
				// 取得shopping cart ID完畢開始加入購物車
				addServerShoppingCart(storeId, cartId);
			}

			@Override
			public void error() {
				mActivity.getViewGroup().getMealListDioLog().checkoutLoading(false);
				mActivity.showMsg(R.string.error);
			}
		});
	}

	/***************************************
	 ************ 加入server購物車 **********
	 ***************************************/
	public void addServerShoppingCart(final String storeId, final String cartId) {
		new HomeDineTaskShopCartServerAdd(mActivity, cartId, storeId, new OnAddShoppingCartListener() {
			@Override
			public void complete(boolean success, String msg) {
				mActivity.getViewGroup().getMealListDioLog().checkoutLoading(false);
				if (success) {
					mActivity.getViewGroup().getMealListDioLog().noAniDismiss();
					Bundle b = new Bundle();
					b.putString("cartId", cartId);
					b.putString("storeId", storeId);
					mActivity.goActivityResult(CheckoutActivity.class, b, HomeDineCode.RESULT_CHECKOUT);
				} else {
					mActivity.showMsg(msg);
				}
			}
		});
	}

	/***************************************
	 ************ 讀取server購物車 **********
	 ***************************************/
	public void loadServerShoppingCart(String cartId) {
		// new HomeDineTaskShopCartServerLoad(mActivity, cartId);
	}

	/***************************************
	 ************* 顯示訂單進度清單 ***********
	 ***************************************/
	public void showProgress() {
		final String storeId = mActivity.getProperty().getNowStoreId();
		if (mActivity.getCache().getStore(storeId) == null) {
			mActivity.showMsg(R.string.homedine_no_select_store);
			return;
		}

		mActivity.getViewGroup().setProgressDialogFragment(new HomeDineProgressDialogFragment(mActivity));
		final HomeDineProgressDialogFragment progressDF = mActivity.getViewGroup().getProgressDialogFragment();
		FragmentManager sfm = mActivity.getSupportFragmentManager();
		progressDF.setStore(storeId);
		progressDF.setStyle(R.style.Dialog_HomeDine_progress, R.style.Dialog_HomeDine_progress);
		progressDF.show(sfm, "progress");
		sfm.executePendingTransactions();
		mActivity.getViewGroup().getRelayout().progress(progressDF);

		progressDF.isLoading(true);
		new HomeDineTaskGetOrderStatus(mActivity, storeId, new OnOrderOkListener() {
			@Override
			public void OK(ArrayList<OrderData> orderList) {
				progressDF.isLoading(false);
				if (orderList.size() > 0) {
					mActivity.getOrderList().put(storeId, orderList);
					progressDF.upPtogressList();
				} else {
					progressDF.dismiss();
					mActivity.showMsg(R.string.homedine_progress_no_order);
				}
			}

			@Override
			public void Error(String msg) {
				mActivity.showMsg(msg);
			}
		});
	}

	/***************************************
	 ************ 顯示進度文字顏色 ***********
	 ***************************************/
	public void showProgressColor(TextView view, String status) {
		if ("pending".equals(status)) {
			view.setTextColor(0xffff0000);
		} else if ("processing".equals(status)) {
			view.setTextColor(0xff0066FF);
		} else if ("complete".equals(status)) {
			view.setTextColor(0xff33FF33);
		} else {
			view.setTextColor(0xff000000);
		}
		view.setText(status);
	}

}