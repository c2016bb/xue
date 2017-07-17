package com.taoxue.ui.module.classification;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.taoxue.ui.model.ComentModel;
import com.taoxue.ui.model.CommitPageModel;
import com.taoxue.ui.model.PageModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class CommentActivity extends BaseActivity {

    @BindView(R.id.comment_introduction_topbar)
    TopBar commentIntroductionTopbar;
    @BindView(R.id.comment_recyclerview)
    RecyclerView commentRecyclerview;
    @BindView(R.id.comment_no_content_ll)
    RelativeLayout commentNoContentTv;
    @BindView(R.id.comment_start_bar)
    StarBar commentStartBar;
    @BindView(R.id.comment_Content_et)
    EditText commentContentEt;
    @BindView(R.id.coment_commit_tv)
    TextView comentCommitTv;
    @BindView(R.id.coment_commit_ll)
    RelativeLayout comentCommitLl;
    @BindView(R.id.commit_ll)
    LinearLayout commitLl;

    List<ComentModel> comentModels;
    ResourceModel data;
    Handler handler = new Handler();
    private  int pageNo=1;
    private  int pageSize=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        getIntentData();
    }
  private void getIntentData(){
      data=(ResourceModel) getIntentKey1();
      if (data!=null){
          requestQueryCommitContent();
      }else {
          showToast("未获取资源");
      }
      CommitContent.addTextChange(this,commentContentEt);//editText控制显示
  }
    private void requestQueryCommitContent() {
        LogUtils.D("查询评论请求");
        Call<CommitPageModel<PageModel<ComentModel>>> call = HttpAdapter.getService().queryResourceComment(data.getResource_id(), pageSize+"", pageNo+"");
        call.enqueue(new OnResponseNoDialogListener<CommitPageModel<PageModel<ComentModel>>>() {
            @Override
            protected void onSuccess(CommitPageModel<PageModel<ComentModel>> model) {
                LogUtils.D("98775454csdckhsjdbhdcjsb");
                LogUtils.D("model.getCode()--->" + model.getCode());
                if (model.getCode() == 1 && null != model.getPage() && !"[]".equals(model.getPage()+"") && null != model.getPage().getResult() && !"[]".equals((model.getPage().getResult()+""))) {
                    LogUtils.D("model.getPage().getResult()--->"+model.getPage().getResult());
                    if (pageNo>=2) {
                       if (model.getPage().getResult().size()>0){
                           comentModels.addAll(model.getPage().getResult());
                          singleItemAdapter.notifyDataSetChangedRv();
                       }
                   }else{
                       if (model.getPage().getResult().size() > 0) {
                           comentModels = model.getPage().getResult();
                           setAdapter();
                       }else{
                           noContent();
                       }
                   }
                }else {
                    LogUtils.D("没有评论");
                    noContent();
                }
            }
        });
    }

    private void noContent() { //没有评论
//        noContentTv.setText("暂时没有评论，请可以添加评论哦 +");
//        noContentTv.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
//        noContentTv.setTextColor(Color.BLACK);
        commentRecyclerview.setVisibility(View.GONE);
        commentNoContentTv.setVisibility(View.VISIBLE);
    }

    private void addItem() {
        for (int i = 0; i <= 5; i++) {
            LogUtils.D("i--->" + i);
            ComentModel model = new ComentModel();
            model.setResource_id(comentModels.get(0).getResource_id());
            model.setAddtime(comentModels.get(0).getAddtime());
            model.setContent(comentModels.get(0).getContent() + "sdcsdvsdcssd" + i + i);
            model.setId(comentModels.get(0).getId());
            model.setPhoto(comentModels.get(0).getPhoto());
            model.setPid(comentModels.get(0).getPid() + "sdf" + i);
            model.setRealname(comentModels.get(0).getRealname());
            model.setUser_id(comentModels.get(0).getUser_id());
            comentModels.add(model);
        }
    }

    RvCommonAdapter singleItemAdapter;
    //设置评论中的页面布局
    private void setAdapter() {
        RvCommonAdapter.Builder builder=new RvCommonAdapter.Builder<ComentModel>(this,R.layout.book_introduction_item,comentModels);
//             builder.setOnItemClickListener(new RvCommonAdapter.Builder.OnItemClickListener() {
//                 @Override
//                 public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
//                     LogUtils.D("点击了"+position);
//                             addItem();
//                     singleItemAdapter.notifyDataSetChangedRv();
//                 }
//             });
              builder.setOnLoadMoreListener(new RvCommonAdapter.Builder.OnLoadMoreListener() {
                  @Override
                  public void onLoadMore(ViewHolder holder) {

                      if (comentModels.size()==pageNo*pageSize){
                          pageNo++;
                          requestQueryCommitContent();
                      }else {
                          LogUtils.D("评论加载更多");
                          holder.setText(R.id.more_progress_text, "已经没有更多了");
                          holder.setVisible(R.id.more_progress_pb, View.GONE);
                      }
                  }
              });

        singleItemAdapter=builder.createAdapter(commentRecyclerview, new RvCommonAdapter.InitViewCallBack<ComentModel>() {
                         @Override
                         public void convert(ViewHolder holder, ComentModel comentModel, int position) {
                             holder.setImageUrl(R.id.commit_image_iv, comentModel.getPhoto());
                             holder.setText(R.id.commit_time_tv, CommitContent.millisToDate(comentModel.getAddtime()));
                             holder.setText(R.id.commit_content_tv, nullToSting(comentModel.getContent()));
                             holder.setText(R.id.commit_name_tv, nullToSting(comentModel.getRealname()));
                             ((StarBar) holder.getView(R.id.commit_start_bar)).setStarMark((float) 3);
                             ((StarBar) holder.getView(R.id.commit_start_bar)).setChangMark(false);
                         }
                     }
             );
        commentRecyclerview.setHasFixedSize(true);
        commentRecyclerview.setNestedScrollingEnabled(false);
    }





    private void updateView() {
            pageNo = 1;
            requestQueryCommitContent();
    }



    //添加评论
    private void addCommitContent() {
        LogUtils.D("发送评论请求");
        String user_id = CommitContent.isLogin(this);
        String comment = commentContentEt.getText().toString();
        if (TextUtils.isEmpty(comment)) {
           showToast("请先输入内容");
            return;
        }
        if (user_id != null) {
            HttpRequest.addCommitContent(user_id, data.getResource_id(), comment, new HttpRequest.RequestCallBack() {
                @Override
                public void onSuccess(String msg) {
                    showToast("评论发布成功");
                    updateView();
                    if (commentNoContentTv.getVisibility()==View.VISIBLE){
                        commentRecyclerview.setVisibility(View.VISIBLE);
                        commentNoContentTv.setVisibility(View.GONE);
                    }
                    commentContentEt.setText("");
                    hideInputMethod(CommentActivity.this, commentContentEt);
                }
                @Override
                public void onRequested(String msg) { //表示评论过了
                    showToast("您已经评论过了，本评论不显示");
                    commentContentEt.setText("");
                    hideInputMethod(CommentActivity.this, commentContentEt);
                }
                @Override
                public void onUnSuccess(String msg) {//表示评论失败
                    showToast(msg);
                }
            });


//            Call<CheckSignModel> call = HttpAdapter.getService().addResourceComment(data.getResource_id(), user_id, comment);
//            call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
//                @Override
//                protected void onSuccess(CheckSignModel checkSignModel) {
//                    if (checkSignModel.getCode() == 1) {
//
//                    } else if (checkSignModel.getCode() == 400) {
//                        if (null != checkSignModel.getMsg() && checkSignModel.getMsg().equals("已经评论")) {
//                            showToast("您已经评论过了，本评论不显示");
//                            commentContentEt.setText("");
//                            hideInputMethod(CommentActivity.this, commentContentEt);
//                        }else {
//                            showToast(checkSignModel.getMsg());
//                        }
//                    }
//                    LogUtils.D("code--->" + checkSignModel.getCode());
//                }
//            });
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param v
     */
    public void hideInputMethod(final Context context, final View v) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                LogUtils.D("yingcang");
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 10);
    }
    @OnClick({R.id.coment_commit_ll,R.id.comment_no_content_tv})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.coment_commit_ll:
                addCommitContent();
                break;
            case R.id.comment_no_content_tv:


                break;


        }

    }
}
