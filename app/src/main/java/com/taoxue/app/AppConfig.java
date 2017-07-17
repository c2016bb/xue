package com.taoxue.app;

import android.os.Environment;

import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;

import java.io.File;

/**
 * Created by Administrator on 2016/12/20.
 */

public class AppConfig {
    public static String getAppCachePath() {
        String path = TaoXueApplication.get().getCacheDir() + "/"+ InternalCacheDiskCacheFactory.DEFAULT_DISK_CACHE_DIR;
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/taoxue/cache";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
    public static String getDownLoadImagePath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/taoxue/DownLoad/Image";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getDownLoadBookPath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/taoxue/DownLoad/book";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }
}
