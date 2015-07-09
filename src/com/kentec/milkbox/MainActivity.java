package com.kentec.milkbox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.xmlrpc.android.XMLRPCException;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.introduction.IntroductionActivity;
import com.kentec.milkbox.setting.SettingActivity;
import com.kentec.milkbox.utils.CheckClientUtil;
import com.kentec.milkbox.utils.CheckClientUtil.OnCheckListener;
import com.kentec.milkbox.weather.JSONWeatherParser;
import com.kentec.milkbox.weather.WeatherHttpClient;
import com.kentec.milkbox.weather.model.Weather;
import com.kentec.milkbox.weather.model.WeatherForecast;

@SuppressWarnings("deprecation")
public class MainActivity extends ASRActivity implements OnClickListener, OnFocusChangeListener
{
	private ImageView moderator;
	private ReLayoutUtil mReLayoutUtil;

	private View mSetupButton;
	private View mCouponButton;
	private View tvButton ;
	
	// weather
	private TextView cityText;
	private TextView condDescr;
	private TextView temp;
	private TextView press;
	private TextView windSpeed;
	private TextView windDeg;
	private TextView unitTemp;

	private TextView hum;
	private ImageView imgView;

	private static String forecastDaysNum = "3";

	private TextView currentDate;

	private RelativeLayout mainScreen;
	private BitmapDrawable bmpDrawBGImg  = null;

	private Boolean flagForWeatherLoad = false;
	private Boolean flagForBackgroundLoad = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		flagForWeatherLoad = false;
		flagForBackgroundLoad = true;
		
		//		CFG.initHost("http://54.187.180.50/boxtw");
		//		String customerId=this.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
		//		System.out.println("mainactivity customerid: "+customerId);
		//		HashMap<?, ?> boxconfig;
		//		try {
		//			boxconfig = CFG.getRpcClient().boxconfigInfoByCustomerId(customerId);
		//			String timezone = (String) boxconfig.get("timezone");
		//			System.out.println("main infobyimei timezone: "+timezone);
		//		} catch (UnknownHostException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		} catch (XMLRPCException e) {
		//			// TODO Auto-generated catch block
		//			e.printStackTrace();
		//		}
		//		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Taipei"));
		AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		if (null != CFG.TIMEZONE)
			alarm.setTimeZone(CFG.TIMEZONE);
		else
			alarm.setTimeZone("Asia/Taipei");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mainScreen = (RelativeLayout) findViewById(R.id.mainScreen);
		
		
		// mContext = this;

		mReLayoutUtil = reLayout();
		mReLayoutUtil.relative(findViewById(R.id.mainBtnBg), ReLayoutUtil.srcDisW - 110 * 2, 300, 0 + 110, 560 + 205);

		mCouponButton = findViewById(R.id.couponButton);
		mReLayoutUtil.linear(mCouponButton, 240, 240);
		mCouponButton.setOnFocusChangeListener(this);

		View groceryButton = findViewById(R.id.groceryButton);
		mReLayoutUtil.linear(groceryButton, 240, 240);
		groceryButton.setOnFocusChangeListener(this);

		tvButton = findViewById(R.id.tvButton);
		mReLayoutUtil.linear(tvButton, 240, 240);
		tvButton.setOnFocusChangeListener(this);

		// View memoryButton = findViewById(R.id.memoryButton);
		// mReLayoutUtil.linear(memoryButton, 240, 240 + 205);
		// memoryButton.setOnFocusChangeListener(this);

		View homedineButton = findViewById(R.id.homedineButton);
		mReLayoutUtil.linear(homedineButton, 240, 240);
		homedineButton.setOnFocusChangeListener(this);

		View radioButton = findViewById(R.id.radioButton);
		mReLayoutUtil.linear(radioButton, 240, 240);
		radioButton.setOnFocusChangeListener(this);

