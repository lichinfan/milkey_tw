package com.kentec.milkbox.homedine.view;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.view.homedineprogress.HomeDineProgressDialogFragment;
import com.kentec.milkbox.thirdparty.TwoWayGridView;

public class HomeDineDeliveryViewGroup {

	private HomeDineDeliveryActivity mActivity;

	private TextView titleTextView;

	private View menuBar;
	private LinearLayout underLinear;
	private TextView underTextView;
	private LinearLayout progressLinear;
	private TextView progressTextView;
	private LinearLayout historyLinear;
	private TextView historyTextView;
	private LinearLayout checkoutLinear;
	private TextView checkoutTextView;

	private FrameLayout storeFrame;
	private GridView storeGridView;

	private RelativeLayout foodRelative;
	private TwoWayGridView foodTwoWaygridview;

	private View storeInfo;
	private ImageView storeImageView;
	private TextView storeNameTextView;
	private TextView storeAboutTextView;

	private RelativeLayout infoRelative;
	private ImageView foodImageView;

	private RelativeLayout foodAandNRela;

	private View description;
	private TextView foodNameTextView;
	private TextView foodAboutTextView;
	private TextView foodPriceTextView;

	private LinearLayout nutritionFactsLinear;
	private LinearLayout nutritionTimeLinear;

	private View nutrition;
	private TextView proteinTextView;
	private TextView carbohydratesTextView;
	private TextView sugarTextView;
	private TextView fatTextView;
	private TextView saturatedFatTextView;
	private TextView transFatTextView;
	private TextView cholesterolTextView;
	private TextView sodlumTextView;
	private TextView caloresTextView;
	private TextView runMinTextView;

	private TextView mealsTextView;

	private RelativeLayout qandpRela;
	private LinearLayout mealsLinear;
	private TextView totalPriceTextView;

	private ImageView switchimageView;

	private View foodInfo;

	private TextView loadShopTextView;
	private TextView loadFoodTextView;

	private HomeDineMealsDioLog mealsDialog;
	private HomeDineMealListDioLog mealListDioLog;
	private HomeDineProgressDialogFragment progressDialogFragment;

	private HomeDineReLayout relayout;

	public HomeDineDeliveryViewGroup(HomeDineDeliveryActivity context) {
		this.mActivity = context;
		this.mActivity.setContentView(R.layout.activity_homedine_main);

		this.titleTextView = (TextView) mActivity.findViewById(R.id.titleTextView);

		this.menuBar = (View) mActivity.findViewById(R.id.menuBar);
		this.underLinear = (LinearLayout) menuBar.findViewById(R.id.underLinear);
		this.underTextView = (TextView) menuBar.findViewById(R.id.underTextView);
		this.progressLinear = (LinearLayout) menuBar.findViewById(R.id.progressLinear);
		this.progressTextView = (TextView) menuBar.findViewById(R.id.progressTextView);
		this.historyLinear = (LinearLayout) menuBar.findViewById(R.id.historyLinear);
		this.historyTextView = (TextView) menuBar.findViewById(R.id.historyTextView);
		this.checkoutLinear = (LinearLayout) menuBar.findViewById(R.id.checkoutLinear);
		this.checkoutTextView = (TextView) menuBar.findViewById(R.id.checkoutTextView);

		this.storeFrame = (FrameLayout) mActivity.findViewById(R.id.storeFrame);
		this.storeGridView = (GridView) mActivity.findViewById(R.id.storeGridView);
		this.foodRelative = (RelativeLayout) mActivity.findViewById(R.id.foodRelative);
		this.foodTwoWaygridview = (TwoWayGridView) mActivity.findViewById(R.id.foodTwoWaygridview);

		this.infoRelative = (RelativeLayout) mActivity.findViewById(R.id.infoRelative);
		this.foodAandNRela = (RelativeLayout) mActivity.findViewById(R.id.foodAandNRela);

		this.storeInfo = (View) mActivity.findViewById(R.id.storeinfo);
		this.storeImageView = (ImageView) mActivity.findViewById(R.id.stroneImageView);
		this.storeNameTextView = (TextView) mActivity.findViewById(R.id.storeNameTextView);
		this.storeAboutTextView = (TextView) mActivity.findViewById(R.id.storeAboutTextView);

		this.foodInfo = (View) mActivity.findViewById(R.id.foodInfo);
		this.foodImageView = (ImageView) mActivity.findViewById(R.id.foodImageView);
		this.switchimageView = (ImageView) mActivity.findViewById(R.id.switchimageView);
		this.totalPriceTextView = (TextView) mActivity.findViewById(R.id.totalPriceTextView);

		this.description = (View) foodInfo.findViewById(R.id.description);
		this.foodNameTextView = (TextView) description.findViewById(R.id.foodNameTextView);
		this.foodAboutTextView = (TextView) description.findViewById(R.id.foodAboutTextView);
		this.foodPriceTextView = (TextView) description.findViewById(R.id.foodPriceTextView);

		this.nutrition = (View) foodInfo.findViewById(R.id.nutrition);
		this.nutritionFactsLinear = (LinearLayout) nutrition.findViewById(R.id.nutritionFactsLinear);
		this.nutritionTimeLinear = (LinearLayout) nutrition.findViewById(R.id.nutritionTimeLinear);
		this.proteinTextView = (TextView) nutrition.findViewById(R.id.proteinTextView);
		this.carbohydratesTextView = (TextView) nutrition.findViewById(R.id.carbohydratesTextView);
		this.sugarTextView = (TextView) nutrition.findViewById(R.id.sugarTextView);
		this.fatTextView = (TextView) nutrition.findViewById(R.id.fatTextView);
		this.saturatedFatTextView = (TextView) nutrition.findViewById(R.id.saturatedFatTextView);
		this.transFatTextView = (TextView) nutrition.findViewById(R.id.transFatTextView);
		this.cholesterolTextView = (TextView) nutrition.findViewById(R.id.cholesterolTextView);
		this.sodlumTextView = (TextView) nutrition.findViewById(R.id.sodlumTextView);
		this.caloresTextView = (TextView) nutrition.findViewById(R.id.caloresTextView);
		this.runMinTextView = (TextView) nutrition.findViewById(R.id.runMinTextView);

		this.mealsTextView = (TextView) foodInfo.findViewById(R.id.mealsTextView);

		this.qandpRela = (RelativeLayout) mActivity.findViewById(R.id.qandpRela);
		this.mealsLinear = (LinearLayout) mActivity.findViewById(R.id.mealsLinear);
		this.loadShopTextView = (TextView) mActivity.findViewById(R.id.loadShopTextView);
		this.loadFoodTextView = (TextView) mActivity.findViewById(R.id.loadFoodTextView);

		this.mealsDialog = new HomeDineMealsDioLog(mActivity);
		this.mealListDioLog = new HomeDineMealListDioLog(mActivity);
		this.progressDialogFragment = new HomeDineProgressDialogFragment(mActivity);

		this.relayout = new HomeDineReLayout(mActivity);
		this.relayout.main(this);
	}

