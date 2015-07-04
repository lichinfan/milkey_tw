package com.kentec.milkbox.homedine.method;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.StoreData;
import com.kentec.milkbox.homedine.view.HomeDineMealListDioLog.OnCheckoutListener;
import com.kentec.milkbox.thirdparty.TwoWayAdapterView;
import com.kentec.milkbox.thirdparty.TwoWayGridView;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineListener {

	private HomeDineDeliveryActivity mActivity;

	public HomeDineListener(HomeDineDeliveryActivity context) {
		this.mActivity = context;

		mActivity.getViewGroup().getStoreGridView().setOnItemSelectedListener(StoreSelectedListener);
		mActivity.getViewGroup().getStoreGridView().setOnItemClickListener(StoreClickListener);
		mActivity.getViewGroup().getFoodTwoWaygridview().setOnItemClickListener(FoodClickListener);
		mActivity.getViewGroup().getFoodTwoWaygridview().setOnItemSelectedListener(FoodSelectedListener);

		mActivity.getViewGroup().getSwitchimageView().setOnClickListener(ClickEvent);
		mActivity.getViewGroup().getUnderLinear().setOnClickListener(ClickEvent);

		mActivity.getViewGroup().getProgressLinear().setOnClickListener(ClickEvent);
		mActivity.getViewGroup().getHistoryLinear().setOnClickListener(ClickEvent);

		mActivity.getViewGroup().getCheckoutLinear().setOnClickListener(ClickEvent);
		mActivity.getViewGroup().getMealsLinear().setOnClickListener(ClickEvent);
	}

	/***************************************
	 ************** 監聽店家選擇 *************
	 ***************************************/
	private OnItemSelectedListener StoreSelectedListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mActivity.getMethod().showStore(mActivity.getAdapterGroup().getStoreAdapter().getItem(arg2));
			mActivity.reTimeOut();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	/***************************************
	 ************** 監聽按鈕點選 *************
	 ***************************************/
	private OnClickListener ClickEvent = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == mActivity.getViewGroup().getSwitchimageView()) {
				mActivity.getMethod().changeDN();
			} else if (v == mActivity.getViewGroup().getUnderLinear()) {
				mActivity.getMethod().changCalores();
			} else if (v == mActivity.getViewGroup().getProgressLinear()) {
				mActivity.getMethod().showProgress();
			} else if (v == mActivity.getViewGroup().getHistoryLinear()) {
				mActivity.getMethod().showOrderHistory();
			} else if (v == mActivity.getViewGroup().getCheckoutLinear()) {
				mActivity.getMethod().showMealList();
			} else if (v == mActivity.getViewGroup().getMealsLinear()) {
				mActivity.getMethod().showMeals();
			}
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ************** 監聽店家點選 *************
	 ***************************************/
	private OnItemClickListener StoreClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (!mActivity.getProperty().isLoading()) {
				StoreData tmpStore = mActivity.getCache().getStoreList().get(arg2);
				mActivity.getMethod().reStoreList(arg2);
				mActivity.getViewGroup().getStoreGridView().setSelection(0);
				mActivity.getMethod().showStore(tmpStore);
				mActivity.getMethod().loadCartAndFoods(tmpStore.getStoreID());
			}
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ************** 監聽食物點選 *************
	 ***************************************/
	private TwoWayGridView.OnItemClickListener FoodClickListener = new TwoWayGridView.OnItemClickListener() {
		@Override
		public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
			DEBUG.e("FoodClickListener", String.valueOf(position));
			mActivity.getMethod().showFood(position);
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ************** 監聽食物選擇 *************
	 ***************************************/
	private TwoWayGridView.OnItemSelectedListener FoodSelectedListener = new TwoWayGridView.OnItemSelectedListener() {
		@Override
		public void onItemSelected(TwoWayAdapterView<?> parent, View view, int position, long id) {
			mActivity.getMethod().showFood(position);
			mActivity.reTimeOut();
		}

		@Override
		public void onNothingSelected(TwoWayAdapterView<?> parent) {

		}
	};

	/***************************************
	 ************* 監聽食物列表焦點 ***********
	 ***************************************/
	private final OnFocusChangeListener FoodFocusListener = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View arg0, boolean arg1) {
			DEBUG.e("FoodFocusListener", String.valueOf(arg1));
			// if (arg1) {
			// mActivity.getViewGroup().getFoodTwoWaygridview().setSelection(mActivity.getProperty().getNowFoodItem());
			// }
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ************** 開始監聽Meal *************
	 ***************************************/
	public void startMealsDialogListener() {
		mActivity.getViewGroup().getMealsDialog().getArrowL().setOnClickListener(MealClickEvent);
		mActivity.getViewGroup().getMealsDialog().getArrowR().setOnClickListener(MealClickEvent);
		mActivity.getViewGroup().getMealsDialog().getAddLinear().setOnClickListener(MealClickEvent);
	}

	/***************************************
	 ************** 監聽Meal按鈕 *************
	 ***************************************/
	public OnClickListener MealClickEvent = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v == mActivity.getViewGroup().getMealsDialog().getArrowL()) {
				mActivity.getMethod().mealsMinus();
			} else if (v == mActivity.getViewGroup().getMealsDialog().getArrowR()) {
				mActivity.getMethod().mealsAdd();
			} else if (v == mActivity.getViewGroup().getMealsDialog().getAddLinear()) {
				mActivity.getMethod().addMealList();
			}
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ************ 開始監聽Meal List **********
	 ***************************************/
	public void startMealListDialogListener() {
		mActivity.getViewGroup().getMealListDioLog().getFoodListView().setOnItemClickListener(MealListClickListener);
		mActivity.getViewGroup().getMealListDioLog().getFoodListView().setOnItemSelectedListener(MealListSelectListener);
		mActivity.getViewGroup().getMealListDioLog().setListener(checkoutEvent);
	}

	/***************************************
	 ************ 監聽Meal list點選 **********
	 ***************************************/
	private OnItemSelectedListener MealListSelectListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			mActivity.getAdapterGroup().getMealListAdapter().setShowDel(arg2);
			// mActivity.getAdapterGroup().getMealListAdapter().notifyDataSetChanged();
			mActivity.reTimeOut();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	};

	/***************************************
	 ************ 監聽Meal list點選 **********
	 ***************************************/
	private OnItemClickListener MealListClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			if (!mActivity.getViewGroup().getMealListDioLog().isCheckoutIng()) {
				String store = mActivity.getProperty().getNowStoreId();
				String foodId = mActivity.getAdapterGroup().getMealListAdapter().getItem(arg2).getFoodItem().getId();

				mActivity.getMethod().delMealList(store, foodId);
			}
			mActivity.reTimeOut();
		}
	};

	/***************************************
	 ****** 監聽Meal list的Checkout 點選 *****
	 ***************************************/
	private OnCheckoutListener checkoutEvent = new OnCheckoutListener() {
		@Override
		public void checkout() {
			int cartQuantity = mActivity.getMealsList().getStoreMealsList(mActivity.getProperty().getNowStoreId()).size();
			if (cartQuantity > 0) {
				mActivity.getMethod().addServerShoppingCart(mActivity.getProperty().getNowStoreId());
			} else {
				mActivity.showMsg(R.string.homedine_meals_list_empty);
			}
			mActivity.reTimeOut();
		}
	};
}