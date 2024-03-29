package com.kentec.milkbox.asr;

import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.SuppressLint;
import android.app.Instrumentation;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MainActivity;
import com.kentec.milkbox.MainApp;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.SetupActivity;
import com.kentec.milkbox.introduction.IntroductionActivity;
import com.kentec.milkbox.moderator.ModeratorDialogActivity;
import com.kentec.milkbox.moderator.ModeratotMainActivity;
import com.kentec.milkbox.server.ServerThread;
import com.kentec.milkbox.setting.SettingActivity;
import com.kentec.milkbox.tv.TvPlayerActivity;
import com.kentec.milkbox.utils.ASRCompare;

@SuppressLint("SimpleDateFormat")
public class ASRActivity extends BaseActivity
{
	//手指按下的點為(x1, y1)手指離開屏幕的點為(x2, y2)  
    float x1 = 0;  
    float x2 = 0;  
    float y1 = 0;  
    float y2 = 0; 
    
	public static boolean compFunFlage = false;
	protected final static int RequestCode_ASR                    = 13579;
	private final static int RequestCode_Milky                       = 13577; // for Voice Command "Milky"
	protected final static int RequestCode_ModeratorDialog = 13575; // for moderatorDialogForResult();

	private ASRSelectorDialog mASRSelectorDialog;

	private Instrumentation mInstrumentation;

	private List<ASRCMDAdapter> mASRCMDAdapterList;

	private View mCurrentFocusView;
	
	// Public variables
//	public static ServerSocket m_serverSocket = null;
	public static final int NEW_CLIENT_COME = 1;
	public static final int CLIENT_ERROR = 2;
	public static final int GET_DATA_HEADER = 3;
	public static final int Ctrl_CMD = 4;
	public Handler mHandler;
	public TvPlayerActivity mTvPlayerActivity;
	private boolean RemindTag;
	
	private String matchWord;
	public static final int CALL_USER_GUIDE = 123;
	
	public String getMatchWord() {
		return matchWord;
	}
	
	public void setMatchWord(String matchWord) {
		this.matchWord = matchWord;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		mASRSelectorDialog = new ASRSelectorDialog(this);
		mInstrumentation = new Instrumentation();

		mASRCMDAdapterList = new ArrayList<ASRCMDAdapter>();
		mCurrentFocusView = null;
		
	    speech_receiver();
	}
	
