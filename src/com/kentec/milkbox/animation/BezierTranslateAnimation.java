package com.kentec.milkbox.animation;


import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

/*
 * copy from http://stackoverflow.com/questions/6849554/problem-to-achieve-curved-animation
 * and modify  Bezier curves : Quadratic -> Cubic (二次方貝茲曲線->三次方貝茲曲線)
 * see also http://zh.wikipedia.org/wiki/%E8%B2%9D%E8%8C%B2%E6%9B%B2%E7%B7%9A
 * or           http://en.wikipedia.org/wiki/B%C3%A9zier_curve
 */

/*
 * sample:
		BezierTranslateAnimation bta = new BezierTranslateAnimation(0, 100, 0, 100, 50, 0,100,50);
		bta.setDuration(1200);
		bta.setRepeatCount(Animation.INFINITE);
		bta.setInterpolator(new DecelerateInterpolator(2.0f) );
		tv1.startAnimation(bta);
 */

public class BezierTranslateAnimation extends TranslateAnimation
{
	private int mFromXType    = ABSOLUTE;
	private int mToXType      = ABSOLUTE;
	private int mFromYType    = ABSOLUTE;
	private int mToYType      = ABSOLUTE;
	private float mFromXValue = 0.0f;
	private float mToXValue   = 0.0f;
	private float mFromYValue = 0.0f;
	private float mToYValue   = 0.0f;
	private float mBezierXDelta;
	private float mBezierYDelta;
	private float mBezier2XDelta;
	private float mBezier2YDelta;
	
	public BezierTranslateAnimation(float fromXDelta, float toXDelta,float fromYDelta, float toYDelta, float bezierXDelta, float bezierYDelta, float bezier2XDelta, float bezier2YDelta)
	{
		super(fromXDelta, toXDelta, fromYDelta, toYDelta);
		mFromXValue = fromXDelta;
		mToXValue   = toXDelta;
		mFromYValue = fromYDelta;
		mToYValue   = toYDelta;
		mFromXType  = ABSOLUTE;
		mToXType    = ABSOLUTE;
		mFromYType  = ABSOLUTE;
		mToYType    = ABSOLUTE;
		mBezierXDelta = bezierXDelta;
		mBezierYDelta = bezierYDelta;
		mBezier2XDelta = bezier2XDelta;
		mBezier2YDelta = bezier2YDelta;
	}

	// B(t) = (1-t)^2*P0 + 2t*(1-t)*P1 + t^2*P2 
	//  P0: START P2:END
	
	// B(t) = P0*(1-t)^3 + 3*P1*t*(1-t)^2 +  3*P2*t^2*(1-t) + P3*t^3
	//  P0: START P3:END
	
	@Override
	protected void  applyTransformation(float interpolatedTime, Transformation t)
	{
	    float dx=0,dy=0;
	    if (mFromXValue != mToXValue)
	    {
	        //dx  = (float) ((1.0-interpolatedTime)*(1.0-interpolatedTime)*mFromXValue + 2.0*interpolatedTime*(1.0-interpolatedTime)*mBezierXDelta + interpolatedTime*interpolatedTime*mToXValue);
	    	dx  = (float) (mFromXValue*(1.0-interpolatedTime)*(1.0-interpolatedTime)*(1.0-interpolatedTime)
	    						+3.0*mBezierXDelta*interpolatedTime*(1.0-interpolatedTime)*(1.0-interpolatedTime)
	    						+3.0*mBezier2XDelta*interpolatedTime*interpolatedTime*(1.0-interpolatedTime)
	    						+mToXValue*interpolatedTime*interpolatedTime*interpolatedTime);
	    }
	    if (mFromYValue != mToYValue)
	    {
	        //dy  = (float) ((1.0-interpolatedTime)*(1.0-interpolatedTime)*mFromYValue + 2.0*interpolatedTime*(1.0-interpolatedTime)*mBezierYDelta + interpolatedTime*interpolatedTime*mToYValue);
	    	dy  = (float) (mFromYValue*(1.0-interpolatedTime)*(1.0-interpolatedTime)*(1.0-interpolatedTime)
					+3.0*mBezierYDelta*interpolatedTime*(1.0-interpolatedTime)*(1.0-interpolatedTime)
					+3.0*mBezier2YDelta*interpolatedTime*interpolatedTime*(1.0-interpolatedTime)
					+mToYValue*interpolatedTime*interpolatedTime*interpolatedTime);
	    }
	    t.getMatrix().setTranslate(dx, dy);

	}

}
