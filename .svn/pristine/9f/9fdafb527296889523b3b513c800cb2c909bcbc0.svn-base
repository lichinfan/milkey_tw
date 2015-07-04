package com.kentec.milkbox.checkout.method;

import android.content.Intent;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.checkout.data.PaymentMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskOrder;
import com.kentec.milkbox.checkout.task.CheckoutTaskSetPaymentMethod;
import com.kentec.milkbox.data.ActivityResultCode;

public class CheckoutMethod {

	private CheckoutActivity mActivity;

	public CheckoutMethod(CheckoutActivity activity) {
		this.mActivity = activity;
	}

	/***************************************
	 ************* 回上Activity *************
	 ***************************************/
	public void backActivity() {
		Intent intent = new Intent();
		intent.putExtras(mActivity.getData().getBundle());
		mActivity.setResult(ActivityResultCode.CANCEL, intent);
		mActivity.finish();
	}

	/***************************************
	 *********** 完成回上Activity ***********
	 ***************************************/
	public void okBackActivity() {
		Intent intent = new Intent();
		intent.putExtras(mActivity.getData().getBundle());
		mActivity.setResult(ActivityResultCode.OK, intent);
		mActivity.finish();
	}

	/***************************************
	 ************* 顯示訂單View *************
	 ***************************************/
	public void reOrderOverviewView() {
		if (mActivity.getViewGroup().getMainViewFlipper().getDisplayedChild() == 5) {
			mActivity.getViewGroup().getOrderOverviewView().relayout();
		}
	}

	/***************************************
	 **************** 下一步 ****************
	 ***************************************/
	public void nextPage() {
		int idx = mActivity.getViewGroup().getMainViewFlipper().getDisplayedChild();

		switch (idx) {
		case 0:
			mActivity.getViewGroup().getBillAddressView().closeAddAddressView();
			break;

		case 1:
			mActivity.getViewGroup().getShippingAddressView().closeAddAddressView();
			break;

		case 5:
			new CheckoutTaskOrder(mActivity).execute();
			return;
		}

		if (idx < mActivity.getViewGroup().getMainViewFlipper().getChildCount()) {
			mActivity.getViewGroup().getMainViewFlipper().setInAnimation(mActivity.getViewGroup().getLeftInAnim());
			mActivity.getViewGroup().getMainViewFlipper().setOutAnimation(mActivity.getViewGroup().getLeftOutAnim());
			mActivity.getViewGroup().getMainViewFlipper().showNext();
			idx++;
		}
		mActivity.getViewGroup().setMenuBg(idx);
	}

	/***************************************
	 **************** 上一步 ****************
	 ***************************************/
	public void backPage() {
		int idx = mActivity.getViewGroup().getMainViewFlipper().getDisplayedChild();

		switch (idx) {
		case 0:
			mActivity.getViewGroup().getBillAddressView().closeAddAddressView();
			backActivity();
			break;

		case 1:
			mActivity.getViewGroup().getShippingAddressView().closeAddAddressView();
			break;
		}

		if (idx > 0) {
			mActivity.getViewGroup().getMainViewFlipper().setInAnimation(mActivity.getViewGroup().getRightInAnim());
			mActivity.getViewGroup().getMainViewFlipper().setOutAnimation(mActivity.getViewGroup().getRightOutAnim());
			mActivity.getViewGroup().getMainViewFlipper().showPrevious();
			idx--;
		}
		mActivity.getViewGroup().setMenuBg(idx);
	}

	/***************************************
	 ************* 重新整理View **************
	 ***************************************/
	public void refreshView() {
		switch (mActivity.getViewGroup().getMainViewFlipper().getDisplayedChild()) {
		case 0:
			mActivity.getViewGroup().getBillAddressView().show();
			break;
		case 1:
			mActivity.getViewGroup().getShippingAddressView().show();
			break;
		case 2:
			mActivity.getViewGroup().getShippingMethodView().show();
			break;
		case 3:
			mActivity.getViewGroup().getPaymentMethodView().show();
			break;
		case 4:
			mActivity.getViewGroup().getCouponView().show();
			break;
		case 5:
			mActivity.getViewGroup().getOrderOverviewView().setProduct(mActivity.getData().getProductList());
			mActivity.getViewGroup().getOrderOverviewView().setTotal(mActivity.getData().getTotalList());
			break;
		}
	}

	/***************************************
	 ************* 設定付款方式 **************
	 ***************************************/
	public void setPaymentMethod() {
		PaymentMethod pm = mActivity.getViewGroup().getPaymentMethodView().getPaymentMethod();
		if (pm.getCode().equals(CheckoutActivity.CC_SAVE)) {
			CreditCard cc = mActivity.getViewGroup().getPaymentMethodView().getCreditCard();
			if (cc.getOwner().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_name_requirement);
				return;
			}
			if (cc.getNumber().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_number_requirement);
				return;
			}
			if (cc.getType().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_type_requirement);
				return;
			}
			if (cc.getExpiryMonth().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_mm_requirement);
				return;
			}
			if (cc.getExpiryYear().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_yy_requirement);
				return;
			}
			if (cc.getCid().length() == 0) {
				mActivity.showMsg(R.string.checkout_cc_cid_requirement);
				return;
			}
			new CheckoutTaskSetPaymentMethod(mActivity, pm, cc).execute();
		} else {
			new CheckoutTaskSetPaymentMethod(mActivity, pm).execute();
		}
	}

}