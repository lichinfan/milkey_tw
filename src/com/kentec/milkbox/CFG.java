package com.kentec.milkbox;

import MilkFactorySettingUtil.MilkFactorySetting;
import android.app.Activity;
import android.widget.VideoView;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.utils.DEBUG;
import com.kentec.milkbox.weather.model.Weather;

public class CFG
{	
	// Check Client Time
	public static final long CHECK_CLIENT_TIMEOUT = 1000 * 60 * 30;

	// HomeDine Shop Cart Time Ago Hour
	public static final int HOMEDINE_SHOPPING_CART_TIME = -3;

	// HomeDine Progress Ago Day
	public static final int HOMEDINE_PROGRESS_DAY = -7;

	// TimeOut to TV，當多久沒操作系統時會產生TimeOutToTV event
	public final static long TimeOutToTV = 10 * 1000;

	// TimeOut to auto close dialog
	public final static long TimeOutToCloseDialog = 5000;

	// IMEI
	public static String IMEI;
	public static String CUSTOMERID="";
	
	// boxconfig TimeZone
	public static String TIMEZONE = null;

	// API URL
	public static String HOST;
	public static String RPC_URL;
	public static String API_ROOT_PATH;
	public static String API_DINE_PATH;
	public static String API_GROCERY_PATH;
	public static String API_COUPON_PATH;
	public static String API_TV;
	public static String API_RADIO;

	public static RpcClient mRpcClient;

	// Socket timeout
	public static final int CONNECT_TIMEOUT = 20 * 1000;
	public static final int SOCKET_TIMEOUT = 20 * 1000;

	public static final int FOOD_PAGE_SIZE = 5;
	public static final int FOOD_BUFF_SIZE = 3;

	// SharedPreferences
	public static final String PREF_CART_ID = "cartId";
	public static final String PREF_CUSTOMER_ID = "customerId";
	public static final String PREF_COUPON_CART_ID = "couponCartId";
	public static final String PREF_TV_CURRENT_CHANNEL = "currentChannel";
	public static final String PREF_RADIO_CURRENT_CHANNEL = "currentRadioChannel";
	public static final String PREF_TV_IDLE_BACK_TIME = "idleBackTime";
	public static final String PREF_LAST_CHECK_TIME = "lastCheckTime";
	
	// Alvin : 2014/02/05 add, 全域變數
	public static String sys_NickName = "";	 // Moderator use, 用來存放 NickName
	public static String sys_FirstName = "" ; // Moderator use, 用來存放FirstName 
	public static String sys_Title = "" ; // Moderator use, 用來存放 Title
	public static MilkFactorySetting mfs; // for Moderator use, 用來指向 local 設定檔，以供讀取本機相關資訊 Ex. NickName、ServerIP ....	
	public static Weather WEATHER = null; // for Moderator use, 每當系統讀取完目前天氣候會保存一份至此以供 moderator 回應天氣狀況時使用	
	public static VideoView TvActivitymMainVv = null; // 用來記錄 TV / Radio 播放器，用來在 Dialog 出現是可以將播放中的 TV / Radio 暫停，以免與 Dialog 的聲音相互干擾. Alvin:2014/02/28
	// public static boolean isModeratorDialogAbort = false; // 用來記錄ModeratorDialogActivi 是否是被中斷的
	
	// Alvin: 2014/02/14 add, 用來記錄是否要自動執行 Moderator
	// public static boolean AutoReStartASR = false;
	public static boolean AUTO_RECALL_ASR = false; // Add: Alvin 2014/02/15 for recall ASR use
	public static boolean ASR_IS_RUN = false;
	public static int onTimeGreetingFlag = 0; // 紀錄問候語執行狀態 0=尚未執行過 1=執行過00:00-04:59 2=執行過12:00-12:59 3=執行過18:00-19:59

	// Alvin: 2014/03/26 add, MilkBoxTTS使用的發音參數
	public static int TTS_MultiSpeak_DelayTime = 1000; //  多句式TTS發音控制的句與句之間的延遲時間(單位: 毫秒)
	public static float TTS_SpeechRate = 0.9f; // 設定發音速度
	
	/**
	 * Init Nick_Name for sys  
	 * @param nickname
	 *
	 * @author Alvin Huang
	 * @date 2014/2/10 下午2:35:42
	 */
	public static void initNickName(String nickname)
	{
		sys_NickName = nickname;
	}
	
	public static void initHost(String host)
	{
		host = (DEBUG.TESTMAGENTO) ? "http://54.187.180.50/boxch" : host;
		host = ((host == null) || (host.length() <= 0)) ? new String("http://54.187.180.50/boxch") : host;
		HOST = host;
		HOST = "http://54.187.180.50/boxch";
		RPC_URL = host + "/index.php/api/xmlrpc";
		API_ROOT_PATH = host + "/capi/";
		API_DINE_PATH = API_ROOT_PATH + "Dine/";
		API_GROCERY_PATH = API_ROOT_PATH + "Grocery/";
		API_COUPON_PATH = API_ROOT_PATH + "Coupon/";
		API_TV = API_ROOT_PATH + "Tv/list.php";
		API_RADIO = API_ROOT_PATH  + "Radio/list.php";
		mRpcClient = new RpcClient();
	}

	public static RpcClient getRpcClient()
	{
		return mRpcClient;
	}
	
	/**
	* @author andywu
	* @date 2014.02.26
	* 預防沒有NickName時，要用FirstName代替
	*/
	public static String getNickName()
	{
//		Log.d("andy", "getNickName,N:"+sys_NickName+",F:"+sys_FirstName);
		if ( (sys_NickName == null || ("").equals(sys_NickName)) && sys_FirstName != null)
			return sys_FirstName ;
		else if ( sys_NickName != null )
			return sys_NickName ;
		else
			return "" ;
	}
}
