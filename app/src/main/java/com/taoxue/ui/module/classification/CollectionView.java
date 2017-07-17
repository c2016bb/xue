package com.taoxue.ui.module.classification;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.ResultModel;
import com.taoxue.ui.module.login.LoginActivity;
import com.taoxue.utils.LogUtils;

import retrofit2.Call;

/**
 * Created by User on 2017/4/14.
 * 收藏button
 */

public class CollectionView extends RelativeLayout {
    private ResourceModel data;
    private TextView tv;
    private ProgressBar pb;

    public ResourceModel getData() {
        return data;
    }

    public void setData(ResourceModel data) {
        this.data = data;
//        chaXunIsCollection();
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            collectionSuccess();
        }
    };

    private void collectionSuccess() { //收藏成功
        pb.setVisibility(View.GONE);
        tv.setVisibility(View.VISIBLE);
        tv.setText("已收藏");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //当版本大于16时
            setBackground(getResources().getDrawable(R.drawable.button_border_white, null));
        }
    }


    public CollectionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CollectionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CollectionView(Context context) {
        this(context, null);
    }

    //点击后收藏功能
    private void addCollection(String id, String resource_id, String gys_id) {
        Call<CheckSignModel> call = HttpAdapter.getService().addCollection(id, resource_id, gys_id);
        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel model) {
                if (model.getCode() == 1) {
                    handler.postDelayed(runnable, 1000);
                } else {
                    if (getContext() instanceof BaseActivity) {
                        if (model.getMsg().equals("已经收藏！")) {
                            collectionSuccess();
                            CollectionView.this.setEnabled(false); //当前收藏功能不可用
                        }else {
                            pb.setVisibility(View.GONE);
                            tv.setText(getResources().getText(R.string.add_book));
                        }
                    }

                }
            }
        });
    }

    private void chaXunIsCollection() {//查询是否已经收藏
        if (data != null) {
            if (getContext() instanceof BaseActivity) {
//                        addCollection(data.get_id(), data.getResource_id(), data.getGys_id()); //测试时使用
                //添加收藏
                if (TaoXueApplication.get().isLogin()) {
                    String user_id = TaoXueApplication.get().getUserModel().getUser_id();
                    addCollection(user_id, data.getResource_id(), data.getGys_id());
                    LogUtils.I("Resource_id()--->" + data.getResource_id() + "Gys_id()-->" + data.getGys_id());
                }
            }
        }

    }

    private void collection() { //收藏操作
        if (data != null) {
            if (getContext() instanceof BaseActivity) {
//                        addCollection(data.get_id(), data.getResource_id(), data.getGys_id()); //测试时使用
                //添加收藏
                if (TaoXueApplication.get().isLogin()) {
                    String user_id = TaoXueApplication.get().getUserModel().getUser_id();
                    LogUtils.D("user_id----->" + user_id);
//                            if (!TextUtils.isEmpty(data.getResource_id())) {
                    tv.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    addCollection(user_id, data.getResource_id(), data.getGys_id());
                    LogUtils.I("Resource_id()--->" + data.getResource_id() + "Gys_id()-->" + data.getGys_id());
//                            }
                } else {
                    ((BaseActivity) getContext()).showToast("请先登录");
                    ((BaseActivity) getContext()).launch(LoginActivity.class);
                }
            }
        } else {
            ((BaseActivity) getContext()).showToast("资源不存在");
        }
    }


    private void init() {
        tv = new TextView(getContext());
        tv.setText(getResources().getText(R.string.add_book));
        tv.setTextSize(16);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //当版本大于23时
            tv.setTextColor(getResources().getColor(R.color.colorTextPrimary, null));
        }
        addView(tv);
        pb = new ProgressBar(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(60, 60);
        addView(pb, params);
        pb.setVisibility(View.GONE);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                collection();
            }
        });
    }
}