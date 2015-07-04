package com.kentec.milkbox.radio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.R;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.RadioASRCreater;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RestClient;
import com.kentec.milkbox.data.RestResult;
import com.kentec.milkbox.homedine.utils.NetworkUtil;
import com.kentec.milkbox.radio.api.RadioApiParser;
import com.kentec.milkbox.radio.data.RadioChannel;

public class RadioPlayerActivity extends ASRActivity {

	private static final String TAG = "RadioPlayerActivity";
	private static final long VOLUME_TIMEOUT = 2000;
	private static final long NAMETV_TIMEOUT = 10000;

	// private Context mContext;
	private AudioManager mAudioManager;
	private ProgressDialog mProgressDialog;

	private TextView mRadioNameTv;
	private TextView mRadioData ;
	private TextView mRadioTitle ;
	private VideoView mRadioMainVv;
	private SeekBar mRadioVolumeSb;
	private ImageView mRadioBg;
	private ImageView mRadioAnim ;
	private ImageView mRadioModerator ;
	
	private int mScreenHeight;
	private int mScreenWidth;

	private int mCurrentChannel;
	private int mMaxVolume;
	private int mCurrentVolume;
	private int mMuteVolume; // Add Alvin: 用來記錄進入靜音前的音量大小
	private int mBgNumber;// Add Andy: 紀錄目前背景是哪一套
	private long mVolumeClickTimeId;

	private ArrayList<RadioChannel> mChannelList;
	
