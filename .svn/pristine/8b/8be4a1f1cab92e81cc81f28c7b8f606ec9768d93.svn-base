package com.kentec.milkbox.homedine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;

import com.kentec.milkbox.MilkBoxTTS;
import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.asr.HomeDineASRCreater;
import com.kentec.milkbox.homedine.adapter.HomeDineAdapterGroup;
import com.kentec.milkbox.homedine.data.HomeDineCache;
import com.kentec.milkbox.homedine.data.HomeDineCode;
import com.kentec.milkbox.homedine.data.HomeDineProperty;
import com.kentec.milkbox.homedine.data.MealsList;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.method.HomeDineListener;
import com.kentec.milkbox.homedine.method.HomeDineMethod;
import com.kentec.milkbox.homedine.view.HomeDineDeliveryViewGroup;

public class HomeDineDeliveryActivity extends ASRActivity {

	private HomeDineProperty mProperty;
	private HomeDineDeliveryViewGroup mViewGroup;
	private HomeDineAdapterGroup mAdapterGroup;
	private HomeDineMethod mMethod;
	private HomeDineCache mCache;
	private HomeDineListener mListener;
	private MealsList mMealsList;
	private HashMap<String, ArrayList<OrderData>> mOrderList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		doCheckTimeOut();

		mProperty = new HomeDineProperty(this);
		mViewGroup = new HomeDineDeliveryViewGroup(this);
		mAdapterGroup = new HomeDineAdapterGroup();
		mMethod = new HomeDineMethod(this);
		mCache = new HomeDineCache(this);
		mListener = new HomeDineListener(this);
		mMealsList = new MealsList();
		mOrderList = new HashMap<String, ArrayList<OrderData>>();

		mMethod.loadCoupon();
		
		new HomeDineASRCreater().init(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		case HomeDineCode.RESULT_CHECKOUT:
			mMethod.checkoutOrder(resultCode, data);
			break;
		}
	}

	public HomeDineAdapterGroup getAdapterGroup() {
		return mAdapterGroup;
	}

	public HomeDineDeliveryViewGroup getViewGroup() {
		return mViewGroup;
	}

	public HomeDineMethod getMethod() {
		return mMethod;
	}

	public HomeDineCache getCache() {
		return mCache;
	}

	public HomeDineProperty getProperty() {
		return mProperty;
	}

	public HomeDineListener getListener() {
		return mListener;
	}

	public MealsList getMealsList() {
		return mMealsList;
	}

	public HashMap<String, ArrayList<OrderData>> getOrderList() {
		return mOrderList;
	}
	
	public void openCart() {
		getMethod().showMealList();
	}
	
	public void openOrderHistory() {
		getMethod().showOrderHistory();
	}	

	public void openProgress() {
		getMethod().showProgress();
	}	
	
	public void openCalorie() {
		getMethod().changCalores();
	}
	
	/**
	 * @author Wesley
	 * 2014.02.21
	 * 在 Takeout 語音呼叫 help
	 */
	public void doTakeoutHelpMenu()
	{
		int indexNo = (int) (Math.random() * 2) + 1; // 亂數得到 1 - 2 的值

		/*
		 You can say:
		"Check Out"、"Progress"、"Order History"、"Calories"
		 */

		// Show message
		String strMsg[] = { "What can I say", 
				                          ", Help", 
				                          ", Command List", 
				                          ", Checkout", 
				                          ", Main Menu", 
				                          ", Order History", 
				                          ", Progress", 
				                          ", Calories"};
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
		* 
		* 這邊由於每個都有唸，而且位置足夠，所以全部以大寫方式呈列。
		* 未來若是不夠，可以把Help、Command List，變成不要唸&變成小字 
		*/
		String dialogStr = "<big>● What can I say</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Help</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Command List</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Checkout</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Main Menu</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Order History</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Progress</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>"
						 + "<big>● Calories</big><sub>&nbsp;&nbsp;<small><small></small></small></sub><br>" ;
		
		moderatorDialog(dialogStr, indexNo + 2, 8000);
		
		// 唸出資訊
		ArrayList<String> speakStrs = new ArrayList<String>();
		speakStrs.add("You can say:");
		Collections.addAll(speakStrs, strMsg);
		MilkBoxTTS.speak(speakStrs, "echoTakeoutWhatCanISay");		
	}

	@Override
	protected void onResume() {
		doCheckTimeOut();
		super.onPause();
	}

	@Override
	protected void onRestart() {
		doCheckTimeOut();
		super.onRestart();
	}
}