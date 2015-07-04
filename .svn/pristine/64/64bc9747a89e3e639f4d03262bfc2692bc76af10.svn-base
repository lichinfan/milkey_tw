package com.kentec.milkbox.grocery.task;

import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.task.BaseTask;

public class ChangeCartTask extends BaseTask {
	
	private String mCartId;
	private Product mProduct;
	private int mQuantity;
	
	public ChangeCartTask(BaseActivity activity, OnSucessListener listener, String cartId, Product product, int quantity) {
		super(activity);
		mCartId = cartId;
		mProduct = product;
		mListener = listener;		
		mQuantity = quantity;
	}

	@Override
	protected int doInBackground() throws Exception {		
		RpcClient client = CFG.getRpcClient();
		Map<String, String> products = new HashMap<String, String>();
		products.put("product_id", mProduct.getId());
		products.put("qty", Integer.toString(mQuantity));
		Boolean success = client.cartProductAdd(mCartId, products);
		if (success) {
			return OK;
		}
		return ERROR;		
	}


	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success();
	}
	
	@Override
	protected void success() {
		mListener.success();
	}

	@Override
	protected void fail(int failCode) {
	}

}
