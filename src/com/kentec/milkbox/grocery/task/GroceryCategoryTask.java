package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.task.BaseTask;


public class GroceryCategoryTask extends BaseTask {
	
	private String mKeyword;
	private String mCategoryId;
	private ArrayList<Category> mCategoryList;

	public GroceryCategoryTask(BaseActivity activity,OnSucessListener listener) {
		super(activity);
		mListener = listener;
	}

	public GroceryCategoryTask(BaseActivity activity,OnSucessListener listener, String categoryId) {
		super(activity);
		mCategoryId = categoryId;
		mListener = listener;
	}

	public GroceryCategoryTask(BaseActivity activity,OnSucessListener listener, String categoryId, String keyword) {
		super(activity);
		mCategoryId = categoryId;
		mListener = listener;
		mKeyword = keyword;
	}
	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<Category> list);
		public void fail(int failCode);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		
		String response = null;
		if(mCategoryId == null) {			
			if(mKeyword==null) {
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php"); 				
			} else {
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php?keyword="+mKeyword); 								
			}			
		} else {
			if(mKeyword==null) {
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId);				
			} else {
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId+"&keyword="+mKeyword);								
			}
		}
				
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
		if (mListener!=null)
			mListener.success(mCategoryList);
	}

	@Override
	protected void fail(int failCode) {
		if (mListener!=null)
			mListener.fail(failCode);
			
		if(failCode==NO_DATA) {
			mActivity.showMsg(R.string.no_data);
		}		
	}
	
}
