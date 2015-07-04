package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;

import android.util.Log;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Category;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.task.BaseTask;


public class GroceryCategoryProductTask extends BaseTask {
	
	private String mKeyword;
	private String mCategoryId;
	private ArrayList<Category> mCategoryList;
	private ProductResult mProductResult;
	
	
	public GroceryCategoryProductTask(BaseActivity activity,OnSucessListener listener,String categoryId) {
		super(activity);
		mCategoryId = categoryId;
		mListener = listener;
	}

	public GroceryCategoryProductTask(BaseActivity activity,OnSucessListener listener,String categoryId, String keyword) {
		super(activity);
		mCategoryId = categoryId;
		mListener = listener;
		mKeyword = keyword;
	}

	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(ProductResult pr,ArrayList<Category> list);
		public void fail(int failCode) ;
	}
	
	@Override
	protected int doInBackground() throws Exception {
		
		// get category		
		String response = null;
		Boolean mCategoryIsNull = false ; //andywu 2014.03.03 用於辨識搜尋分類結果是否找不到資料
		
		if(mCategoryId==null) {
			if(mKeyword==null) {
//				Log.d("andy",CFG.API_GROCERY_PATH+"category.php");
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php");
			} else {
//				Log.d("andy",CFG.API_GROCERY_PATH+"category.php?keyword="+mKeyword);
				response =RestClient.get(CFG.API_GROCERY_PATH+"category.php?keyword="+mKeyword);
			}				
		} else {
			if(mKeyword==null) {
//				Log.d("andy",CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId);
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId);
			} else {
//				Log.d("andy",CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId+"&keyword="+mKeyword);
				response = RestClient.get(CFG.API_GROCERY_PATH+"category.php?categoryId=" + mCategoryId+"&keyword="+mKeyword);
			}				
		}
		
		
		RestResult rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK && rr.getCode() != NO_DATA) { //andywu 2014.03.03 把NO_DATA獨立
			return ERROR;
		}
		else if ( rr.getCode() == NO_DATA ) //andywu 2014.03.03 NO_DATA時
			mCategoryIsNull = true ;
		
		mCategoryList = GroceryApiParser.getCategoryArray(rr.getData());
		
		// get Product
		if(mCategoryId==null ) {
			if(mKeyword==null) {
//				Log.d("andy",CFG.API_GROCERY_PATH + "product.php");
				response = RestClient.get(CFG.API_GROCERY_PATH + "product.php");
			} else {
//				Log.d("andy",CFG.API_GROCERY_PATH + "product.php?keyword="+mKeyword);
				response = RestClient.get(CFG.API_GROCERY_PATH + "product.php?keyword="+mKeyword);
			}			
		} else {
			if(mKeyword==null) {
//				Log.d("andy",CFG.API_GROCERY_PATH + "product.php?categoryId=" + mCategoryId);
				response = RestClient.get(CFG.API_GROCERY_PATH + "product.php?categoryId=" + mCategoryId);
			} else {
//				Log.d("andy",CFG.API_GROCERY_PATH + "product.php?categoryId=" + mCategoryId+"&keyword="+mKeyword);
				response = RestClient.get(CFG.API_GROCERY_PATH + "product.php?categoryId=" + mCategoryId+"&keyword="+mKeyword);
			}
		}
		
		rr = GroceryApiParser.parserRestResult(response);
		if (rr.getCode() != OK && rr.getCode() != NO_DATA) { //andywu 2014.03.03 把NO_DATA獨立
			return ERROR;
		}		
		mProductResult = GroceryApiParser.getProduct(rr.getData());
		
//		Log.d("andy","mCategoryList.size() =" + mCategoryList.size() + "  mProductResult.getList().size() =" + mProductResult.getList().size());
		// check no data
		if (mCategoryList.size() == 0 && mProductResult.getList().size()==0) {
			return NO_DATA;
		}
		if (mCategoryIsNull && rr.getCode() == NO_DATA) //andywu 2014.03.03 當分類跟內容都搜尋不到時，改成顯示NO_DATA
			return NO_DATA;
		return OK;
	}

	@Override
	protected void success() {
		if (mListener!=null)
			mListener.success(mProductResult,mCategoryList);
	}

	@Override
	protected void fail(int failCode) {
		if (mListener!=null)
			mListener.fail(failCode);
		
		if(failCode==NO_DATA) {
//			mActivity.showMsg(R.string.no_data);
			mActivity.showMsg(R.string.not_found); //andywu 20140306 修改內容
		}
	}
	

	
}
