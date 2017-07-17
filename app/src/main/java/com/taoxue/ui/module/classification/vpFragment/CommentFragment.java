package com.taoxue.ui.module.classification.vpFragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.ComentModel;
import com.taoxue.ui.model.CommitPageModel;
import com.taoxue.ui.model.PageModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.module.classification.CommentActivity;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.view.StarBar;
import com.taoxue.utils.LogUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;

/**
 * Created by User on 2017/6/7.
 */

public class CommentFragment extends BaseVpFragment {



    @BindView(R.id.commit_view)
    RecyclerView commitView;
    @BindView(R.id.no_content_ll)
    RelativeLayout noContentTv;


    Unbinder unbinder;
    List<ComentModel> comentModels;
    ResourceModel data;

//    private void addItem() {
//        for (int i = 0; i <= 8; i++) {
//            LogUtils.D("i--->" + i);
//            ComentModel model = new ComentModel();
//            model.setResource_id(comentModels.get(0).getResource_id());
//            model.setAddtime(comentModels.get(0).getAddtime());
//            model.setContent(comentModels.get(0).getContent() + "sdcsdvsdcssd" + i + i);
//            model.setId(comentModels.get(0).getId());
//            model.setPhoto(comentModels.get(0).getPhoto());
//            model.setPid(comentModels.get(0).getPid() + "sdf" + i);
//            model.setRealname(comentModels.get(0).getRealname());
//            model.setUser_id(comentModels.get(0).getUser_id());
//            comentModels.add(model);
//        }
//    }

    private void setAdapter() { //设置评论中的页面布局
//        addItem();
        List<ComentModel> models = new ArrayList<>();
        models.clear();
        if (comentModels.size() > 5) {
            for (int i = 0; i < 5; i++) {
                models.add(comentModels.get(i));
            }
        } else {
            models = comentModels;
        }
        ;
        LogUtils.D("models----》" + models.size());

        RvCommonAdapter.Builder builder = new RvCommonAdapter.Builder<ComentModel>(getActivity(), R.layout.book_introduction_item, models);
        builder.setOnLoadMoreListener(new RvCommonAdapter.Builder.OnLoadMoreListener() {
            @Override
            public void onLoadMore(ViewHolder holder) {
                LogUtils.D("评论加载更多");
                holder.setText(R.id.more_progress_text, "查看更多评论");
                holder.setVisible(R.id.more_progress_pb, View.GONE);

                holder.setOnClickListener(R.id.more_progress_text, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        activityToComment();
                    }
                });
            }
        });

        builder.createAdapter(commitView, new RvCommonAdapter.InitViewCallBack<ComentModel>() {
            @Override
            public void convert(ViewHolder holder, ComentModel comentModel, int position) {
                LogUtils.D("position--->" + position);
                holder.setImageUrl(R.id.commit_image_iv, comentModel.getPhoto());
                holder.setText(R.id.commit_time_tv, CommitContent.millisToDate(comentModel.getAddtime()));
                holder.setText(R.id.commit_content_tv, CommitContent.nullToSting(comentModel.getContent()));
                holder.setText(R.id.commit_name_tv, CommitContent.nullToSting(comentModel.getRealname()));
                ((StarBar) holder.getView(R.id.commit_start_bar)).setStarMark((float) 3);
                ((StarBar) holder.getView(R.id.commit_start_bar)).setChangMark(false);
            }
        });
        commitView.setHasFixedSize(true);
        commitView.setNestedScrollingEnabled(true);
    }


    private void activityToComment() {//跳转至评论activty中
        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).launch(CommentActivity.class, data);
        }
    }


    private void requestQueryCommitContent() {
        LogUtils.D("查询评论请求");
        Call<CommitPageModel<PageModel<ComentModel>>> call = HttpAdapter.getService().queryResourceComment(data.getResource_id(), "10", "1");
        call.enqueue(new OnResponseNoDialogListener<CommitPageModel<PageModel<ComentModel>>>() {
            @Override
            protected void onSuccess(CommitPageModel<PageModel<ComentModel>> model) {
                LogUtils.D("98775454csdckhsjdbhdcjsb");
                LogUtils.D("model.getCode()--->" + model.getCode());
                if (model.getCode() == 1 && null != model.getPage() && !"[]".equals(model.getPage()+"") && null != model.getPage().getResult() && !"[]".equals(model.getPage().getResult()+"")) {
                    comentModels = model.getPage().getResult();
                    if (comentModels.size() > 0) {
                        LogUtils.D("有评论---》" + comentModels.toString());
                        setAdapter();
                        return;
                    }
                }
                LogUtils.D("没有评论");
                noContent();
            }
        });
    }

    private void noContent() { //没有评论
//        noContentTv.setText("暂时没有评论，请可以添加评论哦 +");
//        noContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//        noContentTv.setTextColor(Color.BLACK);
        commitView.setVisibility(View.GONE);
        noContentTv.setVisibility(View.VISIBLE);

    }


    @Override
    protected void onInit(View view) {
        if (data != null) {
            requestQueryCommitContent();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.vp_book_commit;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    protected void getActiArguments(Serializable arguments) {
        LogUtils.D("getId()--->" + getId());
        data = (ResourceModel) arguments;
        if (data != null) {
            LogUtils.D("data--->" + data.toString());
        } else {
            LogUtils.D("data--->null");
        }
    }


//    @Override
//    public void setArgumentsObj(Object obj) {
//        super.setArgumentsObj(obj);
//    }


    @OnClick(R.id.no_content_tv)
    public void onViewClicked() {
        activityToComment();
    }
}
