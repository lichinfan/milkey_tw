/**
 * 
 */
package com.kentec.milkbox.introduction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

/**
 * @author Alvin Huang
 *
 */
public class IntroductionActivity extends ASRActivity
{	
	private Handler mHandler;
	private ImageView introductionImageView;
	
	private ReLayoutUtil mReLayoutUtil; // ReLayoutUtil Tools
	
	private List<IntrouctionScriptAdapter> mIntrouctionScriptAdapter; // 用來存放腳本
	private int sysCount = 0;
	
	private final static int RequestCode_UserGide=24680;
	private final static int RequestCode_UserGideOpening=24682;
	private final static int RequestCode_UserGideOpening2=24684;
	private final static int RequestCode_UserGideOpening3=24686;
	
	private boolean flagForFinish = false;
	private boolean flagForAgain = false;
	
	private boolean flagForUserAbort = false;
	
	public static final String action = "milk.introduction.broadcast.action";  

	/* (non-Javadoc)
	 * @see com.kentec.milkbox.asr.ASRActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// setTheme(R.style.UserGuideActivity);
		setContentView(R.layout.activity_introduction_main);
		
		mReLayoutUtil = reLayout(); // Initialize ReLayout tools 
		
		introductionImageView = (ImageView)findViewById(R.id.imageViewIntroduction);
		
		introductionImageView.setVisibility(View.GONE);
				
		doInitIntrouctionScriptAdapter(); // initialize ScriptAdapter
		
		doIntroductionCheckTimeOut();		
	}

	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/3/10 上午8:22:07
	 */
	private void doIntroductionCheckTimeOut()
	{
		mHandler = new Handler();        
		mHandler.post(introductionRunnable);
	}
	
	/**
	 * 
	 */
	private Runnable introductionRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			// TODO Auto-generated method stub
			// 需要背景作的事
			Log.d("IntroductionActivity", "=== run()->sysCount=" + sysCount);

