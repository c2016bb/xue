package com.taoxue.ui.module.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.utils.UtilSystem;
import com.taoxue.utils.Utildialog;
import com.taoxue.utils.glide.GlideCacheUtil;
import com.taoxue.utils.update.UtilUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.setting_tv_delete)
    TextView mTvDelete;
    @BindView(R.id.setting_tv_version)
    TextView mTvVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        mTvDelete.setText(GlideCacheUtil.getInstance().getCacheSize(this)+"");
        mTvVersion.setText(UtilSystem.getVersionName());
    }

    @OnClick({R.id.setting_rl_delete, R.id.setting_rl_use_guide, R.id.setting_rl_common_problem, R.id.setting_rl_about_us})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_rl_delete:
                Utildialog.show(SettingActivity.this, "清理缓存" + GlideCacheUtil.getInstance().getCacheSize(this) + "", new Runnable() {
                    @Override
                    public void run() {
                        GlideCacheUtil.getInstance().clearImageAllCache(SettingActivity.this);
                        mTvDelete.setText("");
                    }
                });
                break;
            case R.id.setting_rl_use_guide:
                break;
            case R.id.setting_rl_common_problem:
                break;
            case R.id.setting_rl_about_us:
                UtilUpdate.judgeUpdate(this);
                break;
        }
    }
}
