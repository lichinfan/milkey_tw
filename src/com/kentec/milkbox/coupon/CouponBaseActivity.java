package com.kentec.milkbox.coupon;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.kentec.milkbox.asr.ASRActivity;

public class CouponBaseActivity extends ASRActivity {
	
	protected int mWidth;
	protected int mHeight;
	protected int mBaseWidth;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		mHeight = display.getHeight();
		mWidth = display.getWidth();
		mBaseWidth = mWidth / 10;		
	}		
	
	protected void relayout(View view, int base) {
		LayoutParams params = view.getLayoutParams();
		params.width = mBaseWidth * base;
		view.setLayoutParams(params);
	}
	
	
}
