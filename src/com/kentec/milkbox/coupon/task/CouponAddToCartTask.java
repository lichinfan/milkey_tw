package com.kentec.milkbox.coupon.task;

import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.task.BaseTask;

public class CouponAddToCartTask extends BaseTask {

	private String mShoppingCartId;
	private String mProductSku;
	private int mQuantity;

	public CouponAddToCartTask(BaseActivity activity, OnSucessListener listener, String cartId, String productSku) {
		this(activity, listener, cartId, productSku, 1);
	}

	public CouponAddToCartTask(BaseActivity activity, OnSucessListener listener, String cartId, String productSku,
			int quantity) {
		super(activity);
		mListener = listener;
		mShoppingCartId = cartId;
		mProductSku = productSku;
		mQuantity = quantity;
	}

	private OnSucessListener mListener;

	public interface OnSucessListener {
		public void success();
	}

	@Override
	protected int doInBackground() throws Exception {
		RpcClient client = CFG.getRpcClient();
		Map<String, String> products = new HashMap<String, String>();
		products.put("sku", mProductSku);
		products.put("qty", Integer.toString(mQuantity));
		Boolean success = client.cartProductAdd(mShoppingCartId, products);
		if (success) {
			return OK;
		}
		return ERROR;
	}

	@Override
	protected void success() {
		mListener.success();
	}

	@Override
	protected void fail(int failCode) {
	}

}
