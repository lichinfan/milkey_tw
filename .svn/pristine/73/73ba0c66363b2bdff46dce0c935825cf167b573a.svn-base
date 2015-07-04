package com.kentec.milkbox.coupon.task;

import java.util.ArrayList;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.task.BaseTask;

public class CouponNaviTask extends BaseTask {
			
	private ArrayList<Category> mCategoryList;
		
	public CouponNaviTask(BaseActivity activity,OnSucessListener listener) {
		super(activity);
		mListener = listener;
	}
	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<Category> list);
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected int doInBackground() throws Exception {
		mCategoryList = new ArrayList<Category>();

		String response = RestClient.get(CFG.API_COUPON_PATH+"category.php");
		RestResult rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK) {
			return ERROR;
		}
		
		mCategoryList = GroceryApiParser.getCategoryArray(rr.getData());
		if (mCategoryList.size() == 0) {
			return NO_DATA;
		}		
		
		return OK;		
	}
	
	@Override
	protected void success() {
		mListener.success(mCategoryList);		
	}	

	@Override
	protected void fail(int failCode) {
		if(failCode==NO_DATA) {
			mActivity.showMsg(R.string.no_data);
		}		
	}
	


}
