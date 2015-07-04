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
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.utils.Format;


public class OrderOverviewAdapter extends BaseAdapter {

	private static final String TAG = "OrderOverviewAdapter";
	
	private int mBaseWidth;
	
	private ArrayList<Product> mList;
	private LayoutInflater mLayoutInflater;
		
	public OrderOverviewAdapter(Context context, ArrayList<Product> list) {
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
			convertView = mLayoutInflater.inflate(R.layout.view_checkout_order_overview_row, vg, false);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
			vh.qtyTv = (TextView) convertView.findViewById(R.id.qtyTv);
			vh.subtotalTv = (TextView) convertView.findViewById(R.id.subtotalTv);						
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
			
			if(mBaseWidth!=0) {
				relayout(vh.nameTv,5);
				relayout(vh.priceTv,2);
				relayout(vh.qtyTv,1);
				relayout(vh.subtotalTv,2);				
			}

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
	}	
	
	public void setData(ArrayList<Product> list) {
		mList = list;
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
