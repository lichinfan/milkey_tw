package com.kentec.milkbox.grocery.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.Format;


public class ShoppingCartAdapter extends BaseAdapter {

	private static final String TAG = "ShoppingCartAdapter";
	
	private BaseActivity mactivity;
	private ProgressDialog mProgressDialog;
	
	private int mBaseWidth;
	
	private String mCartId;
	private ArrayList<Product> mList;
	private LayoutInflater mLayoutInflater;
		
	public ShoppingCartAdapter(BaseActivity activity, ArrayList<Product> list, int baseWidth, String cartId) {
		mactivity = activity;
		mList = list;		
		mCartId = cartId;		
		mBaseWidth = baseWidth;
		mLayoutInflater = (LayoutInflater) mactivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mProgressDialog = new ProgressDialog(mactivity);
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup vg) {
		
		ViewHolder vh;		
		if (convertView == null) {
			
			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.grocery_shopping_cart_dialog_row, vg, false);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
			vh.qtyTv = (TextView) convertView.findViewById(R.id.qtyTv);
			vh.subtotalTv = (TextView) convertView.findViewById(R.id.subtotalTv);
			
			reLayout(vh.nameTv,5);
			reLayout(vh.priceTv,2);
			reLayout(vh.qtyTv,1);
			reLayout(vh.subtotalTv,2);
			
			convertView.setTag(vh);		
			
		} else {			
			vh =  (ViewHolder) convertView.getTag();			
		}
		
		try {
			
			Product row = mList.get(position);
			vh.nameTv.setText(row.getName());			
			vh.priceTv.setText(Format.price(row.getPrice()));
			vh.qtyTv.setText(Integer.toString((int)row.getQty()));
			vh.subtotalTv.setText(Format.price(row.getSubtotal()));						
			
		} catch (Exception e) {
			Log.e(TAG,e.toString());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView nameTv;
		TextView priceTv;
		TextView qtyTv;
		TextView subtotalTv;		
		Button deleteBtn;
	}	
	
	public void setData(ArrayList<Product> list) {
		mList = list;
	}	
	
	
	OnClickListener deleteClickListener = new OnClickListener() {
		@Override
		public void onClick(View view) {
			int position = (Integer) view.getTag();
			Product product = mList.get(position);			
			new DeleteItemTask(position,mCartId,product.getId()).execute();
			mactivity.reTimeOut();
		}		
	};

	
	class DeleteItemTask extends AsyncTask<String,Void,Integer> implements AsyncTaskCode {
		
		int position;
		String cartId;
		String productId;
		
		DeleteItemTask(int position, String cartid,String productId) {
			this.cartId = cartid;
			this.productId = productId;
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
				
				Map<String,String> product = new HashMap<String,String>();
				product.put("product_id", productId);
				
				boolean success = client.cartProductRemove(cartId, product);				
				if(success) {
					return OK;
				}
				
			} catch(Exception e) {
				Log.e(TAG,e.toString());
			}
			return ERROR;
		}
		
		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();
			
			if (isCancelled()) {
				return;
			}
			
			if(result==OK) {
				mList.remove(position);				
				notifyDataSetChanged();
			}
		}	
	}

	private void reLayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}
}
