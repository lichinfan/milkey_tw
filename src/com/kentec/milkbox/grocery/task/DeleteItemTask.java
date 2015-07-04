package com.kentec.milkbox.grocery.task;

import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.task.BaseTask;

public class DeleteItemTask extends BaseTask {
	
	private String mCartId;
	private Product mProduct;
	
	public DeleteItemTask(BaseActivity activity, OnSucessListener listener, String cartId, Product product) {
		super(activity);
		mCartId = cartId;
		mProduct = product;
		mListener = listener;
	}

	@Override
	protected int doInBackground() throws Exception {
		RpcClient client = CFG.getRpcClient();
		Map<String, String> hm = new HashMap<String, String>();
		hm.put("product_id", mProduct.getId());
		boolean success = client.cartProductRemove(mCartId, hm);
		if (success) {
			return OK;
		}
		return ERROR;
	}


	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(Product product);
	}
	
	@Override
	protected void success() {
		mListener.success(mProduct);
	}

	@Override
	protected void fail(int failCode) {
	}

}
