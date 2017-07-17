package com.taoxue.ui.module.classification;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.view.AudioListDialog;
import com.taoxue.ui.view.FullCommonDialog;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioPlayActivity extends BaseActivity {

    @BindView(R.id.audiplay_topbar)
    TopBar audiplayTopbar;
    @BindView(R.id.audioplay_author_tv)
    TextView audioplayAuthorTv;
    @BindView(R.id.audioplay_circleview_iv)
    ImageView audioplayCircleviewIv;
    @BindView(R.id.audioplay_collection_iv)
    ImageView audioplayCollectionIv;
    @BindView(R.id.audioplay_ping_lun_iv)
    ImageView audioplayPingLunIv;
    @BindView(R.id.audioplay_ping_fen_iv)
    ImageView audioplayPingFenIv;
    @BindView(R.id.audioplay_more_iv)
    ImageView audioplayMoreIv;
    @BindView(R.id.audioplay_current_position_tv)
    TextView audioplayCurrentPositionTv;
    @BindView(R.id.audioplay_play_progress_sb)
    SeekBar audioplayPlayProgressSb;
    @BindView(R.id.audioplay_durtion_tv)
    TextView audioplayDurtionTv;
    @BindView(R.id.previous_iv)
    ImageView previousIv;
    @BindView(R.id.play_play_pause_iv)
    ImageView playPauseIv;
    @BindView(R.id.next_iv)
    ImageView nextIv;
    @BindView(R.id.audioplay_ll)
    LinearLayout audioplayLl;

    ResourceModel model;
    UrlPath urlPath;
    private boolean isSeekBar = false;
    private int durition;
    private boolean isCollection=false;//是否收藏
    private List<UrlModel> urls;
    private String audioUrl;
    double rat=0.7;//
    private  int currentIndex=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        Android全屏显示时，状态栏显示在最顶层,不隐藏。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_audio_play);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
////            //透明底部导航栏
////            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }

        ButterKnife.bind(this);
        getIntentData();
