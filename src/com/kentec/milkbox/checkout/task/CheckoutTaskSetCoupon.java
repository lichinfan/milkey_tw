package com.kentec.milkbox.checkout.task;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.AsyncTask;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.Total;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.DEBUG;


public class CheckoutTaskSetCoupon extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {
	
	private static final int COUPON_ERROR=101; 
	
	private CheckoutActivity mActivity;
	
	private Coupon mCoupon;
	
	public CheckoutTaskSetCoupon(CheckoutActivity activity, Coupon coupon) {
		this.mActivity = activity;
		this.mCoupon = coupon;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mActivity.getViewGroup().getProgressDialog().show();
	}

	@Override
	protected Integer doInBackground(String... arg0) {
		
		RpcClient client = CFG.getRpcClient();		
		String cartId = mActivity.getData().getCartId();
		try {			
			if(mCoupon==null || mCoupon.getCode()==null) {
				client.cartCouponRemove(cartId);				
			} else {
				client.cartCouponAdd(cartId,mCoupon.getCode());
				mActivity.getOrder().setCoupon(mCoupon);				
			}			
		}catch(Exception e) {
			return COUPON_ERROR;
		}		
		
		try {
								
			HashMap<?, ?> map = (HashMap<?, ?>) client.cartInfo(cartId);
			Object[] productArrary = (Object[]) map.get("items");
			ArrayList<Product> productList = APIParser.parserProduct(productArrary);
			mActivity.getData().setProductList(productList);

			Object[] totalArrary = client.cartTotals(cartId);
			ArrayList<Total> totalList = APIParser.parserTotals(totalArrary);
			mActivity.getData().setTotalList(totalList);

			return OK;

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		mActivity.getViewGroup().getProgressDialog().dismiss();
		if (result == OK) {
			mActivity.getMethod().nextPage();
		} else if (result == COUPON_ERROR) {
			mActivity.showMsg(R.string.checkout_cannot_use_this_coupon);			
		} else if (result == ERROR) {
			mActivity.showMsg(R.string.error);
		}		
	}

}