package com.kentec.milkbox.setting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.xmlrpc.android.XMLRPCException;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.animation.CustomizeInterpolator;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.SettingASRCreater;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.setting.SettingState.SettingStateEnum;

import MilkFactorySettingUtil.MilkFactorySetting;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingActivity extends ASRActivity
{
	private ReLayoutUtil mReLayoutUtil;
	TextView tv ;
	ImageView iv ;
//	ImageView mSettingMainSelect ;
	ImageView mSettingItemSelect ;
	ImageView mSettingSetting ;
	ImageView mSettingPersonal ;
	RelativeLayout mSettingPersonalLayout ;
	TextView mSettingFirstNameText ;
	TextView mSettingSurnameText ;
	TextView mSettingNicknameText ;
	TextView mSettingGenderText ;
	TextView mSettingTitleText ;
	RelativeLayout mSettingMoreLayout ;
	ImageView mSettingMoreButton ;
	TextView mSettingAddressText ;
	TextView mSettingTELText ;
	TextView mSettingMobileText ;
	TextView mSettingCreditTypeText ;
	TextView mSettingCreditNoText ;
	TextView mSettingPasswordText ;
	
	RelativeLayout mSettingSettingLayout ;
	ImageView mSettingWifiButton ;
	ImageView mSettingBuletoothButton ;
	ImageView mSettingMIGSButton ;
	ImageView mSettingHIDButton ;
	ImageView mSettingOffensiveButton ;
	TextView mSettingIMEIHint;
	TextView mSettingVersionHint;
	
	SettingState.SettingStateEnum mNowState ;
	MilkFactorySetting mfs;
	Boolean mIsMoreINFO = false ;
	Boolean mIsWifi = false ;
	Boolean mIsBuletooth = false ;
	Boolean mIsMIGS = false ;
	Boolean mIsHID = false ;
	Boolean mIsOffensive = false ;
	Handler mHandler; 
	
	private static CustomizeInterpolator mCustomizeInterpolator = new CustomizeInterpolator(0.039f, 1f) ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		
		mReLayoutUtil = reLayout();
//		mSettingMainSelect = (ImageView) findViewById(R.id.SettingMainSelect) ;
//		mReLayoutUtil.relative(mSettingMainSelect,0.75f,170,150);
		mSettingItemSelect = (ImageView) findViewById(R.id.SettingItemSelect) ;
		mReLayoutUtil.relative(mSettingItemSelect,1f,170,150);
		mSettingPersonalLayout = (RelativeLayout) findViewById(R.id.SettingPersonalLayout) ;
		mSettingSetting = (ImageView) findViewById(R.id.SettingSetting) ;
		mReLayoutUtil.relative(mSettingSetting,330,200,168,315);
		mSettingPersonal = (ImageView) findViewById(R.id.SettingPersonal) ;
		mReLayoutUtil.relative(mSettingPersonal,330,200,170,145);
		tv = (TextView) findViewById(R.id.SettingFirstName) ;
		mReLayoutUtil.relative(586,149,tv);
		tv = (TextView) findViewById(R.id.SettingSurname) ;
		mReLayoutUtil.relative(586,217,tv);
		tv = (TextView) findViewById(R.id.SettingNickname) ;
		mReLayoutUtil.relative(586,278,tv);
		tv = (TextView) findViewById(R.id.SettingGender) ;
		mReLayoutUtil.relative(586,344,tv);
		tv = (TextView) findViewById(R.id.SettingTitle) ;
		mReLayoutUtil.relative(586,408,tv);
		tv = (TextView) findViewById(R.id.SettingMoreText) ;
		mReLayoutUtil.relative(586,473,tv);
		tv = (TextView) findViewById(R.id.SettingAddress) ;
		mReLayoutUtil.relative(586,538,tv);
		tv = (TextView) findViewById(R.id.SettingTEL) ;
		mReLayoutUtil.relative(586,604,tv);
		tv = (TextView) findViewById(R.id.SettingMobile) ;
		mReLayoutUtil.relative(586,667,tv);
		tv = (TextView) findViewById(R.id.SettingCreditType) ;
		mReLayoutUtil.relative(586,730,tv);
		tv = (TextView) findViewById(R.id.SettingCreditNo) ;
		mReLayoutUtil.relative(586,794,tv);
		tv = (TextView) findViewById(R.id.SettingPassword) ;
		mReLayoutUtil.relative(586,860,tv);
		
		mSettingFirstNameText = (TextView) findViewById(R.id.SettingFirstNameText) ;
		mReLayoutUtil.relative(865,149,mSettingFirstNameText);
		mSettingSurnameText = (TextView) findViewById(R.id.SettingSurnameText) ;
		mReLayoutUtil.relative(865,217,mSettingSurnameText);
		mSettingNicknameText = (TextView) findViewById(R.id.SettingNicknameText) ;
		mReLayoutUtil.relative(865,278,mSettingNicknameText);
		mSettingGenderText = (TextView) findViewById(R.id.SettingGenderText) ;
		mReLayoutUtil.relative(865,344,mSettingGenderText);
		mSettingTitleText = (TextView) findViewById(R.id.SettingTitleText) ;
		mReLayoutUtil.relative(865,408,mSettingTitleText);
		iv = (ImageView) findViewById(R.id.SettingMoreButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,863,474);
		mSettingMoreButton = (ImageView) findViewById(R.id.SettingMoreButton) ;
		mReLayoutUtil.relative(mSettingMoreButton,105,50,863,475);
		mSettingAddressText = (TextView) findViewById(R.id.SettingAddressText) ;
//		mReLayoutUtil.relative(865,538,mSettingAddressText);
		mReLayoutUtil.relative(mSettingAddressText,830,50,865,538);
		mSettingTELText = (TextView) findViewById(R.id.SettingTELText) ;
		mReLayoutUtil.relative(865,604,mSettingTELText);
		mSettingMobileText = (TextView) findViewById(R.id.SettingMobileText) ;
		mReLayoutUtil.relative(865,667,mSettingMobileText);
		mSettingCreditTypeText = (TextView) findViewById(R.id.SettingCreditTypeText) ;
		mReLayoutUtil.relative(865,730,mSettingCreditTypeText);
		mSettingCreditNoText = (TextView) findViewById(R.id.SettingCreditNoText) ;
		mReLayoutUtil.relative(865,794,mSettingCreditNoText);
		mSettingPasswordText = (TextView) findViewById(R.id.SettingPasswordText) ;	
		mReLayoutUtil.relative(865,860,mSettingPasswordText);
		
		mSettingSettingLayout = (RelativeLayout) findViewById(R.id.SettingSettingLayout);
		mSettingWifiButton = (ImageView) findViewById(R.id.SettingWifiButton) ;
		mReLayoutUtil.relative(mSettingWifiButton,105,50,1445,265);
		tv = (TextView) findViewById(R.id.SettingWifiText) ;
		mReLayoutUtil.relative(586,255,tv);
		tv = (TextView) findViewById(R.id.SettingWifiHint) ;
		mReLayoutUtil.relative(586,295,tv);
		iv = (ImageView) findViewById(R.id.SettingWifiButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,1445,265);				
		mSettingBuletoothButton = (ImageView) findViewById(R.id.SettingBuletoothButton) ;
		mReLayoutUtil.relative(mSettingBuletoothButton,105,50,1445,378);
		tv = (TextView) findViewById(R.id.SettingBuletoothText) ;
		mReLayoutUtil.relative(586,368,tv);
		tv = (TextView) findViewById(R.id.SettingBuletoothHint) ;
		mReLayoutUtil.relative(586,408,tv);
		iv = (ImageView) findViewById(R.id.SettingBuletoothButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,1445,378);				
		mSettingMIGSButton = (ImageView) findViewById(R.id.SettingMIGSButton) ;
		mReLayoutUtil.relative(mSettingMIGSButton,105,50,1445,491);
		tv = (TextView) findViewById(R.id.SettingMIGSText) ;
		mReLayoutUtil.relative(586,481,tv);
		tv = (TextView) findViewById(R.id.SettingMIGSHint) ;
		mReLayoutUtil.relative(586,521,tv);
		iv = (ImageView) findViewById(R.id.SettingMIGSButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,1445,491);				
		mSettingHIDButton = (ImageView) findViewById(R.id.SettingHIDButton) ;
		mReLayoutUtil.relative(mSettingHIDButton,105,50,1445,604);
		tv = (TextView) findViewById(R.id.SettingHIDText) ;
		mReLayoutUtil.relative(586,594,tv);
		tv = (TextView) findViewById(R.id.SettingHIDHint) ;
		mReLayoutUtil.relative(586,634,tv);
		iv = (ImageView) findViewById(R.id.SettingHIDButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,1445,604);		
		mSettingOffensiveButton = (ImageView) findViewById(R.id.SettingOffensiveButton) ;
		mReLayoutUtil.relative(mSettingOffensiveButton,105,50,1445,717);
		tv = (TextView) findViewById(R.id.SettingOffensiveText) ;
		mReLayoutUtil.relative(586,707,tv);
		tv = (TextView) findViewById(R.id.SettingOffensiveHint) ;
		mReLayoutUtil.relative(586,747,tv);
		iv = (ImageView) findViewById(R.id.SettingOffensiveButtonBg) ;
		mReLayoutUtil.relative(iv,200,50,1445,717);		
		
		tv = (TextView) findViewById(R.id.SettingIMEIText) ;
		mReLayoutUtil.relative(586,820,tv);
		mSettingIMEIHint = (TextView) findViewById(R.id.SettingIMEIHint) ;
		mReLayoutUtil.relative(586,860,mSettingIMEIHint);	
		
		tv = (TextView) findViewById(R.id.SettingVersionText) ;
		mReLayoutUtil.relative(1445,820,tv);
		mSettingVersionHint = (TextView) findViewById(R.id.SettingVersionHint) ;
		mReLayoutUtil.relative(1445,860,mSettingVersionHint);			
		init();
		
		/**
		* @author andywu
		* @date 2014.03.12
		* 加入語音
		*/
		new SettingASRCreater().init(this);
		
		mHandler = new Handler(); 
		String customerId=this.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
//		System.out.println("customer id: "+customerId);
		new LoadTask(customerId).execute();	
		mfs = new MilkFactorySetting(this);
	}
	/*
	 * 頁面控制的起始
	 */
	private void init()
	{
//		mReLayoutUtil.relative(170,-500,mSettingMainSelect);
		mReLayoutUtil.relative(544,-500,mSettingItemSelect);
		
		mSettingMoreLayout = (RelativeLayout) findViewById(R.id.SettingMoreLayout);
		
		reset_button_move_animation(mSettingMoreButton,0);
		//*
		// TODO 可以抓取系統Wifi Buletooth資訊後，就把這裡註解掉
		reset_button_move_animation(mSettingWifiButton,0);
		reset_button_move_animation(mSettingBuletoothButton,0);
		reset_button_move_animation(mSettingMIGSButton,1);
		mIsMIGS = true ;
		reset_button_move_animation(mSettingHIDButton,0);
		reset_button_move_animation(mSettingOffensiveButton,0);
		//*/
		show_personal();		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			right_action();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			left_action();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_UP)
		{
			up_action();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)
		{
			down_action();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_ENTER)
		{
			enter_action();
			return true;
		}
		else if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			back_action();
			return true;
		}
		return super.onKeyDown(keyCode, event);
		
	}
	/*
	 * 抓取 系統資訊
	 */
	public void get_system_state()
	{
		// TODO 抓取 Wifi Buletooth資訊
		// true->ON false->off
		mIsWifi = false ;
		mIsBuletooth = false ;
		mIsMIGS = false ;
		mIsHID = false ;
		mIsOffensive = false ;	
		mHandler.post(new Runnable()
		{
			@Override
			public void run()
			{
				if (mIsWifi)
					reset_button_move_animation(mSettingWifiButton,1);
				else
					reset_button_move_animation(mSettingWifiButton,0);	
				if (mIsBuletooth)
					reset_button_move_animation(mSettingBuletoothButton,1);
				else
					reset_button_move_animation(mSettingBuletoothButton,0);	
				if (mIsMIGS)
					reset_button_move_animation(mSettingMIGSButton,1);
				else
					reset_button_move_animation(mSettingMIGSButton,0);	
				if (mIsHID)
					reset_button_move_animation(mSettingHIDButton,1);
				else
					reset_button_move_animation(mSettingHIDButton,0);	
				if (mIsOffensive)
					reset_button_move_animation(mSettingOffensiveButton,1);
				else
					reset_button_move_animation(mSettingOffensiveButton,0);					
			}
		}); 
	}
	/*
	 * 語音可以從這邊開始
	 */
	public void speech_receiver()
	{ //概念程式碼，可以整個function改掉
		// TODO 語音控制
		String msg = "";
		if (msg.equals("personal info"))
			show_personal();
		else if (msg.equals("Setting"))
			show_setting();	
		else if (msg.equals("First Name"))
		{
			show_personal(); // 1.切換到該分頁
			enter_personal(); // 2.進入該分頁
			go_to_item(SettingStateEnum.PERSONAL_FIRSTNAME); // 3.選擇指定的項目
		}
		else if (msg.equals("SurName"))
		{
			show_personal(); // 1.切換到該分頁
			enter_personal(); // 2.進入該分頁
			go_to_item(SettingStateEnum.PERSONAL_SURNAME); // 3.選擇指定的項目
		}
		else if (msg.equals("Wifi"))
		{
			show_setting(); // 1.切換到該分頁
			enter_setting(); // 2.進入該分頁
			go_to_item(SettingStateEnum.SETTING_WIFI); // 3.選擇指定的項目
		}
		else if (msg.equals("Buletooth"))
		{
			show_setting(); // 1.切換到該分頁
			enter_setting(); // 2.進入該分頁
			go_to_item(SettingStateEnum.SETTING_BLUETOOTH); // 3.選擇指定的項目
		}
		else if (msg.equals("Down"))
		{
			down_action();
		}
		else if (msg.equals("Enter"))
		{
			enter_action();
		}
	}
	/*
	 * 按下OK時的動作
	 */
	private void enter_action()
	{
		switch(mNowState)
		{
		case MAIN_PERSONAL:
			enter_personal();
			break;
		case MAIN_SETTING:
			enter_setting();
			break;
		case PERSONAL_MORE:
			if (mIsMoreINFO == true)
			{
				mSettingMoreLayout.setVisibility(View.INVISIBLE);
				button_move_animation(mSettingMoreButton,0);
			}
			else
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);
			}
			mIsMoreINFO = !mIsMoreINFO ;
			break;
		case SETTING_WIFI:
			if (mIsWifi == true)
			{
				button_move_animation(mSettingWifiButton,0);
				// TODO WIFI function off
			}
			else
			{
				button_move_animation(mSettingWifiButton,1);
				// TODO WIFI function on
			}
			mIsWifi = !mIsWifi ;
			break;
		case SETTING_BLUETOOTH:
			if (mIsBuletooth == true)
			{
				button_move_animation(mSettingBuletoothButton,0);
				// TODO Buletooth function off
			}
			else
			{
				button_move_animation(mSettingBuletoothButton,1);
				// TODO Buletooth function on
			}
			mIsBuletooth = !mIsBuletooth ;
			break;
		case SETTING_MIGS:
			if (mIsMIGS == true)
			{
				button_move_animation(mSettingMIGSButton,0);
				// TODO MIGS function off
			}
			else
			{
				button_move_animation(mSettingMIGSButton,1);
				// TODO MIGS function on
			}
			mIsMIGS = !mIsMIGS ;
			break;
		case SETTING_HID:
			if (mIsHID == true)
			{
				button_move_animation(mSettingHIDButton,0);
				// TODO HID function off
			}
			else
			{
				button_move_animation(mSettingHIDButton,1);
				// TODO HID function on
			}
			mIsHID = !mIsHID ;
			break;
		case SETTING_OFFENSIVE:
			if (mIsOffensive == true)
			{
				button_move_animation(mSettingOffensiveButton,0);
				// TODO Offensive function off
			}
			else
			{
				button_move_animation(mSettingOffensiveButton,1);
				// TODO Offensive function on
			}
			mIsOffensive = !mIsOffensive ;
			break;
		}
	}
	/*
	 * 按下右時的動作
	 */
	private void right_action()
	{
		if (mNowState == SettingStateEnum.MAIN_PERSONAL)
		{
			enter_personal();
		}
		else if (mNowState == SettingStateEnum.MAIN_SETTING)
		{
			enter_setting();
		}
	}	
	/*
	 * 按下BACK時的動作
	 */
	private void back_action()
	{
		if (mNowState.compareTo(SettingStateEnum.PERSONAL_START)>0 && mNowState.compareTo(SettingStateEnum.PERSONAL_END)<0)
		{
			show_personal();
			mReLayoutUtil.relative(544,-500,mSettingItemSelect);
			reset_move_animation();
		}
		else if (mNowState.compareTo(SettingStateEnum.SETTING_START)>0 && mNowState.compareTo(SettingStateEnum.SETTING_END)<0)
		{
			show_setting();
			mReLayoutUtil.relative(544,-500,mSettingItemSelect);
			reset_move_animation();
		}
		else
		{
			finish();
		}
	}
	/*
	 * 按下左時的動作
	 */
	private void left_action()
	{
		if (mNowState.compareTo(SettingStateEnum.PERSONAL_START)>0 && mNowState.compareTo(SettingStateEnum.PERSONAL_END)<0)
		{
			show_personal();
			mReLayoutUtil.relative(544,-500,mSettingItemSelect);
			reset_move_animation();
		}
		else if (mNowState.compareTo(SettingStateEnum.SETTING_START)>0 && mNowState.compareTo(SettingStateEnum.SETTING_END)<0)
		{
			show_setting();
			mReLayoutUtil.relative(544,-500,mSettingItemSelect);
			reset_move_animation();
		}		
	}
	/*
	 * 按下上時的動作
	 */
	private void up_action()
	{
		switch(mNowState)
		{
		case MAIN_SETTING:
			show_personal();
			break;
		case PERSONAL_SURNAME:
			select_move_animation(213,145,1);
			mNowState = SettingStateEnum.PERSONAL_FIRSTNAME ;
			break;
		case PERSONAL_NICKNAME:
			select_move_animation(274,213,1);
			mNowState = SettingStateEnum.PERSONAL_SURNAME ;
			break;
		case PERSONAL_GENDER:
			select_move_animation(340,274,1);
			mNowState = SettingStateEnum.PERSONAL_NICKNAME ;
			break;
		case PERSONAL_TITLE:
			select_move_animation(404,340,1);
			mNowState = SettingStateEnum.PERSONAL_GENDER ;
			break;
		case PERSONAL_MORE:
			select_move_animation(469,404,1);
			mNowState = SettingStateEnum.PERSONAL_TITLE ;
			break;
		case PERSONAL_ADDRESS:
			select_move_animation(534,469,1);
			mNowState = SettingStateEnum.PERSONAL_MORE ;
			break;
		case PERSONAL_TEL:
			select_move_animation(600,534,1);
			mNowState = SettingStateEnum.PERSONAL_ADDRESS ;
			break;
		case PERSONAL_MOBILE:
			select_move_animation(663,600,1);
			mNowState = SettingStateEnum.PERSONAL_TEL ;
			break;
		case PERSONAL_CREDIT_TYPE:
			select_move_animation(726,663,1);
			mNowState = SettingStateEnum.PERSONAL_MOBILE ;
			break;
		case PERSONAL_CREDIT_NO:
			select_move_animation(790,726,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_TYPE ;
			break;
		case PERSONAL_PASSWORD:
			select_move_animation(856,790,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_NO ;
			break;
		
		case SETTING_BLUETOOTH:
			select_move_animation(352,239,2);
			mNowState = SettingStateEnum.SETTING_WIFI ;			
			break;
		case SETTING_MIGS:
			select_move_animation(465,352,2);
			mNowState = SettingStateEnum.SETTING_BLUETOOTH ;			
			break;
		case SETTING_HID:
			select_move_animation(578,465,2);
			mNowState = SettingStateEnum.SETTING_MIGS ;			
			break;
		case SETTING_OFFENSIVE:
			select_move_animation(691,578,2);
			mNowState = SettingStateEnum.SETTING_HID ;			
			break;
		
		}
	}
	/*
	 * 按下下時的動作
	 */
	private void down_action()
	{
		switch(mNowState)
		{
		case MAIN_PERSONAL:
			show_setting();
			break;
		case PERSONAL_FIRSTNAME:
			select_move_animation(145,213,1);
			mNowState = SettingStateEnum.PERSONAL_SURNAME ;
			break;
		case PERSONAL_SURNAME:
			select_move_animation(213,274,1);
			mNowState = SettingStateEnum.PERSONAL_NICKNAME ;
			break;
		case PERSONAL_NICKNAME:
			select_move_animation(274,340,1);
			mNowState = SettingStateEnum.PERSONAL_GENDER ;
			break;
		case PERSONAL_GENDER:
			select_move_animation(340,404,1);
			mNowState = SettingStateEnum.PERSONAL_TITLE ;
			break;
		case PERSONAL_TITLE:
			select_move_animation(404,469,1);
			mNowState = SettingStateEnum.PERSONAL_MORE ;
			break;
		case PERSONAL_MORE:
			if (!mIsMoreINFO)
				break;
			select_move_animation(469,534,1);
			mNowState = SettingStateEnum.PERSONAL_ADDRESS ;
			break;
		case PERSONAL_ADDRESS:
			select_move_animation(534,600,1);
			mNowState = SettingStateEnum.PERSONAL_TEL ;
			break;
		case PERSONAL_TEL:
			select_move_animation(600,663,1);
			mNowState = SettingStateEnum.PERSONAL_MOBILE ;
			break;
		case PERSONAL_MOBILE:
			select_move_animation(663,726,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_TYPE ;
			break;
		case PERSONAL_CREDIT_TYPE:
			select_move_animation(726,790,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_NO ;
			break;
		case PERSONAL_CREDIT_NO:
			select_move_animation(790,856,1);
			mNowState = SettingStateEnum.PERSONAL_PASSWORD ;
			break;
		
		case SETTING_WIFI:
			select_move_animation(239,352,2);
			mNowState = SettingStateEnum.SETTING_BLUETOOTH ;
			break;
		case SETTING_BLUETOOTH:
			select_move_animation(352,465,2);
			mNowState = SettingStateEnum.SETTING_MIGS ;
			break;
		case SETTING_MIGS:
			select_move_animation(465,578,2);
			mNowState = SettingStateEnum.SETTING_HID ;
			break;
		case SETTING_HID:
			select_move_animation(578,691,2);
			mNowState = SettingStateEnum.SETTING_OFFENSIVE ;
			break;
		
		}
	}
	/*
	 * 跳至personal
	 */
	public void show_personal()
	{
		mSettingPersonal.setImageResource(R.drawable.setting_info_press);
		mSettingSetting.setImageResource(R.drawable.setting_set);
		mSettingPersonal.setAlpha(1f);
		mSettingSetting.setAlpha(1f);
		set_text_alpha(0.5f);
		mSettingPersonalLayout.setVisibility(View.VISIBLE);
		mSettingSettingLayout.setVisibility(View.INVISIBLE);
		mNowState = SettingStateEnum.MAIN_PERSONAL ;
	}
	/*
	 * 跳至setting
	 */
	public void show_setting()
	{
		mSettingPersonal.setImageResource(R.drawable.setting_info);
		mSettingSetting.setImageResource(R.drawable.setting_set_press);	
		mSettingPersonal.setAlpha(1f);
		mSettingSetting.setAlpha(1f);
		set_text_alpha(0.5f);
		mSettingSettingLayout.setVisibility(View.VISIBLE);
		mSettingPersonalLayout.setVisibility(View.INVISIBLE);
		mNowState = SettingStateEnum.MAIN_SETTING ;
	}
	/*
	 * 切換狀態到personal
	 */
	private void enter_personal()
	{
		mNowState = SettingStateEnum.PERSONAL_FIRSTNAME ;
		mReLayoutUtil.relative(mSettingItemSelect,1150,60,544,145);
		reset_move_animation();	
		mSettingPersonal.setAlpha(0.5f);
		mSettingSetting.setAlpha(0.5f);
		set_text_alpha(1f);
	}
	/*
	 * 切換狀態到setting
	 */
	private void enter_setting()
	{
		mNowState = SettingStateEnum.SETTING_WIFI ;
		mReLayoutUtil.relative(mSettingItemSelect,1150,100,544,239);
		reset_move_animation();	
		mSettingPersonal.setAlpha(0.5f);
		mSettingSetting.setAlpha(0.5f);
		set_text_alpha(1f);
	}
	/**
	* @author andywu
	* @date 20140307
	* 把資源回收
	*/
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		List<View> mList = getAllChildViews();
		for (View mView : mList)
		{
			if (mView instanceof ImageView)
			{
				mView.setBackgroundDrawable(null);
//				Log.d("andy", "null");
			}
			
			//這邊也可以加上各種需要release的動作
			
		}
		
		System.gc();
	}
	/**
	* @author andywu
	* @date 20140307
	* 抓取場景上所有的view
	*/
	public List<View> getAllChildViews()
	{
		View v = this.getWindow().getDecorView(); 
		return getAllChildViews(v); 
	}
	/**
	* @author andywu
	* @date 20140307
	* 抓取場景上所有的view
	*/
	private List<View> getAllChildViews(View view)
	{ 
		
		List<View> allchildren = new ArrayList<View>();
		if (view instanceof ViewGroup)
		{
			ViewGroup vg = (ViewGroup) view;
			for (int i = 0; i < vg.getChildCount(); i++)
			{
				View childview = vg.getChildAt(i); 
				allchildren.add(childview); 
				allchildren.addAll(getAllChildViews(childview)); 
			}
		}
		return allchildren; 
	}
	
	private void go_to_item(SettingState.SettingStateEnum itemState)
	{
		switch(itemState)
		{
		case PERSONAL_FIRSTNAME:
			select_move_animation(145,145,1);
			mNowState = SettingStateEnum.PERSONAL_FIRSTNAME ;
			enter_action();
			break;
		case PERSONAL_SURNAME:
			select_move_animation(213,213,1);
			mNowState = SettingStateEnum.PERSONAL_SURNAME ;
			enter_action();
			break;
		case PERSONAL_NICKNAME:
			select_move_animation(274,274,1);
			mNowState = SettingStateEnum.PERSONAL_NICKNAME ;
			enter_action();
			break;
		case PERSONAL_GENDER:
			select_move_animation(340,340,1);
			mNowState = SettingStateEnum.PERSONAL_GENDER ;
			enter_action();
			break;
		case PERSONAL_TITLE:
			select_move_animation(404,404,1);
			mNowState = SettingStateEnum.PERSONAL_TITLE ;
			enter_action();
			break;
		case PERSONAL_MORE:
			select_move_animation(469,469,1);
			mNowState = SettingStateEnum.PERSONAL_MORE ;
			enter_action();
			break;
		case PERSONAL_ADDRESS:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(534,534,1);
			mNowState = SettingStateEnum.PERSONAL_ADDRESS ;
			enter_action();
			break;
		case PERSONAL_TEL:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(600,600,1);
			mNowState = SettingStateEnum.PERSONAL_TEL ;
			enter_action();
			break;
		case PERSONAL_MOBILE:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(663,663,1);
			mNowState = SettingStateEnum.PERSONAL_MOBILE ;
			enter_action();
			break;
		case PERSONAL_CREDIT_TYPE:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(726,726,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_TYPE ;
			enter_action();
			break;
		case PERSONAL_CREDIT_NO:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(790,790,1);
			mNowState = SettingStateEnum.PERSONAL_CREDIT_NO ;
			enter_action();
			break;
		case PERSONAL_PASSWORD:
			if (mIsMoreINFO == false)
			{
				mSettingMoreLayout.setVisibility(View.VISIBLE);
				button_move_animation(mSettingMoreButton,1);				
			}
			select_move_animation(856,856,1);
			mNowState = SettingStateEnum.PERSONAL_PASSWORD ;
			enter_action();
			break;
		case SETTING_WIFI:
			select_move_animation(239,239,2);
			mNowState = SettingStateEnum.SETTING_WIFI ;
			enter_action();
			break;
		case SETTING_BLUETOOTH:
			select_move_animation(352,352,2);
			mNowState = SettingStateEnum.SETTING_BLUETOOTH ;
			enter_action();
			break;
		case SETTING_MIGS:
			select_move_animation(465,465,2);
			mNowState = SettingStateEnum.SETTING_MIGS ;
			enter_action();
			break;
		case SETTING_HID:
			select_move_animation(578,578,2);
			mNowState = SettingStateEnum.SETTING_HID ;
			enter_action();
			break;
		case SETTING_OFFENSIVE:	
			select_move_animation(691,691,2);
			mNowState = SettingStateEnum.SETTING_OFFENSIVE ;
			enter_action();
			break;
		}
	}
	private void set_text_alpha(float alpha)
	{
		mSettingFirstNameText.setAlpha(alpha);
		mSettingSurnameText.setAlpha(alpha);
		mSettingNicknameText.setAlpha(alpha);
		mSettingGenderText.setAlpha(alpha);
		mSettingTitleText.setAlpha(alpha);
		mSettingAddressText.setAlpha(alpha);
		mSettingTELText.setAlpha(alpha);
		mSettingMobileText.setAlpha(alpha);
		mSettingCreditTypeText.setAlpha(alpha);
		mSettingCreditNoText.setAlpha(alpha);
		mSettingPasswordText.setAlpha(alpha);
		mSettingIMEIHint.setAlpha(alpha);
		mSettingVersionHint.setAlpha(alpha);
		
		findViewById(R.id.SettingFirstName).setAlpha(alpha) ;
		findViewById(R.id.SettingSurname).setAlpha(alpha) ;
		findViewById(R.id.SettingNickname).setAlpha(alpha) ;
		findViewById(R.id.SettingGender).setAlpha(alpha) ;
		findViewById(R.id.SettingTitle).setAlpha(alpha) ;
		findViewById(R.id.SettingMoreText).setAlpha(alpha) ;
		findViewById(R.id.SettingAddress).setAlpha(alpha) ;
		findViewById(R.id.SettingTEL).setAlpha(alpha) ;
		findViewById(R.id.SettingMobile).setAlpha(alpha) ;
		findViewById(R.id.SettingCreditType).setAlpha(alpha) ;
		findViewById(R.id.SettingCreditNo).setAlpha(alpha) ;
		findViewById(R.id.SettingPassword).setAlpha(alpha) ;	
		
		findViewById(R.id.SettingWifiText).setAlpha(alpha) ;
		findViewById(R.id.SettingWifiHint).setAlpha(alpha) ;
		findViewById(R.id.SettingBuletoothText).setAlpha(alpha) ;
		findViewById(R.id.SettingBuletoothHint).setAlpha(alpha) ;
		findViewById(R.id.SettingMIGSText).setAlpha(alpha) ;
		findViewById(R.id.SettingMIGSHint).setAlpha(alpha) ;
		findViewById(R.id.SettingHIDText).setAlpha(alpha) ;
		findViewById(R.id.SettingHIDHint).setAlpha(alpha) ;
		findViewById(R.id.SettingOffensiveText).setAlpha(alpha) ;
		findViewById(R.id.SettingOffensiveHint).setAlpha(alpha) ;
		findViewById(R.id.SettingIMEIText).setAlpha(alpha) ;
		findViewById(R.id.SettingVersionText).setAlpha(alpha) ;
	}
	private void reset_button_move_animation(View v,int mode)
	{
		if (mode == 0) // off
		{
			TranslateAnimation ta = new TranslateAnimation(0, mReLayoutUtil.newX(96), 0,0);
			ta.setDuration(1);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			v.startAnimation(ta);			
		}
		else if (mode == 1) // on
		{
			TranslateAnimation ta = new TranslateAnimation(mReLayoutUtil.newX(96), 0, 0,0);
			ta.setDuration(1);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			v.startAnimation(ta);			
		}		
	}
	private void button_move_animation(View v,int mode)
	{
		if (mode == 0) // off
		{
			TranslateAnimation ta = new TranslateAnimation(0, mReLayoutUtil.newX(96), 0,0);
			ta.setDuration(200);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			v.startAnimation(ta);			
		}
		else if (mode == 1) // on
		{
			TranslateAnimation ta = new TranslateAnimation(mReLayoutUtil.newX(96), 0, 0,0);
			ta.setDuration(200);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			v.startAnimation(ta);			
		}
	}
	private void select_move_animation( int fromY , int toY , int mode)
	{
		if (mode == 1)
		{
			TranslateAnimation ta = new TranslateAnimation(0, 0, mReLayoutUtil.newY(fromY-145),mReLayoutUtil.newY(toY-145));
			ta.setDuration(200);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			mSettingItemSelect.startAnimation(ta);
		}
		else if (mode == 2)
		{
			TranslateAnimation ta = new TranslateAnimation(0, 0, mReLayoutUtil.newY(fromY-239),mReLayoutUtil.newY(toY-239));
			ta.setDuration(300);
			ta.setFillAfter(true);
			ta.setInterpolator( mCustomizeInterpolator );
			mSettingItemSelect.startAnimation(ta);			
		}
	}

	private void reset_move_animation()
	{
		TranslateAnimation ta = new TranslateAnimation(0, 0, mReLayoutUtil.newY(0),mReLayoutUtil.newY(0));
		ta.setDuration(1);
		ta.setFillAfter(true);
		ta.setInterpolator( mCustomizeInterpolator );
		mSettingItemSelect.startAnimation(ta);		
	}
	private String change_text(String text)
	{
		String mOutText ;
		System.out.printf("");
		if ( text == null || ("").equals(text) || (" ").equals(text))
//			mOutText = new String();
			mOutText = "**********";
		else
			{
				if (text.length() > 15 )
					mOutText = text.substring(0, 4) + "********" +  text.substring(text.length()-4) ;
				else if (text.length() > 7 )
					mOutText = text.substring(0, 4) + "****" +  text.substring(8) ;
				else if (text.length() > 4 )
					mOutText = text.substring(0, 4) + "****"  ;
				else
					mOutText = text.substring(0, 1) + "****"  ;
			}
		return mOutText;
	}
	private void show_data(HashMap<String, String> data,Address addressdata,CreditCard creditdata)
	{
//		System.out.println("customer info: "+data);
		mSettingFirstNameText.setText( data.get("firstname") );
		mSettingSurnameText.setText( data.get("lastname") );
//		s = s.toUpperCase().charAt(0) + s.substring(1);
/*		
		if (CFG.sys_NickName.length() > 1)
			mSettingNicknameText.setText( CFG.sys_NickName.toUpperCase().charAt(0) + CFG.sys_NickName.substring(1) );
		else if (CFG.sys_NickName.length() == 1)
			mSettingNicknameText.setText( CFG.sys_NickName.toUpperCase().charAt(0) + "") ;
//		else
//			mSettingNicknameText.setText( CFG.sys_NickName );

*/
		mSettingNicknameText.setText(CFG.sys_NickName); // andywu 2014.02.24 改成由CFG那邊抓取
		if (("1").equals(data.get("gender")))
			mSettingGenderText.setText( "Male" );
		else
			mSettingGenderText.setText( "Female" );
//		mSettingTitleText.setText( data.get("title") );
		mSettingTitleText.setText( CFG.sys_Title ); // andywu 2014.02.24 改成由CFG那邊抓取
		mSettingAddressText.setText( addressdata.toString() );
		mSettingTELText.setText( addressdata.getTelePhone() );
		mSettingMobileText.setText( data.get("mobile") );
		mSettingCreditTypeText.setText( creditdata.getType() );
		mSettingCreditNoText.setText( change_text(creditdata.getNumber()) );
		mSettingPasswordText.setText( change_text(data.get("password_hash")) );
		mSettingIMEIHint.setText( mfs.getIMEI() );
		mSettingVersionHint.setText( getVerName(this) );
		
	}
	class LoadTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String customerId;
		HashMap<String, String> customer;
		Object[] addressList;
		ArrayList<Address> address;
		Object[] creditcardList;
		ArrayList<CreditCard> creditcards;
		
		LoadTask(String id) {
			customerId = id;
		}
		
		@Override
		protected Integer doInBackground(String... params) {	
			
			try {
				customer = CFG.getRpcClient().customerInfo(customerId);
				addressList = CFG.getRpcClient().customerAddressList(customerId);
				address = APIParser.parserAddress(addressList);
				creditcardList = CFG.getRpcClient().checkoutexdGetCards(customerId);
				creditcards = APIParser.parserCreditCard(creditcardList);
				
			} catch (XMLRPCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			System.out.println("customer info: "+customer);
//			System.out.println("customer firstname: "+customer.get("firstname"));
//			System.out.println("customer address size: "+address.size());
//			if(address.size()>0)
//				System.out.println("customer address: "+address.get(0));
//			if(creditcards.size()>0) {
//				System.out.println("customer creditcards: "+creditcards.get(0).getNumber());
//				System.out.println("customer creditcards: "+creditcards.get(0).getType());
			mHandler.post(new Runnable()
				{
					@Override
					public void run()
					{
						show_data(customer,address.get(0),creditcards.get(0));
					}
				}); 
			return null;
		}
		
	}
}