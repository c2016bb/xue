package com.taoxue.ui.module.classification;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;
import android.widget.MediaController;

import com.taoxue.ui.model.AudioCatalog;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.utils.AudioNotifacation;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.PhoneBroadcastReceiver;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by User on 2017/4/13.
 */

public class AudioPlayerService extends Service {
    private MediaPlayer mediaPlayer;      //媒体播放器对象
    private MyBinder myBinder;

    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
        LogUtils.D("onCreate");
        myBinder = new MyBinder();

//            phoneCallregister();
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.D("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent arg0) {
        LogUtils.D("onbind");
        return null;
    }

    @Override
    public void onDestroy() {
        LogUtils.D("audioservice-------------------> 执行onDestroy");
//         myBinder.onDestroy();
        myBinder.onServiceDestroy();
        super.onDestroy();
    }

//    //发送音音频播放信息
//    public void sendPlayStateMsg(String msg) {
//        LogUtils.D("dvvdvf");
//        EventBus.getDefault().post(new MessageEvent(msg));
//    }

    public class MyBinder {
        private String resource_id;
        private ResourceModel audioInfo;
        private String resource_picture;
        private String path = null;                    //音乐文件路径
        private boolean isLoop = false;              //播放状态
        private int currentProgressPosition = 0;             //音频播放进度
        private long duration;                    //音频总时长
        private InnerThread updateThreadprogress; //线程
        final static int CURRENT_POSITION = 1;    //消息what  指待当前进度
        private PhoneBroadcastReceiver mBroadcastReceiver;
        private AudioBroadcastReceiver mAudioReceiver;

        private boolean isUpdateAudio = false;//判断更换audio文件 默认没有更换

        private boolean isPlayBefore = false;//音频播放页面不处于前台
//        private boolean isAudioActivityDo = false;//音频播放页面的操作，默认yes;
//        private boolean isNotifiDo = false;//是否通知中的操作，默认false

        private UrlPath urlPaths;

        private int playAudioIndex=-1;
        private int playAudioCount;


        public UrlPath getUrlPaths() {
            return urlPaths;
        }

        public void setUrlPaths(UrlPath urlPaths) {
            if (urlPaths != null && !("").equals(urlPaths) && urlPaths.getUrlModel().size() > 0) {
                this.urlPaths = urlPaths;
                setUrl(urlPaths.getUrlModel().get(0).getUrl());
                playAudioIndex=0;
                playAudioIndex=urlPaths.getUrlModel().size();
            }else{
                sendPlayStateInfo(CommitContent.AUDIO_URLPATH_EXCEPTION_CODE,CommitContent.NO_GET_AUDIO_URL);
            };
        }

        public boolean isPlayBefore() {
            return isPlayBefore;
        }

        public void setPlayBefore(boolean playBefore) {
            this.isPlayBefore = playBefore;
            LogUtils.D("isPlayBefore--->" + isPlayBefore);
            if (playBefore && getPlayState()) {//当音频页面进入前台时且播放且资源相同时
              if (!isUpdateAudio) {
                  startUpdateThreadProgress();
                  sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.PLAY_MSG);
                  sendAudioDurtion(duration);
              }else{
                  pause();
                  currentProgressPosition=0;
//                  stopUpdateTHreadProgress();
                  sendAudioProgress("0");//设置当前进度为0
                  AudioNotifacation.cancelNotification();
              }
//                EventBus.getDefault().post(new MessageEvent.Duration(duration));
            }

            if (!playBefore) {//当音频页面进入后台时
                LogUtils.D("进入后台");
                if (getPlayState()) {//播放时
                    stopUpdateTHreadProgress();
                }
            }
            if (playBefore && !getPlayState()) { //当音频页面进入前台时且未再播放时
                LogUtils.D("进入前台 未播放");
               if (!isUpdateAudio){
                   if (mediaPlayer.getDuration() > 0) {
                       sendAudioDurtion(mediaPlayer.getDuration());
                   }
                   sendMsg(CURRENT_POSITION, currentProgressPosition);
                   LogUtils.D("currentProgressPosition---->" + currentProgressPosition);
               }else{
                   currentProgressPosition=0;
                   sendAudioProgress("0");//设置当前进度为0
                   AudioNotifacation.cancelNotification();
               }
            }

//            if (!isPlayBefore&&isLoop){
//                stopUpdateTHreadProgress();
//                LogUtils.D("停止线程");
//            }
//            if (isPlayBefore&&!isLoop){
//                startUpdateThreadProgress();
//                LogUtils.D("开启线程");
        }

        public String getResource_id() {
            return resource_id;
        }

        public void setResource_id(String resource_id) {
            this.resource_id = resource_id;
        }


        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case CURRENT_POSITION: //更新音频进度
                        sendAudioProgress(msg.obj + "");

