package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.model.BaseResultModel;
//import com.taoxue.ui.model.String;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * 注册
 * Created by User on 2017/4/1.
 */

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.register_name)
    EditText registerName;
    @BindView(R.id.register_photo)
    EditText registerPhoto;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_codeBtn)
    TextView registerCodeBtn;
    @BindView(R.id.register_pw)
    EditText registerPw;
    @BindView(R.id.register_newpw)
    EditText registerNewpw;
    @BindView(R.id.register_BTN)
    RelativeLayout registerBTN;

    private TimeCount timeCount;
    private String phoneCode;
    private String reader_card_id;
    private String cgs_id;
     private  int reqCode=1;
    private String name;
    private String mobile;
    private String pwd;
    String code;
    String newPwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_register);
        ButterKnife.bind(this);
//        registerBTN.setPressed(false);
        registerBTN.setEnabled(false);
    }

    @Override
    protected void onInit() {
        super.onInit();
        name = registerName.getText().toString();
        code = registerCode.getText().toString();
        mobile = registerPhoto.getText().toString();
        pwd = registerPw.getText().toString();
        newPwd = registerNewpw.getText().toString();

        TextChange textChange=new TextChange();
        registerName.addTextChangedListener(textChange);
        registerPhoto.addTextChangedListener(textChange);
        registerCode.addTextChangedListener(textChange);
        registerPw.addTextChangedListener(textChange);
        registerNewpw.addTextChangedListener(textChange);
    }
    //EditText的监听器
    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if(registerName.length()>0&&
                    registerPhoto.length()>0&&
                    registerCode.length()>0&&registerPw.length()>0&&registerNewpw.length()>0){
//                LogUtils.
                registerBTN.setFocusable(true);
                registerBTN.setEnabled(true);
//                registerBTN.setPressed(true);
            }else{
                registerBTN.setEnabled(false);
            }
        }
    }
    @OnClick({R.id.register_BTN, R.id.register_codeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_BTN:
//                showToast("点击了下一步");
                name = registerName.getText().toString();
                code = registerCode.getText().toString();
                mobile = registerPhoto.getText().toString();
                pwd = registerPw.getText().toString();
                newPwd = registerNewpw.getText().toString();
                LogUtils.D("MOBILE-->"+mobile+"name--->"+name);
//                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(code) || TextUtils.isEmpty(mobile) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(newPwd)) {
//                    return;
//                }
                if (!pwd.equals(newPwd)) {
                    showToast("请输入一致密码");
                } else {
                        if (code.equals(phoneCode)) {
                            //跳转至读者证验证页
//                       RegisterInfoModel infoModel=new RegisterInfoModel(name,mobile,pwd);
                            Intent intent=new Intent(RegisterActivity.this,ReaderCardActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString(CommitContent.REGISTER_READER,name);
                            intent.putExtras(bundle);
                            startActivityForResult(intent,reqCode);
                            LogUtils.D("跳转到验证cardCode页中");
                        } else {
                            showToast("输入的验证码错误,请重新输入");
                        }
                }
                break;
            case R.id.register_codeBtn:
                String phone = registerPhoto.getText().toString().trim();
                if (!phone.equals("") && phone != null) {
                    if (isMobileNO(phone)) {
                        timeCount = new TimeCount(60000, 1000);
                        timeCount.start();
                        initCode(phone);
                    } else {
                        showToast("请输入正确的手机号码");
                    }
                    ;
                }
                break;
        }
    }
    /**
     * 注册
     */
    private void initRegister(String phone, String nikeName, String pwd,String reader_card_id,String cgs_id
    ) {
        Call<CheckSignModel> call = HttpAdapter.getService().getRegister(phone, nikeName, pwd,reader_card_id,cgs_id);
        call.enqueue(new OnResponseListener<CheckSignModel>(this) {
            @Override
            protected void onSuccess(CheckSignModel checkSignModel) {
                showToast(checkSignModel.getMsg());
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data==null){
            return;
        }
        reader_card_id=data.getStringExtra("reader_card_id");
        cgs_id=data.getStringExtra("cgs_id");
        LogUtils.D("注册");
        switch (requestCode){
            case 1:
                if (!reader_card_id.equals(CommitContent.UNGETREADERID)){ //验证读者证页中 点击了返回
                    LogUtils.D("注册");
                    if (reader_card_id.equals(CommitContent.ZHIJIEZHUCE)){//验证读者证页中 点击了跳过 直接注册
                        reader_card_id="null";
                    }
                    initRegister(mobile, name, pwd, reader_card_id, cgs_id);
                }
                break;
        }
    }

    /**
     * 发送验证码
     *
     * @param phone
     */
    private void initCode(String phone) {
        Call<BaseResultModel<YzmBean>> call = HttpAdapter.getService().sendvalidate(phone);
        call.enqueue(new OnResponseListener<BaseResultModel<YzmBean>>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                showToast(baseResultModel.getMsg());
                    YzmBean yzm = (YzmBean) baseResultModel.getData();
//                    LogUtils.D("cbb--->" + yzm.toString());
                    phoneCode = yzm.getYzm();
                    LogUtils.D("cbbcode--->" + phoneCode);
            }

            @Override
            protected void onFailure(int code) {
                LogUtils.D("code--as>" + code + "");
                super.onFailure(code);
                timeCount.cancel();
                registerCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
                registerCodeBtn.setText("重新获取验证码");
                registerCodeBtn.setClickable(true);
            }
        });

    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            registerCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            registerCodeBtn.setClickable(false);
            registerCodeBtn.setText(millisUntilFinished / 1000 + "秒后可重新发送");
        }

        @Override
        public void onFinish() {
            registerCodeBtn.setTextColor(getResources().getColorStateList(R.color.color666));
            registerCodeBtn.setText("重新获取验证码");
            registerCodeBtn.setClickable(true);
        }
    }

    /*
     **
             * 验证手机格式
     */
    public static boolean isMobileNO(String mobile) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobile)) {
            return false;
        } else {
            return mobile.matches(telRegex);
        }
    }


}
