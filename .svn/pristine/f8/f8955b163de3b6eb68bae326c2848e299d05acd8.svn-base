package com.kentec.milkbox.view;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.api.OrderInfoApiParser;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.OrderInfo;


public class OrderInfoDialog extends BaseDialog implements OnShowListener {
	
	private static final String TAG = "OrderHistoryDialog";	
	
	private BaseActivity mActivity;
	private ProgressDialog mProgressDialog;	
		
	private Animation mLeftInAnim;
	private Animation mLeftOutAnim;	 
	private Animation mRightInAnim;
	private Animation mRightOutAnim;
	
	private TextView mShippingTv;
	private TextView mPaymentTv;
	private TextView mOverviewTv;
	
	
	private ViewFlipper mMainVf;
	private OrderInfoShippingView mShippingView;
	private OrderInfoPaymentView mPaymentView;
	private OrderInfoOverviewView mOverivewView;
	
	private String mCartId;
	private String mOrderId;
	private int menuColor;
	
	public OrderInfoDialog(Context context, String cartId, String orderId) {
		super((BaseActivity)context,R.style.Dialog_Full);
		
		mCartId = cartId;
		mOrderId = orderId;
		
		mActivity = (BaseActivity) context;
		
		mProgressDialog = new ProgressDialog(context);	
		
		mLeftInAnim = AnimationUtils.loadAnimation(context, R.anim.view_left_in);
		mLeftOutAnim = AnimationUtils.loadAnimation(context, R.anim.view_left_out);
		mRightInAnim = AnimationUtils.loadAnimation(context, R.anim.view_right_in);
		mRightOutAnim = AnimationUtils.loadAnimation(context, R.anim.view_right_out);
		
		menuColor = Color.parseColor("#8e8e8e");
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.dialog_order_info);

		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		reLayout(mainLl, mWidth, mHeight);
		
		mShippingTv = (TextView) findViewById(R.id.shippingTv);
		mPaymentTv = (TextView) findViewById(R.id.paymentTv);
		mOverviewTv = (TextView) findViewById(R.id.overviewTv);
		
		mMainVf = (ViewFlipper) findViewById(R.id.mainVf);	
		
		mShippingView = new OrderInfoShippingView(mActivity);
		mPaymentView = new OrderInfoPaymentView(mActivity);
		mOverivewView = new OrderInfoOverviewView(mActivity,mWidth,mHeight);
		
		mMainVf.addView(mShippingView);
		mMainVf.addView(mPaymentView);
		mMainVf.addView(mOverivewView);
		
		setOnShowListener(this);
		
	}
	
	@Override
	public void onShow(DialogInterface arg0) {
		new LoadTask(mCartId,mOrderId).execute();
	}
		
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		mActivity.reTimeOut();
		switch (event.getKeyCode()) {
		case KeyEvent.KEYCODE_BACK:
			dismiss();
			break;

		case KeyEvent.KEYCODE_DPAD_RIGHT:
			nextPage();
			break;

		case KeyEvent.KEYCODE_DPAD_LEFT:
			previousPage();
			break;
			
		default:
			return super.onKeyDown(keyCode, event);
		}

		return true;
	}
	
	private void nextPage() {
		int idx =  mMainVf.getDisplayedChild();
		if(idx == (mMainVf.getChildCount()-1 )) {
			return;
		}		
		if( idx < mMainVf.getChildCount() ) {			
			mMainVf.setInAnimation(mLeftInAnim);
			mMainVf.setOutAnimation(mLeftOutAnim);
			mMainVf.showNext();
			idx++;
			setMenuBg(idx);		
		}				
	}

	private void previousPage() {
		int idx =  mMainVf.getDisplayedChild();
		if(idx==0) {
			return;
		}
		if( idx>0 ) {
			mMainVf.setInAnimation(mRightInAnim);
			mMainVf.setOutAnimation(mRightOutAnim);				
			mMainVf.showPrevious();
			idx--;
			setMenuBg(idx);		
		}
	}
	
	private void setMenuBg(int now) {								
		if(now==0) {
			mShippingTv.setTextColor(Color.WHITE);
			mPaymentTv.setTextColor(menuColor);
			mOverviewTv.setTextColor(menuColor);
			
			mShippingTv.setBackgroundResource(R.drawable.step_bg_left);
			mPaymentTv.setBackgroundResource(R.drawable.step_bg_mid_disable);
			mOverviewTv.setBackgroundResource(R.drawable.step_bg_right_disable);			
		}
		else if(now==1) {
			mShippingTv.setTextColor(menuColor);
			mPaymentTv.setTextColor(Color.WHITE);
			mOverviewTv.setTextColor(menuColor);
			
			mShippingTv.setBackgroundResource(R.drawable.step_bg_left_disable);
			mPaymentTv.setBackgroundResource(R.drawable.step_bg_mid);
			mOverviewTv.setBackgroundResource(R.drawable.step_bg_right_disable);						
		}
		else if(now==2) {
			mShippingTv.setTextColor(menuColor);
			mPaymentTv.setTextColor(menuColor);
			mOverviewTv.setTextColor(Color.WHITE);
			
			mShippingTv.setBackgroundResource(R.drawable.step_bg_left_disable);
			mPaymentTv.setBackgroundResource(R.drawable.step_bg_mid_disable);
			mOverviewTv.setBackgroundResource(R.drawable.step_bg_right);						
		}

	}
	
	class LoadTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {
		
		String cartId; 
		String orderId; 
		OrderInfo oi;
		
		LoadTask(String cartId,String orderId) {
			this.cartId = cartId;
			this.orderId = orderId;			
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}
		
		@Override
		protected Integer doInBackground(String... arg0) {
			try {				
				RpcClient client = new RpcClient();
				
				Object[] list = client.cartPaymentList(cartId);
				
				HashMap<?,?> row = client.salesOrderInfo(orderId);
				oi = OrderInfoApiParser.parserOrderInfo(row,list);
				
				return OK;
				
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;				
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();
			if(result==OK) {
				mShippingView.setOrderInfo(oi);
				mPaymentView.setOrderInfo(oi);
				mOverivewView.setOrderInfo(oi);				
			}
			if(result == ERROR) {
				mActivity.showMsg(R.string.error);
			}			
		}

	}

	
}
