package com.kentec.milkbox.coupon.task;


import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.api.CouponApiParser;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.task.BaseTask;


public class CouponCollectTask extends BaseTask {
	
	private final static int QTY_ZERO = 100;
	private final static int ALREADY_IN_MY_COUPON = 101;
	
	private String mCartId;
	private String mProductId;
	private boolean isShow;
	
	public CouponCollectTask(BaseActivity activity, OnSucessListener listener, String cartId, String productId) {
		super(activity);	
		mCartId = cartId;
		mProductId = productId;
		mListener = listener;
	}

	@Override
	protected int doInBackground() throws Exception {
		
		String response = RestClient.get(CFG.API_COUPON_PATH+"productQty.php?id="+mProductId);
		RestResult rr = CouponApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}
				
		double qty = CouponApiParser.getProductQty(rr.getData());
		if(qty==0) {
			return QTY_ZERO;
		}
		
		RpcClient client = CFG.getRpcClient();
		HashMap<?,?> row;
		Object[] list = client.cartProductList(mCartId);
		for(int i=0; i<list.length; i++) {
			row = (HashMap<?,?>) list[i];
			if(mProductId.equals(row.get("product_id").toString())) {				
				return ALREADY_IN_MY_COUPON;
			}			
		}
				
		response = RestClient.get(CFG.API_COUPON_PATH+"productQty.php?id="+mProductId+"&action=reduce");
		rr = CouponApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}		
				
		Map<String, String> products = new HashMap<String, String>();
		products.put("product_id", mProductId);
		products.put("qty", "2");
		Boolean success = client.cartProductAdd(mCartId, products);
		if (success) 
			return OK;
		
		return ERROR;
	}

	@Override
	protected void success() {
		mListener.success();
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success();
	}
	
	@Override
	protected void fail(int failCode) {
		if(failCode==QTY_ZERO) {
			mActivity.showMsg(R.string.coupon_no_inventory);
		}		
		if(failCode==ALREADY_IN_MY_COUPON) {
			mActivity.showMsg(R.string.coupon_already_in_my_coupon);
		}
	}
	
	@Override
	protected void onPreExecute() {
		if(isShow) {
			super.onPreExecute();
		}
	}


}