	public void speech_receiver()
	{
		 
		try
		{
			int nServerPort = 12345;
			
			if(((MainApp)getApplicationContext()).m_serverSocket==null)
			{
				
			  ((MainApp)getApplicationContext()).m_serverSocket = new ServerSocket(nServerPort);

			  ServerThread serverThread = new ServerThread(ASRActivity.this, ((MainApp)getApplicationContext()).m_serverSocket);
			  serverThread.start();
			}
			
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		//*/

		mHandler = new Handler()
		{
			public void handleMessage(Message msg)
			{
				switch (msg.what)
				{
				case ASRActivity.Ctrl_CMD:

					Bundle bundle = msg.getData();
					String Ctrl_Code = bundle.getString("Ctrl_Code");

					if (CheckIfNumber(Ctrl_Code))
//					if (Ctrl_Code.startsWith("channel"))
					{
//						if(Ctrl_Code.matches(".*\\d.*"))
						{
							Ctrl_Code = Ctrl_Code.replaceAll("[^0-9]", "");
						    Intent intent_TV = new Intent("android.intent.action.TV");
						    Bundle bundle_TV = new Bundle();
						    bundle_TV.putString("TV_Command", Ctrl_Code);
						    intent_TV.putExtras(bundle_TV);
						    sendBroadcast(intent_TV);
						}
					}
					else if (Ctrl_Code.equalsIgnoreCase("Moderator"))
					{
						// Intent intent = new Intent(ASRActivity.this,
						// ModeratotMainActivity.class);
						// startActivity(intent);
					}// */
					else if (Ctrl_Code.equalsIgnoreCase("M"))
					{
						Intent intent = new Intent(ASRActivity.this, ModeratotMainActivity.class);
						startActivity(intent);
					}// */
					else if (Ctrl_Code.equalsIgnoreCase("home"))
					{
						Intent intent = new Intent(ASRActivity.this, MainActivity.class);
						startActivity(intent);
					}// */
/*					else if (Ctrl_Code.equalsIgnoreCase("exit"))//delete: freeman 2012/02/19 : repeat command
					{

						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);

						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						System.exit(0); // 

					}//*/
		            else if(Ctrl_Code.toLowerCase().contains("echo"))
		            {
		            	 Ctrl_Code = Ctrl_Code.replace("echo", "");		            	 		            	 
		 				 int indexNo = (int) (Math.random() * 2); 	
						 moderatorDialog(Ctrl_Code, indexNo + 1, 6000);		            	 
		            	 MilkBoxTTS.speak(Ctrl_Code, Ctrl_Code);
		            	 
		            }//	
/*		            else if (Ctrl_Code.startsWith("remind"))//add: freeman 2014/02/19 : Remind command for remote APP
					{
		 				  String Remind_Time;
		 				  int indexNo = (int) (Math.random() * 2);
						  if(Ctrl_Code.matches(".*\\d.*"))
						  {
							  Remind_Time = Ctrl_Code.replaceAll("[^0-9]", "");
							  final Counter timer = new Counter(Integer.parseInt(Remind_Time) * 1000,1000);//
							  timer.start();	
							  Ctrl_Code = "Ok, I will remind you after " + Remind_Time +" minutes, I promise";
							  moderatorDialog(Ctrl_Code, indexNo + 1, 6000);
							  MilkBoxTTS.speak(Ctrl_Code, "doFeedback");
						  }
						  else	
						  {   
							  Ctrl_Code = CFG.sys_NickName + getResources().getString(R.string.doSayAgain);
							  moderatorDialog(Ctrl_Code, indexNo + 1, 6000);
						      MilkBoxTTS.speak(Ctrl_Code, "doSayAgain");
						  }
					}//*/						
					else
					{
						
						ArrayList<String> speech2text = new ArrayList<String>();
						speech2text.add(0, Ctrl_Code);
						if (!dispatchSpecialCMD(speech2text))
						{
							if (!dispatchSpecialKey(speech2text))
							{

//								if (mCurrentFocusView != null && (mCurrentFocusView instanceof EditText)) mASRSelectorDialog.showASRSelector(speech2text, (EditText) mCurrentFocusView);
								
								if (mCurrentFocusView == null) mCurrentFocusView = /*super.*/getCurrentFocus();

								if (mCurrentFocusView != null && (mCurrentFocusView instanceof EditText))
								{
									mASRSelectorDialog.showASRSelector(speech2text/*msgsArrayList*/, (EditText) mCurrentFocusView);
								}
								else
								{
									
									// 完全被沒有符合的 Voice Command
									//"Can you say that again.", " I don't understand.", "I beg your pardon."
									String strSpeak[] = { "抱歉，請再說一次", "我不明白", "請再說一次"};

									int indexNo2 = (int) (Math.random() * 3) ; // 亂數得到 0 - 2 的值
									int indexNo3 = (int) (Math.random() * 2) ; // 亂數得到 0 - 1 的值

									// Show Milky message and TTS speak
									MilkBoxTTS.speak(strSpeak[indexNo2], "NoVoiceCommandIdentify");
									moderatorDialog(strSpeak[indexNo2], indexNo3+1, 3000);
									
									// by Milky script
									// moderatorDialog(strSpeak[indexNo2], indexNo2, 3000, strSpeak[indexNo2], "NoVoiceCommandIdentify", RequestCode_Milky);

								}
							}
						}//
					}

					break;
				}
				super.handleMessage(msg);
			}
		};//
	}

	public static boolean CheckIfNumber(String in)
	{
		try
		{
			Integer.parseInt(in);
		}
		catch (NumberFormatException ex)
		{
			return false;
		}

		return true;
	}	
	

	@Override
	protected void onPause()
	{
		super.onPause();		
	}

	public boolean dispatchKeyUpEvent(int keyCode, KeyEvent event, View currentFocusView)
	{
		mCurrentFocusView = currentFocusView;

		Log.i("Alvin", "ASRActivity ---- dispatchKeyUpEvent " + keyCode);
		if (keyCode == KeyEvent.KEYCODE_F2 || keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == 41)
		{
			startASR();

			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_F3)
		{
			goActivityNoFinish(IntroductionActivity.class);

			return true;
		}
		return false;
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		Log.i("Alvin", "ASRActivity ---- onKeyUp " + keyCode);
		if (keyCode == KeyEvent.KEYCODE_F2 || keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == 41)
		{
			startASR();
			
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_F3)
		{
			goActivityNoFinish(IntroductionActivity.class);

			return true;
		}

		return super.onKeyUp(keyCode, event);
	}

	public void addASRCMDAdapter(ASRCMDAdapter asrCMDAdapter)
	{
		System.out.println("addASRCMDAdapter() org size: "+mASRCMDAdapterList.size());
		mASRCMDAdapterList.add(asrCMDAdapter);
		System.out.println("addASRCMDAdapter() new size: "+mASRCMDAdapterList.size());
	}
	
	public void removeASRCMDAdapter() {
		if(mASRCMDAdapterList.size() > 0) {
			System.out.println("removeASRCMDAdapter() org size: "+mASRCMDAdapterList.size());
			mASRCMDAdapterList.remove(mASRCMDAdapterList.size()-1);
			System.out.println("removeASRCMDAdapter() new size: "+mASRCMDAdapterList.size());
		}
	}

	public void startASR()
	{
		// 以 intent 到 Moderator Activity 的方式來啟動 Moderator，再以回呼 onActivityResult() 的方式將辨識結果傳回來
		Intent intent = new Intent();
		intent.setClass(this, ModeratotMainActivity.class);

		try
		{			
			startActivityForResult(intent, RequestCode_ASR);
			Log.d("ASRActivity", ">>>  startASR(), set ASR_IS_RUN to true <<<");
			CFG.ASR_IS_RUN = true;
		}
		catch (Exception e)
		{
			super.showMsg("Error on hATM eMilkBox Moderatot\n" + e.getMessage());
		}
	}

	// ASR 回傳值比對
	// Alvin : 2014/03/03 ... 增加對於 compFunFlage 的處理
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		System.out.println("run ASRActivity onActivityResult: "+requestCode+" "+resultCode);
		super.onActivityResult(requestCode, resultCode, data);		
		
		try
		{			
			if (requestCode == RequestCode_ASR) // 確認是否為 Moderator 傳回的資料
			{
				CFG.ASR_IS_RUN = false;
				
				if (resultCode == RESULT_OK) // 確認 Moderator 的執行狀態是否為 OK (表示有正確辨識到資料)
				{
					// String str = new String();

					// 將 Moderator 傳回的辨識資料放入 ArrayList 中
					Bundle bundle = data.getExtras();
					ArrayList<String> msgsArrayList = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

					Log.d("ASRActivity", Arrays.toString(msgsArrayList.toArray()));

					// 開始比對資料
					if (!dispatchSpecialCMD(msgsArrayList)) // 比對 MainMenu 層的 Voice Command，有比對到相符 Voice Command 就結束比對程序，都不符合時就進入基本操作按鍵的比對
					{
						if (!dispatchSpecialKey(msgsArrayList))
						{
							if (mCurrentFocusView == null) mCurrentFocusView = super.getCurrentFocus();

							if (mCurrentFocusView != null && (mCurrentFocusView instanceof EditText))
							{
								mASRSelectorDialog.showASRSelector(msgsArrayList, (EditText) mCurrentFocusView);
							}
							else
							{
								
								// 完全被沒有符合的 Voice Command
								//"Can you say that again.", " I don't understand.", "I beg your pardon."
								String strSpeak[] = { "抱歉，請再說一次", "抱歉，請再說一次", "抱歉，請再說一次"};

								int indexNo2 = (int) (Math.random() * 3) ; // 亂數得到 0 - 2 的值
								int indexNo3 = (int) (Math.random() * 2) ; // 亂數得到 0 - 1 的值

								// Show Milky message and TTS speak
								MilkBoxTTS.speak(strSpeak[indexNo2], "NoVoiceCommandIdentify");
								moderatorDialog(strSpeak[indexNo2], indexNo3+1, 3000);
								
								// by Milky script
								// moderatorDialog(strSpeak[indexNo2], indexNo2, 3000, strSpeak[indexNo2], "NoVoiceCommandIdentify", RequestCode_Milky);

							}
						}
					}
				}
				else if (resultCode == RESULT_CANCELED)
				{
					// Write your code if there's no result
					// "RESULT_CANCELED"
				}
				else
				{
					// "Other:" + String.valueOf(resultCode)
				}
			}
			else if (requestCode == RequestCode_Milky) // 確認是否為 ModeratorDialog 傳回的資料
			{
				if (resultCode == RESULT_OK) // 確認 Moderator 的執行狀態是否為 OK (表示有正確辨識到資料)
				{
					Log.d("ASRActivity", ">>>>>>>>> onActivityResult() -> RequestCode_Milky is RESULT_OK");
					startASR();
					// sendKey(KeyEvent.KEYCODE_F2);
				}
				else if (resultCode == RESULT_CANCELED)
				{
					// Write your code if there's no result
					// "RESULT_CANCELED"
				}
				else
				{
					// "Other:" + String.valueOf(resultCode)
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// Alvin : 2014/03/03 ... 增加對於 compFunFlage 的處理 
	private boolean dispatchSpecialCMD(ArrayList<String> msgsArrayList)
	{
		// Add : Alvin 2014/03/03 ... 當 compFunFlage = true 時表示 Search 功能框已經開啟，不做任何比對直接離開
		if(compFunFlage) return false;
		
		// Toast.makeText(BaseActivity.this, asrDatas.get(i),
		// Toast.LENGTH_SHORT).show();
		System.out.println("run dispatchSpecialCMD()");
		String msg;

		// 亂數得到 0 - 1 的值，用來決定對話框的顯示位置是要在左邊還是右邊
		int indexNo = (int) (Math.random() * 2);
		
		// 將 Moderator 回傳回來的辨識結果，逐一拿來與各 Voice Command List 進行比對
		for (int i = 0; i < msgsArrayList.size(); i++)
		{
			msg = msgsArrayList.get(i);
			// 為了給 Grocery 含 item 名稱用的，將比對到的字傳回
			matchWord=msg;
			
			// 比對 ASRCMDAdapter 裡面的 Voice Command List，
			if (processASRCMDAdapter(msg))
			{
				// 只要有比對到相符合的 Voice Command 就不再往下比對其它的辨識回傳值
				return true;
			}

			
			if(msg.contains("remind")||msg.contains("alarm"))//add: freeman 2014/02/20 : Remind command for RF remote controller
			{
				Calendar c = new GregorianCalendar();//creates a Calendar instance with the current time
				int HourNow = c.get(Calendar.HOUR_OF_DAY);	
				int MinuteNow = c.get(Calendar.MINUTE);
				
				int mHours;
				int mMinutes;				
				
				String []ar=msg.split(" ");//split string to char array
				String TimeFromSpeaker;		
				String TargetTime;
				if(msg.matches(".*\\d.*"))//check if number
				{		

					
					TimeFromSpeaker = msg.replaceAll("[^0-9]", "");
					if(msg.endsWith("hour")||msg.endsWith("hours"))
					{
						TimeFromSpeaker  = String.valueOf(Integer.valueOf(TimeFromSpeaker) * 60);
					}
					
					
					mMinutes = (Integer.parseInt(TimeFromSpeaker)%60 + MinuteNow)%60;
					mHours = HourNow + (Integer.valueOf(TimeFromSpeaker)/60) + ((Integer.parseInt(TimeFromSpeaker)%60 + MinuteNow)/60);							
					TargetTime = String.valueOf(mHours*100+mMinutes);
					
					if(msg.endsWith("minute")||msg.endsWith("minutes")||msg.endsWith("second"))
				    {//last vocabulary is "minutes" or "hour" or "second"
						if(msg.contains("to"))
						{
							toDoSomething(TimeFromSpeaker,TargetTime, msg, indexNo);							
						}					  					  	  
						else 
						{		
							SetAlarmTimer(Integer.parseInt(TimeFromSpeaker),ar[ar.length-1], "time's up ! time's up !");						
                            if(TargetTime.length() == 3)
                            	msg = "Ok, I will remind you at " +TargetTime.substring(0, 1)+" "+TargetTime.substring(1, 3)/*+", is it ok?"*/;
                            else if(TargetTime.length() == 4)
                                msg = "Ok, I will remind you at " +TargetTime.substring(0, 2)+":"+TargetTime.substring(2, 4)/*+", is it ok?"*/;
							moderatorDialog(msg, indexNo + 1, 6000);
							MilkBoxTTS.speak(msg, "doFeedback");
							RemindTag = true;
						}
					}
					else if(msg.endsWith("hour")||msg.endsWith("hours"))
					{
						if(msg.contains("to"))
						{
							toDoSomething(TimeFromSpeaker,TargetTime, msg, indexNo);							
						}					  					  	  
						else 
						{		
							SetAlarmTimer(Integer.parseInt(TimeFromSpeaker),ar[ar.length-1], "time's up ! time's up !");						
                            if(TargetTime.length() == 3)
                            	msg = "Ok, I will remind you at " +TargetTime.substring(0, 1)+" "+TargetTime.substring(1, 3)/*+", is it ok?"*/;
                            else if(TargetTime.length() == 4)
                                msg = "Ok, I will remind you at " +TargetTime.substring(0, 2)+":"+TargetTime.substring(2, 4)/*+", is it ok?"*/;
							moderatorDialog(msg, indexNo + 1, 6000);
							MilkBoxTTS.speak(msg, "doFeedback");
							RemindTag = true;
						}						
					}
					else if(msg.endsWith("a.m.")||msg.endsWith("p.m.")||msg.endsWith("am")||msg.endsWith("pm"))
					{//last vocabulary is "a.m." or "p.m."
						if(msg.contains("to"))
						{
							toDoSomething(TimeFromSpeaker,TargetTime, msg, indexNo);

						}
						else
						{
						    msg = "Ok, I will remind you at " + TimeFromSpeaker +" "+ ar[ar.length-1] /*+", is it ok?"*/;
						    moderatorDialog(msg, indexNo + 1, 6000);
						    MilkBoxTTS.speak(msg, "doFeedback");
						    RemindTag = true;
						}
					}
					else if((2359 > Integer.parseInt(TimeFromSpeaker))&&(Integer.parseInt(TimeFromSpeaker) > 99))
					{//last vocabulary is numbers 
						if(TimeFromSpeaker.length()==3)//remind_time length is 3 digits
						{
							if(msg.contains("to"))
							{
								toDoSomething(TimeFromSpeaker,TargetTime, msg, indexNo);

							}
							else
							{
							    msg = "Ok, I will remind you at " +TimeFromSpeaker.substring(0, 1)+" "+TimeFromSpeaker.substring(1, 3)/*+", is it ok?"*/;
							    moderatorDialog(msg, indexNo + 1, 6000);
							    MilkBoxTTS.speak(msg, "doFeedback");
							    RemindTag = true;
							}
						}
						else//remind_time length is 4 digits
						{
							if(msg.contains("to"))
							{
								toDoSomething(TimeFromSpeaker,TargetTime, msg, indexNo);

							}
							else
							{						
							    msg = "Ok, I will remind you at " +TimeFromSpeaker.substring(0, 2)+" "+TimeFromSpeaker.substring(2, 4)/*+", is it ok?"*/;
							    moderatorDialog(msg, indexNo + 1, 6000);
							    MilkBoxTTS.speak(msg, "doFeedback");	
							    RemindTag = true;
							}
						}
						
					}
					else
					{   
						msg = CFG.getNickName() +",can you speak again ?";
						moderatorDialog(msg, indexNo + 1, 6000);
						MilkBoxTTS.speak(msg, "doSayAgain");
					}//*/	
					  
				}
				else	
				{   
					msg = CFG.getNickName() + ",can you speak again ?";
					moderatorDialog(msg, indexNo + 1, 6000);
					MilkBoxTTS.speak(msg, "doSayAgain");
				}//*/	
					  
			    return true;
			} //*/
			// 比對屬於 Main Menu 層級的 Voice Command
			else if (ASRCompare.matchResourceArray(getResources(), R.array.milky, msg))
			{				
				String strMsg = "Yes, What can I do for you ?";
				if("alvin".equalsIgnoreCase(msg)) strMsg="Yes, my master.";
				// 顯示與唸出資訊
				Log.i("ASRActivity", "dispatchSpecialCMD->" + indexNo);
				moderatorInteractiveDialog(strMsg, indexNo, strMsg, "Milky", RequestCode_Milky);
				// moderatorDialog(strMsg, indexNo, 3000, strMsg, "echoWhatCanISay", RequestCode_Milky);
				
				// 設定旗標讓系統再次開啟 Moderator 接收 User 的語音指令
				CFG.AUTO_RECALL_ASR = true;
				
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.grocery, msg)||
					 keyVocabulary(msg,"grocery")||keyVocabulary(msg,"shopping"))
			{
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				String strMsg = "Let's go shopping.";

				// Show message
				// moderatorDialog(strMsg, indexNo + 1, 1000, strMsg,
				// "GotoGrocery");

				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoGrocery");

				gotoGroceryMainActivity();

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.tv, msg)||
					 keyVocabulary(msg,"tv")&&keyVocabulary(msg,"watch"))
			{
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				String strMsg = "Let's grab some popcorn.";

				// Show message
				// moderatorDialog(strMsg, indexNo + 1, 1000, strMsg, "GotoTv");

				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoTv");

				gotoTvActivity();
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.homedine, msg)||
					 keyVocabulary(msg,"take")&&keyVocabulary(msg,"out"))
			{
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				String strMsg = "Let's grab a bite.";

				// Show message
				// moderatorDialog(strMsg, indexNo + 1, 1000, strMsg,
				// "GotoHomeDine");

				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoHomeDine");

				gotoHomedineActivity();
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.coupon, msg)||
					 keyVocabulary(msg,"coupon"))
			{
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				String strMsg = "Let's see what's on discount.";

				// Show message
				// moderatorDialog(strMsg, indexNo + 1, 1000, strMsg,
				// "GotoCoupon");

				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoCoupon");

				gotoCouponMainActivity();
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.radio, msg)||
					 keyVocabulary(msg,"radio"))
			{
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				String strMsg = "Let's go!";

				// Show message
				// moderatorDialog(strMsg, indexNo + 1, 1000, strMsg,
				// "GotoRadio");

				// 唸出資訊
				MilkBoxTTS.speak(strMsg, "GotoRadio");

				gotoRadioActivity();
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.setting, msg)||
					 keyVocabulary(msg,"setting"))
			{
				String strSpeak[] = { "Let's checking out.", "Let's see what we can do.", "Yes, Master." };

				int indexNo2= (int) (Math.random() * 3); // 亂數得到 0 - 2 的值

				// Show message
				// moderatorDialog(strSpeak[indexNo], indexNo + 1, 1000,
				// strSpeak[indexNo], "setting");

				// 唸出資訊
				MilkBoxTTS.speak(strSpeak[indexNo2], "setting");

				goActivityNoFinish(SettingActivity.class);
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.factory, msg))
			{	
				goActivityNoFinish(SetupActivity.class);
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.exit, msg))
			{
				String strSpeak[] = { "OK, See you tomorrow.", "Bye, Bye~~!", "See you soon!", "System off!" };

				int indexNo2 = (int) (Math.random() * 4); // 亂數得到 0 - 3 的值

				// Show message
				// moderatorDialog(strSpeak[indexNo], 1, 1000,
				// strSpeak[indexNo], "exit");

				// 唸出資訊
				MilkBoxTTS.speak(strSpeak[indexNo2], "exit");

				MilkBoxTTS.releaseTTS(); // 結束前釋放 TTS 資源
				this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				sendKey(KeyEvent.KEYCODE_F12); // Alvin add : 2014/02/25 ... 模擬按下 F12 鍵，以模擬用鍵盤離開系統
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.about, msg))
			{
				// Add : Alvin 2014/02/08
				/**
				* @author andywu
				* @date 2014.02.26
				* 加入新的語句 
				*/
				int StrNo = (int) (Math.random() * 2); // 亂數得到 0 - 2 的值，決定講什麼話
				switch(StrNo)
				{
				default:
				case 0:
					moderatorDialog("I am Milky designed by Kentec Inc.\n based in Taipei, Taiwan.", indexNo + 1, 7000, // 要顯示的內容 
	                          "I am Milky, designed by Kentec Incorporation. based in Taipei, Taiwan.", "About");// 實際要唸出的內容      
					break;
				case 1:
					moderatorDialog("I am Milky. How can I help you?", indexNo + 1, 3000, // 要顯示的內容 
	                          "I am Milky. How can I help you?", "About"); // 實際要唸出的內容     
					break;
				}
				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.whoami, msg)) 
			{
				// Add : Alvin 2014/02/08
				
				echoNickName();

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.HelpMainMenu, msg))
			{
				// Add : Alvin 2014/02/08				
				// Log.d("ASRActivity", ">>> dispatchSpecialCMD -> call echoWhatCanISay() Start.");
				echoWhatCanISay(getResources());
				// Log.d("ASRActivity", ">>> dispatchSpecialCMD -> echoWhatCanISay() end.");

				return true;
			}
			else if (ASRCompare.compareResourceArray(getResources(), R.array.setNickName, msg))
			{
				// Add : Alvin 2014/02/08
				// 此 Voice Command 的比對方式與其它 Voice Command 不同，不採完全字串比對，改用比對自傳前半部，當符合 Voice Command 時將後面剩餘的字串當作 Nick Name 的值來使用
				
				String retStr = ASRCompare.compareValue;
				
				// 取出 Voice Command 後面的字串當作 Nick Name 的值來使用
				CFG.sys_NickName = msg.substring(msg.length() - msg.compareToIgnoreCase(retStr)).trim();
				
				// 將取出的 Nick Name 存入本機的設定檔案中				
				CFG.mfs.setNickName(CFG.sys_NickName);
				
				try
				{
					CFG.mfs.saveData();
				}
				catch (Throwable e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 回應新的 Nick Name 
//				echoNickName();
				echoNickNameAfterSet(); // andywu 2014.02.25 與Who am I 切開

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.WhatTimeIsIt, msg)||
					 keyVocabulary(msg,"what time"))
			{
				// Add : Alvin 2014/02/08
				
				// get now time
				String nowTime = "It is  " + new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());

				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值

				// Show message
				moderatorDialog(nowTime, indexNo + 1, 3000, nowTime, "WhatTimeIsIt");

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.WhatDayIsToday, msg))
			{
				// Add : Alvin 2014/02/10
				
				// get now time
				String whatDayIsToday = "Today is  " + new SimpleDateFormat("yyyy/MM/dd EEEE").format(Calendar.getInstance().getTime());

				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值

				// Show message
				moderatorDialog(whatDayIsToday, indexNo + 1, 6000, whatDayIsToday, "WhatDayIsToday");

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.HowIsTheWeather, msg)||
					 keyVocabulary(msg,"weather"))
			{
				// Add : Alvin 2014/02/10
				
				String strArray[] = { "Today is ", "Current Weather is " };
				// int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
				/**
				* @author andywu
				* @date 2014.02.25 
				* 加入問候語 (前面加 Hi, "NickName" 或 Hi, "FirstName")
				*/
				String nameArray[] = {
//						(CFG.sys_NickName.length() > 0) ? ( CFG.sys_NickName) : ""
						CFG.getNickName()
					   ,(CFG.sys_FirstName.length() > 0) ? ( CFG.sys_FirstName) : ""};
				if (("").equals(nameArray[0]))
					nameArray[0] = nameArray[1] ;
		
		
				int NameNo = (int) (Math.random() * nameArray.length); // 亂數得到 0 - 1 的值，決定用NickName或是FirstName
				strArray[0] = "Hi, " + nameArray[NameNo] + ". " + strArray[0] ;
						
				strArray[0] += CFG.WEATHER.currentCondition.getDescr();
				strArray[1] += CFG.WEATHER.currentCondition.getDescr() + ", at " + Math.round((CFG.WEATHER.temperature.getTemp() - 275.15)) + "°C";

				// Show message
				moderatorDialog(strArray[indexNo], indexNo + 1, 4000, strArray[indexNo], "HowIsTheWeather");

				return true;
			}
			else if (ASRCompare.matchResourceArray(getResources(), R.array.UserGuide, msg)) // for voice command UserGuid, Alvin : 2014/03/07
			{
				doUserGuide();
				
				return true;
			}
			
			/*
			 * 目前無下雨機率的資訊來源
			 * 
			 * else if (ASRCompare.matchResourceArray(getResources(),
			 * R.array.DoINeedAnUmbrella, msg)) // Add : Alvin 2014/02/10 { int
			 * indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值
			 * 
			 * String strMsg = "There is";
			 * 
			 * // CFG.WEATHER.rain. // Show message moderatorDialog(strMsg,
			 * indexNo + 1, 6000);
			 * 
			 * // 唸出資訊 MilkBoxTTS.speak(strMsg, "WhatDayIsToday");
			 * 
			 * return true; }
			 */
			/*
			 * else // 沒有任何 voice command 相符 { String strSpeak[] = {
			 * "Oh sorry, I did not catch, you can repeat it again?",
			 * "You can repeat that again?",
			 * "I do not understand what you mean?",
			 * "You can close to the microphone a little ?",
			 * "A little louder please ?"};
			 * 
			 * int indexNo = (int) (Math.random() * strSpeak.length); // 亂數得到 0
			 * - 3 的值
			 * 
			 * // Show message moderatorDialog(strSpeak[indexNo], 1, 3000,
			 * strSpeak[indexNo], "AllNoMatch");
			 * 
			 * return true; }
			 */

			/*
			 * else if(msg.compareToIgnoreCase("My Name is") > 0) {
			 * CFG.sys_NickName = msg.substring(msg.length() -
			 * msg.compareToIgnoreCase("My Name is"));
			 * echoNickName(ASRActivity.this);
			 * 
			 * return true; } else if(msg.compareToIgnoreCase("Call me") > 0) {
			 * CFG.sys_NickName = msg.substring(msg.length() -
			 * msg.compareToIgnoreCase("Call me"));
			 * echoNickName(ASRActivity.this);
			 * 
			 * return true; }
			 */
		}

		return false;
	}

	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/3/7 下午5:27:03
	 */
	private void doUserGuide()
	{
		Intent introductionMainActivity = new Intent();
		introductionMainActivity.setClass(this, IntroductionActivity.class);

		// 呼叫新的 Activity Class
		startActivity(introductionMainActivity);
		
		// goActivityNoFinish(IntroductionActivity.class);

	}

	/**
	 *  ASRCMDAdapter Voice Command 的比對
	 *  
	 *  1. 逐一以 VoiceCommand List 的內容與 msg 進行比對
	 *  2. VoiceCommand List 的 item 內容中最後2字元如果帶有 " *" 識別字樣則會將此 Voice Command String 判別為部分字串比對方式，否則判為全字串比對方式
	 *  3. 全字串比對方式：msg 與 VoiceCommand List item 的內容要完全符合(忽略大小寫的差異)
	 *  4. 部分字串比對方式：VoiceCommand List item 的內容中會依據 " *" 辨識字樣的位置將 msg 切割為前後兩部分，前面部分與 VoiceCommand list item 內容完全符合時，即表示比對符合，
	 *                                          接著會將檢查後面部分的內容是否為全數字字串，是的話會將數字字串當成回傳值回傳，否則也是當成辨識不符
	 *  5. 這是為了使用在 VociceCommand CommandValue 的命令格式上，Ex. Channel 25、Change Volume 50 ....                                         
	 *  
	 * @param msg ... 比對目標，要來與 VoiceCommand List 做比對的目標，Ex 由語音辨識回傳來的辨識結果
	 * @return
	 *
	 * @author Alvin Huang
	 * @date 2014/2/13 下午1:45:55
	 */
	private boolean processASRCMDAdapter(String msg)
	{
		boolean fullCompare = true; //資料比對方式，true = 使用字串完全比對方式，false = 使用前段符合比對方式
		String cmdSubStr = "";
		
		Iterator<ASRCMDAdapter> iterator = mASRCMDAdapterList.iterator();
		ASRCMDAdapter asrCMDAdapter;
		String[] command;
		while (iterator.hasNext())
		{
			asrCMDAdapter = iterator.next();
			command = asrCMDAdapter.getCommand();
			for (String cmd : command)
			{
				fullCompare = true;
				
				// 決定比對資料的方式，根據 xml 列的有效 Voice Command 後面是否有 " *" 來當判斷依據
				if((cmd.length() > 2) && (cmd.substring(cmd.length() - 2).equalsIgnoreCase(" *")))
				{					
					cmdSubStr = cmd.substring(0, cmd.length() - 2);
					fullCompare = false;
					
//					Log.i("processASRCMDAdapter", "=== cmd : " + cmd + " / cmdSubStr : " + cmdSubStr + " / msg : " + msg);					
				}
				if((cmd.length() > 1) && (cmd.substring(cmd.length() - 1).equalsIgnoreCase("*")))
				{					
					cmdSubStr = cmd.substring(0, cmd.length() - 1);
					fullCompare = false;
				}
				
//				Log.i("processASRCMDAdapter", "===  cmd : " + cmd + " / cmdSubStr : " + cmdSubStr + " / msg : " + msg);
				
				if(fullCompare && msg.equalsIgnoreCase(cmd))
				{
					// for 字串完全比對方式
					System.out.println("full compare string ("+msg+") : ("+cmd+")");
					// Log.i("processASRCMDAdapter", "=== 字串完全比對方式 ===");
					asrCMDAdapter.action();
					return true;
				}
				else if((!fullCompare) && (msg.length() > 0))
				{
					// for 部分字串比對方式
					if ((msg.length() > cmdSubStr.length()) && (msg.substring(0, cmdSubStr.length()).equalsIgnoreCase(cmdSubStr)))
					{
						if (msg.compareToIgnoreCase(cmdSubStr) > 0)
						{
							// Log.i("processASRCMDAdapter", "=== 部分字串比對方式 ===");
							String str = msg.substring(msg.length() - msg.compareToIgnoreCase(cmdSubStr)).trim();
//							if (isNumeric(str))
//							{
//								asrCMDAdapter.action(str);
//								return true;
//							}
//							return false;
							asrCMDAdapter.action(str);
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	// 基本操作鍵的辨識
	// Alvin : 2014/03/03 ... 增加對於 compFunFlage 的處理
	private boolean dispatchSpecialKey(ArrayList<String> msgsArrayList)
	{
		// Add : Alvin 2014/03/03 ... 當 compFunFlage = true 時表示 Search 功能框已經開啟，不做任何比對直接離開
		if(compFunFlage) return false;

		if (isOKMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_ENTER);
			return true;
		}
		else if (isDPadLeftMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_DPAD_LEFT);
			return true;
		}
		else if (isDPadRightMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_DPAD_RIGHT);
			return true;
		}
		else if (isDPadUpMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_DPAD_UP);
			return true;
		}
		else if (isDPadDownMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_DPAD_DOWN);
			return true;
		}
		else if (isBackMsg(msgsArrayList))
		{
			sendKey(KeyEvent.KEYCODE_BACK);
			return true;
		}

		return false;
	}

	private boolean isDPadLeftMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.left, cmd)) return true;
		}

		return false;
	}

	private boolean isDPadRightMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.right, cmd)) return true;
		}

		return false;
	}

	private boolean isDPadUpMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.up, cmd)) return true;
		}

		return false;
	}

	private boolean isDPadDownMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.down, cmd)) return true;
		}

		return false;
	}

	private boolean isBackMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.back, cmd)) return true;
		}

		return false;
	}

	private boolean isOKMsg(ArrayList<String> msgsArrayList)
	{
		Iterator<String> iterator = msgsArrayList.iterator();
		String cmd;
		while (iterator.hasNext())
		{
			cmd = iterator.next();
			if (ASRCompare.matchResourceArray(getResources(), R.array.ok, cmd)) return true;
		}

		return false;
	}

	protected void sendKey(final int keyCode)
	{
		new Thread()
		{
			@Override
			public void run()
			{
				// mInstrumentation.sendKeyDownUpSync(keyCode);

				try
				{
					Runtime.getRuntime().exec(new String[] { "/system/bin/input", "keyevent " + Integer.toString(keyCode) });
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}.start();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		startASR();				
		
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 
	 * @param context
	 * 
	 * @author Alvin Huang
	 * @date 2014/2/6 上午11:48:28
	 *               2014/2/24 .... 修正當 NickName 為空的時候不會顯示出 ','
	 */
	private void echoNickName()
	{
		/**
		* @author andywu
		* @date 2014.02.24
		* 加入firstname跟調整亂數
		* @date 2014.03.07
		* 改成不要radom，有NickName就優先使用
		*/
		String strArray[] = { "Hi, ", "Hello, ", "You are " };
//		String nameArray[] = {
//				(CFG.sys_NickName.length() > 0) ? ( CFG.sys_NickName) : ""
//			   ,(CFG.sys_FirstName.length() > 0) ? ( CFG.sys_FirstName) : ""};
//		if (("").equals(nameArray[0]))
//			nameArray[0] = nameArray[1] ;
			
		int StrNo = (int) (Math.random() * strArray.length); // 亂數得到 0 - strArray(1) 的值，決定講什麼話
//		int NameNo = (int) (Math.random() * nameArray.length); // 亂數得到 0 - 1 的值，決定用NickName或是FirstName
		int PosNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置
//		Log.d("andy",""+StrNo+NameNo+PosNo);
		
		// Show message
		moderatorDialog(strArray[StrNo] + CFG.getNickName(), PosNo + 1, 3000);

		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add(strArray[StrNo]);
		speakStrs.add(CFG.getNickName());
		MilkBoxTTS.speak(speakStrs, "echoNickName");
	}
	/**
	* @author andywu
	* @date 2014.02.25
	* 設定完NickName後會呼叫的function
	*/	
	private void echoNickNameAfterSet()
	{
		int PosNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置
		int StrNo = (int) (Math.random() * 3); // 亂數得到 0 - 2 的值，決定講什麼話
		String outString = "" ;
		switch(StrNo)
		{
		default :
		case 0 :
			outString = "Hello, " + CFG.getNickName();
			break ;
		case 1 :
			outString = "Hi, " + CFG.getNickName();
			break ;
		case 2 :
			outString = "OK, I'll call you " + CFG.getNickName() + " from now on " ;
			break ;
		}
		// Show message
		moderatorDialog(outString, PosNo + 1, 4000);

		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add(outString);
		MilkBoxTTS.speak(speakStrs, "echoNickNameAfterSet");
	}
	/**
	 * 
	 * 
	 * 
	 * @author Alvin Huang
	 * @date 2014/2/7 下午5:38:19
	 */
	private void echoWhatCanISay(Resources res)
	{
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值

		// Show message
		String strMsg[] = { "What can I say",
							"Who Am I",
							"Who are you",
							"How is the Weather",
							"Coupon",
							"Grocery",
							"Takeout",
							"TV",
							"Radio"
							};

		/**
		* @author andywu
		* @date 2014.02.20
		* 利用HTML排版
		* <big> 放大字型
		* <sub> 下標
		* <small> 縮小字型
		* <b> 粗體
		* &nbsp; 空白字元
		*/
		String dialogStr =
				  "<big>● What can I say</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Command List</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Help</b></small></small></sub><br>"
				+ "<big>● Who Am I</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				+ "<big>● Who are you</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				+ "<big>● How is the Weather</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
//				+ "<big>● How is the Weather</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Do I need an umbrella</b></small></small></sub><br>"
				+ "<big>● Coupon</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>What's on sale</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Discount</b></small></small></sub><br>"
				+ "<big>● Grocery</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Store</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Shopping</b></small></small></sub><br>"
				+ "<big>● Takeout</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Restaurant</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Home Delivery</b></small></small></sub><br>"
				+ "<big>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>I am hungry</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>What can I eat</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Fast food</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>I am starving</b></small></small></sub><br>"
				+ "<big>● TV</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Watch TV</b></small></small></sub><br>"
				+ "<big>● Radio</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Play some music</b></small></small></sub><br>";
		
		// 處理唸出用的資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strMsg);
		
		// 顯示與唸出資訊		
		moderatorDialog(dialogStr, indexNo + 2, 10000, speakStrs, "echoWhatCanISay");
	}

	/**
	 * Moderator 對話框1 ... 顯示字串訊息、不發音
	 * 
	 * @param strMsg .................................... 要顯示在對話框的內容
	 * @param showLocation ....................... 要使用的位置, 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕顯示 4:又邊圖+全螢幕顯示
	 * @param mSec ....................................... 自動關閉對話框的時間(單位: 1/1000 秒)
	 * 
	 * @author Alvin Huang
	 * @date 2014/2/11 上午9:30:57
	 */
	public void moderatorDialog(String strMsg, int showLocation, int mSec)
	{
		moderatorDialog(strMsg, showLocation, mSec, "", "");
	}

	/**
	 * Moderator 對話框2 ... 顯示字串訊息、含單句 TTS 發音
	 * 
	 * @param strMsg .................................... 要顯示的文字內容
	 * @param showLocation ....................... 要顯示的位置 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕 4:右邊圖+全螢幕
	 * @param mSec ....................................... 對話框 Activity 自動關閉的時間 (單位 0.0001 秒)
	 * @param strSpeak ................................. 要發音的內容, "" 表示不發聲
	 * @param strSpeakId ............................. 發音內容辨識標籤
	 * 
	 * @author Alvin Huang
	 * @date 2014/2/11 上午9:44:03
	 */
	public void moderatorDialog(String strMsg, int showLocation, int mSec, String strSpeak, String strSpeakId)
	{
		Intent moderatorDialogActivity = new Intent();
		moderatorDialogActivity.setClass(this, ModeratorDialogActivity.class);
		
		moderatorDialogActivity.putExtra("msg", strMsg);
		moderatorDialogActivity.putExtra("showLocation", showLocation);
		moderatorDialogActivity.putExtra("delayTime", mSec);
		moderatorDialogActivity.putExtra("strSpeak", strSpeak);
		moderatorDialogActivity.putExtra("strSpeakId", strSpeakId);
		// moderatorDialogActivity.putExtra("strArrayListSpeak", "");

		// 呼叫新的 Activity Class
		startActivity(moderatorDialogActivity);
	}
	
	/**
	 * Moderator 對話框3 ... 顯示字串訊息、含多句 TTS 發音
	 * 
	 * @param strMsg .................................... 要顯示的文字內容
	 * @param showLocation ....................... 要顯示的位置 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕 4:右邊圖+全螢幕
	 * @param mSec ....................................... 對話框 Activity 自動關閉的時間 (單位 0.0001 秒)
	 * @param strSpeak ................................. 要發音的內容 ArrayList， null 表示不發聲
	 * @param strSpeakId ............................. 發音內容辨識標籤
	 *
	 * @author Alvin Huang
	 * @date 2014/2/22 下午2:12:04
	 */
	public void moderatorDialog(String strMsg, int showLocation, int mSec, ArrayList<String> strSpeak, String strSpeakId)
	{
		Intent moderatorDialogActivity = new Intent();
		moderatorDialogActivity.setClass(this, ModeratorDialogActivity.class);
		
		moderatorDialogActivity.putExtra("msg", strMsg);
		moderatorDialogActivity.putExtra("showLocation", showLocation);
		moderatorDialogActivity.putExtra("delayTime", mSec);
		// moderatorDialogActivity.putExtra("strSpeak", "");
		moderatorDialogActivity.putExtra("strSpeakId", strSpeakId);
		moderatorDialogActivity.putExtra("strArrayListSpeak", strSpeak);

		/*
		Intent intent = new Intent();
		intent.setClass(this, ModeratotMainActivity.class);
		*/
		// 呼叫新的 Activity Class
		startActivity(moderatorDialogActivity);
	}
	
	/**
	 * Moderator 對話框4 ... 顯示字串訊息、含單句 TTS 發音、使用 startActivityForResult() 方式呼叫 Moderator Dialog 
	 * 
	 * @param strMsg .................................... 要顯示的文字內容
	 * @param showLocation ....................... 要顯示的位置 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕 4:右邊圖+全螢幕
	 * @param mSec ....................................... 對話框 Activity 自動關閉的時間 (單位 0.0001 秒)
	 * @param strSpeak ................................. 要發音的內容 ArrayList， null 表示不發聲
	 * @param strSpeakId ............................. 發音內容辨識標籤
	 * @param requestCode ......................... for ActivityResult
	 *
	 * @author Alvin Huang
	 * @date 2014/2/22 下午2:12:04
	 */
	public void moderatorDialog(String strMsg, int showLocation, int mSec, String strSpeak, String strSpeakId, int requestCode)
	{
		Intent moderatorDialogActivity = new Intent();
		moderatorDialogActivity.setClass(this, ModeratorDialogActivity.class);
		
		moderatorDialogActivity.putExtra("msg", strMsg);
		moderatorDialogActivity.putExtra("showLocation", showLocation);
		moderatorDialogActivity.putExtra("delayTime", mSec);
		moderatorDialogActivity.putExtra("strSpeak", strSpeak);
		moderatorDialogActivity.putExtra("strSpeakId", strSpeakId);
		// moderatorDialogActivity.putExtra("strArrayListSpeak", "");

		/*
		Intent intent = new Intent();
		intent.setClass(this, ModeratotMainActivity.class);
		*/
		// 呼叫新的 Activity Class
		startActivityForResult(moderatorDialogActivity, requestCode);
	}
	
	/**
	 *  Moderator 對話框5 ... 顯示字串訊息、含多句 TTS 發音、使用 startActivityForResult 方式 
	 * 
	 * @param strMsg .................................... 要顯示的文字內容
	 * @param showLocation ....................... 要顯示的位置 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕 4:右邊圖+全螢幕
	 * @param mSec ....................................... 對話框 Activity 自動關閉的時間 (單位 0.0001 秒)
	 * @param strSpeak ................................. 要發音的內容 ArrayList， null 表示不發聲
	 * @param strSpeakId ............................. 發音內容辨識標籤 
	 * @param requestCode ......................... startActivityForResult() 的 RequestCode，-1 = 使用 RequestCode_ModeratorDialog
	 *
	 * @author Alvin Huang
	 * @date 2014/3/18 上午9:19:30
	 */
	public void moderatorDialogForResult(String strMsg, int showLocation, int mSec, ArrayList<String> strSpeak, String strSpeakId, int requestCode)
	{
		Intent moderatorDialogActivity = new Intent();
		moderatorDialogActivity.setClass(this, ModeratorDialogActivity.class);
		
		moderatorDialogActivity.putExtra("msg", strMsg);
		moderatorDialogActivity.putExtra("showLocation", showLocation);
		moderatorDialogActivity.putExtra("delayTime", mSec);
		// moderatorDialogActivity.putExtra("strSpeak", "");
		moderatorDialogActivity.putExtra("strSpeakId", strSpeakId);
		moderatorDialogActivity.putExtra("strArrayListSpeak", strSpeak);

		/*
		Intent intent = new Intent();
		intent.setClass(this, ModeratotMainActivity.class);
		*/
		// 呼叫新的 Activity Class
		startActivityForResult(moderatorDialogActivity, (requestCode == -1) ? RequestCode_ModeratorDialog : requestCode);
	}
	
	/**
	 * 交談式對話框
	 * @param strMsg .................................... 要顯示的文字內容
	 * @param showLocation ....................... 要顯示的位置 1:左邊圖+對話框 2:右邊圖+對話框 3:左邊圖+全螢幕 4:右邊圖+全螢幕
	 * @param strSpeak ................................. 要發音的內容 ArrayList， null 表示不發聲
	 * @param strSpeakId ............................. 發音內容辨識標籤
	 * @param requestCode ......................... for ActivityResult
	 *
	 * @author Alvin Huang
	 * @date 2014/2/25 下午3:51:09
	 */
	public void moderatorInteractiveDialog(String strMsg, int showLocation, String strSpeak, String strSpeakId, int requestCode)
	{
		// 以 intent 到 Moderator Activity 的方式來啟動 Moderator，再以回呼
		// onActivityResult() 的方式將辨識結果傳回來
		Intent intent = new Intent();
		intent.setClass(this, ModeratotMainActivity.class);
		intent.putExtra("msg", strMsg);
		intent.putExtra("showLocation", showLocation);
		// intent.putExtra("delayTime", mSec);
		intent.putExtra("strSpeak", strSpeak);
		intent.putExtra("strSpeakId", strSpeakId);
		// intent.putExtra("strArrayListSpeak", "");
		try
		{
			startActivityForResult(intent, RequestCode_ASR);
			Log.d("ASRActivity", ">>>  startASR(), set ASR_IS_RUN to true <<<");
			CFG.ASR_IS_RUN = true;
		}
		catch (Exception e)
		{
			super.showMsg("Error on hATM eMilkBox Moderatot\n" + e.getMessage());
		}
	}
	
	
	/**
	 * 檢查字串是否為數值字串
	 * @param str
	 * @return
	 *
	 * @author Alvin Huang
	 * @date 2014/2/13 下午5:56:57
	 */
	public boolean isNumeric(String str)
	{
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
		{
			return false;
		}
		return true;
	}
	/**
	 *  Remind or Alarm timer時間長度設置
	 *                                            
	 * @param time -------- 待alarm的時間                                     
	 * @param time_unit --------- 時間單位:秒,分,時
	 * @param toDoWhat ----- 鬧鈴時間到時提醒該做什麼事
	 * @return
	 *
	 * @author Freeman Chen
	 * @date 2014/2/24 
	 */	
	public void SetAlarmTimer(long time, String time_unit, String toDoWhat)// Add: freeman 2014/02/24 : alarm timer setting
	{
        long second = time * 1000;
		if(time_unit.equalsIgnoreCase("second"))
		{
		  final Counter timer = new Counter(second ,1000, toDoWhat);//counter for n second
		  timer.start();   
		}
		else if(time_unit.equalsIgnoreCase("minutes")||time_unit.equalsIgnoreCase("minute"))
		{
		  final Counter timer = new Counter(second * 60 ,1000, toDoWhat);//counter for n minutes
		  timer.start();   
		}
		else if(time_unit.equalsIgnoreCase("hour")||time_unit.equalsIgnoreCase("hours"))
		{
		  final Counter timer = new Counter(second * 60 * 60 ,1000, toDoWhat);//counter for n hour
		  timer.start();   
		}
	}	
    public class Counter extends CountDownTimer{// Add: freeman 2014/02/19 : countdown timer tick    
    	
    private String toDo_onFinish;

    public Counter(long millisInFuture, long countDownInterval, String toDoWhat) {
    	
      super(millisInFuture, countDownInterval);
      toDo_onFinish = toDoWhat;
    }

    @Override
    public void onFinish() {
	    int indexNo = (int) (Math.random() * 2); 
//	    String msg = ", wake up! wake up! wake up! ";
	    moderatorDialog("hi, "+CFG.getNickName()/*sys_NickName*/ + toDo_onFinish, indexNo + 1, 6000);	
	    MilkBoxTTS.speak("hi , "+CFG.getNickName()/*sys_NickName*/ + toDo_onFinish, "doRemind");
    }

    @Override
    public void onTick(long millisUntilFinished) {

     }
    }//////*/
	/**
	 *  檢查從ASR收到的remind指令句子中是否有"to",並以"to"為基準抓取之後的單字當待做之事
	 *                                            
	 * @param TimeFromSpeaker -------- 欲提醒的時間長度(minutes or hours)或時間點
	 * @param TargetTime -------- 目標提醒時間(幾點幾分)                                     
	 * @param msg --------------- 從ASR收到的指令句
	 * @param randomNum --------- 亂數時間
	 * @return
	 *
	 * @author Freeman Chen
	 * @date 2014/2/25 下午3:45:55
	 */	
	private void toDoSomething(String TimeFromSpeaker, String TargetTime, String msg, int randomNum)
	{
		String String_to = "to";	
		String String_in = "in";
		String String_after = "after";
		String []ar=msg.split(" ");
		String StringAfterTo = "";
		int idx=0;
		for (idx=0; idx<ar.length; idx++) 
		{
		   if (String_to.equals(ar[idx]))//capture a vocabulary after "to"  
		   {	
			  
			      for (int idx_1=(idx+1); idx_1<ar.length; idx_1++)
			      {
				      if (!(String_in.equals(ar[idx_1])||String_after.equals(ar[idx_1])))
				      {
					      StringAfterTo = StringAfterTo + ar[idx_1] + " " ;
    
				      }
				      else break;
			      }//*/			   
			   
				 
				  if((!ar[ar.length-1].matches(".*\\d.*")) && msg.endsWith("minute")||msg.endsWith("minutes")||msg.endsWith("second"))
				  {//last vocabulary is not numeric,
					  SetAlarmTimer(Integer.parseInt(TimeFromSpeaker),ar[ar.length-1], ", it's time to " + StringAfterTo/*ar[idx + 1]*/);
//				      msg = "Ok, I will remind you to"+StringAfterTo+ " at " +TargetTime.substring(0, 2)+":"+TargetTime.substring(2, 4)/*+", is it ok?"*/;
                      if(TargetTime.length() == 3)
                      	msg = "Ok, I will remind you to "+StringAfterTo+ "at " +TargetTime.substring(0, 1)+":"+TargetTime.substring(1, 3)/*+", is it ok?"*/;
                      else if(TargetTime.length() == 4)
                        msg = "Ok, I will remind you to "+StringAfterTo+ "at " +TargetTime.substring(0, 2)+":"+TargetTime.substring(2, 4)/*+", is it ok?"*/;
				  }
				  else if((!ar[ar.length-1].matches(".*\\d.*")) && msg.endsWith("hour")||msg.endsWith("hours"))
				  {//last vocabulary is not numeric,
					  SetAlarmTimer(Integer.parseInt(TimeFromSpeaker),ar[ar.length-1], ", it's time to " + StringAfterTo/*ar[idx + 1]*/);
				      msg = "Ok, I will remind you to"+StringAfterTo+ "at " +TargetTime.substring(0, 2)+":"+TargetTime.substring(2, 4)/*+", is it ok?"*/;
				  }
				  else if(TimeFromSpeaker.length()==3)//last vocabulary is numeric
				  {
					  msg = "Ok, I will remind you to"+StringAfterTo+ "in " +TimeFromSpeaker.substring(0, 1)+":"+TimeFromSpeaker.substring(1, 3)/*+", is it ok?"*/;
				  }
				  else if(TimeFromSpeaker.length()==4)//last vocabulary is numeric
				  {
					  msg = "Ok, I will remind you to"+StringAfterTo+ "in " +TimeFromSpeaker.substring(0, 2)+":"+TimeFromSpeaker.substring(2, 4)/*+", is it ok?"*/;			
				  }				  
				  moderatorDialog(msg, randomNum + 1, 6000);
				  MilkBoxTTS.speak(msg, "doFeedback");	
				  RemindTag = true;
			      break;
		   }						  
		}		
	}
	/**
	 *  整句關鍵字搜尋
	 *                                            
	 * @param source -------- 來源的長句子
	 * @param match --------- 待比對關鍵字                                     
	 * @return
	 *
	 * @author Freeman Chen
	 * @date 2014/3/11 
	 */		
	public boolean keyVocabulary(String source, String match)
	{
		
		String[] sentence = source.split(" ");
		for(String word: sentence)
		{
		    if(word.equalsIgnoreCase(match))
		        return true;
		}
		return false;
	}
	
	

	
	
	
	
	/**
	* @author andywu
	* @date 2014.02.26
	* 預防沒有NickName時，要用FirstName代替
	*/
	/*private String getNickName() // 移至CFG.java
	{
		if ( (CFG.sys_NickName == null || ("").equals(CFG.sys_NickName)) && CFG.sys_FirstName != null)
			return CFG.sys_FirstName ;
		else if ( CFG.sys_NickName != null )
			return CFG.sys_NickName ;
		else
			return "" ;
	}*/
	
	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/3/6 上午9:30:04
	 */
	private void doOnTimeGreeting()
	{
		String speakGreeting[][] = { { "Hey ", "Hey ", "Good Evening " }, { ", it's time to go to bed", ", it's time for lunch", ", it's time for dinner" } };
		String echoStr[] = { " It's Bed Time!", " Lunch Time!", " Dinner Time!" };

		String nameArray[] = { 
//						(CFG.sys_NickName.length() > 0) ? (CFG.sys_NickName) : ""
						CFG.getNickName()
						, (CFG.sys_FirstName.length() > 0) ? (CFG.sys_FirstName) : "" };
		if (("").equals(nameArray[0])) nameArray[0] = nameArray[1];

		int NameNo = (int) (Math.random() * nameArray.length); // 亂數得到 0 - 1
																// 的值，決定用NickName或是FirstName
		int PosNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值，決定顯示位置

		// get now time
		Calendar cal = Calendar.getInstance(); // 取得當下的時間

		int minuteOfDay = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE); // 轉成今天00：00開始到目前的分鐘數
		if ((minuteOfDay < 299) && (CFG.onTimeGreetingFlag != 1)) // 0-299 = 00:00 - 04:59
		{
			CFG.onTimeGreetingFlag = 1;
		}
		else if ((minuteOfDay >= 720) && (minuteOfDay <= 779) && (CFG.onTimeGreetingFlag != 2)) // 720-779 = 12:00 - 12:59
		{
			CFG.onTimeGreetingFlag = 2;
		}
		else if ((minuteOfDay >= 1080) && (minuteOfDay <= 779) && (CFG.onTimeGreetingFlag != 3)) // 1080-1199 = 18:00 - 19:59
		{
			CFG.onTimeGreetingFlag = 3;
		}
		else
		{
			return;
		}
		
		// Show and Speak message
		moderatorDialog(new SimpleDateFormat("HH:mm").format(cal.getInstance().getTime()) + " " + echoStr[CFG.onTimeGreetingFlag - 1], 
				                          PosNo + 1, 5000, speakGreeting[0][CFG.onTimeGreetingFlag - 1] + nameArray[NameNo] + speakGreeting[1][CFG.onTimeGreetingFlag - 1],
				                          "onTimeGreeting");
	}

	@Override
	public void doSomethingOnCheckTimeOut() {
		doOnTimeGreeting();
		super.doSomethingOnCheckTimeOut();
	}
	
	// 隨意上下左右滑動，即可呼叫 startASR();
	@Override  
    public boolean onTouchEvent(MotionEvent event) {  
        //继承了Activity的onTouchEvent方法，直接监听点击事件  
        if(event.getAction() == MotionEvent.ACTION_DOWN) {  
            //当手指按下的时候  
            x1 = event.getX();  
            y1 = event.getY();   
        }  
        if(event.getAction() == MotionEvent.ACTION_UP) {  
        		startASR();
//            //当手指离开的时候 
        		Log.e("ASRActivity", "asrActivity startASR()");
            x2 = event.getX();  
            y2 = event.getY();  
            if(y1 - y2 > 50) {  
                Toast.makeText(ASRActivity.this, "向上滑", Toast.LENGTH_SHORT).show();  
            } else if(y2 - y1 > 50) {  
                Toast.makeText(ASRActivity.this, "向下滑", Toast.LENGTH_SHORT).show();  
            } else if(x1 - x2 > 50) {  
                Toast.makeText(ASRActivity.this, "向左滑", Toast.LENGTH_SHORT).show();  
            } else if(x2 - x1 > 50) {  
                Toast.makeText(ASRActivity.this, "向右滑", Toast.LENGTH_SHORT).show();  
            }  
        }  
        return super.onTouchEvent(event);  
    } 
	
}