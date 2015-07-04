package com.kentec.milkbox.grocery.view;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.grocery.GroceryMainActivity;
import com.kentec.milkbox.grocery.adapter.WishlistAdapter;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.Wishlist;
import com.kentec.milkbox.grocery.view.WishlistAddDialog.OnAddClickListener;
import com.kentec.milkbox.grocery.view.WishlistAddDialog.OnDeleteClickListener;
import com.kentec.milkbox.view.BaseDialog;

public class WishlistDialog extends BaseDialog implements OnShowListener {

	private static final String TAG = "WishListDialog";

	private Context mContext;
	private ProgressDialog mProgressDialog;

	private LinearLayout mCancelLl;
	public LinearLayout mAddCartLl; // andy 20140219 配合語音，改成public

	private ListView mMainLv;
	private WishlistAdapter mAdapter;
	private ArrayList<Wishlist> mList;

	private String mCartId;
	private String mCustomerId;
	private String mWishlistId;

	public WishlistDialog(BaseActivity activity, String cid, String cartId) {
		super(activity, R.style.Dialog_Full);
		mCartId = cartId;
		mCustomerId = cid;
		mContext = activity;
		mProgressDialog = new ProgressDialog(activity);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_wishlist);

		mList = new ArrayList<Wishlist>();
		mMainLv = (ListView) findViewById(R.id.mainLv);
		mAdapter = new WishlistAdapter(mContext, mList, mWidth, mHeight);
		mMainLv.setAdapter(mAdapter);
		mMainLv.setOnItemClickListener(itemClickListener);

		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		this.reLayout(mainLl, mWidth, mHeight);

		TextView imageTv = (TextView) findViewById(R.id.imageTv);
		TextView productTv = (TextView) findViewById(R.id.productTv);
		TextView descriptionTv = (TextView) findViewById(R.id.descriptionTv);

		mCancelLl = (LinearLayout) findViewById(R.id.cancelLl);
		mAddCartLl = (LinearLayout) findViewById(R.id.addAllToCartLl);

		reLayout(mCancelLl, 3);
		reLayout(mAddCartLl, 3);

		reLayout(imageTv, 3);
		reLayout(productTv, 2);
		reLayout(descriptionTv, 5);

		mCancelLl.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				dismiss();
				mActivity.reTimeOut();
			}
		});

		mAddCartLl.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (mList.size() == 0) {
					return;
				}
				new AddToCartTask(mCartId, mList).execute();
				mActivity.reTimeOut();
			}
		});

		setOnShowListener(this);
	}

	@Override
	public void onShow(DialogInterface arg0) {
		new LoadTask(mCustomerId).execute();
	}

	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int index, long arg3) {
			final Wishlist wishlist = mList.get(index);

			final WishlistAddDialog dialog = new WishlistAddDialog(mActivity){
				@Override
				public void dismiss() {
					super.dismiss();
					WishlistDialog.this.dismiss();
				}				
			};
			dialog.setOnAddClickListener(new OnAddClickListener() {
				@Override
				public void add() {
					dialog.dismiss();
					new AddToCartTask(mCartId, wishlist).execute();
					mActivity.reTimeOut();
					
					WishlistDialog.this.dismiss();
				}
			});

			dialog.setOnDeleteClickListener(new OnDeleteClickListener() {
				@Override
				public void delete() {
					dialog.dismiss();
					new DeleteTask(mWishlistId, wishlist.getId(), index).execute();
					mActivity.reTimeOut();
					
					WishlistDialog.this.dismiss();
				}
			});

			dialog.show();
		}
	};

	class LoadTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String customerId;

		LoadTask(String id) {
			customerId = id;
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
				mWishlistId = client.checkoutexdGetWishId(customerId);
				Object[] list = client.checkoutexdGetWishList(mWishlistId);

				HashMap row;
				Product product;
				Wishlist wishlist;

				mList.clear();

				for (int i = 0; i < list.length; i++) {

					row = (HashMap) list[i];

					String response = RestClient.get(CFG.API_GROCERY_PATH + "productInfo.php?id=" + row.get("product_id"));
					RestResult rr = GroceryApiParser.parserRestResult(response);
					if (rr.getCode() != OK) {
						continue;
					}
					product = GroceryApiParser.parserProduct(new JSONObject(rr.getData()));

					wishlist = new Wishlist();
					wishlist.setId(row.get("wishlist_item_id").toString());
					wishlist.setProduct(product);
					mList.add(wishlist);

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
				if (mList.size() == 0) {
					((BaseActivity) mContext).showMsg(R.string.no_data);
					return;
				}
				mAdapter.setData(mList);
				mAdapter.notifyDataSetChanged();
			}
			if (result == ERROR) {
				((BaseActivity) mContext).showMsg(R.string.error);
			}
		}
	}

	class AddToCartTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String cartId;
		ArrayList<Wishlist> list;

		AddToCartTask(String id, Wishlist wishlist) {
			this.cartId = id;
			this.list = new ArrayList<Wishlist>();
			this.list.add(wishlist);
		}

		AddToCartTask(String id, ArrayList<Wishlist> list) {
			this.cartId = id;
			this.list = list;
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

				Object[] products = new Object[list.size()];

				for (int i = 0; i < products.length; i++) {
					HashMap<String, String> food = new HashMap<String, String>();
					food.put("product_id", list.get(i).getProduct().getId());
					food.put("qty", "1");
					products[i] = food;
				}

				Boolean success = client.cartProductsAdd(cartId, products);
				if (success) {
					return OK;
				}

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
				// andywu 2014.03.28 加入更新購物車數量
				if (mContext instanceof GroceryMainActivity)
					((GroceryMainActivity)mContext).updatecartnumber() ;
				if (list.size() > 1)
					((BaseActivity) mContext).showOkMsg(R.string.add_all_to_cart);
				else
					((BaseActivity) mContext).showOkMsg(R.string.add_to_cart);
			}
			if (result == ERROR) {
				((BaseActivity) mContext).showMsg(R.string.error);
			}
		}
	}

	class DeleteTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String wishlistId;
		String itemId;
		int index;

		DeleteTask(String wishlistId, String itemId, int index) {
			this.wishlistId = wishlistId;
			this.itemId = itemId;
			this.index = index;
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
				Boolean result = client.checkoutexdRemoveWishList(wishlistId, itemId);
				if (result) {
					return OK;
				}
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
				mList.remove(index);
				mAdapter.setData(mList);
				mAdapter.notifyDataSetChanged();
				((BaseActivity) mContext).showOkMsg(R.string.msg_the_item_was_deleted);
			}
			if (result == ERROR) {
				((BaseActivity) mContext).showMsg(R.string.error);
			}
		}
	}
	/**
	* @author andywu
	* @date 2014.03.19
	* @category 加入更新購物車數量動作
	*
	*/
	@Override
	protected void onStop()
	{
		if (mContext instanceof GroceryMainActivity)
			((GroceryMainActivity)mContext).updatecartnumber() ;
		super.onStop();
	}

}
