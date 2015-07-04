package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.task.BaseTask;


public class GroceryMoreProductTask extends BaseTask {
	
	private int mPage;
	private String mCategoryId;
	private ArrayList<Product> mProductList;
	
	public GroceryMoreProductTask(BaseActivity activity,OnSucessListener listener,String categoryId,int page) {
		super(activity);
		mPage = page;
		mCategoryId = categoryId;
		mListener = listener;
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ArrayList<Product> list);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		
		String product = RestClient.get(CFG.API_GROCERY_PATH + "product.php?categoryId=" + mCategoryId + "&page=" + mPage);
		RestResult pRs = GroceryApiParser.parserRestResult(product);
		if (pRs.getCode() != OK) {
			return ERROR;
		}

		ProductResult pr = GroceryApiParser.getProduct(pRs.getData());
		mProductList = pr.getList();
		
		return OK;
	}

	@Override
	protected void success() {
		mListener.success(mProductList);
	}

	@Override
	protected void fail(int failCode) {
	}
	

	
}
