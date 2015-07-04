package com.kentec.milkbox.coupon.view;

import java.util.ArrayList;

import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.coupon.adapter.MyCouponAdapter;
import com.kentec.milkbox.coupon.data.Coupon;
import com.kentec.milkbox.coupon.task.MyCouponTask;
import com.kentec.milkbox.view.BaseDialog;


public class MyCouponDialog extends BaseDialog implements OnShowListener {

	private ListView mMainLv;
	
	private String mCartId;
	private ArrayList<Coupon> mCouponList;
	private MyCouponAdapter mAdapter; 
	
	public MyCouponDialog(BaseActivity activity, String cartId) {
		super(activity, R.style.Dialog_Full);		
		mCartId = cartId;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_coupon_my);
		
		mMainLv = (ListView) findViewById(R.id.mainLv);
				
		LinearLayout mainLl = (LinearLayout) findViewById(R.id.mainLl);
		TextView imageTv = (TextView) findViewById(R.id.imageTv);
		TextView nameTv = (TextView) findViewById(R.id.nameTv);
		TextView shortDescTv = (TextView) findViewById(R.id.shortDescTv);
		TextView expDateTv = (TextView) findViewById(R.id.expDateTv);
		TextView useTv = (TextView) findViewById(R.id.useTv);
		
		reLayout(mainLl,mWidth,mHeight);
		
		reLayout(imageTv, 2);
		reLayout(nameTv, 2);
		reLayout(shortDescTv, 3);
		reLayout(expDateTv, 2);
		reLayout(useTv, 1);		
		
		setOnShowListener(this);
	}	

	@Override
	public void onShow(DialogInterface dialog) {
		new MyCouponTask(mActivity,sucessListener,mCartId);
	}
	
	MyCouponTask.OnSucessListener sucessListener = new MyCouponTask.OnSucessListener() {
		@Override
		public void success(ArrayList<Coupon> list) {
			mCouponList = list;
			mAdapter = new MyCouponAdapter(mActivity,list,mBaseWidth);
			mMainLv.setAdapter(mAdapter);
			mMainLv.setOnItemClickListener(itemClickListener);			
		}

		@Override
		public void noData() {
			mActivity.showMsg(R.string.no_data);
		}		
	};
	
	OnItemClickListener itemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
			Coupon coupon = mCouponList.get(position);			;
			RemoveCouponDialog dialog = new RemoveCouponDialog(((BaseActivity)mActivity), mCartId, coupon);
			dialog.setOnDeleteListener(deleteListener);
			dialog.show();
		}
	};	
	
	RemoveCouponDialog.OnDeleteListener deleteListener = new RemoveCouponDialog.OnDeleteListener() {
		@Override
		public void delete(Coupon coupon) {
			Coupon c;
			for(int i=0; i<mCouponList.size(); i++) {
				c = mCouponList.get(i);
				if(c.getId().equals(coupon.getId())) {
					mCouponList.remove(i);
					mAdapter.setData(mCouponList);
					mAdapter.notifyDataSetChanged();
					mActivity.showMsg(R.string.msg_the_item_was_deleted);
					return;
				}
			}
		}		
	};
	
}
