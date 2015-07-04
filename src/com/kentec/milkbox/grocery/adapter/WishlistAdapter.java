package com.kentec.milkbox.grocery.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kentec.milkbox.R;
import com.kentec.milkbox.comm.CacheBaseAdapter;
import com.kentec.milkbox.grocery.data.Product;
import com.kentec.milkbox.grocery.data.Wishlist;
import com.kentec.milkbox.utils.Format;

public class WishlistAdapter extends CacheBaseAdapter {

	private static final String TAG = "WishlisttAdapter";

	private int mBaseWidth;
	private int mImageWidth;

	private ArrayList<Wishlist> mList;
	private LayoutInflater mLayoutInflater;

	public WishlistAdapter(Context context, ArrayList<Wishlist> list, int width, int height) {
		mList = list;
		mBaseWidth = (int) ((width / 10) * 0.95) ;
		mImageWidth = (int) (mBaseWidth * 2);
		mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup vg) {

		ViewHolder vh;
		if (convertView == null) {

			vh = new ViewHolder();
			convertView = mLayoutInflater.inflate(R.layout.dialog_wishlist_row, vg, false);
			vh.mainRl = (RelativeLayout) convertView.findViewById(R.id.mainRl);
			vh.productIv = (ImageView) convertView.findViewById(R.id.productIv);
			vh.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
			vh.priceTv = (TextView) convertView.findViewById(R.id.priceTv);
			vh.descriptionTv = (TextView) convertView.findViewById(R.id.descriptionTv);

			reLayout(vh.mainRl, 3);
			reLayout(vh.priceTv, 2);
			reLayout(vh.descriptionTv, 5);

			convertView.setTag(vh);

		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		try {

			Wishlist row = mList.get(position);
			Product product = row.getProduct();
			vh.nameTv.setText(product.getName());
			vh.priceTv.setText("$" + Format.price(product.getPrice()));
			vh.descriptionTv.setText(product.getDescription());

			LoadImageAsyncTask task = new LoadImageAsyncTask(product.getImage(), vh.productIv, mImageWidth, mImageWidth);
			DownloadedDrawable drawable = new DownloadedDrawable(task);
			vh.productIv.setImageDrawable(drawable);
			task.execute();

		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}

		return convertView;
	}

	class ViewHolder {
		RelativeLayout mainRl;
		ImageView productIv;
		TextView nameTv;
		TextView priceTv;
		TextView descriptionTv;
	}

	public void setData(ArrayList<Wishlist> list) {
		mList = list;
	}

	private void reLayout(TextView tv, int base) {
		LayoutParams params = tv.getLayoutParams();
		params.width = mBaseWidth * base;
		tv.setLayoutParams(params);
	}

	private void reLayout(RelativeLayout fv, int base) {
		LayoutParams params = fv.getLayoutParams();
		params.width = mBaseWidth * base;
		fv.setLayoutParams(params);
	}

}
