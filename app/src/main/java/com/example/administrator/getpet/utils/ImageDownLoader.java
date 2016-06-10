package com.example.administrator.getpet.utils;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;



public class ImageDownLoader {

	public static void showNetImage(Context my,String UrlAddress, final ImageView imageView,final int loadingImg){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(loadingImg)
				.showImageForEmptyUri(loadingImg)
				.showImageOnFail(loadingImg)
				.cacheInMemory(true)
				.cacheOnDisk(false)
						//		.considerExifParams(true)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
				.bitmapConfig(Bitmap.Config.RGB_565)//设置 图片的解码类�?//
				.resetViewBeforeLoading(true)
				.build();
		String imageUrl=UrlAddress;
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
	}
	public static void showLocationImage(String path,
			final ImageView imageView,final int loadingImg){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnLoading(loadingImg)
		.showImageForEmptyUri(loadingImg)
		.showImageOnFail(loadingImg)
		.cacheInMemory(true)
		.cacheOnDisk(false)
		//		.considerExifParams(true)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示  
		////			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
		.bitmapConfig(Bitmap.Config.RGB_565)//设置 图片的解码类
		.resetViewBeforeLoading(true)
		.build();
		//
		String imageUrl = Scheme.FILE.wrap(path);
		ImageLoader.getInstance().displayImage(imageUrl, imageView, options);

	}
}