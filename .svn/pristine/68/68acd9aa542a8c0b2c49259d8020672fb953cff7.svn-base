package com.kentec.milkbox.homedine.data;

import android.util.DisplayMetrics;

import com.kentec.milkbox.CFG;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;

public class HomeDineProperty {

	private boolean isDescription = true;
	private boolean isUnder200Calores = false;
	private boolean isLoading = false;
	private String nowStoreId = "";
	private String nowFoodId = "";
	private int nowFoodItemId = 0;
	private int disW;
	private int disH;
	private double disWsc;
	private double disHsc;
	private String customerId = "";

	public HomeDineProperty(HomeDineDeliveryActivity activity) {

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		this.disW = dm.widthPixels;
		this.disH = dm.heightPixels;
		this.disWsc = (double) disW / (double) ReLayoutUtil.srcDisW;
		this.disHsc = (double) disH / (double) ReLayoutUtil.srcDisH;

		this.customerId = activity.getSP().getString(CFG.PREF_CUSTOMER_ID, "");
	}

	public boolean isDescription() {
		return isDescription;
	}

	public void setDescription(boolean isDescription) {
		this.isDescription = isDescription;
	}

	public String getNowStoreId() {
		return nowStoreId;
	}

	public void setNowStoreId(String nowStoreId) {
		this.nowStoreId = nowStoreId;
	}

	public String getNowFoodId() {
		return nowFoodId;
	}

	public void setNowFoodId(String nowFoodId) {
		this.nowFoodId = nowFoodId;
	}

	public int getDisW() {
		return disW;
	}

	public int getDisH() {
		return disH;
	}

	public double getDisWsc() {
		return disWsc;
	}

	public double getDisHsc() {
		return disHsc;
	}

	public boolean isUnder200Calores() {
		return isUnder200Calores;
	}

	public void setUnder200Calores(boolean isUnder200Calores) {
		this.isUnder200Calores = isUnder200Calores;
	}

	public boolean isLoading() {
		return isLoading;
	}

	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}

	public int getNowFoodItemId() {
		return nowFoodItemId;
	}

	public void setNowFoodItemId(int nowFoodItemId) {
		this.nowFoodItemId = nowFoodItemId;
	}

	public String getCustomerId() {
		return customerId;
	}

}
