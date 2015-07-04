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
import com.kentec.milkbox.coupon.task.CouponAddToCartTask;
import com.kentec.milkbox.coupon.task.CouponRemoveTask;
import com.kentec.milkbox.coupon.task.GetShoppingCartTask;
import com.kentec.milkbox.task.LoadImageTask;
import com.kentec.milkbox.view.BaseDialog;


public class RemoveCouponDialog extends BaseDialog {

	private String mCartId;
	private Coupon mCoupon;
	private int mImageWidth;
	
	public RemoveCouponDialog(BaseActivity activity, String cartId, Coupon coupon) {
		super(activity, R.style.Dialog_Full);		
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		mHeight = (int) (display.getHeight() * 0.8);
		mWidth = (int) (display.getWidth() * 0.6);
		mBaseWidth = mWidth / 10;	
		mImageWidth = (int) (mWidth * 0.4);		
		mCartId = cartId;
		mCoupon = coupon;		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.dialog_coupon_remove);
		
		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView expDateTv = (TextView) findViewById(R.id.expDateTv);
		ImageView mainIv = (ImageView) findViewById(R.id.mainIv);		
		TextView descTv = (TextView) findViewById(R.id.descTv);
		
		reLayout(mainLl,mWidth,mHeight);
		
		nameTv.setText(mCoupon.getName());
		expDateTv.setText(mCoupon.getExpDate());
		descTv.setText(mCoupon.getDesc());
		
		LinearLayout deleteLl = (LinearLayout) findViewById(R.id.deleteLl);
		reLayout(deleteLl,4);
		deleteLl.setOnClickListener(deleteClickListener);

		LinearLayout addToCartLl = (LinearLayout) findViewById(R.id.addToCartLl);
		reLayout(addToCartLl,4);
		addToCartLl.setOnClickListener(addToCartListener);

		new LoadImageTask(mainIv, mCoupon.getImage(), mImageWidth, mImageWidth).execute();
	}	

	LinearLayout.OnClickListener deleteClickListener = new LinearLayout.OnClickListener() {
		@Override
		public void onClick(View v) {
			mActivity.reTimeOut();						
			if(mCoupon.getStatus()==Coupon.USE) {				
				mActivity.showMsg(R.string.coupon_cannt_remove_coupon);
			} else {	
				new CouponRemoveTask(mActivity,sucessListener,mCartId,mCoupon.getId());
			}
		}		
	};
 	
	CouponRemoveTask.OnSucessListener sucessListener = new CouponRemoveTask.OnSucessListener() {
		@Override
		public void success() {
			dismiss();
			if(mListener!=null) {
				mListener.delete(mCoupon);
			}
		}		
	};
	
	private OnDeleteListener mListener;	
	public interface OnDeleteListener {
		public void delete(Coupon coupon);
	}
	
	public void setOnDeleteListener(OnDeleteListener listener) {
		mListener = listener;
	}
	
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

	GetShoppingCartTask.OnSucessListener getShoppingCartSuccessListener = new GetShoppingCartTask.OnSucessListener() {
		@Override
		public void success(String shoppingCartId) {
			new CouponAddToCartTask(mActivity,cartSucessListener,shoppingCartId,mCoupon.getLinkSku());			
		}		
	};
	
	CouponAddToCartTask.OnSucessListener cartSucessListener = new CouponAddToCartTask.OnSucessListener() {
		@Override
		public void success() {
			dismiss();
			mActivity.showOkMsg(R.string.msg_add_cart_success);
		}		
	};
	
}
