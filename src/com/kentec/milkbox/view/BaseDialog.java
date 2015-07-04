package com.kentec.milkbox.view;

import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.asr.ASRActivity;

public class BaseDialog extends Dialog {

	protected int mWidth;
	protected int mHeight;
	protected int mBaseWidth;
	protected ASRActivity mActivity;
	
	//手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)  
    float x1 = 0;  
    float x2 = 0;  
    float y1 = 0;  
    float y2 = 0; 


	public BaseDialog(BaseActivity activity) {
		super(activity);
		Display display = getWindow().getWindowManager().getDefaultDisplay();
		mHeight = (int) (display.getHeight() * 0.9);
		mWidth = (int) (display.getWidth() * 0.8);
		mBaseWidth = (int ) (mWidth*0.9) / 10;
		mActivity = (ASRActivity) activity;
	}

	public BaseDialog(BaseActivity activity, int style) {
		super(activity, style);
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		mHeight = (int) (dm.heightPixels * 0.9);
		mWidth = (int) (dm.widthPixels * 0.8);
		mBaseWidth = (int ) (mWidth*0.9) / 10;
		mActivity = (ASRActivity) activity;
	}

	protected void reLayout(LinearLayout mainLl, int width, int height) {
		LayoutParams params = mainLl.getLayoutParams();
		params.width = width;
		params.height = height;
		mainLl.setLayoutParams(params);
	}

	protected void reLayout(LinearLayout mainLl, int base) {
		LayoutParams params = mainLl.getLayoutParams();
		params.width = mBaseWidth * base;
		mainLl.setLayoutParams(params);
	}

	protected void reLayout(View view, int base) {
		LayoutParams params = view.getLayoutParams();
		params.width = mBaseWidth * base;
		view.setLayoutParams(params);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (mActivity instanceof ASRActivity) {
			boolean handled =  ((ASRActivity) mActivity).dispatchKeyUpEvent(keyCode, event, super.getCurrentFocus());
			if (handled)
				return true;
		}
		
		return super.onKeyUp(keyCode, event);
	}
	
	// 在 Dialog 加入 上、下、左、右滑動可乎叫 startASR()
		@Override  
	    public boolean onTouchEvent(MotionEvent event) {  
	        //继承了Activity的onTouchEvent方法，直接监听点击事件  
	        if(event.getAction() == MotionEvent.ACTION_DOWN) {  
	            //当手指按下的时候  
	            x1 = event.getX();  
	            y1 = event.getY();  
	        }  
	        if(event.getAction() == MotionEvent.ACTION_UP) {  
	        		mActivity.startASR();
//	            //当手指离开的时候  
//	            x2 = event.getX();  
//	            y2 = event.getY();  
//	            if(y1 - y2 > 50) {  
//	                Toast.makeText(ASRActivity.this, "向上滑", Toast.LENGTH_SHORT).show();  
//	            } else if(y2 - y1 > 50) {  
//	                Toast.makeText(ASRActivity.this, "向下滑", Toast.LENGTH_SHORT).show();  
//	            } else if(x1 - x2 > 50) {  
//	                Toast.makeText(ASRActivity.this, "向左滑", Toast.LENGTH_SHORT).show();  
//	            } else if(x2 - x1 > 50) {  
//	                Toast.makeText(ASRActivity.this, "向右滑", Toast.LENGTH_SHORT).show();  
//	            }  
	        }  
	        return super.onTouchEvent(event);  
	    } 
}
