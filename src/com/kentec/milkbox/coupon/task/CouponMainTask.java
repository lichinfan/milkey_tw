package com.kentec.milkbox.coupon.task;

import java.util.ArrayList;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.coupon.api.CouponApiParser;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.task.BaseTask;


public class CouponMainTask extends BaseTask {
		
	private String mCategoryId;
	private ArrayList<Coupon> mCouponList;
		
	public CouponMainTask(BaseActivity activity,OnSucessListener listener,String categoryId) {
		super(activity);
		mListener = listener;
		mCategoryId = categoryId;
	}
	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<Coupon> list);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		
		String response = RestClient.get(CFG.API_COUPON_PATH + "product.php?categoryId=" + mCategoryId);		
		RestResult rr = CouponApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}
		
		mCouponList = CouponApiParser.getCouponList(rr.getData());
		if( mCouponList.size()==0) {
			return NO_DATA;
		}
		
		return OK;		
	}
	
	@Override
	protected void success() {
		mListener.success(mCouponList);		
	}	

	@Override
	protected void fail(int failCode) {
		if(failCode==NO_DATA) {
			mActivity.showMsg(R.string.no_data);
		}		
	}
	


}
