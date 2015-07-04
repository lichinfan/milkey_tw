package com.kentec.milkbox.checkout.view;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.data.Total;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

public class OrderOverviewView extends LinearLayout {
	
	private Context mContext;
	
	private ListView mMainLv;
	private ListView mTotalLv;
	
	private OrderOverviewAdapter mProductAdapter;
	private OrderOverviewTotalAdapter mTotalAdapter;
	
	private int mBaseWidth;
		
	public OrderOverviewView(Context context) {
		super(context);
		mContext = context;
				
		View.inflate(context, R.layout.view_checkout_order_overview, this);		
		mMainLv = (ListView) findViewById(R.id.mainLv);
		mTotalLv = (ListView) findViewById(R.id.totalLv);
	}
		
	public void relayout() {
		
		mBaseWidth = getWidth() / 10;
		
		TextView nameTv = (TextView) findViewById(R.id.nameTv);				
		TextView priceTv = (TextView) findViewById(R.id.priceTv);
		TextView qtyTv = (TextView) findViewById(R.id.qtyTv);
		TextView subtotalTv = (TextView) findViewById(R.id.subtotalTv);
		
		reLayout(nameTv,5);
		reLayout(priceTv,2);
		reLayout(qtyTv,1);
		reLayout(subtotalTv,2);
		
		mProductAdapter.relayout(getWidth(),getHeight());
		mTotalAdapter.relayout(getWidth(),getHeight());
	}
		
	public void setProduct(ArrayList<Product> list) {
		mProductAdapter = new OrderOverviewAdapter(mContext,list);
		mMainLv.setAdapter(mProductAdapter);
		ReLayoutUtil.listview(mMainLv, 6);
	}

	public void setTotal(ArrayList<Total> list) {
		mTotalAdapter = new OrderOverviewTotalAdapter(mContext,list);
		mTotalLv.setAdapter(mTotalAdapter);				
	}

	private void reLayout(TextView tv, int base) {
		ViewGroup.LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);		
	}	

}
