package com.kentec.milkbox.task;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.kentec.milkbox.BaseActivity;
import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.AsyncTaskCode;
import com.kentec.milkbox.utils.DEBUG;

public abstract class BaseTask extends AsyncTask<String, Void, Integer> implements AsyncTaskCode {
	
	protected BaseActivity mActivity;
	protected ProgressDialog mProgressDialog;
	
	public BaseTask(BaseActivity activity) {
		mActivity = activity;
		mProgressDialog = new ProgressDialog(activity);
		execute();
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if (mActivity.isFinishing())
			return;
		
		try {
			mProgressDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Integer doInBackground(String... arg0) {
		try {
			return doInBackground();
		} catch (Exception e) {
			DEBUG.e("BaseTask", e.toString());
			return ERROR;
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		if (mActivity.isFinishing())
			return;
		
		mProgressDialog.dismiss();
		if (result == OK) {
			success();
		}
		else if (result == ERROR) {
			mActivity.showMsg(R.string.error);
		}else {
			fail(result);
		}
	}

	protected abstract int doInBackground() throws Exception;

	protected abstract void success();

	protected abstract void fail(int failCode);	
}
