package com.kentec.milkbox.grocery.task;


import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.task.BaseTask;

public class GroceryInitialTask extends BaseTask {
	
	private String mImei;
	private String mCartId;
	private String mStoreId;
	
	public GroceryInitialTask(BaseActivity activity,OnSucessListener listener,String iemi) {
		super(activity);
		mListener = listener;
		mImei = iemi;
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
		String response = RestClient.get(CFG.API_GROCERY_PATH+"cartId.php?imei="+mImei);
		RestResult rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}							
		mCartId = rr.getData();
				
		// get grocery store id				
		response = RestClient.get(CFG.API_GROCERY_PATH+"store.php");
		rr = GroceryApiParser.parserRestResult(response);
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
