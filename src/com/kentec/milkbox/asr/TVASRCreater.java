package com.kentec.milkbox.asr;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.tv.TvPlayerActivity;

public class TVASRCreater
{

	private TvPlayerActivity mTvPlayerActivity;

	public void init(TvPlayerActivity tvPlayerActivity)
	{
		mTvPlayerActivity = tvPlayerActivity;

		initASRCMDAdapter();
	}

	private void initASRCMDAdapter()
	{
		/**
		 * @author Wesley
		 * @date 2014.03.03
		 * 避免在 TV 再呼叫 tv後，又再起一個 TV activity
		*/
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.tv))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("You're already in TV.", "echoAlreadyInGrocery");
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		// for Voice Command : What can I say
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvHelpMenu))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doTvHelpMenu();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		// for Voice Command : Change Channel
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvChangeChannel))
		{
			@Override
			public void action()
			{
				
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				mTvPlayerActivity.doTvChangeChannel(strValue);				
			}
		});
		
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvChangeVolume))
		{
			@Override
			public void action()
			{
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				mTvPlayerActivity.doTvChangeVolume(strValue);		
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvChannelUp))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doChannelUp(true);
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvChannelDown))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doChannelDown(true);
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvLastChannel))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doTvLastChannel();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvVolumeUp))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doVolumnUp();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvVolumeDown))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doVolumnDown();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		// Add : Alvin
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvMaxVolume))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doMaxVolume();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvMute))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doMuteVolume();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvVolumeOn))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.doResetMute();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.menu))
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
				mTvPlayerActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
		mTvPlayerActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mTvPlayerActivity.getResources(), R.array.TvExit))
		{
			@Override
			public void action()
			{
				mTvPlayerActivity.gotoMainActivity();
			}

			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub

			}
		});
	}
}
