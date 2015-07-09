package com.kentec.milkbox.grocery.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.ProductResult;
import com.kentec.milkbox.task.BaseTask;

public class GrocerySearchAddToCartTask extends BaseTask {
	
	private String mKeyword;
	private ArrayList<Product> mProductList;
	private ProductResult mProductResult;
	private String mCartId;
	private int mQuantity=1;

	public GrocerySearchAddToCartTask(BaseActivity activity,OnSucessListener listener, String cartId) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
	}


	public GrocerySearchAddToCartTask(BaseActivity activity,OnSucessListener listener,  String cartId, String keyword) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
		mKeyword = keyword;
	}
	
	public GrocerySearchAddToCartTask(BaseActivity activity,OnSucessListener listener,  String cartId, String keyword, int quantity) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
		mKeyword = keyword;
		mQuantity = quantity;
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
		} else if(mProductResult.getList().size() > 0) {
			mProductList = mProductResult.getList();
			RpcClient client = CFG.getRpcClient();
			Map<String, String> products = new HashMap<String, String>();
			products.put("product_id", mProductList.get(0).getId());
			products.put("qty", Integer.toString(mQuantity));
			Boolean success = client.cartProductAdd(mCartId, products);
			if (success) {
				return OK;
			}
		}
		
		return ERROR;			

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
//			mActivity.showMsg(R.string.no_data);
		}		
	}
	
}

