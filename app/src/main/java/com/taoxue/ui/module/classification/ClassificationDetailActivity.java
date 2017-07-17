package com.taoxue.ui.module.classification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.OnItemAdapterClickListener;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.ResultModel;
import com.taoxue.ui.module.home.ZXingActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by User on 2017/4/7.
 */

public class ClassificationDetailActivity extends BaseActivity {
    @BindView(R.id.classification_detail_topbar)
    TopBar topbar;
    @BindView(R.id.classification_detail_gv)
    RecyclerView classGv;
//    @BindView(R.id.classification_detail_ptr_layout)
//    PtrClassicFrameLayout ptrLayout;

    //    XRecyclerView classGv;
//    @BindView(R.id.classification_detail_ptr_layout)
//    PtrClassicFrameLayout ptr;
    private RvCommonAdapter commonAdapter;
    private String title;
    private String type;
    private List<ResultModel> results;
    //    private CommonAdapter adapter;
    private ResourceModel resourceModel;
    private Context context = this;
    private final static int PAGENO1 = 1;
    private int pageNo = 2;
    private int pageSize = 10;
    //    LoadMoreWrapper mLoadMoreAdapter;
    TextView loadMoreTv;
    ProgressBar loadMorePb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_classification_detail);
        ButterKnife.bind(this);
        setTitle();
        //设置向右滑动退出
        CommitContent.setColorLeftSilde(this);
    }

    /**
     * 初始化下拉刷新
     */
    private void intPtrLayout() {

//        final MaterialHeader header = new MaterialHeader(getContext());
//        int[] colors = getResources().getIntArray(R.array.google_colors);
//        header.setColorSchemeColors(colors);
//        header.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
//        header.setPadding(30, LocalDisplay.dp2px(15), 0, LocalDisplay.dp2px(10));
//        header.setPtrFrameLayout(ptrLayout);

//        ptrLayout.setLoadingMinTime(1000);
//        ptrLayout.setDurationToCloseHeader(1500);
//        ptrLayout.setHeaderView(header);
//        ptrLayout.addPtrUIHandler(header);
//        ptrLayout.setDurationToClose(100);
//        ptrLayout.setPinContent(true);

//        classGv.setVerticalScrollBarEnabled(false);

//        ptrLayout.setLastUpdateTimeRelateObject(this);
//        ptrLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                refreshBegin();
//                LogUtils.D("刷新开始");
//            }
//
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, classGv, header);
//            }
//        });
//        // the following are default settings
//        ptrLayout.setResistance(1.7f);
//        ptrLayout.setRatioOfHeaderHeightToRefresh(1.2f);
//        ptrLayout.setDurationToClose(200);
//        ptrLayout.setDurationToCloseHeader(1000);
//        // default is false
//        ptrLayout.setPullToRefresh(false);
//        // default is true
//        ptrLayout.setKeepHeaderWhenRefresh(true);
//        ptrLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // mPtrFrame.autoRefresh();
//            }
//        }, 100);


