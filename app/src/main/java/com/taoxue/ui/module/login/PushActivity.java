package com.taoxue.ui.module.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.app.AppData;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.GysDataBean;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/22.
 *
 * @author yysleep
 */

public class PushActivity extends BaseActivity {
    @BindView(R.id.push_time_tv)
    TextView tvTime;
    int time = 1;
    final int totalCount = 5;
    boolean skiped;
    private Context context = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);
        setTranslucentStatus();
    }

    @Override
    protected void onInit() {
        Intent intent = getIntent();
        String action = intent.getAction();
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {

                String host = uri.getHost();
                String dataString = intent.getDataString();
                final String id = uri.getQueryParameter("resource_id");
                String path = uri.getPath();
                String path1 = uri.getEncodedPath();
                String queryString = uri.getQuery();

                LogUtils.i("host:", host);
                LogUtils.i("dataString:", dataString);
                LogUtils.i("id:", id);
                LogUtils.i("path:", path);
                LogUtils.i("path1:", path1);
                LogUtils.i("queryString:", queryString);
                HttpAdapter.getService().getGys(id, "app").enqueue(new OnResponseNoDialogListener<BaseResultModel<GysDataBean>>() {
                    @Override
                    protected void onSuccess(BaseResultModel<GysDataBean> baseResultModel) {
                        Log.e("zp", "success");
//                        if (baseResultModel.getData().getFile_type().equals("doc")) {
                            activityToDetail(BookIntroductionActivity.class, id, baseResultModel.getData().getFile_type());
//                        } else if (baseResultModel.getData().getFile_type().equals("audio")) {
//                            activityToDetail(AudioIndroductionActivity.class, id, baseResultModel.getData().getFile_type());
//                        } else if (baseResultModel.getData().getFile_type().equals("video")) {
//                            activityToDetail(PlayActivity.class, id, baseResultModel.getData().getFile_type());
//                        } else if (baseResultModel.getData().getFile_type().equals("image")) {
//                            activityToDetail(BookIntroductionActivity.class, id, baseResultModel.getData().getFile_type());
//                        }
                    }

                    @Override
                    protected void onFailure(int code) {
                        super.onFailure(code);
                        setNext();
                    }
                });
            }
        } else {
            setNext();
        }
    }

    /**
     * 跳转到下一个界面
     */
    private void setNext() {
        if (AppData.IS_TEST) {
            UtilIntent.intentDIYLeftToRight(this,MainActivity.class);finish();
            return;
        }
        tvTime.setText(String.valueOf(String.valueOf(totalCount)));
        startCount();
    }

    private void startCount() {
        getWindow().getDecorView().postDelayed(runnable, 1000);
    }

    @OnClick(R.id.ui_push_skip)
    public void onClick(View view) {
        skiped = true;
        next();
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (skiped)
                return;
            tvTime.setText(String.valueOf(String.valueOf(totalCount - time)));
            time++;
            if (time > totalCount) {
                next();
            } else {
                startCount();
            }
        }
    };

    private void next() {
        UtilIntent.intentDIYLeftToRight(this,
                TaoXueApplication.get().isFirstEnter() ? MainActivity.class : MainActivity.class,
                android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    //跳转页面
    private void activityToDetail(Class clazz, String id, String type) {
        launch(clazz,id);
//        LogUtils.I("点击了Item");
//        Intent in = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("booklist", id);
//        bundle.putSerializable("booktype", type);
//        in.putExtras(bundle);
//        launch(clazz, in);
    }
}
