/**
 * 
 */
package com.kentec.milkbox.moderator;

import java.util.ArrayList;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.CheckTimeOutActivity;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author Alvin Huang
 * 
 */
public class ModeratorDialogActivity extends CheckTimeOutActivity
{
	private final int ALL_Hide = 0;
	private final int LEFT_Show = 1;
	private final int RIGHT_Show = 2;
	private final int LEFT_FullScr_Show = 3;
	private final int RIGHT_FullScr_Show = 4;
	private ImageView im1, im2;
	private TextView tv1, tv2, tv3, tv4, tv5_forLeftView, tv6_forRightView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setTheme(R.style.Transparent);
		setContentView(R.layout.activity_moderator_dialog); 

		// 檢查如果 TV / Radio 是在開啟狀態則對播放器下暫停播放的命令
		if(CFG.TvActivitymMainVv != null)
		{
			if(CFG.TvActivitymMainVv.isPlaying())
			{
				CFG.TvActivitymMainVv.pause();
				Log.d("ModeratorDialogActivity", ">>>>>>>>>>>>  onCreate->CFG.TvActivitymMainVv.pause()");
			}
		}
		
		// 將相關參數取出
		Bundle bundle = getIntent().getExtras();
		String msg = bundle.getString("msg"); // 要顯示的字串
		final int showLocation = bundle.getInt("showLocation"); // 要顯示的位置
		int       delayTime = bundle.getInt("delayTime"); // 自動關閉對話框時間
		String strSpeak = bundle.getString("strSpeak"); // 要發聲的字串
		String strSpeakId = bundle.getString("strSpeakId"); // 發聲用的標示字串
		ArrayList<String> strArrayListSpeak = bundle.getStringArrayList("strArrayListSpeak");  // 多字串發聲用的 ArrayList
		
		im1 = (ImageView) findViewById(R.id.imageViewIntroduction);
		im2 = (ImageView) findViewById(R.id.imageView2);
		tv1 = (TextView) findViewById(R.id.textView1);
		tv2 = (TextView) findViewById(R.id.textView2);
		tv3 = (TextView) findViewById(R.id.textView3);
		tv4 = (TextView) findViewById(R.id.textView4);
		tv5_forLeftView = (TextView) findViewById(R.id.textView5);
		tv6_forRightView = (TextView) findViewById(R.id.textView6);
		im1.setImageResource(R.drawable.moderator_left);
		im2.setImageResource(R.drawable.moderator_right);		

		// 設定相關的 View
		setShowGroup(showLocation, msg);		

		if((strSpeak != null) && (strSpeak.length() > 0))
		{
			// 唸出資訊
			MilkBoxTTS.speak(strSpeak, strSpeakId);
			// Log.d("ModeratorDialogActivity->onCreate()", ">>>> MilkBoxTTS.speak() ... ");
		}
		else if((strArrayListSpeak != null) && (strArrayListSpeak.size() > 0))
		{
			MilkBoxTTS.speak(strArrayListSpeak, strSpeakId);
		}

