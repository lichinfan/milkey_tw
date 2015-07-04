package com.kentec.milkbox.coupon.task;


import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.task.BaseTask;


public class CouponRemoveTask extends BaseTask {
	
	private String mCartId;
	private String mProductId;
	
	public CouponRemoveTask(BaseActivity activity, OnSucessListener listener, String cartId, String productId) {
		super(activity);	
		mCartId = cartId;
		mProductId = productId;
		mListener = listener;
	}

	@Override
	protected int doInBackground() throws Exception {				
		RpcClient client = CFG.getRpcClient();				
		Map<String, String> products = new HashMap<String, String>();
		products.put("product_id", mProductId);
		products.put("qty", "2");
		Boolean success = client.cartProductRemove(mCartId, products);
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
	}
	


}
