package com.kentec.milkbox.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

public class BitmapUtil {

	public static Bitmap zoomImage(Bitmap bgimage, double newWidth, double newHeight) {

		float width = bgimage.getWidth();
		float height = bgimage.getHeight();

		Matrix matrix = new Matrix();

		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		matrix.postScale(scaleWidth, scaleHeight);

		return Bitmap.createBitmap(bgimage, 0, 0, (int) width, (int) height, matrix, true);
	}

	public static BitmapFactory.Options getBitmapOptions(int scale) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inSampleSize = scale;
		return options;
	}

	public static Bitmap ImageCrop(Bitmap bitmap) {
		if (bitmap != null) {
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int wh = w > h ? h : w;

			int retX = w > h ? (w - h) / 2 : 0;
			int retY = w > h ? 0 : (h - w) / 2;

			return Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null, false);
		}
		return null;
	}

	public static BitmapFactory.Options getBestOption(byte[] imgByte, int w, int h) {
		BitmapFactory.Options opts = new BitmapFactory.Options();

		opts.inJustDecodeBounds = true;

		BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length, opts);

		opts.inSampleSize = computeSampleSize(opts, -1, w * h);
		opts.inJustDecodeBounds = false;
		return opts;
	}

	private static int computeSampleSize(BitmapFactory.Options opts, int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(opts, minSideLength, maxNumOfPixels);

		int roundedSize;

		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options opts, int minSideLength, int maxNumOfPixels) {
		double w = opts.outWidth;
		double h = opts.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
		int upperBound = (maxNumOfPixels == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

}
