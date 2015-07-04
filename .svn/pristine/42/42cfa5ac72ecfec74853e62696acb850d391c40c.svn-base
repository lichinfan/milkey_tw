package com.kentec.milkbox.checkout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.GroceryCheckoutASRCreater;
import com.kentec.milkbox.checkout.data.CheckoutData;
import com.kentec.milkbox.checkout.data.CheckoutOrderDate;
import com.kentec.milkbox.checkout.method.CheckoutListener;
import com.kentec.milkbox.checkout.method.CheckoutMethod;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadAddress;
import com.kentec.milkbox.checkout.task.CheckoutTaskLoadAddress.OnLoadAddressListener;
import com.kentec.milkbox.checkout.view.CheckoutViewGroup;

public class CheckoutActivity extends ASRActivity {

	public final static String TAG = "CheckoutActivity";
	public final static String MODE_BILLING = "billing";
	public final static String MODE_SHIPPING = "shipping";
	public final static String CC_SAVE = "ccsave";

	private CheckoutViewGroup mViewGroup;
	private CheckoutListener mListener;
	private CheckoutMethod mMethod;
	private CheckoutData mData;
	private CheckoutOrderDate mOrder;
	private InputMethodManager mImm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

		mViewGroup = new CheckoutViewGroup(this);
		mListener = new CheckoutListener(this);
		mMethod = new CheckoutMethod(this);
		mData = new CheckoutData(this);
		mOrder = new CheckoutOrderDate();

		new CheckoutTaskLoadAddress(this, new OnLoadAddressListener() {

			@Override
			public void OK() {
				mViewGroup.setMenuBg(0);
			}

			@Override
			public void Error(String msg) {
				showMsg(msg);
			}
		});
		/**
		* @author andywu
		* @date 2014.03.19
		* 加入語音
		*/
		new GroceryCheckoutASRCreater().init(this);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		mMethod.reOrderOverviewView();
	}

	@Override
	public void onBackPressed() {
		mMethod.backPage();
	}

	public CheckoutViewGroup getViewGroup() {
		return mViewGroup;
	}

	public CheckoutListener getListener() {
		return mListener;
	}

	public CheckoutData getData() {
		return mData;
	}

	public CheckoutMethod getMethod() {
		return mMethod;
	}

	public CheckoutOrderDate getOrder() {
		return mOrder;
	}

	public InputMethodManager getmImm() {
		return mImm;
	}
	/**
	* @author andywu
	* @date 2014.03.19
	* @category 配合語音按下按鈕
	*
	*/
	public void checknext()
	{
//		Log.d("andy","checknext");
		getViewGroup().getNextButton().performClick() ;
	}
}