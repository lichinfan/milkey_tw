/**
 * 
 */
package com.kentec.milkbox.introduction;

/**
 * @author Alvin Huang
 *
 */
public abstract class IntrouctionScriptAdapter
{
	private String strEcho[];   // 對話框的顯示內容
	private String strSpeak[]; // TTS 發聲用的內容
	private int imageResId;    // 要顯示出來的 ImageView resource id 
	private int showTime;       // 展示時間 ms 
	private int locationX;        // 要顯示的 X 座標
	private int locationY;        // 要顯示的 Y 座標
	private int currentHour;  // for MainActivity.java->changeBackground argument, 時間：24小時制的小時
	private String weatherCondStr; // for MainActivity.java->changeBackground argument, 表示目前天氣狀況，如 Clouds, Rain
	private String weatherDescrStr; // for MainActivity.java->changeBackground argument, 表示天氣詳細描述，如 few clouds, sky is clear
	private String weatherIconStr; // for MainActivity.java->changeBackground argument, 天氣 icon 檔名，如 01d, 01n
	
	public IntrouctionScriptAdapter(String echoStr[], String speakStr[], int ms, int resId, int x, int y, int currentHour, String weatherCondStr, String weatherDescrStr, String weatherIconStr)
	{
		this.strEcho = echoStr;
		this.strSpeak = speakStr;
		this.showTime = ms;
		this.imageResId = resId;
		this.locationX = x;
		this.locationY = y;
		this.currentHour = currentHour;
		this.weatherCondStr = weatherCondStr;
		this.weatherDescrStr = weatherDescrStr;
		this.weatherIconStr = weatherIconStr;
	}
	
	public String[] getEchoStr()
	{
		return this.strEcho;
	}
	
	public String[] getSpeakstr()
	{
		return this.strSpeak;
	}
	
	public int getShowTime()
	{
		return this.showTime;
	}
	
	public int getimageResId()
	{
		return this.imageResId;
	}
	
	public int getLocationX()
	{
		return this.locationX;
	}
	
	public int getLocationY()
	{
		return this.locationY;
	}
	
	public int getCurrentHour()
	{
		return this.currentHour;
	}
	
	public String getWeatherCondStr()
	{
		return this.weatherCondStr;
	}
	
	public String getWeatherDescrStr()
	{
		return this.weatherDescrStr;
	}
	
	public String getWweatherIconStr()
	{
		return this.weatherIconStr;
	}
		
	public abstract void action(int arg);
}
