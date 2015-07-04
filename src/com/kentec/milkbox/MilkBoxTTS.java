/**
 * 
 */
package com.kentec.milkbox;

import java.util.ArrayList;
import java.util.HashMap;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;

/**
 * @author Alvin Huang
 * 
 */
public class MilkBoxTTS
{
	private static Handler handler = new Handler(); // 多句式發音時句與句之間的 Delay 機制使用的 Hendler
	private static int ttsCount = -1;                                // 多句式發音時用來記錄目前發音的句子位子
	 // 這是用來指示多句式發音機制是否要立即終止發音並清除Buffer中所有待發音的字串，
	// 這是用在類似 Help 功能被臨時按下 Back 鍵可以馬上停滯 TTS，而不會出現視窗關閉但仍在背景發聲的狀況
	private static boolean nowAboutTTS = false;
	
	// Status define
	public final static int MilkBoxTTS_Status_ERROR = -1;
	public final static int MilkBoxTTS_Status_NONE = 0;
	public final static int MilkBoxTTS_Status_START= 1;
	public final static int MilkBoxTTS_Status_DONE= 2;
	public final static int MilkBoxTTS_Status_ABOUT_SET = 3; // 2014/03/14 Alvin add : nowAboutTTS 旗標被設為 True，表示要TTS立即終止發音
	public final static int MilkBoxTTS_Status_ABOUT = 4;         // 2014/03/14 Alvin add : nowAboutTTS 已經執行，已經對 TTS 下了 Stop()
	
	// for Moderator define
	public static boolean isInitTTS = false; // 紀錄 TTS engine 是否已經成功啟動
	public static Context CONTEXT;
	public static TextToSpeech ttsObj;
	public static int TTS_status = 0;               // 紀錄 TTS engine 傳回的狀態碼，用於必要時可以檢視 TTS engine 的狀態
	public static boolean isFirstRun = true; // 用來記錄 TTS 是否為系統剛執行時的時期，這是用來表示是否要唸出每次系統被啟動時的初次問候語
	private static HashMap<String, String> map ;
	public static String nowUseId; // 紀錄目前使用 TTS 的 ID
	public static int nowUseStatus = MilkBoxTTS_Status_NONE; // 記錄目前使用 TTS 的狀態 0:開始呼叫 TTS	
	
	@SuppressLint("NewApi")
	public static void initMilkBoxTTS(Context context)
	{
		CONTEXT = context;
				
		// for Moderator initialize
		// 全域變數放在 CFG 下
		ttsObj = new TextToSpeech(CONTEXT, new OnInitListener()
		{
			// 當 TTS engine 完全被載入時會被呼叫
			@Override
			public void onInit(int status)
			{
				TTS_status = status;
				
				// TODO Auto-generated method stub
				if (status != TextToSpeech.ERROR)
				{
					// 如果要判斷這個locale有沒有被支援可以使用isLanguageAvailable()
					// 回傳值是TextToSpeech.LANG_COUNTRY_AVAILABLE，代表語言和地區都是可以使用的
					/*
					 * CFG.tts.isLanguageAvailable(Locale.US);
					 * CFG.tts.isLanguageAvailable(Locale.UK);
					 * CFG.tts.isLanguageAvailable(Locale.FRANCE);
					 * CFG.tts.isLanguageAvailable(new Locale("spa", "ESP"));
					 */
					
					map = new HashMap<String, String>();
					isInitTTS = true;					
										
					// 唸出資訊
//					ArrayList<String> speakStrs = new ArrayList<String>();
//					speakStrs.add("Welcome ~~");
//					speakStrs.add("Just open milk.");
//					MilkBoxTTS.speak(speakStrs, "TTSInitializeOK");
					ttsObj.setSpeechRate(CFG.TTS_SpeechRate); // 設定發音速度
					// String myText1 = "Yes~";
					String myText2 = "... Welcome ~~ Just open milk.";
					
					// ttsObj.speak(myText1, TextToSpeech.QUEUE_FLUSH, null);					
//					ttsObj.speak(myText2, TextToSpeech.QUEUE_ADD, null);
					
					Log.d("MilkBoxTTS->TextToSpeech->onInit()", "******* TextToSpeech->onInit() ********");
				}
			}
		});
		
		ttsObj.setOnUtteranceProgressListener(new UtteranceProgressListener()
		{
			@Override
			public void onDone(String utteranceId)
			{
				// TODO Auto-generated method stub
				Log.d("MilkBoxTTS->setOnUtteranceProgressListener->onDone", "*********** " + utteranceId + " *********");

				nowUseId = utteranceId;
				nowUseStatus = MilkBoxTTS_Status_DONE;
			}

			@Override
			public void onError(String utteranceId)
			{
				// TODO Auto-generated method stub
				Log.d("MilkBoxTTS->setOnUtteranceProgressListener->onError", "*********** " + utteranceId + " *********");

				nowUseId = utteranceId;
				nowUseStatus = MilkBoxTTS_Status_ERROR;
			}

			@Override
			public void onStart(String utteranceId)
			{
				// TODO Auto-generated method stub
				Log.d("MilkBoxTTS->setOnUtteranceProgressListener->onStart", "*********** " + utteranceId + " *********");

				nowUseId = utteranceId;
				nowUseStatus = MilkBoxTTS_Status_START;
			}
		});
	}
	
