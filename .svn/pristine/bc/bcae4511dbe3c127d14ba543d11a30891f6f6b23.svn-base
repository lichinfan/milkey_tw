package com.kentec.milkbox.view;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.grocery.api.GroceryApiParser;
import com.kentec.milkbox.grocery.data.OrderHistory;
import com.kentec.milkbox.utils.DateUtil;


public class OrderHistoryDialog extends BaseDialog implements OnShowListener {
	
	private static final String TAG = "OrderHistoryDialog";
	
	private BaseActivity mActivity;
	private ProgressDialog mProgressDialog;	
		
	private GridView mMainGv;
	private OrderHistoryAdapter mAdapter;	
	private ArrayList<OrderHistory> mList;
	
	private String mCartId;
	private String mCustomerId;
	private String mStoreId;
	
	public OrderHistoryDialog(BaseActivity context, String cid, String cartId, String storeId) {
		super(context,R.style.Dialog_Full);
		mCartId = cartId;
		mCustomerId = cid;
		mStoreId = storeId;
		mActivity = context;
		mProgressDialog = new ProgressDialog(context);	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);		
		setContentView(R.layout.dialog_order_history);
				
		mList = new ArrayList<OrderHistory>();
		mMainGv = (GridView) findViewById(R.id.mainGv);
		mAdapter = new OrderHistoryAdapter(mActivity, mList, mBaseWidth);
		mMainGv.setAdapter(mAdapter);
		mMainGv.setOnItemClickListener(itemClickListener);
		
		
		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		this.reLayout(mainLl, mWidth, mHeight);
						
		TextView orderNumTv = (TextView) findViewById(R.id.orderNumTv);
		TextView orderDateTv = (TextView) findViewById(R.id.orderDateTv);
		TextView shipToTv = (TextView) findViewById(R.id.shipToTv);			
		TextView orderTotalTv = (TextView) findViewById(R.id.orderTotalTv);
		TextView statusTv = (TextView) findViewById(R.id.statusTv);

		reLayout(orderNumTv,2);
		reLayout(orderDateTv,2);
		reLayout(shipToTv,2);
		reLayout(orderTotalTv,2);
		reLayout(statusTv,2);
		
		setOnShowListener(this);
		
	}
	
	@Override
	public void onShow(DialogInterface arg0) {
		new LoadTask(mCustomerId,mStoreId).execute();
	}
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, final int index, long arg3) {
			final OrderHistory oh = mList.get(index);
			OrderInfoDialog dialog = new OrderInfoDialog(mActivity,mCartId,oh.getNum());
			dialog.show();			
			mActivity.reTimeOut();
		}		
	};
	
		
	class LoadTask extends AsyncTask<String,Void,Integer> implements AsyncTaskCode {
		
		String customerId;
		String storeId;
		LoadTask(String id, String oid) {
			customerId = id;
			storeId = oid;
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
				Object[] list = client.salesOrderList(customerId,storeId);
				mList.clear();
				OrderHistory oh;
				HashMap<?,?> row;
				for(int i=0; i<list.length; i++) {
					row = (HashMap<?,?>) list[i];
					oh = GroceryApiParser.parserOrderHistory(row);
					oh.setDate(DateUtil.toLocalTime(oh.getDate()));					
					mList.add(oh);					
				}
												
				return OK;
				
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
				if(mList.size()==0) {
					mActivity.showMsg(R.string.no_data);
					return;
				}				
				mAdapter.setData(mList);
				mAdapter.notifyDataSetChanged();
			}
			if(result==ERROR) {
				mActivity.showMsg(R.string.error);
			}			
		}		
	}


}
