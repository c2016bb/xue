package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.taoxue.R;
import com.taoxue.app.AppData;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 登录
 * Created by User on 2017/4/1.
 */
public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_name_Edt)
    EditText loginNameEdt;
    @BindView(R.id.login_PW_Edt)
    EditText loginPWEdt;
    @BindView(R.id.login_changepw)
    TextView loginChangepw;
    @BindView(R.id.QQlogin_Btn)
    ImageView QQloginBtn;
    @BindView(R.id.Wxlogin_Btn)
    ImageView WxloginBtn;
    @BindView(R.id.topbar)
    TopBar topBar;
    @BindView(R.id.login_register_tv)
    TextView tvRegister;

    private TextView tv;
    private UMShareAPI mShareAPI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login);
        tv = new TextView(this);
        tv.setText("注册");

        mShareAPI = UMShareAPI.get(this);//初始化umeng社会化分享


    }



    @Override
    protected void onInit() {
        super.onInit();
        test();
    }

    /**
     * 测试时的自动登陆
     *
     * @return
     */
    private void test() {
        if (AppData.IS_TEST) {
            loginNameEdt.setText("13365654953");//18963787473  123
            loginPWEdt.setText("123321");
        }

    }

    @OnClick({R.id.btn_back, R.id.login_BTN, R.id.login_changepw, R.id.QQlogin_Btn, R.id.Wxlogin_Btn, R.id.login_register_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.login_BTN://登陆
                String name = loginNameEdt.getText().toString().trim();
                String pwd = loginPWEdt.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {

                } else {
                    initLogin(name, pwd);
                }
                break;
            case R.id.login_changepw://忘记密码
                launch(RegisterActivity.class);
                break;
            case R.id.QQlogin_Btn://qq登录
                showToast("qq");
                mShareAPI.doOauthVerify(this, SHARE_MEDIA.QQ, umdelAuthListener);//添加第三方登录(qq)
                break;
            case R.id.Wxlogin_Btn://微信登录
                break;
            case R.id.login_register_tv:
                launch(RegisterActivity.class);
                break;

        }
    }

    /**
     * 登录
     *
     * @param name
     * @param pw
     */
    private void initLogin(String name, String pw) {
        Call<BaseResultModel<UserModel>> call = HttpAdapter.getService().getLogin(name, pw);
        call.enqueue(new OnResponseListener<BaseResultModel<UserModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<UserModel> resultModel) {
                showToast(resultModel.getMsg());
                TaoXueApplication.get().setUserModel(resultModel.getData());
                finish();
            }
        });
    }


    /**
     * umengQQ第三方登录的授权接口
     */
    UMAuthListener umdelAuthListener = new UMAuthListener() {

        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            showToast("授权完成");
            mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        LogUtils.i("key= " + entry.getKey()," and value= " + entry.getValue());
                    }

                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    showToast("授权失败");
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    showToast("授权取消");
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    /**
     * 对于删除授权使用的接口是
     */
    private void deleteAuth() {
        mShareAPI.deleteOauth(this, SHARE_MEDIA.QQ, umdelAuthListener);
    }
    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }
}
