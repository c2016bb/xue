package com.taoxue.ui.module.classification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.taoxue.ui.model.AudioCatalog;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.utils.AudioNotifacation;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/5/9.
 */

public class AudioBroadcastReceiver extends BroadcastReceiver {
   private AudioPlayerService.MyBinder  binder;
    public AudioBroadcastReceiver(AudioPlayerService.MyBinder  binder) {
        LogUtils.D("BINDER--->"+binder.toString());
        this.binder=binder;
    }
    //处理通知中的操作
private  void  doAudioNotification(Intent intent){
    String tag=intent.getStringExtra(AudioNotifacation.INTENT_BUTTONID_TAG);
    LogUtils.D("tag--->"+tag);
    if(tag!=null){
        if(tag.equals(AudioNotifacation.BUTTON_PALY_ID)){
            binder.onlyPlayOrPause();
            LogUtils.D("播放或暂停");
        }else if (tag.equals(AudioNotifacation.BUTTON_DELETE_ID)){
            AudioNotifacation.cancelNotification();
//            binder.pause();
            binder.stop();
//            binder.onDestroy();
        }
    }
}

    private  void doAudioAcvity(Intent intent){
        String tag=intent.getStringExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG);
        LogUtils.D("tag--->"+tag);
        if(tag!=null) {
            if (CommitContent.PLAY_OR_PLAUSE_MSG.equals(tag)) {
                 binder.playOrPause();
            }else if (CommitContent.STOP_UPDATE_THREAD_PROGRESS.equals(tag)){
                binder.stopUpdateTHreadProgress();
            }else if (CommitContent.AUDIO_START_PLAY.equals(tag)){
                binder.play(intent.getIntExtra(CommitContent.AUDIO_PLAY_TAG,0));
            }else if (CommitContent.AUDIO_PLAY_URL.equals(tag)){
                binder.setUrlPaths((UrlPath)intent.getSerializableExtra(CommitContent.AUDIO_PLAY_URL_TAG));
//                 binder.play();
            }else if(CommitContent.AUDIO_INFO.equals(tag)){
                 binder.setAudioInfo((ResourceModel)intent.getSerializableExtra(CommitContent.AUDIO_INFO_TAG));
            }else if (CommitContent.PLAY_ACTIVITY_IS_FORWORD.equals(tag)){
               binder.setPlayBefore((boolean)intent.getBooleanExtra(CommitContent.PLAY_ACTIVITY_IS_FORWORD_TAG,false));
            }else if (CommitContent.PLAY_NEXT_MSG.equals(tag)){//播放下一首
                 binder.next();
            }else if (CommitContent.PLAY_PRE_MSG.equals(tag)){//播放上一首
                binder.pre();
            }else if (CommitContent.AUDIO_PLAY_INDEX_MSG.equals(tag)){
                   binder.playByIndex(intent.getIntExtra(CommitContent.AUDIO_PLAY_INDEX_TAG,0));
            }
        }
    };
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        LogUtils.D("action"+action+"");
        if(action==null){
            return;
        }
        if (action.equals(AudioNotifacation.ACTION_BUTTON)){
            doAudioNotification(intent);
        }else if (action.equals(CommitContent.AUDIO_SERVICE_ACTION)){
           doAudioAcvity(intent);
        }else if (action.equals(CommitContent.STOP_AUDIO_SERVICE_ACTION)){
            binder.onServiceDestroy();
        }else if (CommitContent.SEND_NOTIFICATION_ACTION.equals(action)){
            binder.sendOnlyNotification();
        }
    }
}
