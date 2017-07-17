package com.taoxue.ui.module.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.UtilTools;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NickNameActivity extends BaseActivity {

    @BindView(R.id.top_bar)
    TopBar topBar;
    @BindView(R.id.nick_name_edt_input)
    EditText mEdtInput;
    @BindView(R.id.nick_name_iv_edt_clear)
    ImageView mIvEdtClear;
    private int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        String flag = bundle.getString("flag");
         code = bundle.getInt("code");
        mEdtInput.setHint(flag + "");
        topBar.setTvTopRightIcon("保存", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = mEdtInput.getText().toString().trim() + "";
                if (str.equals("")) {
                    showToast("请填写昵称");
                    return;
                }
                netWork(str);
            }
        });
        UtilTools.clearEditText(mEdtInput, mIvEdtClear);


    }

    private void gobackForResult() {
        Intent intent = new Intent();
        setResult(code, intent);
        finish();
    }

    private void netWork(String str) {
        switch (code){
            case 101:
                HttpAdapter.getService().updUserInfoName(
                        TaoXueApplication.get().getUserModel().getUser_id(),
                        str
                )
                        .enqueue(new OnResponseListener<BaseResultModel<BaseModel>>(this) {
                            @Override
                            protected void onSuccess(BaseResultModel<BaseModel> bean) {
                                showToast(bean.getMsg());
                                gobackForResult();
                            }
                        });
                break;
            case 102:
                HttpAdapter.getService().updUserInfoHangye(
                        TaoXueApplication.get().getUserModel().getUser_id(),
                        str
                )
                        .enqueue(new OnResponseListener<BaseResultModel<BaseModel>>(this) {
                            @Override
                            protected void onSuccess(BaseResultModel<BaseModel> bean) {
                                showToast(bean.getMsg());
                                gobackForResult();
                            }
                        });
                break;
            case 103:
                HttpAdapter.getService().updUserInfoJob(
                        TaoXueApplication.get().getUserModel().getUser_id(),
                        str
                )
                        .enqueue(new OnResponseListener<BaseResultModel<BaseModel>>(this) {
                            @Override
                            protected void onSuccess(BaseResultModel<BaseModel> bean) {
                                showToast(bean.getMsg());
                                gobackForResult();
                            }
                        });
                break;
        }

    }

}