			// Alvin 2014/03/17 : 增加檢查 CFG.isModeratorDialogAbout 判斷整個 User Guide 是否已經中止
			// if((sysCount < mIntrouctionScriptAdapter.size()) && (!CFG.isModeratorDialogAbort))
			if(sysCount < mIntrouctionScriptAdapter.size())
			{	
				introductionImageView.setVisibility(View.GONE);
				
				IntrouctionScriptAdapter mI = mIntrouctionScriptAdapter.get(sysCount++);
				Log.d("IntroductionActivity", "=== run()->sysCount=" + sysCount);
				if((mI.getEchoStr() != null) && (mI.getSpeakstr() != null))
				{
					// 處理唸出用的資訊
					ArrayList<String> speakStrs = new ArrayList<String>();
					Collections.addAll(speakStrs, mI.getSpeakstr());
					
					// 唸出稿子
					Log.d("IntroductionActivity", "<><><><><><><><><><> MilkBoxTTS.speak()");
					MilkBoxTTS.speak(speakStrs, "changeImage");

					// 顯示對話框
					introductionImageView.setImageResource(mI.getimageResId()); // 將要顯示的 Image 放入 Layout ImageView 中
					mReLayoutUtil.relativeXY(introductionImageView, mI.getLocationX(), mI.getLocationY()); // 重新定位顯示對話框 ImageView
					introductionImageView.setVisibility(View.VISIBLE); // 打開 ImageView					

					// 檢查是否需要變更背景欲氣象資訊
					if(mI.getCurrentHour() > -1)
					{						
						// changeBackground(mI.getCurrentHour(), mI.getWeatherCondStr(), mI.getWeatherDescrStr(), mI.getWweatherIconStr());
						
						Intent intent = new Intent(action);  
		                intent.putExtra("CurrentHour", mI.getCurrentHour());  
		                intent.putExtra("WeatherCondStr", mI.getWeatherCondStr());
		                intent.putExtra("WeatherDescrStr", mI.getWeatherDescrStr());
		                intent.putExtra("WweatherIconStr", mI.getWweatherIconStr());
		                sendBroadcast(intent);  
		                // finish();
					}

				}
				else
				{
					mI.action(mI.getShowTime());	
				}
				
				Log.d("IntroductionActivity", "=== run()00->sysCount=" + sysCount);
				mHandler.postDelayed(introductionRunnable, mI.getShowTime());
				Log.d("IntroductionActivity", "=== run()0->sysCount=" + sysCount);				
			}
			else //  if(!flagForUserAbort)
			{
				Log.d("IntroductionActivity", "=== run()3->sysCount=" + sysCount);
				moderatorInteractiveDialog("Am I clear?", 1, "Am I clear?", "Milky", RequestCode_UserGide);	
			}
		}	
	};
	
	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/3/10 下午3:11:12
	 */
	private void doInitIntrouctionScriptAdapter()
	{
		// TODO Auto-generated method stub
		mIntrouctionScriptAdapter = new ArrayList<IntrouctionScriptAdapter>(); // 建立存放腳本的集合容器
		
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(null, null, 9000, -1, -1, -1, -1, null, null, null)
		{
			@Override
			public void action(int arg)
			{
				doOpening(arg);				
			}
		});
		
		String strEcho1[]   = {"REAL-TIME INFOMATION"};
		String strSpeak1[] = {"Let's take a look of the icon in the upper right hand corner.", 
				                                "You can check date, time, weather and temperature here."};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho1, strSpeak1, 8000, R.drawable.ugs_1, 659, 0, -1, null, null, null)
		{
			@Override
			public void action(int arg){}
		});
		
		// 3 個不同時段的介紹
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(null, null, 9000, -1, -1, -1, -1, null, null, null)
		{
			@Override
			public void action(int arg)
			{
				doOpening2(arg);
			}
		});
		
		String strEcho2[]   = {"Sunny in the morning"};
		String strSpeak2[] = {", For example, it's Sunny in the morning"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho2, strSpeak2, 5000, R.drawable.ugs_2, 597, 13, 7, "Clouds", "sky is clear", "01d")
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho3[]   = {"Sunny in the afternoon"};
		String strSpeak3[] = {", In the afternoon"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho3, strSpeak3, 5000, R.drawable.ugs_3, 529, 8, 15, "Clouds", "sky is clear", "01d")
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho4[]   = {"Sunny at night"};
		String strSpeak4[] = {", And at night"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho4, strSpeak4, 5000, R.drawable.ugs_4, 655, 9, 22, "Clouds", "sky is clear", "01n")
		{
			@Override
			public void action(int arg){}
		});
		
		//  6 個不同天氣的介紹
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(null, null, 5000, -1, -1, -1, -1, null, null, null)
		{
			@Override
			public void action(int arg)
			{
				doOpening3(arg);
			}
		});
		
		String strEcho5[]   = {"Snow in the morningt"};
		String strSpeak5[] = {", Snow in the morning"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho5, strSpeak5, 5000, R.drawable.ugs_5, 589, 17, 7, "Snow", "light snow", "13d")
		{
			@Override
			public void action(int arg){}
		});
				
		String strEcho6[]   = {"Mist in the morning"};
		String strSpeak6[] = {"Mist in the morning"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho6, strSpeak6, 5000, R.drawable.ugs_6, 592, 5, 7, "Atmosphere", "mist", "50d")
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho7[]   = {"Raining in the afternoon"};
		String strSpeak7[] = {"Raining in the afternoon"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho7, strSpeak7, 5000, R.drawable.ugs_7, 427, 4, 15, "Rain", "light rain", "10d")
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho8[]   = {"Thunder storm in the afternoon"};
		String strSpeak8[] = {", Thunder storm in the afternoon"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho8, strSpeak8, 5000, R.drawable.ugs_8, 290, 14, 15, "Thunderstorm", "thunderstorm with light rain", "11d")
		{
			@Override
			public void action(int arg){}
		});
		
		/*
		String strEcho9[]   = {"Snow at night"};
		String strSpeak9[] = {"Snow at night"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho9, strSpeak9, 5000, R.drawable.ugs_9, 637, 10, 22, "Snow", "light snow", "13d")
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho10[]   = {"Partly sunny at night"};
		String strSpeak10[] = {", Partly sunny at night"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho10, strSpeak10, 5000, R.drawable.ugs_10, 526, 8, 22, "Clouds", "sky is clear", "01n")
		{
			@Override
			public void action(int arg){}
		});
		*/
		
		String strEcho11[]   = {"TV, Radio"};
		String strSpeak11[] = {", You can say TV or Radio for visual or radio entertainment"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho11, strSpeak11, 5000, R.drawable.ugs_11, 48, 377,				
                Integer.parseInt(new SimpleDateFormat("HH").format(new Date())),
               CFG.WEATHER.currentCondition.getCondition(), CFG.WEATHER.currentCondition.getDescr(), CFG.WEATHER.currentCondition.getIcon())
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho12[]   = {"Grocery"};
		String strSpeak12[] = {", Say Grocery to do shopping online."};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho12, strSpeak12, 5000, R.drawable.ugs_12, 430, 382, -1, null, null, null)
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho14[]   = {"Take Out"};
		String strSpeak14[] = {", Say Take Out to order food delivery"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho14, strSpeak14, 5000, R.drawable.ugs_13, 423, 378, -1, null, null, null)
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho13[]   = {"Coupon"};
		String strSpeak13[] = {", Say Coupon to see what’s on discount"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho13, strSpeak13, 5000, R.drawable.ugs_14, 624, 406, -1, null, null, null)
		{
			@Override
			public void action(int arg){}
		});
		
		String strEcho15[]   = {"About"};
		String strSpeak15[] = {", Or say About to change settings"};
		mIntrouctionScriptAdapter.add(new IntrouctionScriptAdapter(strEcho15, strSpeak15, 5000, R.drawable.ugs_15, 843, 413, -1, null, null, null)
		{
			@Override
			public void action(int arg){
				
			}
		});
	}
	
	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @param arg 
	 * @date 2014/3/10 下午3:17:30
	 */
	protected void doOpening(int arg)
	{
		String strSpeak[] = { "I am your personal assistant.", "I was designed to bring you a new way to enjoy your TV.", "Now let's see what I can do for you." };		
		
		int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置

		// 處理唸出用的資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		// speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strSpeak);

		moderatorDialogForResult("Hi " + CFG.getNickName() + ", I'm Milky", // 要顯示的內容
				indexNo + 1, // 顯示對話框的位置
				arg, // 對話框的顯示時間
				speakStrs, // 實際要唸出的內容
				"Opening",   // 發音用識別碼
				RequestCode_UserGideOpening);
	}

	/**
	 * @param arg
	 *
	 * @author Alvin Huang
	 * @date 2014/3/28 上午9:24:18
	 */
	protected void doOpening2(int arg)
	{
		String strSpeak[] = { "The background photo will change according to current weather status.",
				                              "And each scenario changes in 3 different time periods."};
		
		String dialogStr = "<BR><BR><BR><big>&nbsp;&nbsp;&nbsp;3 Different time periods</big><br><br>"
									  + "<big>&nbsp;&nbsp;&nbsp;● Morning</big><br>"
									  + "<big>&nbsp;&nbsp;&nbsp;● Afternoon</big><br>"
									  + "<big>&nbsp;&nbsp;&nbsp;● Night</big><br>";
		
		// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置
		
		// 處理唸出用的資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		Collections.addAll(speakStrs, strSpeak);
		
		moderatorDialogForResult(dialogStr, // 要顯示的內容
				3, // 顯示對話框的位置
				arg, // 對話框的顯示時間
				speakStrs, // 實際要唸出的內容
				"Opening2",   // 發音用識別碼
				RequestCode_UserGideOpening2);
	}

	/**
	 * @param arg
	 *
	 * @author Alvin Huang
	 * @date 2014/3/28 上午9:24:25
	 */
	protected void doOpening3(int arg)
	{
		String dialogStr =    "<BR><BR><BR><big>&nbsp;&nbsp;&nbsp;6 Difference weather scenarios</big><br><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Sunny</big><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Partly sunny</big><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Snow</big><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Raining</big><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Mist</big><br>"
										  + "<big>&nbsp;&nbsp;&nbsp;● Thunderstorm</big><br><br>";
				
		// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置
		
		// 處理唸出用的資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("There are 6 difference weather scenarios.");
		
		moderatorDialogForResult(dialogStr, // 要顯示的內容
			3, // 顯示對話框的位置
			arg, // 對話框的顯示時間
			speakStrs, // 實際要唸出的內容
			"Opening3", // 發音用識別碼
			RequestCode_UserGideOpening3);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		System.out.println("run Introduction onActivityResult: " + requestCode + " " + resultCode);
		int indexNo = (int) (Math.random() * 2);

		if (requestCode == RequestCode_UserGide)
		{
			startASR();
		}
		else if (requestCode == RequestCode_ASR)
		{
			CFG.ASR_IS_RUN = false;
			if (resultCode == RESULT_OK)
			{
				Bundle bundle = data.getExtras();
				ArrayList<String> msgsArrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
				Log.d("IntroductionActivity", Arrays.toString(msgsArrayList.toArray()));
				if (msgsArrayList.contains("yes") == true)
				{
					moderatorDialog("That's great!", indexNo + 1, 4000);
					MilkBoxTTS.speak("That's great!", "ThatGreat");
					flagForFinish = true;
				}
				else
				{
					moderatorDialog("ok, let me explain again.", indexNo + 1, 3000);
					MilkBoxTTS.speak("ok, let me explain again.", "UserGuideAgain");
					flagForAgain = true;
				}

			}
		}
		// Alvin 2014/03/18 : 當 UserGide 執行時按下 Back 鍵立即離開並關閉 Activity
		else if ((requestCode == RequestCode_UserGideOpening) || 
				     (requestCode == RequestCode_UserGideOpening2)) 
		{	
			if (resultCode == Activity.RESULT_CANCELED)
			{
				mHandler.removeCallbacks(introductionRunnable);
				IntroductionActivity.this.finish();
			}
		}
		else if (requestCode == RequestCode_ModeratorDialog)
		{
			Log.d("IntroductionActivity", "onActivityResult->RequestCode_ModeratorDialog =*=*=*=*=*=*=");
		}
//		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		if(flagForFinish == true) {
			finish();
		}
		super.onResume();
	}

	@Override
	protected void onPause()
	{
		Handler handlerUI = new Handler();
		if (flagForAgain == true)
		{
			handlerUI.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					goActivityNoFinish(IntroductionActivity.class);
				}
			}, 4000);
			
			finish();
		}
				
		super.onPause();
	}

	/* (non-Javadoc)
	 * @see com.kentec.milkbox.asr.ASRActivity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		Log.d("IntroductionActivity", "onKeyUp() *************************");
		if (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE)
		{
			Log.d("IntroductionActivity", "Abort Introduction ..... *************************");
			// 2014/03/25 Alvin : 當按下 Back Key 在結束前先恢復正常的背景圖案
			Intent intent = new Intent(action);  
            intent.putExtra("CurrentHour", Integer.parseInt(new SimpleDateFormat("HH").format(new Date())));  
            intent.putExtra("WeatherCondStr", CFG.WEATHER.currentCondition.getCondition());
            intent.putExtra("WeatherDescrStr", CFG.WEATHER.currentCondition.getDescr());
            intent.putExtra("WweatherIconStr", CFG.WEATHER.currentCondition.getIcon());
            sendBroadcast(intent);
            
			flagForAgain = false;
			flagForUserAbort = true;
			sysCount = mIntrouctionScriptAdapter.size();
			mHandler.removeCallbacks(introductionRunnable);
			// this.finish();
			
			// 讓 TTS 馬上停止
			MilkBoxTTS.setNowAbortSpeak(); // MilkBoxTTS 會立即送出命令讓 TTS 馬上停止發音並清除緩衝區中帶執行的發音命令
		}
		
		// return true;		
		return super.onKeyUp(keyCode, event);
	}	
}