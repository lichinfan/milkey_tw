package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.task.BaseTask;

public class GrocerySearchTask extends BaseTask {
	
	private String mKeyword;
	private ArrayList<Product> mProductList;
	private ProductResult mProductResult;

	public GrocerySearchTask(BaseActivity activity,OnSucessListener listener) {
		super(activity);
		mListener = listener;
	}


	public GrocerySearchTask(BaseActivity activity,OnSucessListener listener,  String keyword) {
		super(activity);
		mListener = listener;
		mKeyword = keyword;
	}
	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ProductResult mProductResult);
		public void fail(int failCode);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		System.out.println("call search.php? keyword="+mKeyword);
		String response = null;
		if(mKeyword!=null) {
			System.out.println("search url: "+CFG.API_GROCERY_PATH+"search.php?keyword="+mKeyword);
			response = RestClient.get(CFG.API_GROCERY_PATH+"search.php?keyword="+mKeyword);	
			System.out.println(response);
		} else {
			return ERROR;
		}
		RestResult rr = GroceryApiParser.parserRestResult(response);
		System.out.println("rr: "+rr.getData());
		if (rr.getCode() != OK && rr.getCode() != NO_DATA) {
			return ERROR;
		}		
		mProductResult = GroceryApiParser.getProduct(rr.getData());
		
		if (mProductResult.getList().size()==0) {
			return NO_DATA;
		}
		
				
		return OK;
	}

	@Override
	protected void success() {
		if (mListener!=null)
			mListener.success(mProductResult);
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