	private AnimationDrawable animationDrawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);//System.out.print("andy:onCreate");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.radio_player);

		// mContext = this;
		mProgressDialog = new ProgressDialog(this);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

		mRadioMainVv = (VideoView) findViewById(R.id.radioMainVv);
		// CFG.TvActivitymMainVv = mRadioMainVv; // 將 Radio 播放器名稱記憶起來
		mRadioNameTv = (TextView) findViewById(R.id.radioNameTv);
		mRadioData = (TextView) findViewById(R.id.radioData);
		mRadioTitle = (TextView) findViewById(R.id.radioTitle);
		mRadioBg = (ImageView) findViewById(R.id.radioBg);
		mRadioAnim = (ImageView) findViewById(R.id.radioAnim);
		mRadioModerator = (ImageView) findViewById(R.id.radioModerator);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		mScreenWidth = metrics.widthPixels;
		mScreenHeight = metrics.heightPixels;

		mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		mCurrentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

		mRadioVolumeSb = (SeekBar) findViewById(R.id.radioVolumeSb);
		mRadioVolumeSb.setMax(mMaxVolume);
		mRadioVolumeSb.setProgress(mCurrentVolume);

		LayoutParams params = mRadioVolumeSb.getLayoutParams();
		params.width = (int) (mScreenWidth * 0.2);
		params.height = (int) (mScreenHeight * 0.03);
		mRadioVolumeSb.setLayoutParams(params);
		mRadioVolumeSb.setVisibility(View.GONE);

		// set mNameTv font
		mRadioNameTv.setTextSize(40);
		mRadioNameTv.setTextColor(0xff00ff00);

		new RadioASRCreater().init(this);
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (mChannelList == null || mChannelList.size() == 0)
			new LoadChannelTask().execute();
	}

	@Override
	protected void onStart() {
		super.onStart();

		mCurrentChannel = super.getSP().getInt(CFG.PREF_RADIO_CURRENT_CHANNEL, 0);
	}

	private void checkCurrentChannel() {
		if (mCurrentChannel >= mChannelList.size()) {
			mCurrentChannel = 0;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();

		super.getSP().edit().putInt(CFG.PREF_RADIO_CURRENT_CHANNEL, mCurrentChannel).commit();
	
		// 清除 Radio 播放器名稱記憶 
		CFG.TvActivitymMainVv  = null;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch(event.getKeyCode()){
			case KeyEvent.KEYCODE_BACK:
				gotoMainActivity();
				break;

			case KeyEvent.KEYCODE_DPAD_UP:
				doChannelUp();
				break;

			case KeyEvent.KEYCODE_DPAD_DOWN:
				doChannelDown();
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

	public void doVolumnUp() {
		mCurrentVolume+=2;
		if (mCurrentVolume <= mMaxVolume) {

			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);

			mRadioVolumeSb.setVisibility(View.VISIBLE);
			mRadioVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else if(mCurrentVolume == mMaxVolume+1) {
			mCurrentVolume = mMaxVolume;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);

			mRadioVolumeSb.setVisibility(View.VISIBLE);
			mRadioVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else {
			mCurrentVolume-=2;
		}
	}
	
	public void doVolumnDown() {
		mCurrentVolume-=2;
		if (mCurrentVolume >= 0) {

			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mRadioVolumeSb.setVisibility(View.VISIBLE);
			mRadioVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else if (mCurrentVolume == -1) {
			mCurrentVolume = 0;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mRadioVolumeSb.setVisibility(View.VISIBLE);
			mRadioVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else {
			mCurrentVolume+=2;
		}
	}
	
	public void doRadioChangeVolume(String strValue) {
		int volume= (Integer.parseInt(strValue) * 15) / 100;
		if(volume >= 0 && volume <= 15) {
			mCurrentVolume=volume;
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
			mRadioVolumeSb.setVisibility(View.VISIBLE);
			mRadioVolumeSb.setProgress(mCurrentVolume);

			mVolumeClickTimeId = System.currentTimeMillis();
			new HideVolume(mVolumeClickTimeId).execute();
		}else {
			MilkBoxTTS.speak("Please say change volume 1 to 100.", "doRadioChangeVolumn");
		}
	}

	public void doChannelUp() {
		mCurrentChannel = (mCurrentChannel + 1) % mChannelList.size();
		playChannel(mChannelList.get(mCurrentChannel));
	}

	public void doChannelDown() {
		mCurrentChannel = (mCurrentChannel - 1);
		if (mCurrentChannel < 0)
			mCurrentChannel = mChannelList.size() - 1;
		playChannel(mChannelList.get(mCurrentChannel));
	}

	class LoadChannelTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog.show();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				if (!NetworkUtil.haveInternet(mActivity)) {
					return NETWORK_ERROR;
				}

				String response = RestClient.get(CFG.API_RADIO);
				RestResult rr = RadioApiParser.parserRestResult(response);
				if (rr.getCode() != OK) {
					return ERROR;
				}

				mChannelList = RadioApiParser.getChannelArray(rr.getData());
				if (mChannelList.size() == 0) {
					return NO_DATA;
				}

				return OK;

			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return ERROR;
		}

		@Override
		protected void onPostExecute(Integer result) {
			mProgressDialog.dismiss();

			if (result == OK) {
				checkCurrentChannel();

				RadioChannel channel = mChannelList.get(mCurrentChannel);
				playChannel(channel);
			}

			if (result == NO_DATA) {
				// Toast.makeText(mContext, getString(R.string.no_data),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.no_data);
			}

			if (result == ERROR) {
				// Toast.makeText(mContext, getString(R.string.error),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.error);
			}

			if (result == NETWORK_ERROR) {
				// Toast.makeText(mContext, getString(R.string.error),
				// Toast.LENGTH_LONG).show();
				showMsg(R.string.error_internet);
			}

		}
	}

	class HideVolume extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		final long id;

		HideVolume(long id) {
			this.id = id;
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				Thread.sleep(VOLUME_TIMEOUT);
			} catch (Exception e) {
				Log.e(TAG, e.toString());
			}
			return null;
		}

		@Override
		protected void onPostExecute(Integer result) {
			if (id == mVolumeClickTimeId) {
				mRadioVolumeSb.setVisibility(View.GONE);
			}
		}
	}

	private void playChannel(RadioChannel channel) {
//		mRadioNameTv.setText(channel.getName());
		Random choose = new Random(); 
		int mNewNumber = 0;
		mNewNumber = choose.nextInt(3)+1 ;
		while(mBgNumber == mNewNumber)
		{
			mNewNumber = choose.nextInt(3)+1 ;
		}
		
		
		if( mRadioBg.getBackground() != null )
		{
			mRadioBg.setBackgroundDrawable(null);
		}
		
		if( mRadioAnim.getBackground() != null )
		{
			((AnimationDrawable)(mRadioAnim.getBackground())).stop();
			mRadioAnim.setBackgroundDrawable(null);
		}
		if( mRadioModerator.getBackground() != null )
		{
			((AnimationDrawable)(mRadioModerator.getBackground())).stop();
			mRadioModerator.setBackgroundDrawable(null);
		}
		LayoutParams params = mRadioAnim.getLayoutParams();
		switch(mNewNumber )
		{
		case 1: // light
			mRadioBg.setBackgroundResource(R.drawable.radio_light_bg);
			mRadioAnim.setBackgroundResource(R.anim.radio_light);
			mRadioAnim.setX(373);
			mRadioAnim.setY(195);
			params.width = 566;
			params.height = 266;
			mRadioAnim.setLayoutParams(params);
			mRadioModerator.setBackgroundResource(R.anim.radio_light_moderator);
			mRadioModerator.setX(520);
			mRadioModerator.setY(285);
			mRadioData.setTextColor(Color.WHITE);
			mRadioData.setBackgroundResource(R.drawable.radio_light_text);
			mRadioTitle.setTextColor(Color.WHITE);
			break;
		case 2: // radio_dark
			mRadioBg.setBackgroundResource(R.drawable.radio_dark_bg);
			mRadioAnim.setBackgroundResource(R.anim.radio_dark);
			mRadioAnim.setX(46);
			mRadioAnim.setY(146);
			params.width = 960;
			params.height = 266;
			mRadioAnim.setLayoutParams(params);
			mRadioModerator.setBackgroundResource(R.anim.radio_dark_moderator);
			mRadioModerator.setX(207);
			mRadioModerator.setY(368);
			mRadioData.setTextColor(Color.WHITE);
			mRadioData.setBackgroundResource(R.drawable.radio_dark_text);
			mRadioTitle.setTextColor(Color.WHITE);
			break;
		case 3: // radio_beach
			mRadioBg.setBackgroundResource(R.drawable.radio_beach_bg);
			mRadioAnim.setBackgroundResource(0);
			mRadioModerator.setBackgroundResource(R.anim.radio_beach_moderator);
			mRadioModerator.setX(75);
			mRadioModerator.setY(370);
			mRadioData.setTextColor(Color.WHITE);
			mRadioData.setBackgroundResource(R.drawable.radio_beach_text);
			mRadioTitle.setTextColor(Color.WHITE);
			break;
		default: // radio_dark
			mRadioBg.setBackgroundResource(R.drawable.radio_dark_bg);
			mRadioAnim.setBackgroundResource(R.anim.radio_dark);
			mRadioAnim.setX(46);
			mRadioAnim.setY(146);
			params.width = 960;
			params.height = 266;
			mRadioAnim.setLayoutParams(params);
			mRadioModerator.setBackgroundResource(R.anim.radio_dark_moderator);
			mRadioModerator.setX(207);
			mRadioModerator.setY(368);
			mRadioData.setTextColor(Color.WHITE);
			mRadioData.setBackgroundResource(R.drawable.radio_dark_text);
			mRadioTitle.setTextColor(Color.WHITE);
			break;
		}
		mBgNumber = mNewNumber ;
		if (mBgNumber != 3)
		{		
			Object backgroundObject = mRadioAnim.getBackground();
			animationDrawable = (AnimationDrawable) backgroundObject;
			animationDrawable.start();
		}
		Object backgroundObject = mRadioModerator.getBackground();
		animationDrawable = (AnimationDrawable) backgroundObject;
		animationDrawable.start();

		
		mRadioTitle.setText(channel.getName()+"\n"+
				channel.getType()+"\n");	
		mRadioData.setText(channel.getInformation());
		
		mRadioMainVv.stopPlayback();
		mRadioMainVv.setVideoURI(Uri.parse(channel.getUrl()));
		mRadioMainVv.requestFocus();
		mRadioMainVv.start();

		hideNameTv();

		TranslateAnimation topIn = new TranslateAnimation(0, 0,-mActivity.getDisH(), 0);
//			topIn.setInterpolator(new AccelerateDecelerateInterpolator());
		topIn.setDuration(800);
		topIn.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation arg0)
			{
				mRadioData.setVisibility(View.VISIBLE);
			}
	
			@Override
			public void onAnimationRepeat(Animation arg0)
			{
	
			}
	
			@Override
			public void onAnimationEnd(Animation arg0)
			{

			}
		});
		mRadioData.startAnimation(topIn);
