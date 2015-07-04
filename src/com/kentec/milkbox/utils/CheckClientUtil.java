package com.kentec.milkbox.utils;

import java.util.HashMap;

import org.xmlrpc.android.XMLRPCFault;

import MilkFactorySettingUtil.MilkFactorySetting;
import android.content.Context;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.CFG;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.core.RpcClient;
import com.kentec.milkbox.homedine.utils.NetworkUtil;

public class CheckClientUtil extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {

	private OnCheckListener listener;
	// private MilkFactorySetting mfs;
	private BaseActivity mActivity;

	public CheckClientUtil(BaseActivity activity, OnCheckListener listener) {
		this.mActivity = activity;
		this.listener = listener;
		this.execute();
	}

	@Override
	protected Integer doInBackground(String... arg0)
	{

		String host = null;
		String imei = null;
		try
		{
			// Modidy@Alvin ... 2014/02/27 ... 因為 host、imei 已經在 MainApp.java 中從 mfs 讀到 CFG 中並且進行防呆處理，所以改成直接從 CFG 讀取需要的值
			// mfs = new MilkFactorySetting(mActivity);
			// host = mfs.getServer1();
			// imei = mfs.getIMEI();
			host = CFG.HOST;
			imei = CFG.IMEI;
			
		}
		catch (Exception e)
		{
			if (DEBUG.MODE)
			{
				TelephonyManager tm = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
				host = "http://testmagento.now.to";
				imei = tm.getDeviceId();
			}
			else
			{
				return ERROR;
			}
		}

		try {
			if (!NetworkUtil.haveInternet(mActivity)) {
				return NETWORK_ERROR;
			}

			if (host == null || "".equals(host)) {
				return NO_HOST;
			}

			CFG.initHost(host);
			CFG.IMEI = imei;

			RpcClient client = CFG.getRpcClient();
			
			HashMap<?, ?> boxconfig = CFG.getRpcClient().boxconfigInfoByImei(imei);
			String timezone = (String) boxconfig.get("timezone");
			System.out.println("infobyimei timezone: "+timezone);
			CFG.TIMEZONE = timezone;
			
			long time = System.currentTimeMillis() - mActivity.getSP().getLong(CFG.PREF_LAST_CHECK_TIME, 0);
			if (time > CFG.CHECK_CLIENT_TIMEOUT) {
				HashMap<?, ?> row = client.boxconfigCustomerInfo(imei);
				String customerId = (String) row.get("entity_id");
				System.out.println("check customerid: ");
				System.out.println("check customerid: "+customerId);
				CFG.CUSTOMERID=(String) row.get("entity_id");
				if (customerId == null || "".equals(customerId)) {
					return ILLEGAL_CLIENT;
				}
				

				String cid = mActivity.getSP().getString(CFG.PREF_CUSTOMER_ID, "");
				if (!cid.equals(customerId)) {
					if (!mActivity.getSP().edit().putString(CFG.PREF_CUSTOMER_ID, customerId).commit()) {
						return ERROR;
					}
					if (!mActivity.getSP().edit().putLong(CFG.PREF_LAST_CHECK_TIME, System.currentTimeMillis()).commit()) {
						return ERROR;
					}
				}
			}

			return OK;
		} catch (XMLRPCFault e) {
			return ILLEGAL_CLIENT;

		} catch (Exception e) {
			DEBUG.c(e);
		}
		return ERROR;
	}

	@Override
	protected void onPostExecute(Integer result) {
		switch (result) {
		case OK:
			if (listener != null)
				listener.OK();
			break;

		case ERROR:
			mActivity.showMsg(R.string.error);
			break;

		case NETWORK_ERROR:
			mActivity.showMsg(R.string.error_internet);
			break;

		case ILLEGAL_CLIENT:
			mActivity.showMsg(R.string.msg_illegal_client);
			break;

		case NO_HOST:
			mActivity.showMsg(R.string.msg_host_error);
			break;
		}

	}

	public interface OnCheckListener {
		public void OK();

	}
}