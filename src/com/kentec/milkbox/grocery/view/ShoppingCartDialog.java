package com.kentec.milkbox.grocery.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.grocery.GroceryMainActivity;
import com.kentec.milkbox.grocery.adapter.ShoppingCartAdapter;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.task.ChangeCartTask;
import com.kentec.milkbox.grocery.task.DeleteItemTask;
import com.kentec.milkbox.grocery.view.SetQuantityDialog.OnSetClickListener;
import com.kentec.milkbox.utils.Format;
import com.kentec.milkbox.view.BaseDialog;


public class ShoppingCartDialog extends BaseDialog implements OnShowListener {

	private static final String TAG = "ShoppingCartDialog";

	private LinearLayout mCancelLl;
	public LinearLayout mCheckoutLl; // andy 20140219 配合語音，改成public

	private ListView mMainLv;
	private TextView mTotalTv;
	private LinearLayout mMainLl;
	protected ProgressDialog mProgressDialog;

	private String mCartId;
	private ArrayList<Product> mList;
	private ShoppingCartAdapter mAdapter;

	private double mTotal;
	private int mPosition;
	private int mQuanitiy;
	
	private BaseActivity mBaseActivity ;
	
	public ShoppingCartDialog(BaseActivity activity, String cartId) {
		super(activity, R.style.Dialog_Full);
		mCartId = cartId;
		mProgressDialog = new ProgressDialog(activity);
		
		mBaseActivity = activity ;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.grocery_shopping_cart_dialog);

		mTotalTv = (TextView) findViewById(R.id.totalTv);

		mMainLl = (LinearLayout) findViewById(R.id.mainLl);
		LayoutParams params = mMainLl.getLayoutParams();
		params.width = mWidth;
		params.height = mHeight;
		mMainLl.setLayoutParams(params);

		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView priceTv = (TextView) findViewById(R.id.priceTv);
		TextView qtyTv = (TextView) findViewById(R.id.qtyTv);
		TextView subtotalTv = (TextView) findViewById(R.id.subtotalTv);

		reLayout(nameTv, 5);
		reLayout(priceTv, 2);
		reLayout(qtyTv, 1);
		reLayout(subtotalTv, 2);

		mMainLv = (ListView) findViewById(R.id.mainLv);
		mList = new ArrayList<Product>();
		mAdapter = new ShoppingCartAdapter(mActivity, mList, mBaseWidth, mCartId);
		mMainLv.setAdapter(mAdapter);
		mMainLv.setOnItemClickListener(itemClickListener);

		mCancelLl = (LinearLayout) findViewById(R.id.cancelLl);
		mCheckoutLl = (LinearLayout) findViewById(R.id.checkoutLl);

		reLayout(mCancelLl, 3);
		reLayout(mCheckoutLl, 3);

		mCancelLl.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

		mCheckoutLl.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mList.size()>0) {
					dismiss();
					Intent i = new Intent(mActivity, CheckoutActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("cartId", mCartId);
					i.putExtras(bundle);
					mActivity.startActivityForResult(i, GroceryMainActivity.RESULT_CHECKOUT);
					mActivity.reTimeOut();
				} else {
					mActivity.showMsg(R.string.no_data);
				}
			}
		});

		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface dialog) {
		new LoadCartTask(mCartId).execute();
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3) {

			final Product product = mList.get(position);			
			final SetQuantityDialog dialog = new SetQuantityDialog(mActivity,(int)product.getQty());
			
			dialog.setOnSetClickListener(new OnSetClickListener() {
				@Override
				public void set(int quantity) {
					dialog.dismiss();
					mActivity.reTimeOut();
					mPosition = position;
					mQuanitiy = quantity;
					new ChangeCartTask(mActivity,changeSuccessListener,mCartId,product,quantity);
				}

				@Override
				public void delete() {
					dialog.dismiss();
					mActivity.reTimeOut();
					mPosition = position;
					new DeleteItemTask(mActivity, deleteSuccessListener, mCartId, product);					
				}
			});
			
			dialog.show();
			
		}
	};

	ChangeCartTask.OnSucessListener changeSuccessListener = new ChangeCartTask.OnSucessListener() {
		@Override
		public void success() {		
			Product p = mList.get(mPosition);
			mTotal = mTotal - p.getSubtotal();
			
			p.setQty(mQuanitiy);
			mTotal = mTotal + p.getSubtotal();
			
			mList.set(mPosition, p);
			mAdapter.setData(mList);			
			mAdapter.notifyDataSetChanged();
			
			mTotalTv.setText(Format.price(mTotal));						
		}
	};

	
	DeleteItemTask.OnSucessListener deleteSuccessListener = new DeleteItemTask.OnSucessListener() {
		@Override
		public void success(Product product) {		
			mList.remove(mPosition);
			mAdapter.setData(mList);
			mAdapter.notifyDataSetChanged();
			mTotal = mTotal - product.getSubtotal();
			mTotalTv.setText(Format.price(mTotal));
			mActivity.showOkMsg(R.string.msg_the_item_was_deleted);
			
			/**
			* @author andywu
			* @date 2014.03.21
			* @category 加入更新購物車數量動作
			*
			*/
			if (mBaseActivity instanceof GroceryMainActivity)
				((GroceryMainActivity)mBaseActivity).updatecartnumber() ;
			
		}
	};
	
	
	class LoadCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String cartId;

		LoadCartTask(String id) {
			cartId = id;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {

				RpcClient client = CFG.getRpcClient();
				HashMap<?,?> hm = (HashMap<?,?>) client.cartInfo(cartId);
				Object[] list = (Object[]) hm.get("items");
				
				if(list.length==0) {
					return NO_DATA;
				}
				
				HashMap<?,?> row;
				Product product;
				mList.clear();
				mTotal = 0;
				double subtotal;
				for (int i = 0; i < list.length; i++) {
					row = (HashMap<?,?>) list[i];
					product = new Product();
					product.setId(row.get("product_id").toString());
					product.setName(row.get("name").toString());
					product.setPrice(Double.parseDouble(row.get("price").toString()));
					product.setQty(Double.parseDouble(row.get("qty").toString()));
					subtotal = Double.parseDouble(row.get("row_total").toString());
					mTotal += subtotal;
					product.setSubtotal(subtotal);
					mList.add(product);
				}

				return OK;

			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();

			if (isCancelled()) {
				return;
			}
			
			if (result == OK) {
				mAdapter.setData(mList);
				mAdapter.notifyDataSetChanged();
				mTotalTv.setText(Format.price(mTotal));
			}
			
			if(result==NO_DATA) {
				mActivity.showMsg(R.string.no_data);
			}
			
		}
	}
			
	private void reLayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);
	}

}
