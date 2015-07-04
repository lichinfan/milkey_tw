package com.kentec.milkbox;

import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.kentec.milkbox.tv.TvPlayerActivity;
import com.kentec.milkbox.utils.DEBUG;

public class CheckTimeOutActivity extends FragmentActivity
{

	private long mLastKeyTime;
	private Handler mHandler;
	private HandlerThread mHandlerThread;
	
	Handler mHandler2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
	}
	
	public void doCheckTimeOut2() {
		mHandler2 = new Handler();
        mHandler2.post(runnable);
	}
	
	final Runnable runnable = new Runnable() {
	    public void run() {
	        // TODO Auto-generated method stub
	        // 需要背景作的事
	    	doSomethingOnCheckTimeOut();
	    	mHandler2.postDelayed(runnable, 10000);
	    }
	};

	@Override
	protected void onPause()
	{
		super.onPause();

		stopCheckTimeOut();
		Log.d("CheckTimeOutActivity", "onPause ... call stopCheckTimeOut()");
	}

	public void stopCheckTimeOut()
	{
		if (mHandlerThread != null)
		{
			// Add : Alvin ... 重設計時器的時間，避免因為計時器間隔時間比較短時，在 stop 計時器時已經有計時器
			// message 放到 queue 裡，容易發生 stop 計時器後又會出現計時器 Active
			mLastKeyTime = System.currentTimeMillis();
			
			Looper looper = mHandlerThread.getLooper();
			if (looper != null) looper.quit();			
		}
	}

	public void doCheckTimeOut()
	{
		if (DEBUG.MODE) return;

		if (mHandlerThread != null)
		{
			stopCheckTimeOut();
		}
		
		try
		{
			mLastKeyTime = System.currentTimeMillis();
			mHandlerThread = new HandlerThread("");
			mHandlerThread.start();

			mHandler = new Handler(mHandlerThread.getLooper());
			mHandler.postDelayed(new CheckTimeOutRunnable(), CFG.TimeOutToTV);

			System.gc(); // Mark & Push system run the GarbageCollection
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doSomethingOnCheckTimeOut() {
		// TODO Auto-generated method stub
		System.out.println("do super.doSomething()");		
	}
	
	public class CheckTimeOutRunnable implements Runnable
	{
		@Override
		public void run()
		{	
			long now = System.currentTimeMillis();
			if ((now - mLastKeyTime) >= CFG.TimeOutToTV) // 檢查是否產生 TimeOutToTv event
			{
				Log.d("CheckTimeOutActivity", "CheckTimeOutRunnable ... is Active");
				// Modify : Alvin ... 拿掉自動回到
				/*
				 *  自動回到 TV 播放功能
				 *
				 finish();
				startActivity(new Intent(CheckTimeOutActivity.this, TvPlayerActivity.class));
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				 */
				
				// Add: Alvin ... 重新啟動計時器
				Log.d("CheckTimeOutActivity", "CheckTimeOutRunnable ... is reStart doCheckTimeOut()");
				
				// 固定時間 Milky 起來問候一下
				doSomethingOnCheckTimeOut();

			}
			else
			{
				mHandler.postDelayed(new CheckTimeOutRunnable(), CFG.TimeOutToTV - mLastKeyTime);
			}
		}

		
	}

	public void gotoTvActivity()
	{
		finish();
		startActivity(new Intent(this, TvPlayerActivity.class));

		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		mLastKeyTime = System.currentTimeMillis();

		return super.onKeyUp(keyCode, event);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		DEBUG.e("onTouchEvent", "getEvent");
		DEBUG.e("onTouchEventAction", String.valueOf(event.getAction()));
		mLastKeyTime = System.currentTimeMillis();
		return super.onTouchEvent(event);
	}

	public void reTimeOut()
	{
		mLastKeyTime = System.currentTimeMillis();
	}
	
	
}