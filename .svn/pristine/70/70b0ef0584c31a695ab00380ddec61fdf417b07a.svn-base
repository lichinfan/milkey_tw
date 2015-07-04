package com.kentec.milkbox.coupon.view;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.coupon.task.CouponCollectAddToCartTask;
import com.kentec.milkbox.coupon.task.CouponCollectTask;
import com.kentec.milkbox.coupon.task.GetShoppingCartTask;
import com.kentec.milkbox.task.LoadImageTask;
import com.kentec.milkbox.view.BaseDialog;

public class CouponDialog extends BaseDialog {

	private String mCouponCartId;
	private Coupon mCoupon;
	private int mImageWidth;
	
	public CouponDialog(BaseActivity activity, String cartId, Coupon coupon) {		
		super(activity, R.style.Dialog_Full);		
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		mHeight = (int) (display.getHeight() * 0.8);
		mWidth = (int) (display.getWidth() * 0.6);
		mBaseWidth = mWidth / 10;	
		mImageWidth = (int) (mWidth * 0.4);		
		mCouponCartId = cartId;
		mCoupon = coupon;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.dialog_coupon_add);

		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView expDateTv = (TextView) findViewById(R.id.expDateTv);
		ImageView mainIv = (ImageView) findViewById(R.id.mainIv);		
		TextView descTv = (TextView) findViewById(R.id.descTv);
		
		reLayout(mainLl,mWidth,mHeight);
		
		nameTv.setText(mCoupon.getName());
		expDateTv.setText(mCoupon.getExpDate());
		descTv.setText(mCoupon.getDesc());
		
		LinearLayout collectLl = (LinearLayout) findViewById(R.id.collectLl);
		reLayout(collectLl,4);
		collectLl.setOnClickListener(collectClickListener);

		LinearLayout addToCartLl = (LinearLayout) findViewById(R.id.addToCartLl);
		reLayout(addToCartLl,4);
		addToCartLl.setOnClickListener(addToCartListener);
		
		new LoadImageTask(mainIv, mCoupon.getImage(), mImageWidth, mImageWidth).execute();		
		
	}	

	LinearLayout.OnClickListener collectClickListener = new LinearLayout.OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.reTimeOut();			
			new CouponCollectTask(mActivity,collectSucessListener,mCouponCartId,mCoupon.getId());
		}		
	};

	LinearLayout.OnClickListener addToCartListener = new LinearLayout.OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.reTimeOut();
			if(mCoupon.getLinkStoreId()==null || "".equals(mCoupon.getLinkStoreId())) {
				mActivity.showOkMsg(R.string.msg_illegal_store);
				return;
			}					
			new GetShoppingCartTask(mActivity,getShoppingCartSuccessListener,mCoupon.getLinkStoreId(),CFG.IMEI);							
		}		
	};

	CouponCollectTask.OnSucessListener collectSucessListener = new CouponCollectTask.OnSucessListener() {
		@Override
		public void success() {
			dismiss();
			mActivity.showOkMsg(R.string.msg_add_my_coupon_success);
		}		
	};

	GetShoppingCartTask.OnSucessListener getShoppingCartSuccessListener = new GetShoppingCartTask.OnSucessListener() {
		@Override
		public void success(String shoppingCartId) {
			new CouponCollectAddToCartTask(mActivity,cartSucessListener,shoppingCartId,mCoupon.getLinkSku(),mCouponCartId,mCoupon.getId());			
		}		
	};
	
	CouponCollectAddToCartTask.OnSucessListener cartSucessListener = new CouponCollectAddToCartTask.OnSucessListener() {
		@Override
		public void success() {
			dismiss();
			mActivity.showOkMsg(R.string.msg_add_cart_success);
		}		
	};
		
	
}
