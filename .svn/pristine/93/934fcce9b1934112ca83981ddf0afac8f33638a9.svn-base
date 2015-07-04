package com.kentec.milkbox.coupon.task;


import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.task.BaseTask;

public class CouponCreateCartTask extends BaseTask {
	
	private String mCartId;
	
	public CouponCreateCartTask(BaseActivity activity,OnSucessListener listener) {
		super(activity);		
		mListener = listener;
	}

	@Override
	protected int doInBackground() throws Exception {		
		RpcClient client = CFG.getRpcClient();
		Integer i = (Integer) client.cartCreate();
		mCartId = i.toString();
		return OK;
	}

	@Override
	protected void success() {
		mListener.success(mCartId);
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(String cartId);
	}
	
	@Override
	protected void fail(int failCode) {
	}
}
