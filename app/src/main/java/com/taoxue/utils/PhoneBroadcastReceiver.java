package com.taoxue.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.taoxue.ui.module.classification.AudioPlayerService;
import com.taoxue.ui.module.classification.CommitContent;

/**
 * Created by User on 2017/5/2.
 * 音乐播放时
 * 来电监听
 */

public class PhoneBroadcastReceiver extends BroadcastReceiver{
   private AudioPlayerService.MyBinder binder;
    public PhoneBroadcastReceiver(AudioPlayerService.MyBinder  binder) {
        this.binder = binder;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
       LogUtils.D("[Broadcast]"+action);
        //呼入电话
        if(action.equals(CommitContent.B_PHONE_STATE)){
            LogUtils.D("[Broadcast]PHONE_STATE");
            doReceivePhone(context,intent);
        }
    }

    /**
     * 处理电话广播.
     * @param context
     * @param intent
     */
    public void doReceivePhone(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra(
                TelephonyManager.EXTRA_INCOMING_NUMBER);
        TelephonyManager telephony =
                (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        int state = telephony.getCallState();
        switch(state){
            case TelephonyManager.CALL_STATE_RINGING:
                LogUtils.D("[Broadcast]等待接电话="+phoneNumber);
                binder.pause();
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                LogUtils.D("[Broadcast]电话挂断="+phoneNumber);
                binder.play();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                LogUtils.D("[Broadcast]通话中="+phoneNumber);
                break;
        }
    }
}
