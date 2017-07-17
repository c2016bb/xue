package com.taoxue.ui.module.mine;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.MyLibBean;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.module.login.ReaderCardActivity;
import com.taoxue.ui.module.login.RegisterActivity;
import com.taoxue.ui.view.RDividerItemDecoration;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.UtilGson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MyLibraryActivity extends BaseActivity {

    @BindView(R.id.my_liblay_rv)
    RecyclerView myLiblayRv;
    @BindView(R.id.my_liblay_bind_reader_card_tv)
    TextView myLiblayBindReaderCardTv;
    List<MyLibInfo> myLibInfos;
    @BindView(R.id.my_liblay_bind_success_ll)
    LinearLayout myLiblayBindSuccessLl;
    @BindView(R.id.my_liblay_bind_reader_tv)
    TextView myLiblayBindReaderTv;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_library);
        ButterKnife.bind(this);

        user_id = CommitContent.isLogin(this);
//        myLibBean = (MyLibBean) UtilGson.fromJson((String) SPUtil.get(SPUtil.MY_LIB_INFO_LIST_KEY, ""), MyLibBean.class);
        if (user_id != null) {
            if (myLibBean == null) {
                queryMyLib(user_id);
            } else {
                updateView();
            }
        }
    }

    private void saveMyLibInfo() {
        myLibBean = new MyLibBean();
        myLibBean.setMyLibInfos(myLibInfos);
        SPUtil.put(SPUtil.MY_LIB_INFO_LIST_KEY, UtilGson.toJson(myLibBean)); //将信息添加进文件中缓存
    }

   RvCommonAdapter commonAdapter;
    private void updateView() {
        myLiblayRv.setNestedScrollingEnabled(false);
        myLibInfos=myLibBean.getMyLibInfos();
        RvCommonAdapter.Builder builder=new RvCommonAdapter.Builder(this,R.layout.my_library_item,myLibBean.getMyLibInfos());
         commonAdapter=builder.createAdapter(myLiblayRv, new RvCommonAdapter.InitViewCallBack<MyLibInfo>() {
                 @Override
                 public void convert(ViewHolder holder, MyLibInfo myLibInfo, int position) {
                     holder.setImageUrl(R.id.my_liblay_item_image_iv, myLibInfo.getLogo());
                     holder.setText(R.id.my_liblay_name_tv, myLibInfo.getName());

                     if (position == 0) {
                         holder.setVisible(R.id.my_libray_item_up, View.GONE);
                     }
                     if (position == (myLibInfos.size() - 1)) {
                         holder.setVisible(R.id.my_libray_item_down, View.GONE);
                     }
                     if (holder.getViewVisibility(R.id.my_libray_item_up) != View.GONE) {
                         holder.setOnClickListener(R.id.my_libray_item_up, new paixu(position));
                     }
                     if (holder.getViewVisibility(R.id.my_libray_item_down) != View.GONE) {
                         holder.setOnClickListener(R.id.my_libray_item_down, new paixu1(position));
                     }
                 }
             });

        RDividerItemDecoration rid=new RDividerItemDecoration(this,RDividerItemDecoration.VERTICAL);
        rid.setDrawable(getResources().getDrawable(R.drawable.divider_bg));
        myLiblayRv.addItemDecoration(rid);

    }

    @OnClick(R.id.my_liblay_bind_reader_card_tv)
    public void onViewClicked() {
        Intent intent=new Intent(MyLibraryActivity.this,ReaderCardActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
        intent.putExtras(bundle);
        startActivityForResult(intent,reqCode);
    }

    private class paixu1 implements View.OnClickListener {
        private int position;

        public paixu1(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            int replaceNum = position + 1;
            myLibInfos.add(position, myLibInfos.get(replaceNum));
            myLibInfos.remove(replaceNum + 1);
            commonAdapter.notifyDataSetChangedRv();
            saveMyLibInfo();
        }
    }


    private class paixu implements View.OnClickListener {
        private int position;

        public paixu(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            LogUtils.D("position--->" + position);
            int replaceNum1 = position - 1;
            LogUtils.D("replaceNum1--->" + replaceNum1);
            int replaceNum2 = position;
            myLibInfos.add(replaceNum1, myLibInfos.get(replaceNum2));
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.add(replaceNum2 + 1, myLibInfos.get(replaceNum1 + 1));
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.remove(replaceNum1 + 1);
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            myLibInfos.remove(replaceNum2 + 1);
            LogUtils.D("myLibInfos--->" + myLibInfos.toString());
            commonAdapter.notifyDataSetChangedRv();
            saveMyLibInfo();
        }
    }

    private  int reqCode=1;
    private MyLibBean myLibBean;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        reader_card_id=data.getStringExtra("reader_card_id");
//        cgs_id=data.getStringExtra("cgs_id");
//        LogUtils.D("注册");
        if (data==null){
            return;
        }
        LogUtils.D("返回数据");
        switch (requestCode){
            case 1:
                LogUtils.D("返回数据");
                queryMyLib(user_id);
                break;
        }
    }
    //查询我的图书馆信息
    private void queryMyLib(String user_id) {
        HttpRequest.queryMyLibInfo(user_id, new HttpRequest.RequestMyLibCallBack() {
            @Override
            public void onSuccess(List<MyLibInfo> myLibInfos) {//查到图书馆信息
                MyLibraryActivity.this.myLibInfos=myLibInfos;
                saveMyLibInfo();
                updateView();
            }

            @Override
            public void onUnSuccess(String msg) {//未查到我的图书馆信息
                myLiblayBindSuccessLl.setVisibility(View.GONE);
                myLiblayBindReaderTv.setVisibility(View.VISIBLE);
                myLiblayBindReaderTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        launch(ReaderCardActivity.class,CommitContent.MY_LIB_CANSHU);
                        Intent intent=new Intent(MyLibraryActivity.this,ReaderCardActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putString(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,reqCode);

//                        Intent intent=new Intent(MyLibraryActivity.this,ReaderCardActivity.class);
//
//                        Bundle bundle =new Bundle();
//                        bundle.putSerializable(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
//////                        intent.putExtra(CommitContent.MY_LIB,CommitContent.MY_LIB_CANSHU);
//                        intent.putExtras(bundle);
//                        startActivity(intent);
//                        startActivityForResult(intent,reqCode);
                    }
                });
            }
        });
    }
}
