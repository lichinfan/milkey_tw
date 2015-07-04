package com.kentec.milkbox.coupon.task;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.api.CouponApiParser;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.task.BaseTask;


public class MyCouponTask extends BaseTask {
			
	private String mCartId;
	private ArrayList<Coupon> mCouponList;
		
	public MyCouponTask(BaseActivity activity,OnSucessListener listener, String cartId) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<Coupon> list);
		public void noData();
	}

	@Override
	protected int doInBackground() throws Exception {
		
		// get Coupon in shipping cart		
		RpcClient client = CFG.getRpcClient();						
		HashMap<?,?> hm = (HashMap<?,?>) client.cartInfo(mCartId);
		String couponIdStr = CouponApiParser.getCouponIdStr(hm);
		
		String response = RestClient.get(CFG.API_COUPON_PATH+"productInfo.php?id="+couponIdStr);
		RestResult rr = CouponApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}

		ArrayList<Coupon> couponList = CouponApiParser.getCouponList(rr.getData());
		if(couponList.size()==0) {
			return NO_DATA;
		}		
		
		/* 
		 *  check coupon status 
		 *  qty = 1 : coupon is use already
		 *  qty > 1 : coupon is available
		 *  default coupon qty is 2
		 */
		
		HashMap<String,Double> qtyHm = CouponApiParser.getCouponQty(hm);
		
		double qty;
		Coupon coupon;
		for(int i=0; i<couponList.size(); i++ ) {
			coupon = couponList.get(i);			
			qty = qtyHm.get(coupon.getId());
			if(qty<2) {
				coupon.setStatus(Coupon.USE);
			}
			couponList.set(i, coupon);						
		}
				
		
		// check coupon expirty date
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date expDate;
		long today = System.currentTimeMillis();
		Calendar cal = Calendar.getInstance();
		mCouponList = new ArrayList<Coupon>();
		
		for(int i=0; i<couponList.size(); i++ ) {
			coupon = couponList.get(i);
			expDate = sdf.parse(coupon.getExpDate());			
			
			cal.setTime(expDate);
			cal.add(Calendar.DATE,1);
			
			if(today<cal.getTimeInMillis()) {
				mCouponList.add(coupon);
			}			
		}	
		
		if(mCouponList.size()==0) {
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
			mListener.noData();			
		}
	}
	

}
