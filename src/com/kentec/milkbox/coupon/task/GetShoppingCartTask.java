package com.kentec.milkbox.coupon.task;

import android.content.SharedPreferences.Editor;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.task.BaseTask;

public class GetShoppingCartTask extends BaseTask  {
	
	private String mProductStoreId;
	private String mCartId;
	private String mImei;
	
	public GetShoppingCartTask(BaseActivity activity,OnSucessListener listener,String storeId, String imei) {
		super(activity);
		mListener = listener;
		mProductStoreId = storeId;
		mImei = imei;
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(String cartId);
	}

	@Override
	protected int doInBackground() throws Exception {
				
		// get grocery store id		
		String response = RestClient.get(CFG.API_GROCERY_PATH+"store.php");
		RestResult rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}							
		String groceryStoreId = GroceryApiParser.getStoreId(rr.getData());
		
		// check is grocery product coupon
		if(groceryStoreId.equals(mProductStoreId)) {			
			// get grocery shopping cart id		
			response = RestClient.get(CFG.API_GROCERY_PATH+"cartId.php?imei="+mImei);
			rr = GroceryApiParser.parserRestResult(response);
			if (rr.getCode() != OK) {
				return ERROR;
			}							
			mCartId = rr.getData();		
			return OK;
		}
		
		// check is home dine product coupon
		mCartId = mActivity.getSP().getString(mProductStoreId, null);
		if( mCartId != null ) {
			return OK;
		}
		
		// create home dine shopping cart id
		RpcClient client = CFG.getRpcClient();
		Object obj = client.cartCreate(mProductStoreId);
		mCartId = obj.toString();
		
		// save home dine shopping cart id
		Editor editor = mActivity.getSP().edit();
		editor.putString(CFG.PREF_CART_ID, mCartId);
		editor.commit();
		
		return OK;
	}

	@Override
	protected void success() {
		mListener.success(mCartId);
	}

	@Override
	protected void fail(int failCode) {
	}

}