		// View garagesaleButton = findViewById(R.id.garagesaleButton);
		// mReLayoutUtil.linear(garagesaleButton, 240, 240 + 205);
		// garagesaleButton.setOnFocusChangeListener(this);

		// View gymButton = findViewById(R.id.gymButton);
		// mReLayoutUtil.linear(gymButton, 240, 240 + 205);
		// gymButton.setOnFocusChangeListener(this);

		mSetupButton = findViewById(R.id.setupButton);
		mReLayoutUtil.linear(mSetupButton, 240, 240);
		mSetupButton.setOnFocusChangeListener(this);
		mSetupButton.setOnClickListener(this);

		moderator = (ImageView) findViewById(R.id.moderator);

		// initial click event
		mCouponButton.setOnClickListener(this);
		tvButton.setOnClickListener(this);
		groceryButton.setOnClickListener(this);
		// memoryButton.setOnClickListener(this);
		homedineButton.setOnClickListener(this);
		radioButton.setOnClickListener(this);
		// garagesaleButton.setOnClickListener(this);
		// gymButton.setOnClickListener(this);

		// weather
//		String city = "Taipei, Taiwan";
		// Chinese Traditional - zh_tw
//		String lang = "en";

//		cityText = (TextView) findViewById(R.id.cityText);
		temp = (TextView) findViewById(R.id.temp);
		unitTemp = (TextView) findViewById(R.id.unittemp);
		unitTemp.setText("°C");
		condDescr = (TextView) findViewById(R.id.skydesc);

		imgView = (ImageView) findViewById(R.id.condIcon);
		/*
		 * condDescr = (TextView) findViewById(R.id.condDescr);
		 * 
		 * hum = (TextView) findViewById(R.id.hum); press = (TextView)
		 * findViewById(R.id.press); windSpeed = (TextView)
		 * findViewById(R.id.windSpeed); windDeg = (TextView)
		 * findViewById(R.id.windDeg);
		 */
	
//		JSONWeatherTask task = new JSONWeatherTask();
//		task.execute(new String[] { city, lang });
		
		// set a timer and every hour to update weather data 
//		Timer timer = new Timer();
//		timer.schedule(new TimerTask() {
//		    public void run() {
//		        new JSONWeatherTask().execute(new String[] { "New York", "en" });
//		    }
//		}, 0, 60*60*1000);

		new Timer().scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(flagForBackgroundLoad == true) {
							new JSONWeatherTask().execute(new String[] { TimeZone.getDefault().getID().split("/")[1], "en" });    					
						}
					}
				});
			}
		}, 0, 30*60*1000);
		
        
//		取得一周的天氣
//		JSONForecastWeatherTask task1 = new JSONForecastWeatherTask();
//		task1.execute(new String[] { city, lang, forecastDaysNum });

		/**
		* @author andywu
		* @date 2014.02.24
		* 加入讀取使用者基本資料的動作
		*/
		String customerId=this.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
		//		System.out.println("customer id: "+customerId);
//		new LoadCustomerTask(customerId).execute();	
		
		new CheckClientUtil(this, new OnCheckListener(){

			@Override
			public void OK() {
				/**
				* @author andywu
				* @date 2014.02.24
				* 加入讀取使用者基本資料的動作
				*/

//				String customerId=this.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
				System.out.println("mainactivity customer id: "+CFG.CUSTOMERID);
				if(CFG.CUSTOMERID==null || "".equalsIgnoreCase(CFG.CUSTOMERID))
					;
				else
					new LoadCustomerTask(CFG.CUSTOMERID).execute();	
				
			}
			
		});
		
		System.out.println("after load customer CFG.timezone: "+CFG.TIMEZONE);
		// date
		String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		currentDate = (TextView) findViewById(R.id.currentDate);
		currentDate.setText(formattedDate);
		Log.e("date ", formattedDate);


		SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
		Calendar calendar = Calendar.getInstance();
