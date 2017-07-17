package com.taoxue.ui.module.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.danxx.mdplayer.mdplayer.MDPlayer;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.model.VideoCatalog;
import com.taoxue.ui.module.classification.CollectionView;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.utils.ImageLoaderUtil;
import com.taoxue.utils.LimitLineTextView;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.StatusBarCompat;
import com.taoxue.utils.UtilTools;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by User on 2017/3/31.
 */

public class PlayActivity extends BaseActivity implements MDPlayer.OnNetChangeListener {

    @BindView(R.id.view_super_player)
    MDPlayer player;
    @BindView(R.id.full_screen)
    FrameLayout fullScreen;//当视频窗口全屏的时候videoview的容器

    @BindView(R.id.tv_super_player_complete)
    TextView tvSuperPlayerComplete;
    @BindView(R.id.video_screen)
    FrameLayout videoScreen;
    @BindView(R.id.video_image_iv)
    ImageView videoImageIv;
    @BindView(R.id.video_title_tv)
    TextView videoTitleTv;
    @BindView(R.id.video_type_tv)
    TextView videoTypeTv;
    @BindView(R.id.video_dianji_count_tv)
    TextView videoDianjiCountTv;
//    @BindView(R.id.commit_view)
//    @BindView(R.id.commit_count_tv)
//    TextView videoCommitCountTv;
//    @BindView(R.id.commit_btn_tv)
//    TextView videoCommitBtnTv;
//    @BindView(R.id.book_commit_lv)
//    CommitView videoCommitLv;

    private boolean isLive;
    private EditText videoUrl;
    private Button playBtn;


    private ResourceModel data;
    private Context context = this;
    private String id;

    /**
     * 测试地址
     */
//    private String url = "http://117.71.57.47:9000/DRIS_manager_V1.0.1//upload/2017/04/06/402883bc5b42a341015b42b32e5d0004.mp4";
    private String url = "http://baobab.wandoujia.com/api/v1/playUrl?vid=9392&editionType=normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_play);
        ButterKnife.bind(this);
        //修改状态栏颜色
        StatusBarCompat.compat(this, Color.parseColor("#e0000000"));//半透明

        getIntentData();//得到intent中的数据
        Intent intent = new Intent(Intent.ACTION_VIEW);

    }


    private void initVideoView() {
            videoTitleTv.setText(nullToSting(data.getResource_name()));
            ImageLoaderUtil.displayImage(data.getResource_picture(), videoImageIv);
            videoDianjiCountTv.setText("123");
            videoTypeTv.setText(CommitContent.nullToSting(data.getFile_format()));

        initPlayer(data.getResource_name(), UtilTools.getProxyUrl(urlPath.getUrlModel().get(0).getUrl()));
//          String user_id=CommitContent.isLogin(this);
//            if(user_id!=null){
//                initVideoFile(data.getResource_id(),user_id,data.getResource_name());
//            }
//            if (TextUtils.isEmpty(data.getUrl())) {
//                showToast("当前测试,无文件");
//                initPlayer("视频播放", url);
//            } else {
//                initPlayer(videoCatalog.getTitle(), data.getUrl());
//                String type = data.getUrl().substring(data.getUrl().lastIndexOf(".") + 1);
//            }
        }


//    //获取
//    private void initVideoFile(String resource_id, String user_id, final String title) {
//        Call<BaseListModel<UrlModel>> call = HttpAdapter.getService().getFileUrl(resource_id, user_id);
//        call.enqueue(new OnResponseNoDialogListener<BaseListModel<UrlModel>>() {
//            @Override
//            protected void onSuccess(BaseListModel<UrlModel> model) {
//                if (model != null) {
//                    urls = model.getItem();
//                    if (urls != null) {
//
//                    }else{
//                        showToast("没有视频文件");
//                    }
//                }else{
//                    showToast("未获取视频文件");
//                }
//            }
//        });
//    }
    private  UrlPath urlPath;
    /**
     * 得到intent中的数据
     *
     * @return
     */
    private void getIntentData() {
        urlPath=(UrlPath) getIntentKey1();
        data=(ResourceModel) getIntentKey2();
        if (null!=urlPath&&null!=data) {
            initVideoView();
//            initResourceVideoDetail(id);
        } else {
            showToast("当前无资源");
        }
    }

