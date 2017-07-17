package com.taoxue.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.taoxue.R;
import com.taoxue.ui.module.login.PushActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by User on 2017/5/9.
 */

public class AudioNotifacation {
   private static Service service;

    public static Service getService() {
        return service;
    }

    public static void setService(Service service) {
        AudioNotifacation.service = service;
    }

    public static String ACTION_BUTTON = "ACTION_BUTTON";
    private static NotificationManager mNotificationManager;
    public static String INTENT_BUTTONID_TAG = "INTENT_BUTTONID_TAG";
    public static String BUTTON_PALY_ID = "BUTTON_PALY_ID";
    public static String BUTTON_DELETE_ID = "BUTTON_DELETE_ID";

    public static int NOTIFICATION_ID = 0;

    public static RemoteViews mRemoteViews;
    public static NotificationCompat.Builder mBuilder;
    private static String picture_url;
    private static String title;
    private static Bitmap bitmapc;
    private static boolean isPlay;

    public static boolean isPlay() {
        return isPlay;
    }

    public static void setIsPlay(boolean isPlay) {
        AudioNotifacation.isPlay = isPlay;
    }

    public static String getPicture_url() {
        return picture_url;
    }

    public static void setPicture_url(String picture_url) {
        AudioNotifacation.picture_url = picture_url;
    }

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        AudioNotifacation.title = title;
    }

    public static void sendAudioNotification(Service service, String picture_url, String title, boolean isPlay) {
        LogUtils.D("发送消息");
        setService(service);
        setIsPlay(isPlay);
        if (mNotificationManager == null) {
            mNotificationManager = (NotificationManager) service.getSystemService(service.NOTIFICATION_SERVICE);
            mBuilder = new NotificationCompat.Builder(service);
            mRemoteViews = new RemoteViews(service.getPackageName(), R.layout.ui_audio_notifacation);
        }
        if (!picture_url.equals(getPicture_url())) {
            setTitle(title);
            loadBitmap(picture_url);
            setPicture_url(picture_url);
        } else {
            updateNotificationByBitmap(bitmapc);
        }
    }

    public static void updateNotificationByBitmap(Bitmap bitmap) {
        mRemoteViews.setImageViewBitmap(R.id.audio_notifacation_image_iv, bitmap);
        mRemoteViews.setTextViewText(R.id.audio_notifacation_title_tv, title);
        //点击的事件处理
        Intent buttonIntent = new Intent(ACTION_BUTTON);
//        /* 上一首按钮 */
//        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PREV_ID);
//        //这里加了广播，所及INTENT的必须用getBroadcast方法
//        PendingIntent intent_prev = PendingIntent.getBroadcast(service, 1, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        mRemoteViews.setOnClickPendingIntent(R.id.btn_custom_prev, intent_prev);
        /* 播放/暂停  按钮 */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_PALY_ID);
        PendingIntent intent_paly = PendingIntent.getBroadcast(service, 2, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.audio_notifacation_pause_iv, intent_paly);
        /* 下一首 按钮  */
        buttonIntent.putExtra(INTENT_BUTTONID_TAG, BUTTON_DELETE_ID);
        PendingIntent intent_next = PendingIntent.getBroadcast(service, 3, buttonIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setOnClickPendingIntent(R.id.audio_notifacation_delete_tv, intent_next);
        updateAudioNotification(isPlay);
    }

    public static void updateAudioNotification(boolean isPlay) {
        if (mNotificationManager != null) {
            setAudioIsPlay(isPlay);
            mBuilder.setContent(mRemoteViews)
                    .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT, service))
                    .setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
                    .setTicker("正在播放")
                    .setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_back)
            ;
            Notification notify = mBuilder.build();
            notify.flags = Notification.FLAG_ONGOING_EVENT;
            mNotificationManager.notify(NOTIFICATION_ID, notify);
        }
    }


    /**
     * 取消通知
     */
    public static void cancelNotification() {
        if (mNotificationManager != null) {
            mNotificationManager.cancel(NOTIFICATION_ID);
            mNotificationManager = null;
            mRemoteViews = null;
        }
    }

    public static PendingIntent getDefalutIntent(int flags, Service service) {
        /**
         * FLAG_CANCEL_CURRENT:如果当前系统中已经存在一个相同的 PendingIntent 对象，那么就将先将已有的 PendingIntent 取消，然后重新生成一个 PendingIntent 对象。

         FLAG_NO_CREATE:如果当前系统中不存在相同的 PendingIntent 对象，系统将不会创建该 PendingIntent 对象而是直接返回 null 。

         FLAG_ONE_SHOT:该 PendingIntent 只作用一次。

         FLAG_UPDATE_CURRENT:如果系统中已存在该 PendingIntent 对象，那么系统将保留该 PendingIntent 对象，但是会使用新的 Intent 来更新之前 PendingIntent 中的 Intent 对象数据，例如更新 Intent 中的 Extras 。
         *new Intent(service,lastLctivity)
         */
        Intent notificationIntent = new Intent(Intent.ACTION_MAIN);
//        Intent.FLAG_service_NEW_TASK|Intent.FLAG_service_RESET_TASK_IF_NEEDED;PendingIntent.FLAG_UPDATE_CURRENT
        notificationIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        notificationIntent.setClass(service, PushActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED); //可打开退出前的页面
//        Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
//        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(service, 1, notificationIntent, flags);
        return pendingIntent;
    }

    public static void setAudioIsPlay(boolean isplay) {
        if (mRemoteViews != null) {
            if (isplay) {
                LogUtils.D("播放图标");
                mRemoteViews.setImageViewResource(R.id.audio_notifacation_pause_iv, R.mipmap.icon_pause);
            } else {
                LogUtils.D("播放图标 flase");
                mRemoteViews.setImageViewResource(R.id.audio_notifacation_pause_iv, R.mipmap.icon_play);
            }
        }
    }


    private static void loadBitmap(final String url) {
        AsyncTask<String, String, Bitmap> task = new AsyncTask<String, String, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... params) {
                URL fileUrl = null;
                Bitmap bitmap = null;

                try {
                    fileUrl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                try {
                    HttpURLConnection conn = (HttpURLConnection) fileUrl
                            .openConnection();
                    conn.setRequestMethod("GET");
//                conn.setDoInput(true);
                    LogUtils.D("sfds");
//                conn.connect();
                    LogUtils.D("ssddfs");
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                bitmapc = bitmap;
                updateNotificationByBitmap(bitmap);
            }
        };
        task.execute();

    }

//    public static void sendAudioBroacast(String str){
//        Intent intent=new Intent(CommitContent.AUDIO_SERVICE_ACTION);
//        intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG,str);
//        activity.sendBroadcast(intent);
//    }
//    提醒标志符成员：
//    Notification.FLAG_SHOW_LIGHTS //三色灯提醒，在使用三色灯提醒时候必须加该标志符
//
//    Notification.FLAG_ONGOING_EVENT //发起正在运行事件（活动中）
//
//    Notification.FLAG_INSISTENT //让声音、振动无限循环，直到用户响应 （取消或者打开）
//
//    Notification.FLAG_ONLY_ALERT_ONCE //发起Notification后，铃声和震动均只执行一次
//
//    Notification.FLAG_AUTO_CANCEL //用户单击通知后自动消失
//
//    Notification.FLAG_NO_CLEAR //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
//
//    Notification.FLAG_FOREGROUND_SERVICE //表示正在运行的服务

}


