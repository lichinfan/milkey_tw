package com.kentec.milkbox.asr;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.radio.RadioPlayerActivity;

public class RadioASRCreater
{

	private RadioPlayerActivity mRadioPlayerActivity;

	public void init(RadioPlayerActivity radioPlayerActivity)
	{
		mRadioPlayerActivity = radioPlayerActivity;

		initASRCMDAdapter();
	}

	private void initASRCMDAdapter()
	{
		/**
		 * @author Wesley
		 * @date 2014.03.03
		 * 避免在 Radio 再呼叫 radio後，又再起一個 Radio activity
		*/
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.radio))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("You're already in Radio.", "echoAlreadyInGrocery");
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioHelpMenu))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doRadioHelpMenu();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioChangeVolume))
		{
			@Override
			public void action()
			{
			}

			@Override
			public void action(String strValue)
			{
				mRadioPlayerActivity.doRadioChangeVolume(strValue);		
			}
		});
		
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioChannelUp))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doChannelUp();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioChannelDown))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doChannelDown();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioLastChannel))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doRadioLastChannel();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioVolumeUp))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doVolumnUp();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioVolumeDown))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doVolumnDown();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		// Add : Alvin
		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioMaxVolume))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doMaxVolume();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioMute))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doMuteVolume();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.RadioVolumeOn))
		{
			@Override
			public void action()
			{
				mRadioPlayerActivity.doResetMute();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mRadioPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mRadioPlayerActivity.getResources(), R.array.menu))
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
				mRadioPlayerActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
	}
}