//        ptrLayout.setLastUpdateTimeRelateObject(this);
//        ptrLayout.disableWhenHorizontalMove(true);
//        ptrLayout.setPtrHandler(new PtrHandler() {
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return PtrDefaultHandler.checkContentCanBePulledDown(frame, classGv, header);
//            }
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//                LogUtils.D("刷新开始");
//                refreshBegin();
//            }
//        });
    }


    //刷新开始
    private void refreshBegin() {
        Call<BasePageModel<ResultModel>> call = HttpAdapter.getService().getDetail(title, type, PAGENO1 + "", pageSize + "");
        call.enqueue(new OnResponseNoDialogListener<BasePageModel<ResultModel>>() {
            @Override
            protected void onSuccess(final BasePageModel<ResultModel> model) {
                showToast("刷新" + model.getMsg());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageNo = 2;
//                ptr.refreshComplete();
//                        ptrLayout.refreshComplete();//刷新停止
//                classGv.hideMoreProgress();
                        results.clear();
                        results.addAll(model.getPage().getResult());
//                results = model.getPage().getResult();
                        LogUtils.I("results-->" + results.toString());
                        if (results.equals("") || results == null) {

                        } else {
                            commonAdapter.notifyDataSetChangedRv();
                        }
//                        classGv.refreshComplete();
                    }
                }, 1000);
            }
        });
    }


    private void setTitle() {
        title = (String)getIntentKey1();
        type = (String) getIntentKey2();
        LogUtils.I("guanName-title->" + title);
        if (!TextUtils.isEmpty(title)) {
            topbar.setTitle(title);
            topbar.setBtnTopRightIcon(R.mipmap.home_icon_scan);
            topbar.setBtnTopRightClickListener(new ClickListener());
//            classGv.setOnItemClickListener(this);
            if (TextUtils.isEmpty(type)) {
                initDetail(title, "false");
            } else {
                if (title.equals("image")) {
                    topbar.setTitle("图像");
                } else if (title.equals("video")) {
                    topbar.setTitle("视频");
                } else if (title.equals("audio")) {
                    topbar.setTitle("音频");
                } else if (title.equals("doc")) {
                    topbar.setTitle("电子书");
                }
                initDetail(title, type);
            }
        }
    }


    //调用二维码
    private void initActivity() {
        UtilIntent.intentDIYLeftToRight(this, ZXingActivity.class);
    }


    class ClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            initActivity();
        }
    }

    /**
     * 获取分类详情
     */
    private void initDetail(String key, String type_flag) {
        Call<BasePageModel<ResultModel>> call = HttpAdapter.getService().getDetail(key, type_flag, PAGENO1 + "", pageSize + "");
        call.enqueue(new OnResponseNoDialogListener<BasePageModel<ResultModel>>() {
            @Override
            protected void onSuccess(BasePageModel<ResultModel> model) {
                LogUtils.D("model.getcode()" + model.getCode());
                if (model.getCode() == 1 && null != model.getPage() && !"[]".equals(model.getPage()) && null != model.getPage().getResult() && !"[]".equals(model.getPage().getResult() + "")) {
                    results = model.getPage().getResult();
                    setRecycle();
                } else {
                    showToast("未获取资源");
                }
            }
        });
    }

    private  class OnItem implements OnItemAdapterClickListener{
        @Override
        public void onItemClick(View view, ViewHolder holder, int position, int viewType) {
            String _id = results.get(position).getId();
            String fileType = results.get(position).getFile_type();
            launch(BookIntroductionActivity.class, _id);
        }
    }

    private  class OnLoadMore implements RvCommonAdapter.Builder.OnLoadMoreListener{
        @Override
        public void onLoadMore(ViewHolder holder) {
            if (results.size() >= 10) {//当有一列数据值加载更多
                onloadmore(holder);
            } else {
                holder.setVisible(R.id.load_more_progress_rl, View.GONE);
            } ;

        }
    }


    private void setRecycle() {//设置页面显示
        commonAdapter = new RvCommonAdapter.Builder<ResultModel>(this, R.layout.classification_detail_item, results)
                         .setLayoutManagerType(RvCommonAdapter.Builder.GRIDLAYOUTMANAGER, 3)
                         .setOnItemAdapterClickListener(new OnItem())
                         .setOnLoadMoreListener(new OnLoadMore())
                         .createAdapter(classGv, new RvCommonAdapter.InitViewCallBack<ResultModel>() {
            @Override
            public void convert(ViewHolder holder, ResultModel resultModel, int position) {
                holder.setText(R.id.class_title_tv, CommitContent.nullToSting(resultModel.getTitle()));
                holder.setImageUrl(R.id.picture_iv, resultModel.getResource_picture());
            }
        });
    }

    private void onloadmore(final ViewHolder holder) {  //加载更多
        Call<BasePageModel<ResultModel>> call = HttpAdapter.getService().getDetail(title, type, pageNo + "", pageSize + "");
        call.enqueue(new OnResponseNoDialogListener<BasePageModel<ResultModel>>() {
            @Override
            protected void onSuccess(final BasePageModel<ResultModel> model) {
                LogUtils.D("pageNo--->" + pageNo);
                if (1 == model.getCode() && null != model.getPage() && null != model.getPage().getResult() && !("[]".equals(model.getPage().getResult() + ""))) {
                    results.addAll(model.getPage().getResult());
                    commonAdapter.notifyDataSetChangedRv();
                    pageNo++;
                } else { //加载完成
                    LogUtils.D("pageNo2--->" + pageNo);
                    holder.setText(R.id.more_progress_text, "已经没有更多了");
                    holder.setVisible(R.id.more_progress_pb, View.GONE);
                }
            }
        });
    }

    @Override
    protected void onInit() {
        super.onInit();
    }
}
