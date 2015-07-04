package com.kentec.milkbox.homedine.view;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.adapter.MealListAdapter;
import com.kentec.milkbox.homedine.data.MealsFood;
import com.kentec.milkbox.homedine.data.StoreData;
import com.kentec.milkbox.utils.Format;

public class HomeDineMealListDioLog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;
	private boolean isCheckoutIng = false;

	private HomeDineDeliveryActivity mActivity;

	private LinearLayout mainLinear;
	private RelativeLayout titleRelative;

	private TextView title;
	private TextView caloriestext;
	private TextView calories;

	private TextView listItems;
	private TextView listQuantity;
	private TextView listCalories;
	private TextView listPrice;

	// private RelativeLayout subtotalRelative;
	private TextView subtotalTextView;

	// private RelativeLayout foodListRelative;
	private ListView foodListView;

	private LinearLayout cancelLinear;
	private LinearLayout checkoutLinear;
	private LinearLayout checkoutLoadingLinear;
	private ImageView loadingImageView;

	public void setFoodListView(ListView foodListView) {
		this.foodListView = foodListView;
	}

	public HomeDineMealListDioLog(HomeDineDeliveryActivity context) {
		super(context, R.style.Dialog_Full);
		this.mActivity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_homedine_meallist);

		mainLinear = (LinearLayout) findViewById(R.id.mainLinear);

		titleRelative = (RelativeLayout) findViewById(R.id.titleRelative);
		title = (TextView) findViewById(R.id.title);
		caloriestext = (TextView) findViewById(R.id.caloriestext);
		calories = (TextView) findViewById(R.id.calories);

		listItems = (TextView) findViewById(R.id.listItems);
		listQuantity = (TextView) findViewById(R.id.listQuantity);
		listCalories = (TextView) findViewById(R.id.listCalories);
		listPrice = (TextView) findViewById(R.id.listPrice);

		// subtotalRelative = (RelativeLayout)
		// findViewById(R.id.subtotalRelative);
		subtotalTextView = (TextView) findViewById(R.id.subtotalTextView);

		// foodListRelative = (RelativeLayout)
		// findViewById(R.id.foodListRelative);
		foodListView = (ListView) findViewById(R.id.foodListView);
		loadingImageView = (ImageView) findViewById(R.id.loadingImageView);

		checkoutLoadingLinear = (LinearLayout) findViewById(R.id.checkoutLoadingLinear);

		cancelLinear = (LinearLayout) findViewById(R.id.cancelLinear);
		cancelLinear.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

		checkoutLinear = (LinearLayout) findViewById(R.id.checkoutLinear);
		checkoutLinear.setOnClickListener(new LinearLayout.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (listener != null)
					listener.checkout();
				mActivity.reTimeOut();
			}
		});

		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface arg0) {
		mActivity.getAdapterGroup().setMealListAdapter(new MealListAdapter(mActivity, new ArrayList<MealsFood>()));
		foodListView.setAdapter(mActivity.getAdapterGroup().getMealListAdapter());
		update();
	}

	public void update() {
		ArrayList<MealsFood> list = mActivity.getMealsList().getStoreMealsList(mActivity.getProperty().getNowStoreId());
		mActivity.getAdapterGroup().getMealListAdapter().upList(list);
		
//		ReLayoutUtil.listview(	mActivity.getViewGroup().getMealListDioLog().getFoodListView(), 1);
		
		StoreData storeData = mActivity.getCache().getStore(mActivity.getProperty().getNowStoreId());
		title.setText(storeData.getStoreName() + " " + mActivity.getText(R.string.homedine_meals_mealslist).toString());
		calories.setText(String.valueOf(" " + mActivity.getAdapterGroup().getMealListAdapter().getCaloriesCount()));

		double subTotal = 0.0;
		for (int i = 0; i < list.size(); i++) {
			subTotal += list.get(i).getFoodQuantity() * list.get(i).getFoodItem().getPrice();
		}
		subtotalTextView.setText(" " + Format.price(subTotal));
		foodListView.requestFocus();
	}

	public void checkoutLoading(boolean isLoading) {
		if (isLoading) {
			isCheckoutIng = true;
			setCancelable(false);
			checkoutLinear.setVisibility(View.GONE);
			cancelLinear.setVisibility(View.GONE);
			checkoutLoadingLinear.setVisibility(View.VISIBLE);
			RotateAnimation loadAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
			loadAnim.setDuration(500);
			loadAnim.setRepeatCount(Animation.INFINITE);
			loadAnim.setFillEnabled(true);
			loadAnim.setInterpolator(new LinearInterpolator());

			loadingImageView.startAnimation(loadAnim);
		} else {
			loadingImageView.clearAnimation();
			checkoutLoadingLinear.setVisibility(View.GONE);
			checkoutLinear.setVisibility(View.VISIBLE);
			cancelLinear.setVisibility(View.VISIBLE);

			setCancelable(true);
			isCheckoutIng = false;
		}
	}

	@Override
	public void dismiss() {
		if (this.isShowing()) {
			if (isAniOver) {
				isAniOver = false;
				super.dismiss();
			} else {
				// AnimationExit ani = new AnimationExit();
				// ani.setAnimationListener(new AnimationListener() {
				// @Override
				// public void onAnimationStart(Animation animation) {
				// }
				//
				// @Override
				// public void onAnimationRepeat(Animation animation) {
				// }
				//
				// @Override
				// public void onAnimationEnd(Animation animation) {
				// isAniOver = true;
				// dismiss();
				// }
				// });
				// dialodRelativeLayout.startAnimation(ani);
				super.dismiss();
			}
		}

	}

	public void noAniDismiss() {
		if (this.isShowing()) {
			super.dismiss();
		}
	}

	private OnCheckoutListener listener;

	public void setListener(OnCheckoutListener listener) {
		this.listener = listener;
	}

	public interface OnCheckoutListener {
		public void checkout();

	};

	public LinearLayout getMainLinear() {
		return mainLinear;
	}

	public TextView getTitle() {
		return title;
	}

	public TextView getCalories() {
		return calories;
	}

	public TextView getCaloriestext() {
		return caloriestext;
	}

	public TextView getListItems() {
		return listItems;
	}

	public TextView getListQuantity() {
		return listQuantity;
	}

	public TextView getListCalories() {
		return listCalories;
	}

	public TextView getListPrice() {
		return listPrice;
	}

	public ListView getFoodListView() {
		return foodListView;
	}

	public RelativeLayout getTitleRelative() {
		return titleRelative;
	}

	public LinearLayout getCancelLinear() {
		return cancelLinear;
	}

	public LinearLayout getCheckoutLinear() {
		return checkoutLinear;
	}

	public LinearLayout getCheckoutLoadingLinear() {
		return checkoutLoadingLinear;
	}

	public boolean isCheckoutIng() {
		return isCheckoutIng;
	}

	public TextView getSubtotalTextView() {
		return subtotalTextView;
	}

}