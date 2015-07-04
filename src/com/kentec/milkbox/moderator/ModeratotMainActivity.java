/**
 * 
 */
package com.kentec.milkbox.moderator;

import java.util.ArrayList;
import java.util.List;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 
 * @author Alvin Huang
 *
 */
public class ModeratotMainActivity extends Activity implements OnClickListener
{
	private TextView mText2; // 在左上角顯示 Debug Info 用
	private SpeechRecognizer sr;
	// private Activity moderatorActivityClass;

	// private AudioManager audioManager;
	// private int mCurrentVolume;

	// 用來存放每個泡泡對話框的 Layout 元素(FrameLayout 、ImageView、TextView 的 id)
	private List<ModeratorTextDialogAdapter> mModeratorTextDialogAdapterList;

	private Intent recognizerIntent;
	private boolean mIsListening;
	private static final String TAG = "MyStt3Activity";
	private ImageView operator, mic, op2;
	private int micmageIds[] = { R.drawable.v1, R.drawable.v2, R.drawable.v3, R.drawable.v4, R.drawable.v5, R.drawable.v6, R.drawable.v7 };

	private AnimationDrawable animationDrawable, OpAnimationDrawable;

	private boolean chatMode = false;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.Transparent);
		setContentView(R.layout.activity_moderator_main);
				
		mText2 = (TextView) findViewById(R.id.textView2);
		
		initmoderatorTextDialog();
		
		sr = SpeechRecognizer.createSpeechRecognizer(this);
		sr.setRecognitionListener(new listener());

		recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		// recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,
		// Locale.ENGLISH.toString()); // 強制指定為英語
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, "com.kentec.milkbox");
		recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, true);

		// 開始接收聲音訊號時的身體動畫
		operator = (ImageView) findViewById(R.id.imageView2);
		operator.setBackgroundResource(R.anim.frame_animation);
		Object backgroundObject = operator.getBackground();
		animationDrawable = (AnimationDrawable) backgroundObject;
		animationDrawable.start();

		// 接收完聲音訊號後的動畫 -- 有異常發生時
		op2 = (ImageView) findViewById(R.id.imageView3);
		op2.setBackgroundResource(R.anim.op_animation);
		Object backgroundObject2 = op2.getBackground();
		OpAnimationDrawable = (AnimationDrawable) backgroundObject2;
		OpAnimationDrawable.start();
		op2.setVisibility(View.INVISIBLE);

		// 接收聲音訊號時顯示音頻訊號大小變化
		// operator.setImageResource(R.drawable.icon);
		mic = (ImageView) findViewById(R.id.imageViewIntroduction);
		// mic.setImageResource(R.drawable.v1);

		op2.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				runASR();
			}
		});

		operator.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				runASR();
			}
		});

		// *************************************************************************************************************************************************************
		// 2014/02/25 Add. Alvin
		//  新增在開始語音辨識收音前顯示對話框與唸出對話內容的功能，以便達到與使用者交談對話的情境
		//  String msg ........................................................................................ 要顯示的字串
		//  int showLocation ............................................................................ 要顯示的位置
		//  int delayTime ................................................................................... 自動關閉對話框時間
		//  String strSpeak ................................................................................ 要發聲的字串
		//  String strSpeakId ............................................................................ 發聲用的標示字串
		//  ArrayList<String> strArrayListSpeak ......................................... 多字串發聲用的 ArrayList
		// 
		// * msg or strSpeak 有傳入資料時表示要啟用對話框功能，可以顯示、發聲擇一或同時啟用
		// * 當 strSpeak 有傳入資料時 strSpeakId 也必須要有資料
		// * delayTime如果不存在或為0時系統會以3秒為預設值
		
		
		// 檢查是否有代入任何資料，沒有表示不需要啟用對話框功能
		Bundle bundle = getIntent().getExtras();		
		if (bundle != null)
		{
			// 將相關參數取出
			String msg = bundle.getString("msg"); // 要顯示的字串
			// int showLocation = bundle.getInt("showLocation"); // 要顯示的位置
			int delayTime = bundle.getInt("delayTime"); // 自動關閉對話框時間
			String strSpeak = bundle.getString("strSpeak"); // 要發聲的字串
			String strSpeakId = bundle.getString("strSpeakId"); // 發聲用的標示字串
			// ArrayList<String> strArrayListSpeak =
			// bundle.getStringArrayList("strArrayListSpeak"); // 多字串發聲用的
			// ArrayList

			if ((msg.length() > 0) && ((strSpeak.length() > 0) && (strSpeakId.length() > 0))) // 確定有傳入
			{
				chatMode = true;
				if (delayTime <= 0) delayTime = 2500;

				// 顯示對話框
				moderatorTextDialog(msg, -1);

				// 唸出資訊
				MilkBoxTTS.speak(strSpeak, strSpeakId);

				// 設定多久後自動關閉 Activity，每0.05秒更新一次倒數秒數數值
				new CountDownTimer(delayTime, 50)
				{

					@Override
					public void onFinish()
					{
						/*
						// 檢查如果 TV / Radio 是在開啟狀態則對播放器下暫停播放的命令
						if (CFG.TvActivitymMainVv != null)
						{
							if(!CFG.TvActivitymMainVv .isPlaying())
							{
								CFG.TvActivitymMainVv.start();
								Log.d("ModeratorMainActivity", ">>>>>>>>>>>>  onDestroy->CFG.TvActivitymMainVv.start()");
							}			
						}
						*/
						
						// Dialog 關閉前先將被暫停播放的媒體切回繼續播放
						if (CFG.TvActivitymMainVv != null)
						{
							Log.d("ModeratorMainActivity", ">>>>>>>>>>>>  onFinish()->CFG.TvActivitymMainVv.start()----A");
							if (!CFG.TvActivitymMainVv.isPlaying())
							{
								CFG.TvActivitymMainVv.start();
								Log.d("ModeratorMainActivity", ">>>>>>>>>>>>  onFinish()->CFG.TvActivitymMainVv.start()");
							}
						}

						// TODO Auto-generated method stub
						chatMode = false;
						runASR();
					}

					@Override
					public void onTick(long millisUntilFinished)
					{
						// TODO Auto-generated method stub
						mText2.setText(String.valueOf(millisUntilFinished));
					}					
				}.start();
			}
		}
		// *************************************************************************************************************************************************************
		else
		{
			runASR();
		}
	}

	class listener implements RecognitionListener
	{
		public void onReadyForSpeech(Bundle params)
		{
			mic.setVisibility(View.VISIBLE);
			operator.setVisibility(View.VISIBLE);
			op2.setVisibility(View.INVISIBLE);

			Log.d(TAG, "onReadyForSpeech");
			if(!chatMode) moderatorTextDialog("請說話 (" + (mIsListening ? "On)" : "Off)"), -1);
		}

		public void onBeginningOfSpeech()
		{
			Log.d(TAG, "onBeginningOfSpeech");
			if(!chatMode) moderatorTextDialog("開始收到語音了\n (" + (mIsListening ? "On)" : "Off)"), -1);
		}

		public void onRmsChanged(float rmsdB)
		{
			int volumeNo = ((int) Math.round(rmsdB) / 5);

			if (volumeNo < 0)
				volumeNo = 0;
			else if (volumeNo > 6) volumeNo = 6;
			// Log.d(TAG, "onRmsChanged: " + volumeNo );
			// mText2.setText(String.valueOf(volumeNo));

			mic.setImageResource(micmageIds[volumeNo]);
		}

		public void onBufferReceived(byte[] buffer)
		{
			// Log.d(TAG, "onBufferReceived");
			// mText2.setText("Buffer Received");
		}

		public void onEndOfSpeech()
		{
			Log.d(TAG, "onEndofSpeech");
			// mText.setText("已經停止收到語音，正在辨識中 ...  (" + (mIsListening ? "On)" :
			// "Off)"));
			// mText2.setText("End of Speech");
			if(!chatMode) moderatorTextDialog("已經停止收到語音，正在辨識中 ...  (" + (mIsListening ? "On)" : "Off)"), -1);
		}

		public void onError(int error)
		{
			mic.setVisibility(View.INVISIBLE);
			operator.setVisibility(View.INVISIBLE);
			op2.setVisibility(View.VISIBLE);
			Log.d(TAG, "error " + error);

			String str_ErrMsg;

			switch (error)
			{
			case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
				str_ErrMsg = "無語音輸入";
				break;

			case SpeechRecognizer.ERROR_NO_MATCH:
				str_ErrMsg = "無法辨識";
				break;

			case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
				str_ErrMsg = "網路連線逾時";
				break;

			case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
				str_ErrMsg = "系統繁忙, 請稍後再試";
				break;

			default:
				str_ErrMsg = String.valueOf(error);
			}

			sr.cancel();
			mIsListening = false;
			// mText.setText(str_ErrMsg + " (" + (mIsListening ? "On)" :
			// "Off)"));
			
			// Alvin : 2014.01/27  ... 唸出文字
			 if(MilkBoxTTS.TTS_status == TextToSpeech.SUCCESS)
	         {
	        	 MilkBoxTTS.ttsObj.speak(str_ErrMsg, TextToSpeech.QUEUE_FLUSH, null);
	         }
			 
			moderatorTextDialog(str_ErrMsg + " (" + (mIsListening ? "On)" : "Off)"), -1);
						
		}

		public void onResults(final Bundle results)
		{
			mIsListening = false; // Turn_Off flage

			String str = new String();
			Log.d(TAG, "onResults " + results);
			final ArrayList<String> data = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

			for (int i = 0; i < data.size(); i++)
			{
				Log.d(TAG, "result " + data.get(i));
				str += data.get(i) + "\n";
			}
			// **************************************************************************************************************

			if (data.size() > 0)
			{
				// 將辨識資料打包傳回前一個 Intent

				// String array[] = data.toArray(new String[data.size()]);
				Log.i("MASR", String.valueOf(data.size()));

				Bundle bundle = new Bundle();
				bundle.putString("asrResult", String.valueOf(data.size()));
				Intent intent = new Intent();
				// intent.putExtras(bundle);
				intent.putExtras(results);
				setResult(RESULT_OK, intent);

				setVisibilityStatus(-1);
				finish(); // close self
			}

			/*
			 * // mText.setText("辨識結果有 " + String.valueOf(data.size()) + "個  ("
			 * + // (mIsListening ? "On)" : "Off)"));
			 * moderatorTextDialog("辨識結果有 " + String.valueOf(data.size()) +
			 * "個  (" + (mIsListening ? "On)" : "Off)"), -1);
			 * mText2.setText(str);
			 * 
			 * new Handler().postDelayed(new Runnable() {
			 * 
			 * @Override public void run() { // TODO Auto-generated method stub
			 * if (data.size() > 0) { // 將辨識資料打包傳回前一個 Intent
			 * 
			 * // String array[] = data.toArray(new // String[data.size()]);
			 * Log.i("MASR", String.valueOf(data.size()));
			 * 
			 * Bundle bundle = new Bundle(); bundle.putString("asrResult",
			 * String.valueOf(data.size())); Intent intent = new Intent(); //
			 * intent.putExtras(bundle); intent.putExtras(results);
			 * setResult(RESULT_OK, intent);
			 * 
			 * setVisibilityStatus(-1); finish(); } } }, 5000);
			 */

			// ***************************************************************************************************************

		}

		public void onPartialResults(Bundle partialResults)
		{
			mIsListening = false;
			Log.d(TAG, "onPartialResults");
			// mText2.setText("PartialResults");
		}

		public void onEvent(int eventType, Bundle params)
		{
			Log.d(TAG, "onEvent " + eventType);
			// mText2.setText("onEvent " + eventType);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		if (v.getId() == R.id.imageViewIntroduction)
		{
			runASR();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_F2)
		{
			runASR();
		}
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	public void runASR()
	{
		if (mIsListening)
		{
			sr.cancel();
			mIsListening = false;
		}

		if (!mIsListening)
		{
			setVisibilityStatus(-1);
			mIsListening = true;
			sr.startListening(recognizerIntent);			
		}
	}

	@Override
	protected void onResume()
	{
		// TODO Auto-generated method stub

		// mText.setText("請稍後");
		if(!chatMode) moderatorTextDialog("請稍後", -1);

		super.onResume();
	}

	@Override
	protected void onDestroy()
	{
		// TODO Auto-generated method stub

		// mText.setText("onDestroy");
		Log.d("ModeratotMainActivity", ">>>  onDestroy() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
		
		// Add : 2014/02/26 ..... Alvin 在使用 SpeechRecognizer 的 Activity destroy 時 SpeechRecognizer 必須已經是被 destroy 的狀態，否則會引起 Error
		if(sr != null)
		{
			sr.destroy();
		}		

		super.onDestroy();
	}
	
	
	@Override
	protected void onPause()
	{
		// 檢查如果 TV / Radio 是在開啟狀態則對播放器下暫停播放的命令
		if (CFG.TvActivitymMainVv != null)
		{
			if (!CFG.TvActivitymMainVv.isPlaying())
			{
				CFG.TvActivitymMainVv.start();
				Log.d("ModeratorMainActivity", ">>>>>>>>>>>>  onPause->CFG.TvActivitymMainVv.start()");
			}
		}
		
		super.onPause();
		
		Log.d("ModeratotMainActivity", ">>>  onPause() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
	}

	@Override
	protected void onPostResume()
	{
		// TODO Auto-generated method stub
		super.onPostResume();
		
		Log.d("ModeratotMainActivity", ">>>  onPostResume() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
	}

	@Override
	protected void onRestart()
	{
		// TODO Auto-generated method stub
		super.onRestart();
		
		Log.d("ModeratotMainActivity", ">>>  onRestart() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
	}

	@Override
	protected void onStart()
	{
		// 檢查如果 TV / Radio 是在開啟狀態則對播放器下暫停播放的命令
		if (CFG.TvActivitymMainVv != null)
		{
			if(CFG.TvActivitymMainVv.isPlaying())
			{
				CFG.TvActivitymMainVv.pause();
				Log.d("ModeratorMainActivity", ">>>>>>>>>>>>  onStart->CFG.TvActivitymMainVv.pause()");
			}
		}

		
		// TODO Auto-generated method stub
		super.onStart();
		
		Log.d("ModeratotMainActivity", ">>>  onStart() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
	}

	@Override
	protected void onStop()
	{
		// TODO Auto-generated method stub
		super.onStop();
		
		Log.d("ModeratotMainActivity", ">>>  onStop() <<<");
		Log.d("ModeratotMainActivity", ">>>  CFG.AUTO_RECALL_ASR " + (CFG.AUTO_RECALL_ASR ? "true" : "false"));
		Log.d("ModeratotMainActivity", ">>>  ASR_IS_RUN " + (CFG.ASR_IS_RUN ? "true" : "false"));
	}

	/**
	 * Moderator Text dialog
	 * 
	 * String strText ... 要顯示的字串內容 int typeIndex ... 要使用的對話框編號， -1 表示隨機決定
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/26 上午10:22:43
	 */
	// public void moderatorTextDialog(String strText, int typeIndex)
	public void moderatorTextDialog(String strText, int typeIndex)
	{
		int indexNo = typeIndex;

		if (typeIndex == -1)
		{
			// 以亂數決定要從那個位置顯示
			indexNo = (int) (Math.random() * mModeratorTextDialogAdapterList.size());
		}

		mModeratorTextDialogAdapterList.get(indexNo).setText(strText);
		setVisibilityStatus(indexNo);		
	}

	/**
	 * 
	 * @param mAdapterList
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/26 下午4:21:51
	 */
	public void initmoderatorTextDialog()
	{
		mModeratorTextDialogAdapterList = new ArrayList<ModeratorTextDialogAdapter>();

		mModeratorTextDialogAdapterList.add(new ModeratorTextDialogAdapter((TextView) findViewById(R.id.textView1))); // 中間的對話框
		mModeratorTextDialogAdapterList.add(new ModeratorTextDialogAdapter((TextView) findViewById(R.id.textView3))); // 右邊的對話框
		mModeratorTextDialogAdapterList.add(new ModeratorTextDialogAdapter((TextView) findViewById(R.id.textView4))); // 左邊的對話框
	}

	/**
	 * 顯示指定的 frameLayout 並將其它的 frameLayout 自動隱藏
	 * 
	 * @param indexNo
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/27 上午10:43:18
	 */
	public void setVisibilityStatus(int indexNo)
	{
		for (int i = 0; i < mModeratorTextDialogAdapterList.size(); i++)
		{
			if ((i != indexNo) && (mModeratorTextDialogAdapterList.get(i).getTextView().getVisibility() == View.VISIBLE))
			{
				mModeratorTextDialogAdapterList.get(i).getTextView().setVisibility(View.INVISIBLE);
			}
			else if (i == indexNo)
			{
				mModeratorTextDialogAdapterList.get(i).getTextView().setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub

		mText2.setText(String.valueOf(keyCode));
		if (keyCode == KeyEvent.KEYCODE_F2)
		{
			return false;
		}

		return super.onKeyUp(keyCode, event);

	}
}
