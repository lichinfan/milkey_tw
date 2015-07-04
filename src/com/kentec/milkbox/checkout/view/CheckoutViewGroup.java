package com.kentec.milkbox.checkout.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.homedine.utils.AnimUtil;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

public class CheckoutViewGroup {
	private CheckoutActivity mActivity;

	private ProgressDialog mProgressDialog;

	private LinearLayout mainLinear;

	private TextView billingAddressTextView;
	private TextView shippingAddressTextView;
	private TextView shippingMethodTextView;
	private TextView paymentMethodTextView;
	private TextView couponTextView;
	private TextView orderOverviewTextView;

	private BillingAddressView mBillAddressView;
	private ShippingAddressView mShippingAddressView;
	private ShippingMethodView mShippingMethodView;
	private PaymentMethodView mPaymentMethodView;
	private CouponView mCouponView;
	private OrderOverviewView mOrderOverviewView;

	private ViewFlipper mainViewFlipper;

	private Button backButton;
	private Button nextButton;

	private Animation leftInAnim;
	private Animation leftOutAnim;
	private Animation rightInAnim;
	private Animation rightOutAnim;

	private ReLayoutUtil mReLayoutUtil;

	private AnimUtil mAnimUtil;

	public CheckoutViewGroup(CheckoutActivity activity) {
		this.mActivity = activity;
		this.mAnimUtil = new AnimUtil(mActivity);

		mActivity.setContentView(R.layout.activity_checkout);

		mainLinear = (LinearLayout) mActivity.findViewById(R.id.mainLinear);

		mainViewFlipper = (ViewFlipper) mActivity.findViewById(R.id.mainVp);

		billingAddressTextView = (TextView) mActivity.findViewById(R.id.billingAddressTextView);
		shippingAddressTextView = (TextView) mActivity.findViewById(R.id.shippingAddressTextView);
		shippingMethodTextView = (TextView) mActivity.findViewById(R.id.shippingMethodTextView);
		paymentMethodTextView = (TextView) mActivity.findViewById(R.id.paymentMethodTextView);
		couponTextView = (TextView) mActivity.findViewById(R.id.couponTextView);
		orderOverviewTextView = (TextView) mActivity.findViewById(R.id.orderOverviewTextView);

		backButton = (Button) mActivity.findViewById(R.id.backButton);
		nextButton = (Button) mActivity.findViewById(R.id.nextButton);

		mBillAddressView = new BillingAddressView(mActivity);
		mShippingAddressView = new ShippingAddressView(mActivity);
		mShippingMethodView = new ShippingMethodView(mActivity);
		mPaymentMethodView = new PaymentMethodView(mActivity);
		mCouponView = new CouponView(mActivity);
		mOrderOverviewView = new OrderOverviewView(mActivity);

		mainViewFlipper.addView(mBillAddressView);
		mainViewFlipper.addView(mShippingAddressView);
		mainViewFlipper.addView(mShippingMethodView);
		mainViewFlipper.addView(mPaymentMethodView);
		mainViewFlipper.addView(mCouponView);
		mainViewFlipper.addView(mOrderOverviewView);

		mReLayoutUtil = mActivity.reLayout();
		mProgressDialog = new ProgressDialog(mActivity);

		leftInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.view_left_in);
		leftOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.view_left_out);
		rightInAnim = AnimationUtils.loadAnimation(mActivity, R.anim.view_right_in);
		rightOutAnim = AnimationUtils.loadAnimation(mActivity, R.anim.view_right_out);

		mReLayoutUtil.relative(mainLinear, 1600, 900);
		
		mReLayoutUtil.padding(mainLinear, 30,30,30,30);
		billingAddressTextView.setPadding(10, 0, 30, 0);
		shippingAddressTextView.setPadding(20, 0, 20, 0);
		shippingMethodTextView.setPadding(20, 0, 20, 0);
		paymentMethodTextView.setPadding(20, 0, 20, 0);
		couponTextView.setPadding(20, 0, 20, 0);
		orderOverviewTextView.setPadding(30, 0, 10, 0);

		mainLinear.getBackground().setAlpha(235);
		WindowManager.LayoutParams params = mActivity.getWindow().getAttributes();
		params.width = mActivity.getDisW();
		params.height = mActivity.getDisH();
		// params.alpha = (float) 0.95;
		// params.dimAmount = (float) 0.8;
		mActivity.getWindow().setAttributes(params);

	}

	/***************************************
	 ************** 設置TAB背景 *************
	 ***************************************/
	public void setMenuBg(int now) {
		if (now == 0) {
			backButton.setVisibility(View.INVISIBLE);
		} else {
			backButton.setVisibility(View.VISIBLE);
		}

		if (now == 3 || now == 5) {
			nextButton.setVisibility(View.VISIBLE);
		} else {
			nextButton.setVisibility(View.GONE);
		}

		int c8e8e8e = Color.parseColor("#8e8e8e");

		if (now == 0) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right_disable);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(c8e8e8e);
			shippingMethodTextView.setTextColor(c8e8e8e);
			paymentMethodTextView.setTextColor(c8e8e8e);
			couponTextView.setTextColor(c8e8e8e);
			orderOverviewTextView.setTextColor(c8e8e8e);

		} else if (now == 1) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right_disable);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(Color.WHITE);
			shippingMethodTextView.setTextColor(c8e8e8e);
			paymentMethodTextView.setTextColor(c8e8e8e);
			couponTextView.setTextColor(c8e8e8e);
			orderOverviewTextView.setTextColor(c8e8e8e);

		} else if (now == 2) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right_disable);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(Color.WHITE);
			shippingMethodTextView.setTextColor(Color.WHITE);
			paymentMethodTextView.setTextColor(c8e8e8e);
			couponTextView.setTextColor(c8e8e8e);
			orderOverviewTextView.setTextColor(c8e8e8e);

		} else if (now == 3) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid_disable);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right_disable);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(Color.WHITE);
			shippingMethodTextView.setTextColor(Color.WHITE);
			paymentMethodTextView.setTextColor(Color.WHITE);
			couponTextView.setTextColor(c8e8e8e);
			orderOverviewTextView.setTextColor(c8e8e8e);

		} else if (now == 4) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right_disable);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(Color.WHITE);
			shippingMethodTextView.setTextColor(Color.WHITE);
			paymentMethodTextView.setTextColor(Color.WHITE);
			couponTextView.setTextColor(Color.WHITE);
			orderOverviewTextView.setTextColor(c8e8e8e);

		} else if (now == 5) {
			billingAddressTextView.setBackgroundResource(R.drawable.step_bg_left);
			shippingAddressTextView.setBackgroundResource(R.drawable.step_bg_mid);
			shippingMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			paymentMethodTextView.setBackgroundResource(R.drawable.step_bg_mid);
			couponTextView.setBackgroundResource(R.drawable.step_bg_mid);
			orderOverviewTextView.setBackgroundResource(R.drawable.step_bg_right);

			billingAddressTextView.setTextColor(Color.WHITE);
			shippingAddressTextView.setTextColor(Color.WHITE);
			shippingMethodTextView.setTextColor(Color.WHITE);
			paymentMethodTextView.setTextColor(Color.WHITE);
			couponTextView.setTextColor(Color.WHITE);
			orderOverviewTextView.setTextColor(Color.WHITE);
		}

		if (now == 5) {
			nextButton.setText(mActivity.getString(R.string.checkout_order));
		} else {
			nextButton.setText(mActivity.getString(R.string.checkout_next));
		}

		mActivity.getMethod().refreshView();
	}

	public ProgressDialog getProgressDialog() {
		return mProgressDialog;
	}

	public BillingAddressView getBillAddressView() {
		return mBillAddressView;
	}

	public ShippingAddressView getShippingAddressView() {
		return mShippingAddressView;
	}

	public ShippingMethodView getShippingMethodView() {
		return mShippingMethodView;
	}

	public PaymentMethodView getPaymentMethodView() {
		return mPaymentMethodView;
	}

	public CouponView getCouponView() {
		return mCouponView;
	}

	public OrderOverviewView getOrderOverviewView() {
		return mOrderOverviewView;
	}

	public Button getBackButton() {
		return backButton;
	}

	public Button getNextButton() {
		return nextButton;
	}

	public ViewFlipper getMainViewFlipper() {
		return mainViewFlipper;
	}

	public Animation getLeftInAnim() {
		return leftInAnim;
	}

	public Animation getLeftOutAnim() {
		return leftOutAnim;
	}

	public Animation getRightInAnim() {
		return rightInAnim;
	}

	public Animation getRightOutAnim() {
		return rightOutAnim;
	}

	public AnimUtil getAnimUtil() {
		return mAnimUtil;
	}

	public ReLayoutUtil getReLayoutUtil() {
		return mReLayoutUtil;
	}

}
