/**
 *  用來描述 Moderator 每個泡泡對話框的資訊
 */
package com.kentec.milkbox.moderator;

import android.view.View;
import android.widget.TextView;

/**
 * @author Alvin Huang
 *
 */
public class ModeratorTextDialogAdapter
{
	private TextView textView;
	
	public ModeratorTextDialogAdapter(TextView textView)
	{
		this.textView = textView;
		this.textView.setVisibility(View.INVISIBLE);
	}
	
	public TextView getTextView()
	{
		return this.textView;
	}
	
	/**
	 * Set Text
	 * @param textString
	 *
	 * @author Alvin Huang
	 * @date 2013/12/27 上午9:58:13
	 */
	public void setText(String textString)
	{
		this.textView.setText(textString + "\n\n" + "按[M]鍵開始說話");
	}	
}