//        sendAudioPlayIsForwardBroacast(true);
//
    }


 private  void  getIntentData(){
     urlPath=(UrlPath) getIntentKey1();
     model=(ResourceModel) getIntentKey2();
     if (urlPath!=null&&urlPath.getUrlModel().size()>0&&model!=null){
         urls=urlPath.getUrlModel();
         sendAudioInfoBroacast(model);
         audioUrl = urls.get(0).getUrl();
         sendAudioUrlBroacast(urlPath);
         updateView();
     }else{
         showToast("当前无音频资源");
     }
 }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sendAudioPlayIsForwardBroacast(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    //获取音频总时长
    @Override
    public void onEventMainThread(MessageEvent.Duration event) {
        LogUtils.D("总时长");
        durition = (int) event.getCurent();
        audioplayDurtionTv.setText(intToTime(durition));
    }
    private void updateView(){
        if (model.getIs_collection().equals("1")){
            audioplayCollectionIv.setImageResource(R.mipmap.collectioned);
            isCollection=true;
        }

        audioplayCircleviewIv.getLayoutParams().height=(int)(DisplayUtil.screenWidthPx*rat);
        audioplayCircleviewIv.getLayoutParams().width=(int)(DisplayUtil.screenWidthPx*rat);

        LogUtils.D(model.getResource_picture()+"--url");
        UtilGlide.showImageViewBlur(this,R.mipmap.image_default,model.getResource_picture(),audioplayLl);
       UtilGlide.loadImg(this,model.getResource_picture(),audioplayCircleviewIv);
        audiplayTopbar.setTitle(nullToSting(model.getResource_name()));
        audioplayPlayProgressSb.setOnSeekBarChangeListener(new OnSeekBarChange());
        LogUtils.D("model.getCatalog()--->"+model.getCatalog());
        LogUtils.D("model.getCatalog().indexOf(\"：\")--->"+model.getCatalog().indexOf("："));
         LogUtils.D("model.getCatalog().indexOf(\"<\",5)--->"+model.getCatalog().indexOf("<",5));

        final  int authorStart=model.getCatalog().indexOf("作者：")+3;
     String author=model.getCatalog().substring(authorStart,model.getCatalog().indexOf("<",authorStart));
        if (author==null){
            author="音频";
        }

        final  int durationStart=model.getCatalog().indexOf("时长：")+3;
        String audioDuration=model.getCatalog().substring(durationStart,model.getCatalog().indexOf("<",durationStart));
        LogUtils.D("audioDuration"+audioDuration +"length"+audioDuration.length());

        if (audioDuration==null){
            audioDuration="00:00";
        }else{
            audioDuration=audioDuration.substring(3,8);
        }
        audioplayAuthorTv.setText(author);
        audioplayDurtionTv.setText(audioDuration);
    }

    //音频进度条效果
    private class OnSeekBarChange implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            sendAudioBroacast(CommitContent.STOP_UPDATE_THREAD_PROGRESS);
            isSeekBar = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sendAudioPlayBroacast((durition * seekBar.getProgress()) / 100);
        }
    }
    //获取音音频播放状态
    @Override
    public void onEventMainThread(MessageEvent event) {
     if (event.getCode()==CommitContent.AUDIO_PLAY_STATE_CODE){
         if (event.getMessage().toString().equals(CommitContent.PLAY_MSG)) {//播放时
             playPauseIv.setImageResource(R.mipmap.audio_pause);
         } else if (event.getMessage().toString().equals(CommitContent.PAUSE_MSG)) {//暂停
             playPauseIv.setImageResource(R.mipmap.audio_play);
         } else if (event.getMessage().toString().equals(CommitContent.STOP_MSG)) {//停止
             audioplayPlayProgressSb.setProgress(0);
             playPauseIv.setImageResource(R.mipmap.audio_play);
             audioplayCurrentPositionTv.setText("00:00");
         }

     } else if(event.getCode()==CommitContent.AUDIO_URLPATH_EXCEPTION_CODE){
        if (event.getMessage().toString().equals(CommitContent.NO_AUDIO_URL)) {//没有音频文件资源
             showToast("没有音频资源");
             if (isSeekBar) {
                 isSeekBar = false;
                 audioplayPlayProgressSb.setProgress(0);
             }
         }else if (event.getMessage().toString().equals(CommitContent.EXIST_AUDIO_URL)) {  //存在音频路径
            if (isSeekBar) {
                isSeekBar = false;
            }
        }else  if(event.getMessage().toString().equals(CommitContent.NO_GET_AUDIO_URL)){
            showToast("服务未获取到音频路径");
        }
     }else if (event.getCode()==CommitContent.AUDIO_PLAY_EXCEPTION_CODE){
         showToast(event.getMessage().toString());
     }else  if (event.getCode()==CommitContent.AUDIO_PLAY_INFO_SHOW_CODE){
         showToast(event.getMessage().toString());
     }else if (event.getCode()==CommitContent.AUDIO_PLAY_INDEX_CODE){
        final int index=Integer.parseInt(event.getMessage().toString());
         if (currentIndex!=index){
             currentIndex=index;
             audioListDialog.setIndex(currentIndex);
         }
     }
    }


    //获取音频当前播放时长
    public void onEventMainThread(String str) {
        LogUtils.D(str + "" + "str");
        int current = Integer.parseInt(str);
        audioplayCurrentPositionTv.setText(intToTime(current) + "");
        if (durition > 0) {
            int currentProgress = current * 100 / durition;
            audioplayPlayProgressSb.setProgress(currentProgress);
        }
    }
    private String intToTime(int time) {
        String fen = (time / (60 * 1000)) + "";
        String miao = ((time / 1000) % 60) + "";
        fen = fen.length() == 1 ? 0 + fen : fen;
        miao = miao.length() == 1 ? 0 + miao : miao;
        return fen + ":" + miao;
    }






    private void sendAudioUrlBroacast(UrlPath url) {
        Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
        intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, CommitContent.AUDIO_PLAY_URL);
        intent.putExtra(CommitContent.AUDIO_PLAY_URL_TAG, url);
