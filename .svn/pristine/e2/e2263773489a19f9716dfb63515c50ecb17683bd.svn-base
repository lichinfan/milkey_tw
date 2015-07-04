package com.kentec.milkbox.checkout.view;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.data.Total;
import com.kentec.milkbox.utils.Format;


public class OrderOverviewTotalAdapter extends BaseAdapter {

	private static final String TAG = "OrderOverviewTotalAdapter";
	
	private int mBaseWidth;	
	private ArrayList<Total> mList;
	private LayoutInflater mLayoutInflater;
		
	public OrderOverviewTotalAdapter(Context context, ArrayList<Total> list) {
		mList = list;		
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
			convertView = mLayoutInflater.inflate(R.layout.view_checkout_order_overview_total_row, vg, false);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
			convertView.setTag(vh);			
		} else {			
			vh =  (ViewHolder) convertView.getTag();			
		}
		
		try {
			
			Total row = mList.get(position);
			vh.nameTv.setText(row.getTitle());			
			vh.priceTv.setText(Format.price(row.getAmount()));
			
			if(mBaseWidth!=0) {
				relayout(vh.nameTv,8);
				relayout(vh.priceTv,2);
			}
			
		} catch (Exception e) {
			Log.e(TAG,e.toString());
		}
		
		return convertView;
	}
	
	class ViewHolder {
		TextView nameTv;
		TextView priceTv;
	}	

	private void relayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}	
	
	public void relayout(int width, int height) {
		mBaseWidth = width /10;
		notifyDataSetChanged();
	}
}
