package com.kentec.milkbox;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NoNetworkActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_msg);

		reLayoutUtil.linear(findViewById(R.id.mainLinear), 720, 480);
		LinearLayout okLinear = (LinearLayout) findViewById(R.id.okLinear);
		okLinear.setOnClickListener(new LinearLayout.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TextView msgTextView = (TextView) findViewById(R.id.msgTextView);
		msgTextView.setText(R.string.error_internet);
	}
}