	public GridView getStoreGridView() {
		return storeGridView;
	}

	public ImageView getFoodImageView() {
		return foodImageView;
	}

	public TextView getFoodNameTextView() {
		return foodNameTextView;
	}

	public TextView getFoodAboutTextView() {
		return foodAboutTextView;
	}

	public TextView getFoodPriceTextView() {
		return foodPriceTextView;
	}

	public HomeDineDeliveryActivity getmActivity() {
		return mActivity;
	}

	public TextView getProteinTextView() {
		return proteinTextView;
	}

	public TextView getCarbohydratesTextView() {
		return carbohydratesTextView;
	}

	public TextView getSugarTextView() {
		return sugarTextView;
	}

	public TextView getFatTextView() {
		return fatTextView;
	}

	public TextView getSaturatedFatTextView() {
		return saturatedFatTextView;
	}

	public TextView getTransFatTextView() {
		return transFatTextView;
	}

	public TextView getCholesterolTextView() {
		return cholesterolTextView;
	}

	public TextView getSodlumTextView() {
		return sodlumTextView;
	}

	public TextView getCaloresTextView() {
		return caloresTextView;
	}

	public TextView getRunMinTextView() {
		return runMinTextView;
	}

	public ImageView getSwitchimageView() {
		return switchimageView;
	}

	public View getDescription() {
		return description;
	}

	public View getNutrition() {
		return nutrition;
	}

	public TextView getTotalPriceTextView() {
		return totalPriceTextView;
	}

	public View getFoodInfo() {
		return foodInfo;
	}

	public View getStoreInfo() {
		return storeInfo;
	}

	public ImageView getStoreImageView() {
		return storeImageView;
	}

	public TextView getStoreAboutTextView() {
		return storeAboutTextView;
	}

	public FrameLayout getStoreFrame() {
		return storeFrame;
	}

	public TextView getLoadShopTextView() {
		return loadShopTextView;
	}

	public TextView getLoadFoodTextView() {
		return loadFoodTextView;
	}

	public TextView getStoreNameTextView() {
		return storeNameTextView;
	}

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public TextView getUnderTextView() {
		return underTextView;
	}

	public TextView getCheckoutTextView() {
		return checkoutTextView;
	}

	public RelativeLayout getInfoRelative() {
		return infoRelative;
	}

	public HomeDineReLayout getRelayout() {
		return relayout;
	}

	public RelativeLayout getQandpRela() {
		return qandpRela;
	}

	public LinearLayout getMealsLinear() {
		return mealsLinear;
	}

	public LinearLayout getNutritionFactsLinear() {
		return nutritionFactsLinear;
	}

	public LinearLayout getNutritionTimeLinear() {
		return nutritionTimeLinear;
	}

	public RelativeLayout getFoodAandNRela() {
		return foodAandNRela;
	}

	public LinearLayout getUnderLinear() {
		return underLinear;
	}

	public TwoWayGridView getFoodTwoWaygridview() {
		return foodTwoWaygridview;
	}

	public RelativeLayout getFoodRelative() {
		return foodRelative;
	}

	public HomeDineMealsDioLog getMealsDialog() {
		return mealsDialog;
	}

	public TextView getMealsTextView() {
		return mealsTextView;
	}

	public HomeDineMealListDioLog getMealListDioLog() {
		return mealListDioLog;
	}

	public LinearLayout getCheckoutLinear() {
		return checkoutLinear;
	}

	public View getMenuBar() {
		return menuBar;
	}

	public LinearLayout getProgressLinear() {
		return progressLinear;
	}

	public TextView getProgressTextView() {
		return progressTextView;
	}

	public LinearLayout getHistoryLinear() {
		return historyLinear;
	}

	public TextView getHistoryTextView() {
		return historyTextView;
	}

	public HomeDineProgressDialogFragment getProgressDialogFragment() {
		return progressDialogFragment;
	}

	public void setProgressDialogFragment(HomeDineProgressDialogFragment progressDialogFragment) {
		this.progressDialogFragment = progressDialogFragment;
	}

}
