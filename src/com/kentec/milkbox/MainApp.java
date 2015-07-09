package com.kentec.milkbox;

import java.net.ServerSocket;

import MilkFactorySettingUtil.MilkFactorySetting;
import android.app.Application;
import android.util.Log;

public class MainApp extends Application
{
	public ServerSocket m_serverSocket = null;
	@Override
	public void onCreate()
	{
		super.onCreate();
		
		CFG.mfs = new MilkFactorySetting(super.getApplicationContext());
		
		initServerHost();
	}	
	
	public void initServerHost()
	{
		try
		{
			// 讀取 MilkBox 相關設定值
			CFG.initHost(CFG.mfs.getServer1().trim()); // get Server1 valus
			CFG.initNickName(CFG.mfs.getNickName().trim()); // get NickName value
//			CFG.IMEI = CFG.mfs.getIMEI();
			CFG.IMEI = "0006F01D0230B9052"; // user: wesley
			
			// 初始化 TTS 模組
			MilkBoxTTS.initMilkBoxTTS(super.getApplicationContext());
			
			// Check TTS engine
			// 因為 TTS engine 啟動完成的時間不會是馬上完成所以不能在此檢查，而是從 TTS 的回呼方法 onInit() 來檢查是否成功
		}
		catch (Exception e)
		{
			// 當 CFG 透過 MFS 讀取資料錯誤時改由此代入預設值
			// CFG.initHost("http://testmagento.now.to");
			CFG.initHost("http://54.187.180.50/boxtw/");
			Log.e("MainApp", e.toString());
		}
	}
}
