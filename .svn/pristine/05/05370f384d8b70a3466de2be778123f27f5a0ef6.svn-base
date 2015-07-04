package com.kentec.milkbox.tv;

import java.util.ArrayList;
import java.util.Collections;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.TVASRCreater;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.tv.api.TvApiParser;
import com.kentec.milkbox.tv.data.TvChannel;

public class TvPlayerActivity extends ASRActivity
{
	private static final String TAG = "TvPlayerActivity";
	private static final long VOLUME_TIMEOUT = 2000;
	private static final long NAMETV_TIMEOUT = 10000;

	// private Context mContext;
	private AudioManager mAudioManager;
	private ProgressDialog mProgressDialog;

	private TextView mNameTv;
	private TextView mTvHint;
	private VideoView mMainVv;
	private SeekBar mVolumeSb;

	private int mScreenHeight;
	private int mScreenWidth;

	private int mCurrentChannel; // 目前正在播放的頻道
	private int mLastChannel = -1; // Add : Alvin ..... 前一個播放的頻道，用來記憶上一個頻道位置， -1 表示還未記憶最後的頻道位置
	private int mMaxVolume;
	private int mCurrentVolume;
	private int mMuteVolume; // Add Alvin: 用來記錄進入靜音前的音量大小

	private long mVolumeClickTimeId;

	private ArrayList<TvChannel> mChannelList;

	static public String Ctrl_Code;

