package com.taoxue.utils.update;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.taoxue.app.AppData;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilToast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wangshengya on 2016/7/22.
 */
public class MyUpdateService extends Service {
    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;
    /*下载失败*/
    private static final int DOWNLOAD_ERROR = 3;

    private MyNotificationControl myNotificationControl;
    private DownloadApkThread mDownloadApkThread;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_ERROR:
                    UtilToast.showText("下载新版本失败");
                    break;
            }
        }
    };

    public MyUpdateService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.i("onCreate", "onCreate");
        myNotificationControl = new MyNotificationControl(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        LogUtils.i("onStartCommand", "onStartCommand");
        //启动下载线程
        if (mDownloadApkThread == null) {
            mDownloadApkThread = new DownloadApkThread();
            mDownloadApkThread.start();
            UtilToast.showText("开始下载...");
        } else {
            UtilToast.showText("正在下载中...");
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     * @date 2012-4-26
     * @blog http://blog.92coding.com
     */
    private class DownloadApkThread extends Thread {
        @Override
        public void run() {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                // 判断SD卡是否存在，并且是否具有读写权限f
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + AppData.APP_INDEX + "/download";
                    URL url = new URL(AppData.apkURL);

                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();

                    myNotificationControl.showNotification();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    is = conn.getInputStream();
                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdirs();
                    }
//                    File apkFile = new File(mSavePath, versionMap.get("apkurl").substring(versionMap.get("apkurl").lastIndexOf("/") + 1));
                    File apkFile = new File(mSavePath, AppData.apkName);
                    // 判断file有没有
                    if (apkFile.exists()) {
                        apkFile.delete();
                    }

                    fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    int readedprogress = 0;//读到的次数
                    do {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        if (numread <= 0) {
                            // 下载完成
                            myNotificationControl.finishNotification();
                            //关闭通知
//                            myNotificationControl.cancelNotification();
//                            mDownloadApkThread = null;
//                            stopSelf();
                            break;
                        }

                        // 更新进度
                        if (progress > readedprogress) {
                            myNotificationControl.updateNotification(progress);
                            readedprogress += 1;
                        }

                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.

                }
            } catch (Exception e) {
                mHandler.sendEmptyMessage(DOWNLOAD_ERROR);
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (is != null) {
                        is.close();
                    }
                    //关闭通知
                    myNotificationControl.cancelNotification();
                    mDownloadApkThread = null;
                    //关闭本服务
                    stopSelf();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 安装APK文件
     */
    public void installApk() {
//        File apkfile = new File(mSavePath, versionMap.get("apkurl").substring(versionMap.get("apkurl").lastIndexOf("/") + 1));
        File apkfile = new File(mSavePath, AppData.apkName);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        LogUtils.i("updatemanager", Uri.parse("file://" + apkfile.toString()));
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

        //关闭本服务
        stopSelf();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