//    //获取图像详情页
//    private void initResourceVideoDetail(String id) {
//
//        Call<BaseResultModel<ResourceModel<VideoCatalog>>> call = HttpAdapter.getService().getVideoResourceDetail(id);
//        call.enqueue(new OnResponseListener<BaseResultModel<ResourceModel<VideoCatalog>>>(this) {
//            @Override
//            protected void onSuccess(BaseResultModel<ResourceModel<VideoCatalog>> model) {
//                ResourceModel<VideoCatalog> resourceModel = model.getData();
//                VideoCatalog videoCatalog = resourceModel.getCatalog();
//                data = resourceModel;
//                if (resourceModel.equals("") & resourceModel == null) {
//                    showToast("无法获取当前资源");
//                } else {
//                    LogUtils.D("ERSOURCE---->" + resourceModel.toString());
//                    LogUtils.D("_id-=->" + resourceModel.get_id() + "?? getResource_id--->" + resourceModel.getResource_id() + "?? Upload_user_id-->" + resourceModel.getUpload_user_id());
//                    initVideoView(videoCatalog);
//                }
//            }
//        });
//    }

    /**
     * 初始化播放器
     */
    private void initPlayer(String title, String url) {
        if (isLive) {
            player.setLive(true);//设置该地址是直播的地址
        }
        player.setNetChangeListener(true)//设置监听手机网络的变化
                .setOnNetChangeListener(this)//实现网络变化的回调
                .onPrepared(new MDPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared() {
                        /**
                         * 监听视频是否已经准备完成开始播放。（可以在这里处理视频封面的显示跟隐藏）
                         */
                    }
                }).onComplete(new Runnable() {
            @Override
            public void run() {
                /**
                 * 监听视频是否已经播放完成了。（可以在这里处理视频播放完成进行的操作）
                 */
            }
        }).onInfo(new MDPlayer.OnInfoListener() {
            @Override
            public void onInfo(int what, int extra) {
                /**
                 * 监听视频的相关信息。
                 */

            }
        }).onError(new MDPlayer.OnErrorListener() {
            @Override
            public void onError(int what, int extra) {
                /**
                 * 监听视频播放失败的回调
                 */

            }
        }).setTitle(title)//设置视频的titleName
                .play(url);//开始播放视频
        player.setScaleType(MDPlayer.SCALETYPE_FITXY);
    }

    @Override
    protected void onInit() {
        super.onInit();
    }

    @OnClick(value = {})
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }

    @Override
    public void onWifi() {
//        showToast("当前网络环境是WIFI");

    }

    @Override
    public void onMobile() {
        showToast("当前网络环境是手机网络");
        player.pause();
    }

    @Override
    public void onDisConnect() {
        showToast("网络链接断开");
        player.pause();
    }

    @Override
    public void onNoAvailable() {
        showToast("无网络链接");
        player.pause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (player != null && player.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    /**
     * 下面的这几个Activity的生命状态很重要
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (player != null) {
            player.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null) {
            player.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.onDestroy();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        if (player != null) {
//            player.onConfigurationChanged(newConfig);
//        }
        if (player != null) {
            /**
             * 在activity中监听到横竖屏变化时调用播放器的监听方法来实现播放器大小切换
             */
            player.onConfigurationChanged(newConfig);
            // 切换为小屏
            if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
                fullScreen.setVisibility(View.GONE);
                fullScreen.removeAllViews();
                FrameLayout frameLayout = (FrameLayout) findViewById(com.danxx.mdplayer.R.id.video_screen);
                frameLayout.removeAllViews();
                ViewGroup last = (ViewGroup) player.getParent();//找到videoitemview的父类，然后remove
                if (last != null) {
//                    last.removeAllViews();
                    last.removeView(player);
                }
                frameLayout.addView(player);
                int mShowFlags =
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                fullScreen.setSystemUiVisibility(mShowFlags);
            } else if (newConfig.orientation == this.getResources().getConfiguration().ORIENTATION_LANDSCAPE) {
                //切换为全屏
                ViewGroup viewGroup = (ViewGroup) player.getParent();
                if (viewGroup == null)
                    return;
                viewGroup.removeAllViews();
                fullScreen.addView(player);
                fullScreen.setVisibility(View.VISIBLE);
                int mHideFlags =
                        View.SYSTEM_UI_FLAG_LOW_PROFILE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
                fullScreen.setSystemUiVisibility(mHideFlags);
            }
        } else {
            fullScreen.setVisibility(View.GONE);
        }
    }

}
