package com.kentec.milkbox.homedine.view.homedineprogress;

import java.text.ParseException;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.homedine.HomeDineDeliveryActivity;
import com.kentec.milkbox.homedine.data.OrderData;
import com.kentec.milkbox.homedine.data.OrderInfoItems;
import com.kentec.milkbox.homedine.data.OrderInfoStatusHistory;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetFoodThumbnail;
import com.kentec.milkbox.homedine.task.HomeDineTaskGetFoodThumbnail.OnFoodThumbnailListener;
import com.kentec.milkbox.homedine.utils.ReLayoutUtil;
import com.kentec.milkbox.utils.DateUtil;

@SuppressLint("ValidFragment")
public class HomeDineProgressFragment extends Fragment {
	private HomeDineDeliveryActivity mActivity;
	private OrderData mOrderData;
	private HomeDineProgressFoodAdapter foodAdapter;

	private RelativeLayout mainRelative;
	private LinearLayout mainLinear;
	private LinearLayout proLinear;
	private LinearLayout topLinear;
	private ImageView loadingImageView;
	private TextView titleTextView;
	private TextView createdAt;
	// private TextView grandTotal;
	private TextView status;
	private GridView foodGridView;
	private ListView statusListView;

	public HomeDineProgressFragment(HomeDineDeliveryActivity activity, OrderData orderData) {
		super();
		this.mActivity = activity;
		this.mOrderData = orderData;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.view_homedine_progress, container, false);
		mainRelative = (RelativeLayout) view.findViewById(R.id.mainRelative);
		loadingImageView = (ImageView) view.findViewById(R.id.loadingImageView);
		titleTextView = (TextView) view.findViewById(R.id.titleTextView);
		mainLinear = (LinearLayout) view.findViewById(R.id.mainLinear);
		topLinear = (LinearLayout) view.findViewById(R.id.topLinear);
		proLinear = (LinearLayout) view.findViewById(R.id.proLinear);
		createdAt = (TextView) view.findViewById(R.id.createdAt);
		// grandTotal = (TextView) view.findViewById(R.id.grandTotal);
		status = (TextView) view.findViewById(R.id.status);
		foodGridView = (GridView) view.findViewById(R.id.foodGridView);
		statusListView = (ListView) view.findViewById(R.id.statusListView);

		mActivity.getViewGroup().getRelayout().progress(this);

		try {
			createdAt.setText(DateUtil.toLocalTime(mOrderData.getCreatedAt()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// grandTotal.setText(mOrderData.getGrandTotal());
		titleTextView.setText(mOrderData.getIncrementId());

		mActivity.getMethod().showProgressColor(status, mOrderData.getStatus());

		foodAdapter = new HomeDineProgressFoodAdapter(mActivity, new ArrayList<OrderInfoItems>());
		foodGridView.setAdapter(foodAdapter);
		foodGridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				String tmpName = foodAdapter.getItem(arg2).getName();
				String tmpQty = foodAdapter.getItem(arg2).getQtyOrdered();
				tmpQty = String.valueOf((int) Double.parseDouble(tmpQty));
				mActivity.showNoImgMsg(tmpName + " X " + tmpQty);
			}
		});
		return view;
	}

	public void isLoading(boolean isloading) {
		if (isVisible()) {
			if (isloading) {
				foodGridView.setVisibility(View.GONE);
				statusListView.setVisibility(View.GONE);
				loadingImageView.setVisibility(View.VISIBLE);
				RotateAnimation loadAnim = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				loadAnim.setDuration(500);
				loadAnim.setRepeatCount(Animation.INFINITE);
				loadAnim.setFillEnabled(true);
				loadAnim.setInterpolator(new LinearInterpolator());

				loadingImageView.startAnimation(loadAnim);
			} else {
				loadingImageView.clearAnimation();
				loadingImageView.setVisibility(View.GONE);
				foodGridView.setVisibility(View.VISIBLE);
				statusListView.setVisibility(View.VISIBLE);
				// mActivity.getMethod().getAnimUtil().AlphaIn(foodGridView,
				// 300);
				// mActivity.getMethod().getAnimUtil().AlphaIn(statusListView,
				// 300);
			}
		}
	}

	public void showFood(ArrayList<OrderInfoItems> itemList) {
		new HomeDineTaskGetFoodThumbnail(mActivity, itemList, new OnFoodThumbnailListener() {
			@Override
			public void OK(ArrayList<OrderInfoItems> itemList) {
				if (isVisible()) {
					foodAdapter.upFoodList(itemList);
					ReLayoutUtil.gridView(foodGridView, 4, 2);
				}
			}

			@Override
			public void Error(String msg) {

			}
		});
	}

	public void showStatus(ArrayList<OrderInfoStatusHistory> itemList) {
		if (isVisible()) {
			statusListView.setAdapter(new HomeDineProgressStatusAdapter(mActivity, itemList));
		}
	}

	public OrderData getmOrderData() {
		return mOrderData;
	}

	public ImageView getLoadingImageView() {
		return loadingImageView;
	}

	public TextView getCreatedAt() {
		return createdAt;
	}

	// public TextView getGrandTotal() {
	// return grandTotal;
	// }

	public TextView getStatus() {
		return status;
	}

	public GridView getFoodGridView() {
		return foodGridView;
	}

	public ListView getStatusListView() {
		return statusListView;
	}

	public LinearLayout getMainLinear() {
		return mainLinear;
	}

	public LinearLayout getProLinear() {
		return proLinear;
	}

	public TextView getTitleTextView() {
		return titleTextView;
	}

	public RelativeLayout getMainRelative() {
		return mainRelative;
	}

	public LinearLayout getTopLinear() {
		return topLinear;
	}
}