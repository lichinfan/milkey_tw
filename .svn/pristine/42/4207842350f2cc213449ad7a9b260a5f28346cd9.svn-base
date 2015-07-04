package com.kentec.milkbox.homedine.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;

import com.kentec.milkbox.BaseActivity;

public class AnimUtil {

	private BaseActivity mActivity;

	public AnimUtil(BaseActivity activity) {
		mActivity = activity;
	}

	public void AlphaIn(View view, int ms) {
		AlphaAnimation alphaIn = new AlphaAnimation(0.0f, 1.0f);
		alphaIn.setDuration(ms);
		view.setVisibility(View.VISIBLE);
		view.startAnimation(alphaIn);
	}

	public void AlphaOut(final View view, int ms) {
		AlphaAnimation alphaOut = new AlphaAnimation(1.0f, 0.0f);
		alphaOut.setDuration(ms);
		alphaOut.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				view.setVisibility(View.GONE);
			}
		});
		view.startAnimation(alphaOut);
	}

	public void TopIn(final View view, int ms) {
		TranslateAnimation topIn = new TranslateAnimation(0, 0, -mActivity.getDisH(), 0);
		topIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				view.setVisibility(View.VISIBLE);
			}
		});
		topIn.setDuration(ms);
		view.startAnimation(topIn);
	}

	public void TopOut(final View view, int ms) {
		if (view.getVisibility() == View.VISIBLE) {
			TranslateAnimation topOut = new TranslateAnimation(0, 0, 0, -mActivity.getDisH());
			topOut.setDuration(ms);
			topOut.setAnimationListener(new AnimationListener() {

				@Override
				public void onAnimationStart(Animation arg0) {

				}

				@Override
				public void onAnimationRepeat(Animation arg0) {

				}

				@Override
				public void onAnimationEnd(Animation arg0) {
					view.setVisibility(View.GONE);
				}
			});
			view.startAnimation(topOut);
		}
	}

	public void BottomIn(final View view, int ms) {
		TranslateAnimation bottomIn = new TranslateAnimation(0, 0, mActivity.getDisH(), 0);
		bottomIn.setDuration(ms);
		bottomIn.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation arg0) {

			}

			@Override
			public void onAnimationRepeat(Animation arg0) {

			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				view.setVisibility(View.VISIBLE);
			}
		});
		view.startAnimation(bottomIn);
	}

	public void BottomOut(View view, int ms) {
		TranslateAnimation bottomOut = new TranslateAnimation(0, 0, 0, mActivity.getDisH());
		bottomOut.setDuration(ms);
		view.startAnimation(bottomOut);
	}

	public void LeftIn(View view, int ms) {
		TranslateAnimation leftIn = new TranslateAnimation(-mActivity.getDisW(), 0, 0, 0);
		leftIn.setDuration(ms);
		view.startAnimation(leftIn);
		view.setVisibility(View.VISIBLE);
	}

	public void LeftOut(View view, int ms) {
		TranslateAnimation leftOut = new TranslateAnimation(0, -mActivity.getDisW(), 0, 0);
		leftOut.setDuration(ms);
		view.startAnimation(leftOut);
	}

	public void RightIn(View view, int ms) {
		TranslateAnimation rightIn = new TranslateAnimation(mActivity.getDisW(), 0, 0, 0);
		rightIn.setDuration(ms);
		view.startAnimation(rightIn);
		view.setVisibility(View.VISIBLE);
	}

	public void RightIn(View view, int ms, float ratio) {
		TranslateAnimation rightIn = new TranslateAnimation(mActivity.getDisW() * ratio, 0, 0, 0);
		rightIn.setDuration(ms);
		view.startAnimation(rightIn);
		view.setVisibility(View.VISIBLE);
	}

	public void RightIn(View view, int ms, float ratio, AnimationListener animationListener) {
		TranslateAnimation rightIn = new TranslateAnimation(mActivity.getDisW() * ratio, 0, 0, 0);
		rightIn.setDuration(ms);
		rightIn.setAnimationListener(animationListener);
		view.startAnimation(rightIn);
		view.setVisibility(View.VISIBLE);
	}

	public void RightOut(View view, int ms) {
		TranslateAnimation rightOut = new TranslateAnimation(0, mActivity.getDisW(), 0, 0);
		rightOut.setDuration(ms);
		view.startAnimation(rightOut);
	}
}
