package com.kentec.milkbox.homedine.view;

import android.view.Window;
import android.view.WindowManager;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.adapter.FoodAdapter;
import com.kentec.milkbox.homedine.adapter.MealListAdapter.MealListViewHolder;
import com.kentec.milkbox.homedine.adapter.StoreAdapter;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.homedine.view.homedineprogress.HomeDineProgressDialogFragment;
import com.kentec.milkbox.homedine.view.homedineprogress.HomeDineProgressFoodAdapter;
import com.kentec.milkbox.homedine.view.homedineprogress.HomeDineProgressFragment;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineReLayout {

	private HomeDineDeliveryActivity mActivity;

	private ReLayoutUtil reLayoutUtil;

	public HomeDineReLayout(HomeDineDeliveryActivity context) {
		this.mActivity = context;
		this.reLayoutUtil = mActivity.reLayout();
	}

	/***************************************
	 ************** 調整主要元件 *************
	 ***************************************/
	public void main(HomeDineDeliveryViewGroup vg) {
		reLayoutUtil.absolute(vg.getTitleTextView(), 538, 74, 124, 25);
		reLayoutUtil.absolute(vg.getMenuBar(), 1200, 70, 670, 30);
		reLayoutUtil.absolute(vg.getStoreFrame(), 250, 930, 120, 130);
		reLayoutUtil.absolute(vg.getFoodRelative(), 1320, 290, 460, 130);
		reLayoutUtil.absolute(vg.getInfoRelative(), 1470, 460, 410, 520);
		if (DEBUG.MOBILEPHONE)
			reLayoutUtil.linear(vg.getHistoryLinear(), 111, 70, 0, 0);
	}

	/***************************************
	 ************** 調整店家清單 *************
	 ***************************************/
	public void storeitem(StoreAdapter.ViewHolder viewHolder) {
		reLayoutUtil.linear(viewHolder.rowLinear, 240, 300);
		reLayoutUtil.linear(viewHolder.storeBg, 240, 240);
		reLayoutUtil.relative(viewHolder.storeImg, 180, 180);
	}

	/***************************************
	 ************** 調整店家介紹 *************
	 ***************************************/
	public void storeInfo() {
		reLayoutUtil.linear(mActivity.getViewGroup().getStoreImageView(), 500, 440);
	}

	/***************************************
	 ************** 調整食物清單 *************
	 ***************************************/
	public void foodList(int foodCount) {
		reLayoutUtil.relative(mActivity.getViewGroup().getFoodTwoWaygridview(), 1320, 290);

		mActivity.getViewGroup().getFoodTwoWaygridview().setHorizontalSpacing(30);
		mActivity.getViewGroup().getFoodTwoWaygridview().setColumnWidth(260);
		mActivity.getViewGroup().getFoodTwoWaygridview().setRowHeight(260);
		mActivity.getViewGroup().getFoodTwoWaygridview().setNumColumns(foodCount);
		mActivity.getViewGroup().getFoodTwoWaygridview().setNumRows(1);
		mActivity.getViewGroup().getFoodTwoWaygridview().setSelector(R.drawable.transparent);
	}

	/***************************************
	 ************** 調整食物項目 *************
	 ***************************************/
	public void foodItem(FoodAdapter.ViewHolder viewHolder) {
		reLayoutUtil.relative(viewHolder.rowLinear, 260, 260);
		reLayoutUtil.linear(viewHolder.foodImg, 210, 210);
	}

	/***************************************
	 ************** 調整食物資訊 *************
	 ***************************************/
	public void foodInfo() {
		reLayoutUtil.relative(mActivity.getViewGroup().getFoodImageView(), 490, 430);
		reLayoutUtil.relative(mActivity.getViewGroup().getSwitchimageView(), 415, 75);
		reLayoutUtil.relative(mActivity.getViewGroup().getDescription(), 650, 340);
		reLayoutUtil.relative(mActivity.getViewGroup().getNutrition(), 650, 340);
		reLayoutUtil.linear(mActivity.getViewGroup().getNutritionFactsLinear(), 300, 330);
		reLayoutUtil.linear(mActivity.getViewGroup().getNutritionTimeLinear(), 340, 330);
		reLayoutUtil.relative(mActivity.getViewGroup().getQandpRela(), 240, 330);
		reLayoutUtil.relative(mActivity.getViewGroup().getMealsLinear(), 180, 180);
	}

	/***************************************
	 ************** 調整食物數量 *************
	 ***************************************/
	public void foodMeals(HomeDineMealsDioLog mdl) {
		Window window = mdl.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		lp.dimAmount = (float) 0.8;

		lp.x = 0;
		lp.y = 0;
		lp.width = mActivity.getDisW();
		lp.height = mActivity.getDisH();

		window.setAttributes(lp);

		reLayoutUtil.linear(mdl.getAboutTextView(), 1110, 120, 0, -10);
		reLayoutUtil.linear(mdl.getRemainingQtyTextView(), 1110, 120, 0, 20);
		reLayoutUtil.linear(mdl.getQuantityLiner(), 1110, 140, 0, 20);
		reLayoutUtil.linear(mdl.getButtonLinear(), 540, 400, 0, 20);
	}

	/***************************************
	 ************* 調整Meal List ************
	 ***************************************/
	public void MealList(HomeDineMealListDioLog mealListDioLog) {
		Window window = mealListDioLog.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();

		lp.dimAmount = (float) 0.5;

		lp.x = 0;
		lp.y = 0;
		lp.width = mActivity.getDisW();
		lp.height = mActivity.getDisH();

		window.setAttributes(lp);

		reLayoutUtil.linear(mealListDioLog.getMainLinear(), 1600, 900);
		reLayoutUtil.padding(mealListDioLog.getMainLinear(), 60, 60, 60, 60);

		reLayoutUtil.linear(mealListDioLog.getTitleRelative(), 1450, 100);

		reLayoutUtil.relative(mealListDioLog.getListItems(), 730, 55, 10, 0);
		reLayoutUtil.relative(mealListDioLog.getListQuantity(), 180, 55);
		reLayoutUtil.relative(mealListDioLog.getListCalories(), 180, 55);
		reLayoutUtil.relative(mealListDioLog.getListPrice(), 320, 55);

		reLayoutUtil.relative(mealListDioLog.getCancelLinear(), 360, 120);
		reLayoutUtil.relative(mealListDioLog.getCheckoutLinear(), 360, 120);
		reLayoutUtil.relativeMargins(mealListDioLog.getSubtotalTextView(), 0, 0, 20, 0);
		// reLayoutUtil.linear(mealListDioLog.getFoodListRelative(), 1420, 500);
		// reLayoutUtil.relative(mealListDioLog.getSubtotalRelative(), 1420,
		// 65);

		reLayoutUtil.linear(mealListDioLog.getFoodListView(), 1480, 400);
		ReLayoutUtil.listview(mealListDioLog.getFoodListView(), 6);
	}

	/***************************************
	 *********** 調整Meal List Row **********
	 ***************************************/
	public void MealListRow(MealListViewHolder vh) {
		reLayoutUtil.linear(vh.rowImg, 70, 70);
		reLayoutUtil.linear(vh.rowItems, 660, 70);
		reLayoutUtil.linear(vh.rowQuantity, 180, 70);
		reLayoutUtil.linear(vh.rowCalories, 180, 70);
		reLayoutUtil.linear(vh.rowPrice, 340, 70);
	}

	/***************************************
	 ********* 調整Meal List Delete *********
	 ***************************************/
	public void MealListDelete(HomeDineMealsDeleteDioLog mealsDeleteDioLog) {
		// reLayoutUtil.linear(mealsDeleteDioLog.getMsgTextView(), 800, 150, 0,
		// 60);
		// reLayoutUtil.relative(mealsDeleteDioLog.getDeleteLinear(), 300, 130,
		// 0, 0);
		// reLayoutUtil.relative(mealsDeleteDioLog.getKeepLinear(), 300, 130, 0,
		// 0);
	}

	/***************************************
	 ************* 調整餐點進度 **************
	 ***************************************/
	public void progress(HomeDineProgressDialogFragment hdpdf) {
		WindowManager.LayoutParams lp = hdpdf.getDialog().getWindow().getAttributes();
		lp.dimAmount = (float) 0.8;
		lp.x = 0;
		lp.y = 0;
		lp.width = mActivity.getDisW();
		lp.height = mActivity.getDisH();
		hdpdf.getDialog().getWindow().setAttributes(lp);

		reLayoutUtil.relative(hdpdf.getMainViewPager(), 1920, 900, 0, 0);
		reLayoutUtil.relative(hdpdf.getAboutTextView(), 1920, 120, 0, 20);
	}

	/***************************************
	 *********** 調整餐點進度單頁 ************
	 ***************************************/
	public void progress(HomeDineProgressFragment hdpd) {
		reLayoutUtil.relative(hdpd.getMainLinear(), 720, 900, 0, 0);
		reLayoutUtil.linear(hdpd.getTitleTextView(), 720, 50, 0, 55);
		reLayoutUtil.linear(hdpd.getProLinear(), 700, 760, 13, 10);

		reLayoutUtil.linear(hdpd.getTopLinear(), 580, 60, 0, 50);
		reLayoutUtil.linear(hdpd.getFoodGridView(), 600, 220);
		reLayoutUtil.linear(hdpd.getStatusListView(), 600, 380);
	}

	/***************************************
	 ********** 調整餐點進度食物項目 *********
	 ***************************************/
//	public void foodItem(HomeDineProgressFoodAdapter.ViewHolder viewHolder) {
//		reLayoutUtil.linear(viewHolder.foodImg, 90, 90);
//	}

}