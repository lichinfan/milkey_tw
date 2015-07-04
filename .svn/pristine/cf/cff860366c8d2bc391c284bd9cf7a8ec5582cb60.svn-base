package com.kentec.milkbox.animation;

import android.view.animation.Interpolator;

public class CustomizeInterpolator implements Interpolator
{
	float Bezier1 = 0;
	float Bezier2 = 0;
	
	public CustomizeInterpolator(float Bezier1, float Bezier2)
	{
		super();
		this.Bezier1 = Bezier1;
		this.Bezier2 = Bezier2;
	}
	@Override
	public float getInterpolation(float time)
	{
		// TODO Auto-generated method stub
		//return 1;
		return (float) (3.0*Bezier1*time*(1.0-time)*(1.0-time)
				+3.0*Bezier2*time*time*(1.0-time)
				+time*time*time);
	}


}