		// 設定多久後自動關閉 Activity，每0.05秒更新一次倒數秒數數值，如果設定值 <0 則會預設為10秒
		new CountDownTimer((delayTime >= 0) ? delayTime : 10000, 50)
		{
			public void onTick(long millisUntilFinished)
			{
				if ((showLocation == LEFT_Show) || (showLocation == LEFT_FullScr_Show))
				{
					tv5_forLeftView.setText(String.valueOf(millisUntilFinished));
				}
				else
				{
					tv6_forRightView.setText(String.valueOf(millisUntilFinished));
				}				
			}

			public void onFinish()
			{
				Intent intent = new Intent();
				setResult(RESULT_OK, intent);
				/**
				* @author andywu
				* @date 2014.03.10
				* 改回在onDestroy處理，在結束後也會呼叫finish
				*/
				/*
				// Dialog 關閉前先將被暫停播放的媒體切回繼續播放
				if(CFG.TvActivitymMainVv != null)
				{
					if(!CFG.TvActivitymMainVv.isPlaying())
					{
						CFG.TvActivitymMainVv.start();
						Log.d("ModeratorDialogActivity", ">>>>>>>>>>>>  onFinish()->CFG.TvActivitymMainVv.start()");	
					}					
				}
				*/
				finish();
				Log.d("ModeratorDialogActivity", ">>>  ModeratorDialog is finish <<<");
			}
		}.start();
	}	
	
	/**
	 *  2014/02/25 ... Alvin : 增加離開 Activity 時強制關閉發聲中的 TTS
	 */
	@Override
	protected void onDestroy()
	{
		// 這是用來啟動 Idle Time 計時機制，在 onPause() 的 super.onPause() 時因為 super.onPause() 會是 CheckTimeOutActivity.onPause() (因為 ASRActivity 有繼承到 CheckTimeOutActivity)
		// 表示會因為 Android Activity Lifecycle 機制而被呼叫到  CheckTimeOutActivity.onPause()，這裡面又正好有呼叫 stopCheckTimeOut() 會將 Idle Time 計時機制停止，因此必須在這邊重新
		// 將 Idle Time 計時機制啟動
//		doCheckTimeOut();
//		Log.d("ModeratorDialogActivity", ">>>  onDestroy->call doCheckTimeOut() <<<");
		
//		if(MilkBoxTTS.isMultiTTS())
//		{
//			MilkBoxTTS.setNowAbortSpeak();
//		}
//		
//		MilkBoxTTS.ttsObj.stop(); // 清除 TTS 正在發聲與 Queue 中等待發聲的要求，立即停止發聲		

		/**
		* @author andywu
		* @date 2014.03.10
		* 改回在onDestroy處理，CountDownTimer在結束後也會呼叫finish
		*/
		// Dialog 關閉前先將被暫停播放的媒體切回繼續播放
		if(CFG.TvActivitymMainVv != null)
		{
			if(!CFG.TvActivitymMainVv.isPlaying())
			{
			CFG.TvActivitymMainVv.start();
			Log.d("ModeratorDialogActivity", ">>>>>>>>>>>>  onDestroy->CFG.TvActivitymMainVv.start()");
			}
		}

		// IntroductionActivity.this.mTvPlayerActivity
				
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d("ModeratorDialogActivity", ">>>  onDestroy->ModeratorDialog is Destroy <<<");
	}
	
	/**
	 * 2014/02/25 ... Alvin : 增加按下 [OK] 鍵離開 Activity
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_ENTER) this.finish();
		
		return super.onKeyUp(keyCode, event);
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent)
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent event)
	{
		if ((event.getKeyCode() == KeyEvent.KEYCODE_BACK) || (event.getKeyCode() == KeyEvent.KEYCODE_ESCAPE))
		{
			// CFG.isModeratorDialogAbout = true;
			// Log.d("ModeratorDialogActivity", ">>>>>>>>>>>>  dispatchKeyEvent()->CFG.IntroductionActivityActive is" + (CFG.isModeratorDialogAbout ? "True ***" : "False ***"));
			
			if(MilkBoxTTS.isMultiTTS())
			{
				Log.d("MilkBoxTTS-ttsMultiSpeak", "ModeratorDialogActivity->dispatchKeyEvent() .... 1 >>> ");
				
			}
			MilkBoxTTS.setNowAbortSpeak(); // MilkBoxTTS 會立即送出命令讓 TTS 馬上停止發音並清除緩衝區中帶執行的發音命令
			
			// MilkBoxTTS.ttsObj.stop(); // 清除 TTS 正在發聲與 Queue 中等待發聲的要求，立即停止發聲
		}
		
		return super.dispatchKeyEvent(event);
	}

	/**
	 * @param lEFT_Show2
	 * @param msg
	 *
	 * @author Alvin Huang
	 * @date 2014/2/7 下午4:40:57
	 */
	private void setShowGroup(int gNo, String msg)	
	{		
		if (gNo == ALL_Hide) // 全部隱藏
		{
			tv3.setVisibility(View.INVISIBLE);
			tv4.setVisibility(View.INVISIBLE);
			
			im1.setVisibility(View.INVISIBLE);
			tv1.setVisibility(View.INVISIBLE);

			im2.setVisibility(View.INVISIBLE);
			tv2.setVisibility(View.INVISIBLE);
			
			tv5_forLeftView.setVisibility(View.INVISIBLE);
			tv6_forRightView.setVisibility(View.INVISIBLE);
		}
		else if ((gNo == LEFT_Show) || (gNo == LEFT_FullScr_Show))
		{
			im1.setVisibility(View.VISIBLE);
			if(gNo == LEFT_Show)
			{
				// tv1.setText(Html.fromHtml(msg)); // andywu 2014.02.20 利用HTML排版
				tv1.setText(msg);
				tv1.setVisibility(View.VISIBLE);
				tv3.setVisibility(View.INVISIBLE);
			}
			else
			{
				tv3.setText(Html.fromHtml(msg)); // andywu 2014.02.20 利用HTML排版
//				tv3.setText(msg);
				tv3.setVisibility(View.VISIBLE);
				tv1.setVisibility(View.INVISIBLE);
			}
									
			im2.setVisibility(View.INVISIBLE);
			tv2.setVisibility(View.INVISIBLE);
			tv4.setVisibility(View.INVISIBLE);
			tv5_forLeftView.setVisibility(View.VISIBLE);
			tv6_forRightView.setVisibility(View.INVISIBLE);
		}
		else if ((gNo == RIGHT_Show) || (gNo == RIGHT_FullScr_Show))
		{
			im2.setVisibility(View.VISIBLE);
			if(gNo == RIGHT_Show)
			{
				// tv4.setText(Html.fromHtml(msg)); // andywu 2014.02.20 利用HTML排版
				tv2.setText(msg);
				tv2.setVisibility(View.VISIBLE);
				tv4.setVisibility(View.INVISIBLE);
			}
			else
			{
				tv4.setText(Html.fromHtml(msg)); // andywu 2014.02.20 利用HTML排版
//				tv4.setText(msg);
				tv4.setVisibility(View.VISIBLE);
				tv2.setVisibility(View.INVISIBLE);
			}
			
			im1.setVisibility(View.INVISIBLE);
			tv1.setVisibility(View.INVISIBLE);
			tv3.setVisibility(View.INVISIBLE);
			tv5_forLeftView.setVisibility(View.INVISIBLE);	
			tv6_forRightView.setVisibility(View.VISIBLE);
		}
	}
}
