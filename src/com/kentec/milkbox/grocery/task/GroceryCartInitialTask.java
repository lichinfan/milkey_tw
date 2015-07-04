package com.kentec.milkbox.grocery.task;


import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.task.BaseTask;

public class GroceryCartInitialTask extends BaseTask {
	
	private String mCustomerId;
	private String mCartId;
	private String mStoreId;
	
	public GroceryCartInitialTask(BaseActivity activity,OnSucessListener listener,String customerId) {
		super(activity);
		mListener = listener;
		mCustomerId = customerId;
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(String cartId,String storeId);
	}
	
	@Override
	protected void success() {
		mListener.success(mCartId, mStoreId);
	}

	@Override
	protected void fail(int failCode) {		
	}

	@Override
	protected int doInBackground() throws Exception {
		
		// get shopping cart id		
		RpcClient client = CFG.getRpcClient();
		mCartId = client.checkoutexdGetCartId(mCustomerId);
//		mCartId = "188";
		
		// get grocery store id		
		String response = RestClient.get(CFG.API_GROCERY_PATH+"store.php");
		RestResult rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}							
		mStoreId = GroceryApiParser.getStoreId(rr.getData());
				
		return OK;
	}

	@Override
	protected void onPreExecute() {		
		// not show progress dialog
	}

}
