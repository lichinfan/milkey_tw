package com.kentec.milkbox.checkout.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.checkout.CheckoutActivity;
import com.kentec.milkbox.checkout.task.CheckoutTaskSetCoupon;
import com.kentec.milkbox.coupon.data.Coupon;


public class CouponView extends LinearLayout {

	protected CheckoutActivity mActivity;
	protected TextView titleTextView;
	protected TextView currentTextView;
	protected ListView couponListView;

	public CouponView(CheckoutActivity activity) {
		super(activity);
		this.mActivity = activity;
		View.inflate(mActivity, R.layout.view_checkout_coupon, this);
		titleTextView = (TextView) findViewById(R.id.titleTextView);
		currentTextView = (TextView) findViewById(R.id.currentTextView);
		couponListView = (ListView) findViewById(R.id.couponListView);
	}

	public void show() {		
		String[] keys = { "coupon" };
		int[] rids = { R.id.textView };				
		List<HashMap<String, String>> couponList = null;
		if(mActivity.getData().getCouponList()==null) {
			couponList = new ArrayList<HashMap<String,String>>();
			HashMap<String,String> row = new HashMap<String,String>();
			row.put("coupon","No Coupon");
			couponList.add(row);
		} else {
			couponList = getHmCouponList(mActivity.getData().getCouponList());;			
		}		
		SimpleAdapter adapter = new SimpleAdapter(mActivity, couponList, R.layout.row_checkout_coupon, keys, rids);
		couponListView.setAdapter(adapter);
		couponListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long arg3) {
				mActivity.reTimeOut();
				setCoupon(mActivity.getData().getCouponList().get(index));
			}
		});
		couponListView.requestFocus();
		showCurrent();
	}

	private void setCoupon(Coupon coupon) {
		new CheckoutTaskSetCoupon(mActivity, coupon).execute();
	}

	private void showCurrent() {
		Coupon coupon = mActivity.getOrder().getCoupon();
		if(coupon!=null) {
			currentTextView.setText(coupon.getName());			
		} else {
			mActivity.getViewGroup().getNextButton().setVisibility(View.GONE);			
		}
	}
	
	private List<HashMap<String, String>> getHmCouponList(ArrayList<Coupon> list) {
		List<HashMap<String,String>> hmList = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> row;
		for(int i = 0;i < list.size(); i++) {
			row = new HashMap<String,String>();
			row.put("coupon",list.get(i).getName());
			hmList.add(row);
		} 		
		return hmList;
	}
	
}
