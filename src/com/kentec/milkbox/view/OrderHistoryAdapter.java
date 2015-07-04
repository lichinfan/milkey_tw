package com.kentec.milkbox.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.CacheBaseAdapter;
import com.kentec.milkbox.grocery.data.OrderHistory;
import com.kentec.milkbox.utils.Format;


public class OrderHistoryAdapter extends CacheBaseAdapter {

	private static final String TAG = "OrderHistoryAdapter";
	
	private int mBaseWidth;	
	private ArrayList<OrderHistory> mList;
	private LayoutInflater mLayoutInflater;
	
	public OrderHistoryAdapter(Context context, ArrayList<OrderHistory> list, int baseWidth) {
		mList = list;		
		mBaseWidth = baseWidth;
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
			convertView = mLayoutInflater.inflate(R.layout.dialog_order_history_row, vg, false);
			
			vh.orderNumTv = (TextView) convertView.findViewById(R.id.orderNumTv);
			vh.orderDateTv = (TextView) convertView.findViewById(R.id.orderDateTv);
			vh.shipToTv = (TextView) convertView.findViewById(R.id.shipToTv);			
			vh.orderTotalTv = (TextView) convertView.findViewById(R.id.orderTotalTv);
			vh.statusTv = (TextView) convertView.findViewById(R.id.statusTv);
			
			reLayout(vh.orderNumTv,2);
			reLayout(vh.orderDateTv,2);
			reLayout(vh.shipToTv,2);
			reLayout(vh.orderTotalTv,2);
			reLayout(vh.statusTv,2);
			
			convertView.setTag(vh);
			
		} else {			
			vh =  (ViewHolder) convertView.getTag();			
		}
		
		try {
			
			OrderHistory row = mList.get(position);
			
			vh.orderNumTv.setText(row.getNum());
			vh.orderDateTv.setText(row.getDate());
			vh.shipToTv.setText(row.getShipTo());
			vh.orderTotalTv.setText(Format.price(row.getTotal()));	
			vh.statusTv.setText(row.getStatus());
						
		} catch (Exception e) {
			Log.e(TAG,e.toString());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView orderNumTv;
		TextView orderDateTv;		
		TextView shipToTv;
		TextView orderTotalTv;
		TextView statusTv;
	}	
	
	public void setData(ArrayList<OrderHistory> list) {
		mList = list;
	}

	private void reLayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}

}
