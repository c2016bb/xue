package com.taoxue.ui.module.classification;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.AudioCatalog;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

public class ResourceDetailActivity extends BaseActivity {
    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.resplay_image_iv)
    ImageView resplayImageIv;
    @BindView(R.id.resplay_title_tv)
    TextView resplayTitleTv;
    @BindView(R.id.resplay_starbar_pinfen)
    StarBar resplayStarbarPinfen;
    @BindView(R.id.resplay_pingfen_coun_tv)
    TextView resplayPingfenCounTv;
    @BindView(R.id.resplay_collection_iv)
    ImageView resplayCollectionIv;
    @BindView(R.id.resplay_collection_coumn_tv)
    TextView resplayCollectionCoumnTv;
    @BindView(R.id.resplay_giv_thumb_iv)
    ImageView resplayGivThumbIv;
    @BindView(R.id.resplay_giv_thumb_coumn_tv)
    TextView resplayGivThumbCoumnTv;
    @BindView(R.id.resplay_jieshao_ll)
    LinearLayout resplayJieshaoLl;
    @BindView(R.id.resplay_recyclerview)
    RecyclerView resplayRecyclerview;
    @BindView(R.id.activity_resource_detail)
    LinearLayout activityResourceDetail;
    private String id;
    private AudioCatalog audioCatalog;
    private ResourceModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_detail);
        ButterKnife.bind(this);

        getIntentData();
    }

    /**
     * 得到intent中的数据
     *
     * @return
     */
    private void getIntentData() {
        final Intent intent = getIntent();
        id = intent.getStringExtra("booklist");
        if (!TextUtils.isEmpty(id)) {
//            initResourceAudioDetail(id);
        } else {
            showToast("当前无资源");
        }
    }
//
//    //获取图像详情页
//    private void initResourceAudioDetail(String id) {
//
//        Call<BaseResultModel<ResourceModel<AudioCatalog>>> call = HttpAdapter.getService().getAudioResourceDetail(id);
//        call.enqueue(new OnResponseListener<BaseResultModel<ResourceModel<AudioCatalog>>>(this) {
//            @Override
//            protected void onSuccess(BaseResultModel<ResourceModel<AudioCatalog>> model) {
//                ResourceModel<AudioCatalog> resourceModel =  model.getData();
//                audioCatalog = resourceModel.getCatalog();
//                data = resourceModel;
//                if (resourceModel.equals("") & resourceModel == null) {
//                    showToast("无法获取当前资源");
//                } else {
//                    LogUtils.D("ERSOURCE---->" + resourceModel.toString());
//                    LogUtils.D("_id-=->" + resourceModel.get_id() + "?? getResource_id--->" + resourceModel.getResource_id() + "?? Upload_user_id-->" + resourceModel.getUpload_user_id());
//                    initAudioView();
////                    sendAudioInfoBroacast(resourceModel);
//                }
//            }
//        });
//
//
//    }
    private List<UrlModel> urls;
    //获取音频的文件
    private void initAudioFile(final String resource_id, String user_id) {

        Call<BaseListModel<UrlModel>> call = HttpAdapter.getService().getFileUrl(resource_id, user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<UrlModel>>() {
            @Override
            protected void onSuccess(BaseListModel<UrlModel> model) {
                if (model != null) {
                    urls = model.getItem();
                    if (urls != null) {
//                        audioUrl = TextUtils.isEmpty(urls.get(0).getUrl()) ? CommitContent.NO_AUDIO_URL : urls.get(0).getUrl();
//                        sendAudioUrlBroacast(audioUrl, resource_id);
                    } else {
                        showToast("没有音频文件");
                    }
                } else {
                    showToast("未获取音频文件");
                }
            }
        });
    }



    private void initAudioView() {
//        resplayTitleTv.setText(CommitContent.nullToSting(audioCatalog.getTitle()));
//        introductionTopbar.setTitle(CommitContent.nullToSting(audioCatalog.getTitle()));
//        UtilGlide.loadImg(this, data.getResource_picture(), resplayImageIv);
//        resplayCollectionCoumnTv.setText(nullToSting(data.getPingfen()));
//        resplayStarbarPinfen.setStarMark(Float.parseFloat(data.getPingfen()));
//        resplayStarbarPinfen.setChangMark(false);
//        resplayCollectionCoumnTv.setText(nullToSting(data.getShoucang_num()));
//        resplayGivThumbCoumnTv.setText(nullToSting(data.getZan_num()));


        resplayRecyclerview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                launch();
                String user_id = CommitContent.isLogin(ResourceDetailActivity.this);
                if (user_id != null) {
                    //获取音乐文件
                    initAudioFile(data.getResource_id(), user_id);
                }
            }
        });
//        SingleItemAdapter adapter=new Sin
    }
}
