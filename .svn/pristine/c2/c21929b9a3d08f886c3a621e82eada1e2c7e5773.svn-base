package com.kentec.milkbox.asr;

import java.util.ArrayList;

import android.content.res.Resources;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.coupon.CouponMainActivity;

public class CouponASRCreater
{

	private CouponMainActivity mCouponMainActivity;

	public void init(CouponMainActivity couponMainActivity)
	{
		mCouponMainActivity = couponMainActivity;

		initASRCMDAdapter();
	}

	private void initASRCMDAdapter()
	{
		/**
		 * @author Wesley
		 * @date 2014.03.03
		 * 避免在 Coupon 再呼叫 coupon後，又再起一個 Coupon activity
		*/
		mCouponMainActivity.addASRCMDAdapter(new ASRCMDAdapter(	ASRString.getResourceString(mCouponMainActivity.getResources(), R.array.coupon))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("You're already in Coupon.", "echoAlreadyInGrocery");
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mCouponMainActivity.addASRCMDAdapter(new ASRCMDAdapter(	ASRString.getResourceString(mCouponMainActivity.getResources(), R.array.mycoupon))
		{
			@Override
			public void action()
			{
				mCouponMainActivity.openMyCoupon();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mCouponMainActivity.addASRCMDAdapter(new ASRCMDAdapter(	ASRString.getResourceString(	mCouponMainActivity.getResources(), R.array.menu))
		{
			@Override
			public void action()
			{
				/**
				* @author andywu
				* @date 2014.02.26
				* 加入語音
				*/
				String strMsg = "好的，回到主畫面";
				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoMainMenu");
				mCouponMainActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}

}