	/**
	 * 單句發音
	 * @param strSpeak ... 要發聲的字串
	 * @return
	 *
	 * @author Alvin Huang
	 * @date 2014/2/8 下午12:38:43
	 */
	public static int speak(String strSpeak, String uID)
	{
		int TTSreturnVal;
		if(!isInitTTS) return TextToSpeech.ERROR; // 檢查 TTS 是否已經 initialize
		
		nowUseId = uID;		
		nowUseStatus = MilkBoxTTS_Status_NONE;

		// TTSreturnVal = ttsObj.speak("~~~", TextToSpeech.QUEUE_FLUSH, null); // 這是為了解決 MilkBox 經常會第一個字來不及發音用，以確保真正要蔫出來的內容都會被念到

		// if(TTSreturnVal == TextToSpeech.SUCCESS)
		{
			map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, uID);
			TTSreturnVal = ttsObj.speak(strSpeak, TextToSpeech.QUEUE_ADD, map);
			// while(ttsObj.isSpeaking()) {
			// }

		}
		
		if(TTSreturnVal != TextToSpeech.SUCCESS)	Log.d("MilkBoxTTS->speak1", "===  ERROR : " + String.valueOf(TTSreturnVal) + " ===");
		
		return TTSreturnVal;
			
	}

	/**
	 * 
	 * @param strSpeak
	 * @param uID
	 * @return
	 *
	 * @author Alvin Huang
	 * @date 2014/3/13 下午5:30:19 重新改寫多句發音的方式，新增一個 Thread 去做句與句之間的 Delay，因此句與句的發聲邏輯控制也移入 Runnable 中處理
	 */
	public static int speak(ArrayList<String> strSpeak, String uID)
	{		
		// int TTSreturnVal;		
		if (!isInitTTS) return TextToSpeech.ERROR; // 檢查 TTS 是否已經 initialize

		if (!strSpeak.isEmpty())
		{
			ttsCount = 0;

			Log.d("MilkBoxTTS-ttsMultiSpeak", "<><><><><><><><><><> handler.post(ttsMultiSpeak(strSpeak, uID))");
			handler.post(ttsMultiSpeak(strSpeak, uID));
		}

		return TTS_status;
	}
		
	/** 多句式TTS發音控制 Runnable
	 * @param strSpeak
	 * @param uID
	 * @return
	 *
	 * @author Alvin Huang
	 * @date 2014/3/13 下午4:45:53
	 */
	private static Runnable ttsMultiSpeak(final ArrayList<String> strSpeak, final String uID)
	{
		// ttsCount = 0;
		return new Runnable()
		{
			@Override
			public void run()
			{
				Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 1 >>> ttsCount = " + ttsCount + " strSpeak = " + strSpeak.size());
				if(nowAboutTTS)
				{
					Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 2 >>> ttsCount = " + ttsCount + " strSpeak = " + strSpeak.size());
					
					MilkBoxTTS.ttsObj.stop(); // 清除 TTS 正在發聲與 Queue 中等待發聲的要求，立即停止發聲
					nowAboutTTS = false;
					TTS_status = MilkBoxTTS_Status_ABOUT;
					ttsCount = -1; // 表示多句式 TTS 以全部發音完畢
				}
				else
				{
					Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 3 >>> ttsCount = " + ttsCount + " strSpeak = " + strSpeak.size());
					
					//  檢查 strSpeak 內容是否為空？ 不是的話就拿第一句來發音
					if(!strSpeak.isEmpty())
					{
						Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 4 >>> ttsCount = " + ttsCount + " strSpeak = " + strSpeak.size() + " str=" +strSpeak.get(0));
						
						map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, uID + ttsCount);
						Log.d("MilkBoxTTS-run()", "<><><><><><><><><><> ttsObj.speak()");
						int TTSreturnVal = ttsObj.speak(strSpeak.get(0), TextToSpeech.QUEUE_ADD, map);
						strSpeak.remove(0);
						// ttsObj.speak(strSpeak.get(ttsCount), TextToSpeech.QUEUE_ADD, map);
						ttsCount++;
						
						Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 4-1 >>> ttsCount = " + ttsCount + " strSpeak = " + strSpeak.size() + " TTSreturnVal = " + TTSreturnVal);
						
						handler.postDelayed(this, CFG.TTS_MultiSpeak_DelayTime); // 以 Handler + Thread 的方式暫停指定的時間後再繼續下一句的發音
						
						Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 4-2 >>> 	handler.postDelayed()");
					}
					else
					{
						Log.d("MilkBoxTTS-ttsMultiSpeak", "ttsMultiSpeak->run() .... 5 >>> ttsCount = " + ttsCount);
						
						ttsCount = -1; // 表示多句式 TTS 已經全部發音完畢
					}
				}				
			}			
		};		
	}
	
	/**
	 * 
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/3/14 下午2:28:02
	 */
	public static void setNowAbortSpeak()
	{
		nowAboutTTS = true;
		TTS_status = MilkBoxTTS_Status_ABOUT_SET;
		
		MilkBoxTTS.ttsObj.stop(); // 清除 TTS 正在發聲與 Queue 中等待發聲的要求，立即停止發聲
	}

	/**
	 * 檢查 TTS 是否是在多句式發音模式下
	 * 
	 * @return true / false
	 *
	 * @author Alvin Huang
	 * @date 2014/3/14 下午2:47:40
	 */
	public static boolean isMultiTTS()
	{
		return (ttsCount != -1);
	}
	
	/**
	 *  
	 *  釋放 TTS engine 所佔用的資源，用來解決開機時 TTS 會無故自己發出語音的問題
	 *
	 * @author Alvin Huang
	 * @date 2014/2/8 上午11:38:28
	 */
	public static void releaseTTS()
	{
		ttsObj.shutdown();
		isInitTTS = false;
		
		Log.d("MilkBoxTTS", "*********** TTS engine is release. *********");
	}
}