//		weekDay = dayFormat.format(calendar.getTime());
		TextView weekDay = (TextView) findViewById(R.id.weekDay);
		//weekDay.setText(dayFormat.format(calendar.getTime()));

		//andywu 2014.03.28 修改顯示星期的方式，並中文化
		int day=calendar.get(Calendar.DAY_OF_WEEK);
		switch(day)
		{
		case 1:
			weekDay.setText( getResources().getString(R.string.sun) );
			break;
		case 2:
			weekDay.setText( getResources().getString(R.string.mon) );
			break;
		case 3:
			weekDay.setText( getResources().getString(R.string.tue) );
			break;
		case 4:
			weekDay.setText( getResources().getString(R.string.wed) );
			break;
		case 5:
			weekDay.setText( getResources().getString(R.string.thu) );
			break;
		case 6:
			weekDay.setText( getResources().getString(R.string.fri) );
			break;
		case 7:
			weekDay.setText( getResources().getString(R.string.sat) );
			break;
		default:
			weekDay.setText( getResources().getString(R.string.sun) );
			break;
		}
		
		// 每次開機時根據時間發出問候語
		if (MilkBoxTTS.isFirstRun)
		{
			MilkBoxTTS.isFirstRun = false;

			// Delay 2秒後根據時間唸出問候語，2秒是為了讓上層有足夠的時間透過網路去完成讀取天氣資訊，以免主畫面還沒顯試出來就唸出問候語
			Log.d("MainActivity->onStart()", "======================================= MainActivity : Begin Greeting .....");
			Handler mHandler = new Handler();
			mHandler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					doRunInGreeting();
				}
			}, (CFG.WEATHER == null) ? 2000 : 100);
		}
		
		// 2012/03/25 Alvin : for Introduction use
		IntentFilter filter = new IntentFilter(IntroductionActivity.action);  
		registerReceiver(broadcastReceiver, filter);
		
		// 這是用來啟動 Idle Time 計時機制，請放在 onCreate() 最後，以防開機的啟始動作也被算入 Idle Time		
		doCheckTimeOut();
	}
	
	/**
	 * 2014/03/25 : Alvin
	 * for Introduction  BroadcastReceiver
	 */
	BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			// TODO Auto-generated method stub
            changeBackground(intent.getExtras().getInt("CurrentHour"),
            								   	  intent.getExtras().getString("WeatherCondStr"),
            								      intent.getExtras().getString("WeatherDescrStr"),
            								      intent.getExtras().getString("WweatherIconStr"));
            /*
            Toast.makeText(MainActivity.this, 
            		intent.getExtras().getInt("CurrentHour") + "\n" + intent.getExtras().getString("WeatherCondStr") + "\n" +  intent.getExtras().getString("WeatherDescrStr") + "\n" +  intent.getExtras().getString("WweatherIconStr"), 
            		Toast.LENGTH_SHORT).show();
    		*/
		}
	};
	
	@Override
	protected void onDestroy()
	{
		unregisterReceiver(broadcastReceiver);  
		 
		super.onDestroy();
		
		if (null != mainScreen) mainScreen.getBackground().setCallback(null);
		if (null != bmpDrawBGImg && !bmpDrawBGImg.getBitmap().isRecycled())
		{
			bmpDrawBGImg.getBitmap().recycle();
		}
		System.gc();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState)
	{
		super.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	/**
	 * 根據時間講出問候語 + Nick Name 
	 *
	 * @author Alvin Huang
	 * @date 2014/2/21 下午5:13:53
	 *               2014/03/03 ........ Alvin : 修正問候語的組合，當 NickName 不存在時就不會加上 ',' 符號
	 */
	private void doRunInGreeting()
	{
		int indexNo = (int) (Math.random() * 2); // 亂數得到 0 - 1 的值

		String speakStrs = "";
		
		// Alvin : 2014/03/03 .... 修正問候語的組合，當 NickName 不存在時就不會加上 ',' 符號
		speakStrs += ((CFG.getNickName().length() > 0) ? ( CFG.getNickName())+"，" : "");
		// get now time
		Calendar cal = Calendar.getInstance(); // 取得當下的時間
		int minuteOfDay = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE); // 轉成今天
																							// 00：00開始到目前的分鐘數
		if (minuteOfDay < 720) // 0-719 = 00:00 - 11:59
		{
			speakStrs += "早安";
		}
		else if (minuteOfDay < 1080) // 720-1079 = 12:00 - 17:59
		{
			speakStrs += "午安";
		}
		else
		// 1080-1440 = 18:00 - 23:59
		{
			speakStrs += "晚安";
		}


		// Show message
		moderatorDialog(speakStrs, indexNo + 1, 3000);

		// 唸出資訊
		MilkBoxTTS.speak(speakStrs, "FirstRunGreeting");
		Log.d("MainActivity->onStart()", "======================================= MainActivity->onStart() : Greeting End .....");
	}
	
	/**
	 * 2014/01/23 : Alvin ... 拿掉 Main Menu 畫面下按下 Back 建進入 TV Play 的功能
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT)
		{
			if (mSetupButton.isFocused())
			{
				tvButton.requestFocus();
				return true;
			}
		}
		else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
		{
			if (tvButton.isFocused())
			{
				mSetupButton.requestFocus();
				return true;
			}
		}
		else if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			// gotoTvActivity();
			// return true;
			// 2014/01/23 : Alvin ... MainMenu 畫面下按下 Back 建時不進入 TV Play 功能，同時回傳
			// false 讓 Back 失效
			return false;
		}

		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View view)
	{
		reTimeOut();
		switch (view.getId())
		{
		case R.id.groceryButton:
			gotoGroceryMainActivity();
			break;

		case R.id.tvButton:
			gotoTvActivity();
			break;

		case R.id.homedineButton:
			gotoHomedineActivity();
			break;

		case R.id.couponButton:
			gotoCouponMainActivity();
			break;

		case R.id.radioButton:
			gotoRadioActivity();
			break;

		case R.id.setupButton:
//			goActivityNoFinish(SetupActivity.class);
			goActivityNoFinish(SettingActivity.class);
			break;

		default:
			break;
		}
	}

	@Override
	public void onFocusChange(View view, boolean focused)
	{
		switch (view.getId())
		{
		case R.id.couponButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_coupon, 320, 320, 1020, 280 + 205+5);
			break;
		case R.id.groceryButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_grocery, 320, 320, 870, 625 + 205);
			break;
		case R.id.tvButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_tv, 320, 320, 140, 265 + 205);
			break;
		// case R.id.memoryButton:
		// mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_memory,
		// 320, 320, 300, 800 + 205);
		// break;
		case R.id.homedineButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_homedine, 320, 320, 650, 620 + 205);
			break;
		// case R.id.garagesaleButton:
		// mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_sale,
		// 320, 320, 560, 800 + 300);
		// break;
		// case R.id.gymButton:
		// mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_gym,
		// 320, 320, 200, 350 + 300);
		// break;
		case R.id.radioButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_music, 320, 320, 380, 255 + 205);
			break;
		case R.id.setupButton:
			mReLayoutUtil.updateImageView(moderator, R.drawable.moderator_setting, 320, 320, 1500, 260 + 205);
			break;
		default:
			break;
		}
	}

	// weather

	private class JSONWeatherTask extends AsyncTask<String, Void, Weather>
	{

		@Override
		protected Weather doInBackground(String... params)
		{
			flagForWeatherLoad = false;
			Weather weather = new Weather();
			String data = ((new WeatherHttpClient()).getWeatherData(params[0], params[1]));

			try
			{
				weather = JSONWeatherParser.getWeather(data);
				
				System.out.println("Weather [" + weather + "]");
				// Let's retrieve the icon
				weather.iconData = ((new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return weather;

		}

		@SuppressWarnings("deprecation")
		@Override
		protected void onPostExecute(Weather weather)
		{
			super.onPostExecute(weather);

			// Add : Alvin 2014/02/10 for ASRActivity
			CFG.WEATHER = weather;
			
//			load icon from weather web service
//			if (weather.iconData != null && weather.iconData.length > 0)
//			{
//				Bitmap img = BitmapFactory.decodeByteArray(weather.iconData, 0, weather.iconData.length);
//				imgView.setImageBitmap(img);
//			}

//			cityText.setText(weather.location.getCity() + "," + weather.location.getCountry());
			temp.setText("" + Math.round((weather.temperature.getTemp() - 275.15)));
//			condDescr.setText(weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescr() + ")");
			condDescr.setText(weather.currentCondition.getCondition());
			/*
			 * 
			 * temp.setText("" + Math.round((weather.temperature.getTemp() -
			 * 275.15)) + "�C"); hum.setText("" +
			 * weather.currentCondition.getHumidity() + "%"); press.setText("" +
			 * weather.currentCondition.getPressure() + " hPa");
			 * windSpeed.setText("" + weather.wind.getSpeed() + " mps");
			 * windDeg.setText("" + weather.wind.getDeg() + "�");
			 */

			// get current time hour
			SimpleDateFormat sdf = new SimpleDateFormat("HH");
			String h = sdf.format(new Date());
			int currentHour = Integer.parseInt(h);
			System.out.println("hour: "+h);
			System.out.println("weather icon: "+weather.currentCondition.getIcon());
			System.out.println("rain: "+weather.rain.getAmmount());
			
			// weather data from http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
			// currentHour: 24小時制的小時
			// weather.currentCondition.getCondition(): 表示目前天氣狀況，如 Clouds, Rain
			// weather.currentCondition.getDescr(): 表示天氣詳細描述，如 few clouds, sky is clear
			// weather.currentCondition.getIcon(): 天氣 icon 檔名，如 01d, 01n
			changeBackground(currentHour, weather.currentCondition.getCondition(), weather.currentCondition.getDescr(), weather.currentCondition.getIcon());
			flagForWeatherLoad = true;
		}
		

	}

	public int getRandomImage(int[] images) {
		Random generator = new Random();

		return images[generator.nextInt(images.length)];
		
	}
	
	public void changeBackground(int currentHour, String weatherCondStr, String weatherDescrStr, String weatherIconStr) {
		
		final int[] bgSunDayImages = {R.drawable.main_bg_sun_d, R.drawable.main_bg_sun_d2};
		final int[] bgSunNightImages = {R.drawable.main_bg_sun_n, R.drawable.main_bg_sun_n2};
		
		final int[] bgCloudDayImages = {R.drawable.main_bg_cloud_d, R.drawable.main_bg_cloud_d2};
		final int[] bgCloudNightImages = {R.drawable.main_bg_cloud_n, R.drawable.main_bg_cloud_n2};
		
		final int[] bgRainDayImages = {R.drawable.main_bg_rain_d, R.drawable.main_bg_rain_d2};
		final int[] bgRainNightImages = {R.drawable.main_bg_rain_n, R.drawable.main_bg_rain_n2};
		
		final int[] bgSnowDayImages = {R.drawable.main_bg_snow_d, R.drawable.main_bg_snow_d2,R.drawable.main_bg_snow_d3};
		final int[] bgSnowNightImages = {R.drawable.main_bg_snow_n, R.drawable.main_bg_snow_n2};
		
		final int[] bgThunderDayImages = {R.drawable.main_bg_thunder_d, R.drawable.main_bg_thunder_d2};
		final int[] bgThunderNightImages = {R.drawable.main_bg_thunder_n, R.drawable.main_bg_thunder_n2};
		
		HashMap<String, Integer> weatherIcon = new HashMap<String, Integer>();
		weatherIcon.put("01d",R.drawable.weather_sun_d);
		weatherIcon.put("01n",R.drawable.weather_sun_n);
		weatherIcon.put("02d",R.drawable.weather_partlysun_d);
		weatherIcon.put("02n",R.drawable.weather_partlysun_n);
		weatherIcon.put("03d",R.drawable.weather_cloudy);
		weatherIcon.put("03n",R.drawable.weather_cloudy);
		weatherIcon.put("04d",R.drawable.weather_cloudy);
		weatherIcon.put("04n",R.drawable.weather_cloudy);
		weatherIcon.put("09d",R.drawable.weather_rain);
		weatherIcon.put("09n",R.drawable.weather_rain);
		weatherIcon.put("10d",R.drawable.weather_partlyrain_d);
		weatherIcon.put("10n",R.drawable.weather_partlyrain_n);
		weatherIcon.put("11d",R.drawable.weather_thunder);
		weatherIcon.put("11n",R.drawable.weather_thunder);
		weatherIcon.put("13d",R.drawable.weather_snow);
		weatherIcon.put("13n",R.drawable.weather_snow);
		weatherIcon.put("50d",R.drawable.weather_mist);
		weatherIcon.put("50n",R.drawable.weather_mist);
		
		// change main screen background for weather
		// mainScreen = (RelativeLayout) findViewById(R.id.mainScreen); // Alvin 2014/03/20 : 移至 onCreate()

		try {
			
			if ("Clouds".equalsIgnoreCase(weatherCondStr))
			{
				if("few clouds".equalsIgnoreCase(weatherDescrStr) ||
						"sky is clear".equalsIgnoreCase(weatherDescrStr) ) {
					condDescr.setText( getResources().getString(R.string.weather_sun) ) ; // andywu 2014.03.28 更改顯示內容+翻譯
					if(currentHour > 6 && currentHour <= 16)
						bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSunDayImages)));
					else
						bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSunNightImages)));	
				}else {
					condDescr.setText( getResources().getString(R.string.weather_clouds) ) ; // andywu 2014.03.28 更改顯示內容+翻譯
					if(currentHour > 6 && currentHour <= 16)
						bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgCloudDayImages)));
					else
						bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgCloudNightImages)));
				}
				
				mainScreen.setBackgroundDrawable(bmpDrawBGImg);
				imgView.setImageResource(weatherIcon.get(weatherIconStr));
			}
			else if ("Thunderstorm".equalsIgnoreCase(weatherCondStr))
			{
				condDescr.setText( getResources().getString(R.string.weather_thunderstorm) ) ; // andywu 2014.03.28 更改顯示內容+翻譯
				if(currentHour > 6 && currentHour <= 16)
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgThunderDayImages)));
				else
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgThunderNightImages)));
				
				mainScreen.setBackgroundDrawable(bmpDrawBGImg);
				imgView.setImageResource(weatherIcon.get(weatherIconStr));
			}
			else if ("Rain".equalsIgnoreCase(weatherCondStr) || "Drizzle".equalsIgnoreCase(weatherCondStr))
			{
				condDescr.setText( getResources().getString(R.string.weather_rain) ) ; // andywu 2014.03.28 更改顯示內容+翻譯
				if(currentHour > 6 && currentHour <= 16)
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgRainDayImages)));
				else
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgRainNightImages)));

				mainScreen.setBackgroundDrawable(bmpDrawBGImg);
				imgView.setImageResource(weatherIcon.get(weatherIconStr));
			}
			else if ("Snow".equalsIgnoreCase(weatherCondStr) )
			{
				condDescr.setText( getResources().getString(R.string.weather_snow) ) ; // andywu 2014.03.28 更改顯示內容+翻譯
				if(currentHour > 6 && currentHour <= 16)
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSnowDayImages)));
				else
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSnowNightImages)));

				mainScreen.setBackgroundDrawable(bmpDrawBGImg);
				imgView.setImageResource(weatherIcon.get(weatherIconStr));
			}
			else
			{
				if ("mist".equalsIgnoreCase(weatherDescrStr) ) // andywu 2014.03.28 更改顯示內容+翻譯
					condDescr.setText( getResources().getString(R.string.weather_mist) ) ;
				else
					condDescr.setText( getResources().getString(R.string.weather_sun) ) ;
				if(currentHour > 6 && currentHour <= 16)
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSunDayImages)));
				else
					bmpDrawBGImg = new BitmapDrawable(getResources().openRawResource(this.getRandomImage(bgSunNightImages)));

				mainScreen.setBackgroundDrawable(bmpDrawBGImg);
				imgView.setImageResource(weatherIcon.get(weatherIconStr));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private class JSONForecastWeatherTask extends AsyncTask<String, Void, WeatherForecast>
	{

		@Override
		protected WeatherForecast doInBackground(String... params)
		{

			String data = ((new WeatherHttpClient()).getForecastWeatherData(params[0], params[1], params[2]));
			WeatherForecast forecast = new WeatherForecast();
			try
			{
				forecast = JSONWeatherParser.getForecastWeather(data);
				System.out.println("Weather [" + forecast + "]");
				// Let's retrieve the icon
				// weather.iconData = ( (new
				// WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));

			}
			catch (JSONException e)
			{
				e.printStackTrace();
			}
			return forecast;
		}
	}
	/**
	* @author andywu
	* @date 2014.02.24
	* 加入讀取使用者基本資料的動作
	*/
	private class LoadCustomerTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

		String customerId;
		HashMap<String, String> customer;
		Object[] addressList;
		ArrayList<Address> address;
		Object[] creditcardList;
		ArrayList<CreditCard> creditcards;
		
		LoadCustomerTask(String id) {
			customerId = id;
		}
		
		@Override
		protected Integer doInBackground(String... params) {	
			
			try {
				customer = CFG.getRpcClient().customerInfo(customerId);
//				addressList = CFG.getRpcClient().customerAddressList(customerId);
//				address = APIParser.parserAddress(addressList);
//				creditcardList = CFG.getRpcClient().checkoutexdGetCards(customerId);
//				creditcards = APIParser.parserCreditCard(creditcardList);
				
			} catch (XMLRPCException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (customer.get("firstname") != null || ("").equals(customer.get("firstname")) )
			{
				CFG.sys_FirstName = customer.get("firstname") ;
			}
			if (customer.get("title") != null || ("").equals(customer.get("title")) )
			{
				CFG.sys_Title = customer.get("title") ;
			}				

//			Log.d("andy", "LoadCustomerTask OKOK") ;
			return null;
		}
		
	}
	
	/**
	* @author Wesley Li
	* @date 2014.03.05
	* 在 main menu 背景圖還沒載入時，將 OK (enter) 鍵攔下
	* 避免執行 TV, Audio activity 再快速切回 main menu，來回這樣切換造成 app 當掉
	*/
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {

		if (e.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
		    if(flagForWeatherLoad == false)
		    	return true;
		}
		return super.dispatchKeyEvent(e);
	}
	
//	@Override
//	public void doSomething() {
//		// TODO Auto-generated method stub
//		System.out.println("run mainActivity doSomething()");
//		moderatorDialog("do something...", 1, 3000);
//		MilkBoxTTS.speak("do something", "dosomething");
//		super.doSomething();
//	}

	@Override
	protected void onPause() {
		flagForBackgroundLoad = false;
		super.onPause();
	}

	@Override
	protected void onResume() {
		flagForBackgroundLoad = true;
		doCheckTimeOut();
		super.onResume();
	}

	@Override
	protected void onRestart() {
		flagForBackgroundLoad = true;
		doCheckTimeOut();
		super.onRestart();
	}
	
	
}
