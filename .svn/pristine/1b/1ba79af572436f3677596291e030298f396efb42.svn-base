package com.kentec.milkbox;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import com.kentec.milkbox.homedine.utils.AnimUtil;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

public class BootActivity extends BaseActivity
{

	private ReLayoutUtil reLayoutUtil;
	private AnimUtil mAnimUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_boot);

		mAnimUtil = new AnimUtil(this);
		reLayoutUtil = reLayout();

		View moderator_hi_bg = findViewById(R.id.moderator_hi_bg);
		reLayoutUtil.relative(moderator_hi_bg, ReLayoutUtil.srcDisW, 534, 0, 500);
		mAnimUtil.AlphaIn(moderator_hi_bg, 500);

		View moderator_hi = findViewById(R.id.moderator_hi);
		reLayoutUtil.relative(moderator_hi, 744, 956, 901, 102);
		mAnimUtil.AlphaIn(moderator_hi, 500);

		View moderator_hi_bubble = findViewById(R.id.moderator_hi_bubble);

		final View moderator_hi_bubble_title = findViewById(R.id.moderator_hi_bubble_title);
		final View moderator_hi_bubble_text = findViewById(R.id.moderator_hi_bubble_text);
		moderator_hi_bubble_title.setVisibility(View.INVISIBLE);
		moderator_hi_bubble_text.setVisibility(View.INVISIBLE);

		reLayoutUtil.relative(moderator_hi_bubble, 904, 505, 106, 51);
		reLayoutUtil.relative(findViewById(R.id.bubbleTextLinear), 700, 500);
		mAnimUtil.RightIn(moderator_hi_bubble, 600, 0.3f, new AnimationListener()
		{
			@Override
			public void onAnimationEnd(Animation animation)
			{
				mAnimUtil.TopIn(moderator_hi_bubble_title, 800);

				mAnimUtil.TopIn(moderator_hi_bubble_text, 1000);
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}

			@Override
			public void onAnimationStart(Animation animation)
			{
			}
		});
		moderator_hi_bubble.setVisibility(View.VISIBLE);

		new CountDownTimer(3200, 3200)
		{

			/**
			 *  2014/01/23 : 修改 BootActivity 後不會自己進入 TV Play，而是直接到 Main Menu mode
			 */
			@Override
			public void onFinish()
			{								
				finish();
				
				// gotoTvActivity(); // Modify by Alvin
				gotoMainActivity(); // Add by by Alvin
				
				/*
				if (!DEBUG.MODE)
					startActivity(new Intent(BootActivity.this, MainActivity.class)); // Add : Alvin
					// startActivity(new Intent(BootActivity.this, TvPlayerActivity.class));
				else
					startActivity(new Intent(BootActivity.this, MainActivity.class));

				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				*/
			}

			@Override
			public void onTick(long millisUntilFinished)
			{
			}

		}.start();
	}

	@Override
	protected void onResume()
	{
		super.onResume();
	}

	/**
	 * Add: Alvin 2014/02/08 ... Activity 釋放時同時釋放 TTS engine 所使用的資源
	 */
	@Override
	protected void onDestroy()
	{
		// MilkBoxTTS.releaseTTS();

		// TODO Auto-generated method stub
		super.onDestroy();
	}
}
