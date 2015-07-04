package com.kentec.milkbox.homedine.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.utils.DEBUG;

public class HomeDineMealsDioLog extends Dialog implements OnShowListener {

	private boolean isAniOver = false;

	private HomeDineDeliveryActivity mActivity;
	private TextView aboutTextView;
	private LinearLayout quantityLiner;
	private LinearLayout buttonLinear;
	private TextView remainingQtyTextView;
	private TextView quantityTextView;
	private ImageView arrowL;
	private ImageView arrowR;
	private LinearLayout addLinear;
	private LinearLayout cancelLinear;

	private long quantity = 1;
	private long remainingQuantity = 0;

	public HomeDineMealsDioLog(HomeDineDeliveryActivity context) {
		super(context, R.style.Dialog_Full);
		this.mActivity = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_homedine_meals);

		aboutTextView = (TextView) findViewById(R.id.aboutTextView);
		quantityLiner = (LinearLayout) findViewById(R.id.quantityLiner);
		buttonLinear = (LinearLayout) findViewById(R.id.buttonLinear);

		quantityTextView = (TextView) findViewById(R.id.quantityTextView);
		remainingQtyTextView = (TextView) findViewById(R.id.remainingQuantityTextView);
		arrowL = (ImageView) findViewById(R.id.arrowL);
		arrowR = (ImageView) findViewById(R.id.arrowR);
		addLinear = (LinearLayout) findViewById(R.id.addLinear);
		cancelLinear = (LinearLayout) findViewById(R.id.cancelLinear);

		aboutTextView.setText(Html.fromHtml(mActivity.getText(R.string.homedine_meals_about).toString()));

		cancelLinear.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

		setOnShowListener(this);
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

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			noAniDismiss();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			addQuantity();
		} else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			minusQuantity();
		}
		return false;
	}

	@Override
	public void onShow(DialogInterface arg0) {
		requantity();
	}

	public void updateMeals() {
		String text = mActivity.getString(R.string.homedine_meals_quantity).toString();
		String text2 = String.valueOf(quantity);
		quantityTextView.setText(text + " " + text2);

		text = mActivity.getString(R.string.homedine_meals_remaining_quantity).toString();
		text2 = String.valueOf(remainingQuantity);
		remainingQtyTextView.setText(text + " " + text2);
	}

	private void requantity() {
		DEBUG.e("getNowFoodItemId", String.valueOf(mActivity.getProperty().getNowFoodItemId()));
		remainingQuantity = (long) mActivity.getCache().getFoodData().getFoodList().get(mActivity.getProperty().getNowFoodItemId()).getRemainingQuantity();
		if (remainingQuantity < 0){
			remainingQuantity = 0;
		}
		quantity = mActivity.getMealsList().getQuantity(mActivity.getProperty().getNowStoreId(), mActivity.getProperty().getNowFoodId());
		// Set Meals 時，初始改為1。這樣使用者可以少按一次右鍵
		// from wesley
		if(quantity==0) 
			quantity=1;
		
		updateMeals();
	}

	public TextView getAboutTextView() {
		return aboutTextView;
	}

	public LinearLayout getQuantityLiner() {
		return quantityLiner;
	}

	public LinearLayout getButtonLinear() {
		return buttonLinear;
	}

	public TextView getQuantityTextView() {
		return quantityTextView;
	}

	public boolean isAniOver() {
		return isAniOver;
	}

	public ImageView getArrowL() {
		return arrowL;
	}

	public ImageView getArrowR() {
		return arrowR;
	}

	public LinearLayout getAddLinear() {
		return addLinear;
	}

	public LinearLayout getCancelLinear() {
		return cancelLinear;
	}

	public long getQuantity() {
		return quantity;
	}

	public void addQuantity() {
		if (quantity < remainingQuantity)
			this.quantity++;
		updateMeals();
	}

	public void minusQuantity() {
		if (quantity > 0)
			this.quantity--;
		updateMeals();
	}

	public TextView getRemainingQtyTextView() {
		return remainingQtyTextView;
	}

}