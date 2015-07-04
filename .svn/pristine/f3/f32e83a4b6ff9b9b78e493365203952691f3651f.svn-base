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

public class GrocerySearchDeleteFromCartTask extends BaseTask {
	
	private String mKeyword;
	private ArrayList<Product> mProductList;
	private ProductResult mProductResult;
	private String mCartId;

	public GrocerySearchDeleteFromCartTask(BaseActivity activity,OnSucessListener listener, String cartId) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
	}


	public GrocerySearchDeleteFromCartTask(BaseActivity activity,OnSucessListener listener,  String cartId, String keyword) {
		super(activity);
		mCartId = cartId;
		mListener = listener;
		mKeyword = keyword;
	}
	
	private OnSucessListener mListener;	
	public interface OnSucessListener {
		public void success(String productName);
		public void fail(int failCode);
	}
	
	@Override
	protected int doInBackground() throws Exception {
		RpcClient client = CFG.getRpcClient();
		String mProductId = null;
		System.out.println("cardid ="+mCartId);
		System.out.println("call search.php? keyword="+mKeyword);
//		String response = null;
		if(mKeyword!=null) {
			System.out.println("run mKeywork != null");
//			System.out.println("search url: "+CFG.API_GROCERY_PATH+"search.php?keyword="+mKeyword);
//			response = RestClient.get(CFG.API_GROCERY_PATH+"search.php?keyword="+mKeyword);	
//			System.out.println(response);
			
			HashMap<?,?> row;
			Object[] list = client.cartProductList(mCartId);
			System.out.println("list.length: "+list.length);
			for(int i=0; i<list.length; i++) {
				row = (HashMap<?,?>) list[i];
				if(mKeyword.equalsIgnoreCase(row.get("name").toString())) {				
					mProductId = row.get("product_id").toString();
					System.out.println("delete product_id: "+mProductId);
					System.out.println("delete product_name: "+row.get("name"));
				}			
				System.out.println("delete product_id: "+mProductId);
				System.out.println("delete product_name: "+row.get("name"));
			}
			
			if(mProductId != null) {
				Map<String, String> product = new HashMap<String, String>();
				product.put("product_id", mProductId);
				Boolean success = client.cartProductRemove(mCartId, product);
				if (success) {
					return OK;
				}
				
			}
			
			return NO_DATA;
			
		} else {
			return ERROR;
		}
//		RestResult rr = GroceryApiParser.parserRestResult(response);
//		System.out.println("rr: "+rr.getData());
//		if (rr.getCode() != OK && rr.getCode() != NO_DATA) {
//			return ERROR;
//		}		
//		mProductResult = GroceryApiParser.getProduct(rr.getData());
//		
//		if (mProductResult.getList().size()==0) {
//			return NO_DATA;
//		} else if(mProductResult.getList().size() > 0) {
//			mProductList = mProductResult.getList();
//			Map<String, String> product = new HashMap<String, String>();
//			product.put("product_id", mProductList.get(0).getId());
//			Boolean success = client.cartProductRemove(mCartId, product);
//			if (success) {
//				return OK;
//			}
//		}			

	}

	@Override
	protected void success() {
		if (mListener!=null)
			mListener.success(mKeyword);
	}

	@Override
	protected void fail(int failCode) {
		System.out.println("delete fail");
		if (mListener!=null)
			mListener.fail(failCode);
			
//		if(failCode==NO_DATA) {
//			mActivity.showMsg(R.string.no_data);
//		}		
	}
	
}

