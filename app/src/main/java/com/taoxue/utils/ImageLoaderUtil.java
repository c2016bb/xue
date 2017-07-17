package com.taoxue.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.utils.glide.UtilGlide;

import java.io.File;

/**
 * com.xjx.crm.util.ImageLoaderUtil
 *
 * @author YuanChao <br/>
 *         create at 2015年9月25日 上午11:05:49
 */
@Deprecated
public class ImageLoaderUtil {
    @Deprecated
    public static void displayImage(String uri, ImageView imageView) {

        UtilGlide.loadImg(TaoXueApplication.get().getApplicationContext(),uri,imageView);
//        if (TextUtils.isEmpty(uri)) {
//            uri = "";
//        }
//        if (!uri.startsWith("http")) {
//            uri = "http://117.71.57.47:9000/DRIS_frontend_V1.0.1/" + uri;
//        }
//        ImageLoader.getInstance().displayImage(uri, imageView, createOptions());
    }
    @Deprecated
    public static void displayPhotoImage(String uri, ImageView imageView) {

        UtilGlide.loadImg(TaoXueApplication.get().getApplicationContext(),uri,imageView);
//        if (TextUtils.isEmpty(uri)) {
//            uri = "";
//        }
//        if (!uri.startsWith("http")) {
//            uri = "http://117.71.57.47:9000/DRIS_frontend_V1.0.1/" + uri;
//        }
//        ImageLoader.getInstance().displayImage(uri, imageView, createOptionsPhoto());
    }

    public static DisplayImageOptions createOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_default) // image在加载过程中，显示的图片
                .showImageForEmptyUri(R.mipmap.image_default) // empty URI时显示的图片
                .showImageOnFail(R.mipmap.image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // default 不缓存至内存
                .cacheOnDisk(true) // default 不缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                //				.displayer(new SimpleBitmapDisplayer()) // default
                // 可以设置动画，比如圆角或者渐变
                .displayer(new SimpleBitmapDisplayer()).build();
        return options;
    }
    //宽度不变，高度自适应
    public static DisplayImageOptions displayImageByWidth() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_default) // image在加载过程中，显示的图片
                .showImageForEmptyUri(R.mipmap.image_default) // empty URI时显示的图片
                .showImageOnFail(R.mipmap.image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // default 不缓存至内存
                .cacheOnDisk(true) // default 不缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                //				.displayer(new SimpleBitmapDisplayer()) // default
                // 可以设置动画，比如圆角或者渐变
                .displayer(new SimpleBitmapDisplayer()).build();
        return options;
    }
    public static DisplayImageOptions createOptionsPhoto() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_default) // image在加载过程中，显示的图片
                .showImageForEmptyUri(R.mipmap.image_default) // empty URI时显示的图片
                .showImageOnFail(R.mipmap.image_default) // 不是图片文件 显示图片
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // default 不缓存至内存
                .cacheOnDisk(true) // default 不缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default
                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                //				.displayer(new SimpleBitmapDisplayer()) // default
                // 可以设置动画，比如圆角或者渐变
                .displayer(new SimpleBitmapDisplayer()).build();
        return options;
    }

    public static void displayImageByWid(String uri, ImageView imageView){
        DisplayImageOptions options = displayImageByWidth();
        ImageLoader.getInstance().displayImage(uri,imageView,options);
    }

    public static DisplayImageOptions createRoundRectOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.image_default) // image在加载过程中，显示的图片
                .showImageForEmptyUri(R.mipmap.image_default) // empty URI时显示的图片
                // .showImageOnFail(R.drawable.ic_error) // 不是图片文件 显示图片
                .resetViewBeforeLoading(false) // default
                .delayBeforeLoading(1000).cacheInMemory(true) // default 不缓存至内存
                .cacheOnDisk(false) // default 不缓存至手机SDCard
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)// default
                .bitmapConfig(Bitmap.Config.ARGB_4444) // default
                //				.displayer(new SimpleBitmapDisplayer()) // default
                // 可以设置动画，比如圆角或者渐变
                .displayer(new RoundedBitmapDisplayer(8)).build();
        return options;
    }

    public static void init(Context context, String cachePath) {
        File cacheDir = StorageUtils.getOwnCacheDirectory(context, cachePath);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .memoryCacheExtraOptions(480, 800)
                // maxwidth, max height，即保存的每个缓存文件的最大长宽
                .threadPoolSize(3)
                //线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                //				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                //				.memoryCacheSize(2 * 1024 * 1024).discCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                //将保存的时候的URI名称用MD5 加密
                //				.tasksProcessingOrder(QueueProcessingType.LIFO).discCacheFileCount(100)
                //缓存的文件数量
                .diskCache(new UnlimitedDiskCache(cacheDir))
                //自定义缓存路径
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                //				.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .writeDebugLogs() // Remove for releaseapp
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }
}
