package com.kentec.milkbox.asr;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;

public class HomeDineASRCreater
{

	private HomeDineDeliveryActivity mHomeDineDeliveryActivity;

	public void init(HomeDineDeliveryActivity homeDineDeliveryActivity)
	{
		mHomeDineDeliveryActivity = homeDineDeliveryActivity;

		initASRCMDAdapter();
	}

	private void initASRCMDAdapter()
	{
		/**
		 * @author Wesley
		 * @date 2014.03.03
		 * 避免在 Takeout 再呼叫 takeout後，又再起一個 Tackout activity
		*/
		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.homedine))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("You're already in Takeout.", "echoAlreadyInGrocery");
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.HelpMainMenu))
		{
			@Override
			public void action()
			{
				mHomeDineDeliveryActivity.doTakeoutHelpMenu();
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.checkout))
		{
			@Override
			public void action()
			{
				mHomeDineDeliveryActivity.openCart();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.progress))
		{
			@Override
			public void action()
			{
				mHomeDineDeliveryActivity.openProgress();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.history))
		{
			@Override
			public void action()
			{
				mHomeDineDeliveryActivity.openOrderHistory();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.calorie))
		{
			@Override
			public void action()
			{
				mHomeDineDeliveryActivity.openCalorie();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mHomeDineDeliveryActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mHomeDineDeliveryActivity.getResources(), R.array.menu))
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
				mHomeDineDeliveryActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}
}