//		mRadioData.setAnimation(topIn);
//		topIn.startNow();
		}
		


	private void hideNameTv() {
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				mRadioNameTv.setText("");
			}
		}, NAMETV_TIMEOUT);
	}
	/**
	 * Add : Alvin 為避免於 TV 播放中因 TV 節目本身的音量造成語音辨識收音干擾，所以在呼叫 ASR 之前先記住目前的音量值後將音量降低
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{

		if ((keyCode == KeyEvent.KEYCODE_F2) && (mCurrentVolume >= 3))
		{
			// 呼叫 ASR 前先將 TV 的聲音降低以免干擾到麥克風
			mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 1, 0);
			
			// 將 Radio 播放器名稱記憶起來，因為播放器與 TTS 都是使用同一個 Audio channel 所以無法單獨調整音量，只能暫停播放影片
			CFG.TvActivitymMainVv = mRadioMainVv;
		}

		// 接續原有的 F2 功能
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

		// Log.i("hATM", "onActivityResult at TvPlayerActivity ....");
		// this.setCurrentVolume();
		// Alvin 2013/12/30
		// 因為不希望語音辨識結束後顯示音量條所以改成直接設定回原有的音量而不呼叫 API
		mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mCurrentVolume, 0);
	}
	/**
	* @author andywu
	* @date 20140306
	* 把資源回收
	*/
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mRadioAnim.setBackgroundDrawable(null);
		mRadioModerator.setBackgroundDrawable(null);
		mRadioBg.setBackgroundDrawable(null);
		System.gc();
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
			Log.i("hATM", "doMuteVolume                                                                                                                                                                                                                  at TvPlayerActivity ....");
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
		mRadioVolumeSb.setVisibility(View.VISIBLE);
		mRadioVolumeSb.setProgress(mCurrentVolume);

		mVolumeClickTimeId = System.currentTimeMillis();
		new HideVolume(mVolumeClickTimeId).execute();
	}
	
	public void doRadioHelpMenu()
	{
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值

		/*
		 "Help", "Command List", "Channel Up", "Channel Down", "Mute", "Volume Up", "Volume Down",  "Change Volume", "Volume Change", "Main Menu", "TV Off"
		 */
		
		
		// Show message
		String strMsg[] = { "What can I say", 
//				                          "Help", 
//				                          "Command List", 
				                          "Channel Up", 
				                          "Channel Down", 
				                          "Mute", 
				                          "Volume Up", 
				                          "Volume Down", 
				                          "Change Volume", 
//				                          "Volume Change", 
				                          "Main Menu", 
				                          "Radio Off" };

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
						 + "<big>● Change Volume</big><sub>&nbsp;&nbsp;<small><small>or&nbsp;&nbsp;<b>Volume Change</b></small></small></sub><br>"
						 + "<big>● Main Menu</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Radio Off</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>" ;
				
		moderatorDialog(dialogStr, indexNo + 2, 10000);

		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strMsg);
		MilkBoxTTS.speak(speakStrs, "echoWhatCanISay");
	}
	
	public void doRadioLastChannel()
	{
		// TODO Auto-generated method stub
		mCurrentChannel = mChannelList.size() - 1;

		// 唸出資訊
		MilkBoxTTS.speak("Chang To Channel " + (mCurrentChannel + 1), "doRadioLastChannel");

		playChannel(mChannelList.get(mCurrentChannel));
	}
	
}