                        break;
                }
            }
        };

        public ResourceModel getAudioInfo() {
            return audioInfo;
        }


        public void setAudioInfo(ResourceModel audioInfo) {
            if (getAudioInfo() != null) {
                if (!getAudioInfo().getResource_id().equals(audioInfo.getResource_id())) {
                    isUpdateAudio = true;
                } else {
                    isUpdateAudio = false;
                }
            }
            setPlayBefore(true);
            this.audioInfo = audioInfo;
        }

        //电话状态监听
        public void phoneCallregister() {
            LogUtils.D("registerIt");
            mBroadcastReceiver = new PhoneBroadcastReceiver(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommitContent.B_PHONE_STATE);
            intentFilter.setPriority(Integer.MAX_VALUE);
            registerReceiver(mBroadcastReceiver, intentFilter);//注册来电监听

            mAudioReceiver = new AudioBroadcastReceiver(this);
            IntentFilter intentFilter1 = new IntentFilter();
            intentFilter1.addAction(CommitContent.AUDIO_SERVICE_ACTION);
            intentFilter1.addAction(AudioNotifacation.ACTION_BUTTON);
            intentFilter1.addAction(CommitContent.STOP_AUDIO_SERVICE_ACTION);
            intentFilter1.addAction(CommitContent.SEND_NOTIFICATION_ACTION);
            registerReceiver(mAudioReceiver, intentFilter1); //注册音乐播放监听
        }

        /*
           发消息
         */
        private void sendMsg(int what, Object obj) {
            Message msg = Message.obtain();
            msg.what = what;
            msg.obj = obj;
            LogUtils.D("what" + obj);
            handler.sendMessage(msg);
        }

        private boolean  isNext(){//判断是否有下一首
            if (playAudioCount>0&&playAudioIndex<playAudioCount-1) {
                return true;
            }
           return false;
        }



    public  void next(){//播放下一首
        if (isNext()){
            playAudioIndex++;
            setUrl(urlPaths.getUrlModel().get(playAudioIndex).getUrl());
            currentProgressPosition=0;
            play();
        }else{
            sendPlayStateInfo(CommitContent.AUDIO_PLAY_INFO_SHOW_CODE,"没有下一首");
        }
    }
        public  void pre(){ //上一首
            if (playAudioCount>0&&playAudioIndex>0){
                playAudioIndex--;
                setUrl(urlPaths.getUrlModel().get(playAudioIndex).getUrl());
                currentProgressPosition=0;
                play();
            }else{
                sendPlayStateInfo(CommitContent.AUDIO_PLAY_INFO_SHOW_CODE,"没有上一首");
            }
        }



        //向播放页面发送播放状态信息
        private void sendPlayStateInfo(int code,String playState) {
            if (isPlayBefore && !isUpdateAudio) {
                EventBus.getDefault().post(MessageEvent.newInstance().setCode(code).setMessage(playState));
            }
        }

        private void sendAudioProgress(String progress) {//发送音频进度信息
            EventBus.getDefault().post(progress);
        }

        private void sendAudioDurtion(long duration) {//发送音频总时长
            EventBus.getDefault().post(new MessageEvent.Duration(duration));
        }

        public void setLoop(boolean loop) {
            isLoop = loop;
        }

        public MyBinder() {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (isNext()){
                        next();
                    }else{
                        stop();
                    }
                }
            });
            phoneCallregister();
        }

        public void setUrl(String url) {
            if (TextUtils.isEmpty(url)) {
                this.path = CommitContent.NO_AUDIO_URL;
            } else {
                this.path = url;
            }
        }

        public String getUrl() {
            return path;
        }

        //判断是否存在音频资源
        public boolean isExistUrl() {
            LogUtils.D(getUrl() + "<-----getUrl()");
            if (!TextUtils.isEmpty(getUrl())) {
                if (getUrl().equals(CommitContent.NO_AUDIO_URL)) {
                    sendPlayStateInfo(CommitContent.AUDIO_URLPATH_EXCEPTION_CODE,CommitContent.NO_AUDIO_URL);
                    return false;
                } else {
                    sendPlayStateInfo(CommitContent.AUDIO_URLPATH_EXCEPTION_CODE,CommitContent.EXIST_AUDIO_URL);
                    return true;
                }
            }
            return false;
        }


        //获取总时长
        public long getDurtion() {
            return this.duration;
        }


        //获取播放状态
        public boolean getPlayState() {
            return mediaPlayer.isPlaying();
        }


        /**
         * 播放音乐
         *
         * @param
         */
        public synchronized void play() {
            if (isExistUrl()) {
                isUpdateAudio=false;
                try {
//                    if (!audioInfo.getResource_id().equals(getOldResource_id())) {
//                        currentProgressPosition = 0;
//                    }
                    mediaPlayer.reset();//把各项参数恢复到初始状态
                    LogUtils.D("reset");
                    mediaPlayer.setDataSource(getUrl());
                    LogUtils.D("setDataSource");
                    mediaPlayer.prepareAsync();  //进行缓冲
                    LogUtils.D("prepare");
                    mediaPlayer.setOnPreparedListener(new PreparedListener(currentProgressPosition));//注册一个监听器
                    mediaPlayer.setOnErrorListener(new OnMediaErrorListener()); //在播放过程中出现异常的监听
                } catch (Exception e) {
                    sendPlayStateInfo(CommitContent.AUDIO_PLAY_EXCEPTION_CODE,e.toString());
                }
            }
        }

        private class OnMediaErrorListener implements MediaPlayer.OnErrorListener {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                sendPlayStateInfo(CommitContent.AUDIO_PLAY_EXCEPTION_CODE,CommitContent.MEDIA_PREPARE_EXCEPTION);
                return false;
            }
        }

        /**
         * 开启线程
         */
        public synchronized void startUpdateThreadProgress() {
            if (updateThreadprogress == null & isExistUrl()) {
                LogUtils.D("开启线程");
                updateThreadprogress = new InnerThread();
                setLoop(true);
                updateThreadprogress.start();
            }
        }

        public synchronized void stopUpdateTHreadProgress() {
            if (updateThreadprogress != null) {
                setLoop(false);
                updateThreadprogress = null;
            }
        }

        /*
   * 播放指定位置
    */
        public void play(int position) {
            currentProgressPosition=position;
                play();
        }
        /**
         * 播放指定下标的音频
         */
        public  void playByIndex(int audioIndex){
            LogUtils.D("播放了指定下表的音频--》"+audioIndex);
            if (playAudioIndex!=audioIndex){
                playAudioIndex = audioIndex;
                currentProgressPosition=0;
                play();
            }
        }



        /**
         * 暂停音乐
         */
        public void pause() {
            if (isExistUrl()) {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    LogUtils.D("暂停状态------------------------------------------------------------------暂停");
                    currentProgressPosition = mediaPlayer.getCurrentPosition();
                    sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.PAUSE_MSG);
                    stopUpdateTHreadProgress();
                    AudioNotifacation.updateAudioNotification(getPlayState());
                }
            }
        }

        //播放或暂停
        public void playOrPause() {
                onlyPlayOrPause();
//            if(isAudioActivityDo){
//                if (isUpdateAudio){
//                    currentProgressPosition=0;
//                    onlyPlayOrPause();
//                }else
//
//            }else{
//                onlyPlayOrPause();
//            }
//            if (mediaPlayer.isPlaying()) {
//                pause();
//            } else {
//                play();
//            }
        }

        public void onlyPlayOrPause() {
            if (mediaPlayer.isPlaying()) {
                pause();
            } else {
                play();
            }
        }


        private class InnerThread extends Thread {
            @Override
            public void run() {
                while (isLoop) {
                    try {
                        LogUtils.D("mediaPlayer.isPlaying()--->" + mediaPlayer.isPlaying());
                        if (getPlayState() && isPlayBefore) {
                            LogUtils.D("position" + mediaPlayer.getCurrentPosition());
                            sendMsg(CURRENT_POSITION, mediaPlayer.getCurrentPosition());
                        }
                        Thread.sleep(998);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        /**
         * 停止音乐
         */
        public void stop() {
            AudioNotifacation.cancelNotification();
            if (mediaPlayer != null) {
                mediaPlayer.pause();
                currentProgressPosition = 0;
                mediaPlayer.stop();
                sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.STOP_MSG);
                stopUpdateTHreadProgress();

//                try {
//                    mediaPlayer.prepare(); // 在调用stop后如果需要再次通过start进行播放,需要之前调用prepare函数
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
        }

        public void sendOnlyNotification() {
            AudioNotifacation.updateAudioNotification(getPlayState());
        }


        public void onServiceDestroy() {
            if (mediaPlayer != null) {
                stop();
                mediaPlayer.release();
            }
            if (mBroadcastReceiver != null) {
                unregisterReceiver(mBroadcastReceiver);
            }
            if (mAudioReceiver != null) {
                unregisterReceiver(mAudioReceiver);
            }
            stopSelf();
        }

        /**
         * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
         */
        private final class PreparedListener implements MediaPlayer.OnPreparedListener {
            private int positon;

            public PreparedListener(int positon) {
                this.positon = positon;
            }

            @Override
            public void onPrepared(MediaPlayer mp) {
                isLoop = true;
                LogUtils.D("播放状态------------------------------------------------------------------暂停");
                mediaPlayer.start();    //开始播放

                duration = mediaPlayer.getDuration();
                if (isPlayBefore && !isUpdateAudio) {
                    sendAudioDurtion(duration);
//                    EventBus.getDefault().post(new MessageEvent.Duration(duration));
                    startUpdateThreadProgress();
                }
                sendPlayStateInfo(CommitContent.AUDIO_PLAY_INDEX_CODE,playAudioIndex+"");
                sendPlayStateInfo(CommitContent.AUDIO_PLAY_STATE_CODE,CommitContent.PLAY_MSG);
                if (positon > 0) {    //如果音乐不是从头播放
                    mediaPlayer.seekTo(positon);
                }
                AudioNotifacation.sendAudioNotification(AudioPlayerService.this, audioInfo.getResource_picture(), audioInfo.getResource_name(), getPlayState());
            }
        }

    }
}
