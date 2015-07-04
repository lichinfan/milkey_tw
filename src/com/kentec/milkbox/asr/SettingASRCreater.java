package com.kentec.milkbox.asr;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.setting.SettingActivity;


/**
* @author andywu
* @date 2014.03.12
* Setting語音控制 
*/
public class SettingASRCreater
{

	private SettingActivity mSettingActivity ;
	
	public void init (SettingActivity settingActivity)
	{
		mSettingActivity = settingActivity ;
		initASRCMDAdapter();
	}
	
	private void initASRCMDAdapter()
	{
		// andywu 一定要加在最前面，它是"settting"
		mSettingActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mSettingActivity.getResources(), R.array.settingsetting))
		{
			@Override
			public void action()
			{
				mSettingActivity.show_setting() ;
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mSettingActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mSettingActivity.getResources(), R.array.setting))
		{
			@Override
			public void action()
			{
				MilkBoxTTS.speak("You're already in Setting.", "echoAlreadyInSetting");
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mSettingActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mSettingActivity.getResources(), R.array.menu))
		{
			@Override
			public void action()
			{
				String strMsg = "好的，回到主畫面";
				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoMainMenu");
				mSettingActivity.gotoMainActivity();
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});
		mSettingActivity.addASRCMDAdapter(new ASRCMDAdapter(ASRString.getResourceString(mSettingActivity.getResources(), R.array.personalinfo))
		{
			@Override
			public void action()
			{
				mSettingActivity.show_personal() ;
			}
			
			@Override
			public void action(String strValue)
			{
				// TODO Auto-generated method stub
				
			}
		});

	}
	
}