//        intent.putExtra(CommitContent.AUDIO_RESOURCE_ID, resource_id);
        sendBroadcast(intent);
    }

    private void sendAudioPlayBroacast(int position) {//发送播放位置的广播
        Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
        intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, CommitContent.AUDIO_START_PLAY);
        intent.putExtra(CommitContent.AUDIO_PLAY_TAG, position);
        sendBroadcast(intent);
    }

    private void sendAudioInfoBroacast(ResourceModel resourceModel) {//发送音频信息广播
        Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
        intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, CommitContent.AUDIO_INFO);
        intent.putExtra(CommitContent.AUDIO_INFO_TAG, resourceModel);
        sendBroadcast(intent);
    }

    private void sendAudioPlayIsForwardBroacast(boolean isForward) { //发送音频播放页面是否处于前台
        Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
        intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, CommitContent.PLAY_ACTIVITY_IS_FORWORD);
        intent.putExtra(CommitContent.PLAY_ACTIVITY_IS_FORWORD_TAG,isForward);
        sendBroadcast(intent);
    }


    private void sendAudioBroacast(String str) {
            Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
            intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, str);
            sendBroadcast(intent);
    }
  private  void sendAudioPlayIndex(int index){
      Intent intent = new Intent(CommitContent.AUDIO_SERVICE_ACTION);
      intent.putExtra(CommitContent.ACTIVITY_VIEW_PLAY_TAG, CommitContent.AUDIO_PLAY_INDEX_MSG);
      intent.putExtra(CommitContent.AUDIO_PLAY_INDEX_TAG, index);
      sendBroadcast(intent);
  }


    private void requestCollection(){//收藏
        String user_id=CommitContent.isLogin(AudioPlayActivity.this);
        if (user_id!=null){
            HttpRequest.addCollection(user_id, model.getResource_id(), model.getGys_id(), new HttpRequest.RequestCallBack() {
                @Override
                public void onSuccess(String msg) {
                    showToast("收藏成功");
                    audioplayCollectionIv.setImageResource(R.mipmap.collectioned);
                    isCollection=true;
                }
                @Override
                public void onRequested(String msg) {
                    showToast("已经收藏过了");
                }
                @Override
                public void onUnSuccess(String msg) {
                    showToast(msg);
                }
            });
        }
    }

    private  void  requestGiveThumb(){//点赞
        String user_id=CommitContent.isLogin(AudioPlayActivity.this);
        if (user_id!=null){
            HttpRequest.giveThumbs(user_id, model.getResource_id(), model.getGys_id(), new HttpRequest.RequestCallBack() {
                @Override
                public void onSuccess(String msg) {
                    showToast("已点赞");
                    audioplayPingFenIv.setImageResource(R.mipmap.givethumbed);
                }
                @Override
                public void onRequested(String msg) {
//                    showToast("已经点赞过了");
                }
                @Override
                public void onUnSuccess(String msg) {
                    showToast(msg);
                }
            });
        }
    }
  private   AudioListDialog  audioListDialog;
    @OnClick({R.id.audioplay_collection_iv, R.id.audioplay_ping_lun_iv, R.id.audioplay_ping_fen_iv, R.id.audioplay_more_iv, R.id.previous_iv, R.id.play_play_pause_iv, R.id.next_iv, R.id.audioplay_ll})
    public void onViewClicked(View view) {
            switch (view.getId()) {
            case R.id.audioplay_collection_iv:
                if (model!=null){
                    if (model.getIs_collection().equals("0")){
                           requestCollection();
                    }else{
                        showToast("已经收藏过了");
                    }
                }
                break;
            case R.id.audioplay_ping_lun_iv:
                launch(CommentActivity.class, model);
                break;
            case R.id.audioplay_ping_fen_iv: //点赞
                if(model!=null){
                    requestGiveThumb();
                }
                break;
            case R.id.audioplay_more_iv:
                if (urlPath!=null) {
                    if (audioListDialog==null) {

                       audioListDialog = new AudioListDialog(this, R.layout.audio_list, urlPath, new FullCommonDialog.OnItemClickListener() {
                            @Override
                            public void onClick(View v, int position) {
                                LogUtils.D("dianjile " + position);
                                sendAudioPlayIndex(position);
                            }
                        });
                        audioListDialog.show();
                    }else{
                        audioListDialog.show();
                    }
                }else{
                    LogUtils.D("urlPath 为空");
                }
                break;
            case R.id.previous_iv:
                sendAudioBroacast(CommitContent.PLAY_PRE_MSG);//播放下一首
                break;
            case R.id.play_play_pause_iv:
                    sendAudioBroacast(CommitContent.PLAY_OR_PLAUSE_MSG);
                break;
            case R.id.next_iv:
                sendAudioBroacast(CommitContent.PLAY_NEXT_MSG);//播放上一首
                break;
            case R.id.audioplay_ll:
                break;
        }
    }
}
