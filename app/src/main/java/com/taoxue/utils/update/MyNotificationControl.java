package com.taoxue.utils.update;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.taoxue.MainActivity;
import com.taoxue.R;


/**
 * Created by wangshengya on 2016/7/22.
 */
public class MyNotificationControl {

    //通知栏
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    private final int NOTIFYCATIONID = 1001;

    private Context context;

    public MyNotificationControl(Context context) {
        this.context = context;
        initNotification();//初始化通知栏
    }

    /**
     * 初始化通知栏
     */
    public void initNotification() {
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setWhen(System.currentTimeMillis())
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setOngoing(true)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("等待下载")
                .setContentText("进度: " + "0%")
                .setProgress(100, 0, false)
                .setTicker("开始下载");// 通知首次出现在通知栏，带上升动画效果的
//        mBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;

        //通知栏点击操作
        Intent intent = new Intent(context, MainActivity.class);
        mBuilder.setContentIntent(PendingIntent.getActivity(context, 0, intent, 0));

    }

    /**
     * 首次显示通知栏
     */
    public void showNotification() {
        mNotificationManager.notify(NOTIFYCATIONID, mBuilder.build());
    }

    /**
     * 更新通知栏进度
     */
    public void updateNotification(int progress) {
        mBuilder.setContentTitle("下载中");
        mBuilder.setContentText("进度: " + progress + "%");
        mBuilder.setProgress(100, progress, false);
        mNotificationManager.notify(NOTIFYCATIONID, mBuilder.build());


    }


    //通知栏显示完成更新
    public void finishNotification() {
        mBuilder.setContentTitle("下载完成");
        mBuilder.setContentText("点击安装");
        mBuilder.setProgress(100, 100, false);
        mBuilder.build().flags = Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFYCATIONID, mBuilder.build());
        ((MyUpdateService)context).installApk();

    }

    //关闭通知栏
    public void cancelNotification() {
        mNotificationManager.cancel(NOTIFYCATIONID);
    }

}
