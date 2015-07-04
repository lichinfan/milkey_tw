package com.kentec.milkbox.coupon.task;

import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.coupon.api.CouponApiParser;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.task.BaseTask;

public class CouponCollectAddToCartTask extends BaseTask {

	private final static int QTY_ZERO = 100;

	private String mCouponCartId;
	private String mProductId;

	private String mShoppingCartId;
	private String mProductSku;

	private int mQuantity;

	public CouponCollectAddToCartTask(BaseActivity activity, OnSucessListener listener, String cartId,
			String productSku, String couponCartId, String productId) {
		this(activity, listener, cartId, productSku, 1, couponCartId, productId);
	}

	public CouponCollectAddToCartTask(BaseActivity activity, OnSucessListener listener, String cartId,
			String productSku, int quantity, String couponCartId, String productId) {
		super(activity);
		mListener = listener;
		mShoppingCartId = cartId;
		mProductSku = productSku;
		mQuantity = quantity;		
		mCouponCartId = couponCartId;
		mProductId = productId;
	}

	private OnSucessListener mListener;

	public interface OnSucessListener {
		public void success();
	}

	@Override
	protected int doInBackground() throws Exception {

		// collect coupon
		String response = RestClient.get(CFG.API_COUPON_PATH + "productQty.php?id=" + mProductId);
		RestResult rr = CouponApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}

		double qty = CouponApiParser.getProductQty(rr.getData());
		if (qty == 0) {
			return QTY_ZERO;
		}

		RpcClient client = CFG.getRpcClient();
		HashMap<?, ?> row;
		boolean inMyCoupon = false;
		Object[] list = client.cartProductList(mCouponCartId);
		for (int i = 0; i < list.length; i++) {
			row = (HashMap<?, ?>) list[i];
			if (mProductId.equals(row.get("product_id").toString())) {
				inMyCoupon = true;
			}
		}

		if (inMyCoupon == false) {
			response = RestClient.get(CFG.API_COUPON_PATH + "productQty.php?id=" + mProductId + "&action=reduce");
			rr = CouponApiParser.parserRestResult(response);
			if (rr.getCode() != OK) {
				return ERROR;
			}

			Map<String, String> products = new HashMap<String, String>();
			products.put("product_id", mProductId);
			products.put("qty", "2");
			Boolean success = client.cartProductAdd(mCouponCartId, products);
			if (false == success)
				return ERROR;
		}

		// add product to my shopping cart
		Map<String, String> products = new HashMap<String, String>();
		products.put("sku", mProductSku);
		products.put("qty", Integer.toString(mQuantity));
		Boolean success = client.cartProductAdd(mShoppingCartId, products);
		if (false == success) {
			return ERROR;
		}

		return OK;
	}

	@Override
	protected void success() {
		mListener.success();
	}

	@Override
	protected void fail(int failCode) {
	}

}
