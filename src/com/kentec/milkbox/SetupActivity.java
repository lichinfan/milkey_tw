package com.kentec.milkbox;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlrpc.android.XMLRPCException;

import MilkFactorySettingUtil.MilkFactorySetting;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kentec.milkbox.asr.ASRActivity;
import com.kentec.milkbox.checkout.api.APIParser;
import com.kentec.milkbox.checkout.data.Address;
import com.kentec.milkbox.checkout.data.CreditCard;
import com.kentec.milkbox.comm.AsyncTaskCode;

public class SetupActivity extends ASRActivity
{
	MilkFactorySetting mfs;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup);

		TextView tv = (TextView) findViewById(R.id.textView1);
		final EditText et1 = (EditText) findViewById(R.id.editText1);
		final EditText et2 = (EditText) findViewById(R.id.editText2);
		Button btn = (Button) findViewById(R.id.button1);

		mfs = new MilkFactorySetting(this);
		
		tv.setText("NickName: " + CFG.sys_NickName + "\n" +
				           "IMEI: " + mfs.getIMEI() + "\n" + 
					       "Android ID: " + mfs.getAndroid_ID() + "\n" + 
				           "Eth MAC Addr: " + mfs.getEth_MacAddr() + "\n" + 
					       "WiFi MAC Addr: " + mfs.getWiFi_MacAddr() + "\n" + 
				           "Build Serial: " + mfs.getBuildSerial() + "\n"	+ 
					       "UUID: " + mfs.getUUID());

		et1.setText(mfs.getServer1());
		et2.setText(mfs.getNickName());
		// et.setText("http://emilk.kenmec.com/setupbox");
		
		String customerId=this.getSP().getString(CFG.PREF_CUSTOMER_ID, null);
		System.out.println("customer id: "+customerId);
		new LoadTask(customerId).execute();
		
		btn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				String host = et1.getText().toString().trim();
				mfs.setServer1(host);
				mfs.setNickName(et2.getText().toString().trim());
				try
				{
					// V1101版開始 mfs.saveData需加上 try/catch，用來在底層檔案存取發生異常時能觸發錯誤機制
					// 以加強對於底層資料檔存取的正確與否加以判斷
					mfs.saveData();
					CFG.initHost(host);
					showMsg(CFG.HOST + " is success");
				}
				catch (Throwable e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				reTimeOut();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
			System.out.println("customer info: "+customer);
			System.out.println("customer firstname: "+customer.get("firstname"));
			System.out.println("customer address size: "+address.size());
			if(address.size()>0)
				System.out.println("customer address: "+address.get(0));
			if(creditcards.size()>0) {
				System.out.println("customer creditcards: "+creditcards.get(0).getNumber());
				System.out.println("customer creditcards: "+creditcards.get(0).getType());
				
			}
			return null;
		}
		
	}

}