	public float upX, upY, downX, downY;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tv_player);

		// mContext = this;
		mProgressDialog = new ProgressDialog(this);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		mMainVv = (VideoView) findViewById(R.id.mainVv);
		// CFG.TvActivitymMainVv = mMainVv; // 將 Radio 播放器名稱記憶起來
		mNameTv = (TextView) findViewById(R.id.nameTv);
		mTvHint = (TextView) findViewById(R.id.tvHint);		
		
		// Add : Alvin ... 設置監播放完成聽器，在撥完後自己重播
		mMainVv.setOnCompletionListener(new OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mp)
			{
				// TODO Auto-generated method stub
				mMainVv.start();
			}
		});

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		mScreenHeight = metrics.heightPixels;

		mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		mVolumeSb = (SeekBar) findViewById(R.id.volumeSb);
		mVolumeSb.setMax(mMaxVolume);
		mVolumeSb.setProgress(mCurrentVolume);

		LayoutParams params = mVolumeSb.getLayoutParams();
		params.width = (int) (mScreenWidth * 0.2);
		params.height = (int) (mScreenHeight * 0.03);
		mVolumeSb.setLayoutParams(params);
		mVolumeSb.setVisibility(View.GONE);

		// set mNameTv font
		mNameTv.setTextSize(40);
		mNameTv.setTextColor(0xff00ff00);

		// 建立 TV Player Activity 所屬的 Voice Command List Class 		
		new TVASRCreater().init(this);
		
		BroadcastReceiver mReceiver = new BroadcastReceiver()
		{
			public void onReceive(Context context, Intent intent)
			{
				// String action = intent.getAction();

				Ctrl_Code = intent.getStringExtra("TV_Command");

				if (Ctrl_Code != null)
				{					
					doTvChangeChannel(Ctrl_Code);
					/*
					mCurrentChannel = Integer.parseInt(Ctrl_Code) - 1;
					if (mCurrentChannel <= mChannelList.size())
						playChannel(mChannelList.get(mCurrentChannel));
					else
						playChannel(mChannelList.get(0));//*/
				}
			}
		};

		IntentFilter intentFilter = new IntentFilter("android.intent.action.TV");
		registerReceiver(mReceiver, intentFilter);// */
	}

	@Override
	protected void onResume()
	{
		super.onResume();

		if (mChannelList == null || mChannelList.size() == 0) new LoadChannelTask().execute();
	}

	@Override
	protected void onStart()
	{
		super.onStart();

		mCurrentChannel = super.getSP().getInt(CFG.PREF_TV_CURRENT_CHANNEL, 0);
	}

	private void checkCurrentChannel()
	{
		if (mCurrentChannel >= mChannelList.size())
		{
			mCurrentChannel = 0;
		}
	}

	@Override
	protected void onStop()
	{
		super.onStop();

		// 將目前播放的頻道位置記憶下來，當成下次進入 TV 時的預設播放頻道
		super.getSP().edit().putInt(CFG.PREF_TV_CURRENT_CHANNEL, mCurrentChannel).commit();
		
		// 清除 TV 播放器名稱記憶
		CFG.TvActivitymMainVv  = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		switch (event.getKeyCode())
		{
		case KeyEvent.KEYCODE_BACK:
			gotoMainActivity();
			break;

		case KeyEvent.KEYCODE_DPAD_UP:
//			doChannelUp(false);
			doChannelDown(false); // andywu 2014.03.05 修改操作方式符合使用者的習慣
			break;

		case KeyEvent.KEYCODE_DPAD_DOWN:
//			doChannelDown(false);
			doChannelUp(false); // andywu 2014.03.05 修改操作方式符合使用者的習慣
			break;

		case KeyEvent.KEYCODE_DPAD_RIGHT:
			doVolumnUp();
			break;

		case KeyEvent.KEYCODE_DPAD_LEFT:
			doVolumnDown();
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}

		return true;
	}

	public void doVolumnUp()
	{
		mCurrentVolume+=2;
		if ( mCurrentVolume >= mMaxVolume ) mCurrentVolume = mMaxVolume;
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
		mVolumeSb.setVisibility(View.VISIBLE);
		mVolumeSb.setProgress(mCurrentVolume);

		mVolumeClickTimeId = System.currentTimeMillis();
		new HideVolume(mVolumeClickTimeId).execute();
				
		/*
		
		if ( mCurrentVolume <= mMaxVolume )
		{
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mVolumeSb.setVisibility(View.VISIBLE);
			mVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		} else if(mCurrentVolume == mMaxVolume+1) {
			mCurrentVolume = mMaxVolume;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mVolumeSb.setVisibility(View.VISIBLE);
			mVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
			
		}else {
			mCurrentVolume-=2;
		}
		*/
	}

	public void doVolumnDown()
	{
		mCurrentVolume-=2;
		
		if(mCurrentVolume < 0) mCurrentVolume = 0;
		
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
		mVolumeSb.setVisibility(View.VISIBLE);
		mVolumeSb.setProgress(mCurrentVolume);

		mVolumeClickTimeId = System.currentTimeMillis();
		new HideVolume(mVolumeClickTimeId).execute();
		
		/*
		if (mCurrentVolume >= 0)
		{
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mVolumeSb.setVisibility(View.VISIBLE);
			mVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else if(mCurrentVolume == -1) {
			mCurrentVolume=0;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mVolumeSb.setVisibility(View.VISIBLE);
			mVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
			
		}else {
			mCurrentVolume+=2;
		}
		*/		
	}
	
	public void doTvChangeVolume(String strValue)
	{
		int volumn = (Integer.parseInt(strValue) * 15) / 100;
		if (volumn >= 0 && volumn <= 15)
		{
			mCurrentVolume = volumn;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mVolumeSb.setVisibility(View.VISIBLE);
			mVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}
		else
		{
			MilkBoxTTS.speak("Please say change volume 1 to 100.", "doTvChangeVolumn");
		}
	}

	/**
	 * 
	 * @param speakCNo .... true = 變換頻道時同時唸出頻道號碼
	 *                                            false = 變換頻道時不會同時唸出頻道號碼
	 *
	 * @author Alvin Huang
	 * @date 2014/2/18 下午2:31:26 ... Add speakCNo 參數
	 *               2014/2/24 ......................... 增加對最後頻道位置的處理 
	 */
	public void doChannelUp(boolean speakCNo)
	{
		// 將目前的頻道位置記憶起來
		mLastChannel = mCurrentChannel;
		
		mCurrentChannel = (mCurrentChannel - 1);
		if (mCurrentChannel < 0) mCurrentChannel = mChannelList.size() - 1;
		
		// 唸出資訊
		if(speakCNo) MilkBoxTTS.speak("Chang To Channel " + (mCurrentChannel + 1), "doTvChangeChannel");
		
		playChannel(mChannelList.get(mCurrentChannel));
	}

	/**
	 * 
	 * @param speakCNo .... true = 變換頻道時同時唸出頻道號碼
	 *                                            false = 變換頻道時不會同時唸出頻道號碼
	 *                                            
	 * @author Alvin Huang
	 * @date 2014/2/18 下午2:33:38
	 *                2014/2/24 ......................... 增加對最後頻道位置的處理
	 */
	public void doChannelDown(boolean speakCNo)
	{
		// 將目前的頻道位置記憶起來
		mLastChannel = mCurrentChannel;

		
		mCurrentChannel = (mCurrentChannel + 1) % mChannelList.size();
		
		// 唸出資訊
		if(speakCNo) MilkBoxTTS.speak("Chang To Channel " + (mCurrentChannel + 1), "doTvChangeChannel");
				
		playChannel(mChannelList.get(mCurrentChannel));		
	}

	class LoadChannelTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode
	{

		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... arg0)
		{
			try
			{
				if (!NetworkUtil.haveInternet(mActivity))
				{
					return NETWORK_ERROR;
				}

				String response = RestClient.get(CFG.API_TV);
				RestResult rr = TvApiParser.parserRestResult(response);
				if (rr.getCode() != OK)
				{
					return ERROR;
				}

				mChannelList = TvApiParser.getChannelArray(rr.getData());
				if (mChannelList.size() == 0)
				{
					return NO_DATA;
				}

				return OK;

			}
			catch (Exception e)
			{
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			mProgressDialog.dismiss();

			if (result == OK)
			{
				checkCurrentChannel();

				TvChannel channel = mChannelList.get(mCurrentChannel);
				playChannel(channel);
			}

			if (result == NO_DATA)
			{
				// Toast.makeText(mContext, getString(R.string.no_data),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.no_data);
			}

			if (result == ERROR)
			{
				// Toast.makeText(mContext, getString(R.string.error),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.error);
			}

			if (result == NETWORK_ERROR)
			{
				// Toast.makeText(mContext, getString(R.string.error),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.error_internet);
			}

		}
	}

	class HideVolume extends AsyncTask<String, Void, Integer> implements AsyncTaskCode
	{

		final long id;

		HideVolume(long id)
		{
			this.id = id;
		}

		@Override
		protected Integer doInBackground(String... arg0)
		{
			try
			{
				Thread.sleep(VOLUME_TIMEOUT);
			}
			catch (Exception e)
			{
				Log.e(TAG, e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result)
		{
			if (id == mVolumeClickTimeId)
			{
				mVolumeSb.setVisibility(View.GONE);
			}
		}
	}

	private void playChannel(TvChannel channel)
	{
		// mNameTv.setText(channel.getName() + "\n" + ((mLastChannel < 0) ? "-1" : mChannelList.get(mLastChannel).getName())); // show Volume info. for test @Alvin
		mNameTv.setText(channel.getName());
		mTvHint.setText(" Press 'M' for Voice input ");
		mMainVv.stopPlayback();
		mMainVv.setVideoURI(Uri.parse(channel.getUrl()));
		mMainVv.requestFocus();
		mMainVv.start();

		hideNameTv();
	}

	private void hideNameTv()
	{
		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				mNameTv.setText("");
				mTvHint.setText("");
			}
		}, NAMETV_TIMEOUT);
	}

	/**
	 * Add : Alvin 為避免於 TV 播放中因 TV 節目本身的音量造成語音辨識收音干擾，所以在呼叫 ASR 之前先記住目前的音量值後將音量降低
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if ((keyCode == KeyEvent.KEYCODE_F2) && (mCurrentVolume >= 2))
		{
			// 呼叫 ASR 前先將 TV 的聲音降低以免干擾到麥克風，因為為去變動到 mCurrentVolume 而直接將音量降低
			//  行程 mCurrentVolume 記住了改變前的音量大小
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, 0);
			Log.i("Alvin", "TvPlayerActivity ---- onKeyUp Change Volume to 1 ****");
			
			// 將 Radio 播放器名稱記憶起來，因為播放器與 TTS 都是使用同一個 Audio channel 所以無法單獨調整音量，只能暫停播放影片
			CFG.TvActivitymMainVv = mMainVv;
		}

		// 接續原有的 F2 功能
		// 因為 TvPlayerActivity extends ASRActivity 所以會上傳給 ASRActivity
		Log.i("Alvin", "TvPlayerActivity ---- onKeyUp return [F2] to super.onKeyUp");
		return super.onKeyUp(keyCode, event);
	}

	/**
	 * Add : Alvin
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);

		Log.i("hATM", "onActivityResult at TvPlayerActivity ....");
		// this.setCurrentVolume();
		// Alvin 2013/12/30
		// 因為不希望語音辨識結束後顯示音量條所以改成直接設定回原有的音量而不呼叫 API
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);

		// Dialog 關閉前先將被暫停播放的媒體切回繼續播放
		if(!mMainVv.isPlaying())
		{
			mMainVv.start();
			Log.d("TvPlayerActivity", ">>>>>>>>>>>>  onActivityResult->mMainVv.start()");	
		}
	}

	/**
	 * 
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/16 下午5:29:21
	 */
	public void doMaxVolume()
	{
		if (mCurrentVolume < mMaxVolume)
		{
			mCurrentVolume = mMaxVolume;
			this.setCurrentVolume();
			
			// for Alvin TEST
			Toast.makeText(this, mCurrentVolume +" / " + mMaxVolume , Toast.LENGTH_SHORT).show();
		}
	}

	/**
	 * 
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/16 下午5:30:22
	 */
	public void doMuteVolume()
	{
		if (mCurrentVolume > 0)
		{
			Log.i("hATM", "doMuteVolume   at TvPlayerActivity ....");
			this.mMuteVolume = this.mCurrentVolume;
			this.mCurrentVolume = 0;
			this.setCurrentVolume();
		}
	}

	/**
	 * 
	 * 
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/17 下午4:54:18
	 */
	public void doResetMute()
	{
		Log.i("hATM", "doResetMute at TvPlayerActivity ....");
		this.mCurrentVolume = this.mMuteVolume;
		this.setCurrentVolume();
	}

	/**
	 * 
	 * @param volumeValue
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/16 下午5:38:14
	 */
	public void setCurrentVolume()
	{
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
		mVolumeSb.setVisibility(View.VISIBLE);
		mVolumeSb.setProgress(mCurrentVolume);

		mVolumeClickTimeId = System.currentTimeMillis();
		new HideVolume(mVolumeClickTimeId).execute();
	}
	
	/**
	 * 
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/2/12 下午6:00:41
	 */
	public void doTvHelpMenu()
	{
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值

		/*
		 "Help", "Command List", "Change Channel", "Channel Up", "Channel Down", "Mute", "Volume Up", "Volume Down", "Channel 1~ 999", "Change Volume", "Volume Change", "Main Menu", "TV Off"
		 */
		
		
		// Show message

		String strMsg[] = { "What can I say", 
//				                          "Help", 
//				                          "Command List", 
//				                          "Change Channel", 
				                          "Channel Up", 
				                          "Channel Down", 
				                          "Mute", 
				                          "Volume Up", 
				                          "Volume Down", 
				                          "Channel 1 to ninety nine", 
				                          "Change Volume 0 to one hundred", 
//				                          "Volume Change", 
				                          "Main Menu", 
//				                          "TV Off"
				                          };
		
		/**
		* @author andywu
		* @date 2014.02.21
		* 修改呈現方式
		* 
		* 利用HTML排版
		* <big> 放大字型
		* <sub> 下標
		* <small> 縮小字型
		* <b> 粗體
		* &nbsp; 空白字元
		*/
		String dialogStr = "<big>● What can I say</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Command List</b>&nbsp;&nbsp;or&nbsp;&nbsp;<b>Help</b></small></small></sub><br>"
				 + "<big>● Channel Up</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Channel Down</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Mute</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Volume Up</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Volume Down</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Channel 1~ 99</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 + "<big>● Change Volume 0~ 100</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Volume Change 0~ 100</b></small></small></sub><br>"
				 + "<big>● Main Menu</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>TV Off</b></small></small></sub><br>"
//				 + "<big>● TV Off</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
				 ;
						
		moderatorDialog(dialogStr, indexNo + 2, 15000);

		/*
		if (mCurrentVolume >= 2)
		{
			// 呼叫 ASR 前先將 TV 的聲音降低以免干擾到麥克風，因為為去變動到 mCurrentVolume 而直接將音量降低
			//  行程 mCurrentVolume 記住了改變前的音量大小
			// mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, 0);
			// Log.i("Alvin", "TvPlayerActivity ---- doTvHelpMenu Change volume to 1 ****");
						
			// 因為播放TV使用 VideoView，這與 TTS 都屬於 STREAM_MUSIC 所以無法單獨控制兩者的音量為不同大小，因此改以 pause 暫停播放的方式來避免干擾 TTS
			// mMainVv.pause();
		}
		*/
		
		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strMsg);
		MilkBoxTTS.speak(speakStrs, "echoWhatCanISay");		
	}

	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/2/13 上午9:37:22
     *               2014/2/24 ......................... 增加對最後頻道位置的處理
	 */
	public void doTvChangeChannel(String strValue)
	{
		// TODO Auto-generated method stub
		// Log.i("TvPlayerActivity", "=== strValue : " + strValue + " ===");
		
		if( Integer.valueOf(strValue) > mChannelList.size() )
		{
			// 唸出資訊
			MilkBoxTTS.speak("I can't find this Channel.", "doTvChangeChannel");
		}
		else
		{			
			
			mMainVv.start();
			
			// 唸出資訊
			MilkBoxTTS.speak("Chang To Channel " + strValue, "doTvChangeChannel");
			
			// 將目前的頻道位置記憶起來
			mLastChannel = mCurrentChannel;
			
			mCurrentChannel = Integer.valueOf(strValue) - 1;
			playChannel(mChannelList.get(mCurrentChannel));
		}				
	}

	/**
	 * 
	 *
	 * @author Alvin Huang
	 * @date 2014/2/14 下午1:43:11
	 */
	public void doTvLastChannel()
	{
		int tmpChannel = mLastChannel;

		if (mLastChannel >= 0) // mLastChannel == -1 表示還未記憶過最後放頻道
		{
			// 將目前的頻道位置記憶起來
			mLastChannel = mCurrentChannel;

			// 切換頻道位置到最後播放頻道的位置
			mCurrentChannel = tmpChannel;

			// 唸出資訊
			MilkBoxTTS.speak("Chang To Channel " + (mCurrentChannel + 1), "doTvLastChannel");

			playChannel(mChannelList.get(mCurrentChannel));
		}
	}
}
