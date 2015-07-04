package com.kentec.milkbox;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager.LayoutParams;
import com.kentec.milkbox.coupon.CouponMainActivity;
import com.kentec.milkbox.grocery.GroceryMainActivity;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.radio.RadioPlayerActivity;
import com.kentec.milkbox.tv.TvPlayerActivity;
import com.kentec.milkbox.utils.CheckClientUtil;
import com.kentec.milkbox.utils.CheckClientUtil.OnCheckListener;
import com.kentec.milkbox.view.MessageDialog;
import com.kentec.milkbox.view.OkDialog;

public class BaseActivity extends CheckTimeOutActivity
{

	private int mDisW;
	private int mDisH;

	private double mDisWsc;
	private double mDisHsc;

	private OkDialog mOkDialog;
	private MessageDialog mMsgDialog;
	private MessageDialog mMsgNoImgDialog;

	protected BaseActivity mActivity;
	protected SharedPreferences mSharedPreferences;

	protected ReLayoutUtil reLayoutUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// 為了要拿到 boxconfig 的 timezone 資料
		// 給 mainActivity 設定時區用
		new CheckClientUtil(this, null);
		
		getWindow().addFlags(LayoutParams.FLAG_KEEP_SCREEN_ON);

		mActivity = this;
		mSharedPreferences = getSharedPreferences("preference", 0);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		mDisW = dm.widthPixels;
		mDisH = dm.heightPixels;
		mDisWsc = (double) mDisW / (double) ReLayoutUtil.srcDisW;
		mDisHsc = (double) mDisH / (double) ReLayoutUtil.srcDisH;

		mMsgDialog = new MessageDialog(this);
		mOkDialog = new OkDialog(this);
		mMsgNoImgDialog = new MessageDialog(this, false);

		reLayoutUtil = new ReLayoutUtil(mActivity);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_F12)
		{
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void showMsg(int resourceId)
	{
		if (!this.isFinishing())
		{
			mMsgDialog.show(getString(resourceId));
			MilkBoxTTS.speak(getResources().getString(resourceId),"showMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void showMsg(String msg)
	{
		if (!this.isFinishing())
		{
			mMsgDialog.show(msg);
			MilkBoxTTS.speak(msg,"showMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void showOkMsg(int resourceId)
	{
		if (!this.isFinishing())
		{
			mOkDialog.show(getString(resourceId));
			MilkBoxTTS.speak(getResources().getString(resourceId),"showOkMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void showOkMsg(String text)
	{
		if (!this.isFinishing())
		{
			mOkDialog.show(text);
			MilkBoxTTS.speak(text,"showOkMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void showNoImgMsg(int resourceId)
	{
		if (!this.isFinishing())
		{
			mMsgNoImgDialog.show(getString(resourceId));
			MilkBoxTTS.speak(getResources().getString(resourceId),"showNoImgMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void showNoImgMsg(String text)
	{
		if (!this.isFinishing())
		{
			mMsgNoImgDialog.show(text);
			MilkBoxTTS.speak(text,"showNoImgMsg"); // andywu 2014.02.20 唸出對話框的文字
		}
	}

	public void goActivityNoFinish(Class<?> class1)
	{
		startActivity(new Intent(this, class1));
	}

	public void goActivityNoFinish(Class<?> class1, Bundle b)
	{
		Intent i = new Intent(this, class1);
		i.putExtras(b);
		startActivity(i);
	}

	public void gotoTvActivity()
	{
		finish();
		startActivity(new Intent(BaseActivity.this, TvPlayerActivity.class));
		overridePendingTransition(android.R.anim.fade_in,
		android.R.anim.fade_out);
	}

	public void gotoMainActivity()
	{
		finish();
		startActivity(new Intent(this, MainActivity.class));
		overridePendingTransition(android.R.anim.fade_in,
		android.R.anim.fade_out);
	}

	public void gotoHomedineActivity()
	{
		// finish();
		// startActivity(new Intent(BaseActivity.this,
		// HomeDineDeliveryActivity.class));
		new CheckClientUtil(this, new OnCheckListener()
		{
			@Override
			public void OK()
			{
				goActivityNoFinish(HomeDineDeliveryActivity.class);
			}
		});
		// overridePendingTransition(android.R.anim.fade_in,
		// android.R.anim.fade_out);
	}

	public void gotoGroceryMainActivity()
	{
		// finish();
		// startActivity(new Intent(BaseActivity.this,
		// GroceryMainActivity.class));

		new CheckClientUtil(this, new OnCheckListener()
		{
			@Override
			public void OK()
			{
				goActivityNoFinish(GroceryMainActivity.class);
			}
		});

		// overridePendingTransition(android.R.anim.fade_in,
		// android.R.anim.fade_out);
	}

	public void gotoCouponMainActivity()
	{
		// finish();
		// startActivity(new Intent(BaseActivity.this,
		// CouponMainActivity.class));

		new CheckClientUtil(this, new OnCheckListener()
		{
			@Override
			public void OK()
			{
				goActivityNoFinish(CouponMainActivity.class);
			}
		});

		// overridePendingTransition(android.R.anim.fade_in,
		// android.R.anim.fade_out);
	}

	public void gotoRadioActivity()
	{
		new CheckClientUtil(this, new OnCheckListener()
		{
			@Override
			public void OK()
			{
				goActivityNoFinish(RadioPlayerActivity.class);
			}
		});
	}

	public void goActivityResult(Class<?> class1, Bundle b, int code)
	{
		Intent i = new Intent(this, class1);
		i.putExtras(b);
		startActivityForResult(i, 1);
	}

	public int getDisW()
	{
		return mDisW;
	}

	public void setDisW(int disW)
	{
		this.mDisW = disW;
	}

	public double getDisWsc()
	{
		return mDisWsc;
	}

	public void setDisWsc(double disWsc)
	{
		this.mDisWsc = disWsc;
	}

	public double getDisHsc()
	{
		return mDisHsc;
	}

	public int getDisH()
	{
		return mDisH;
	}

	public SharedPreferences getSP()
	{
		return mSharedPreferences;
	}

	public String getCustomerId()
	{
		return mSharedPreferences.getString(CFG.PREF_CUSTOMER_ID, null);
	}

	public String getCouponCartId()
	{
		return mSharedPreferences.getString(CFG.PREF_COUPON_CART_ID, null);
	}

	public ReLayoutUtil reLayout()
	{
		return reLayoutUtil;
	}

	/**
	 * 取得 Project 的 Version Code ( 定義在 AndroidManifest.xml 內的
	 * android:versionCode 值)
	 * 
	 * @param context
	 * @return
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/19 上午9:08:45
	 */
	public int getVerCode(Context context)
	{
		int verCode = -1;
		try
		{
			verCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		}
		catch (NameNotFoundException e)
		{
			Log.e("hATM-Milk", e.getMessage());
		}
		return verCode;
	}

	/**
	 * 取得 Project 的 Version Name ( 定義在 AndroidManifest.xml 內的
	 * android:versionName 值)
	 * 
	 * @param context
	 * @return
	 * 
	 * @author Alvin Huang
	 * @date 2013/12/20 上午10:20:22
	 */
	public String getVerName(Context context)
	{
		String verName = "";
		try
		{
			verName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		}
		catch (NameNotFoundException e)
		{
			Log.e("hATM-Milk", e.getMessage());
		}
		return verName;
	}

}
