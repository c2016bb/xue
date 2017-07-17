package com.taoxue.ui.module.mine;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.home.MyCollectionActivity;
import com.taoxue.ui.module.home.PlayRecordActivity;
import com.taoxue.ui.module.setting.SettingActivity;
import com.taoxue.ui.view.CircleImageView;
import com.taoxue.ui.view.ShareFragment;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * Created by CC on 2016/11/16.
 *
 * @author yysleep
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.ac_my_iv_head)
    CircleImageView mIvHead;
    @BindView(R.id.ac_my_tv_name)
    TextView mTvName;
    Unbinder unbinder;
    private UserModel bean;

    @Override
    protected int getLayout() {
        return R.layout.activity_my_information;
    }

    @Override
    protected void onInit() {
    }

    private void setData() {
        if (TaoXueApplication.get().isLogin()) {
            bean = TaoXueApplication.get().getUserModel();
            mTvName.setText(bean.getName() + "");
//            ImageLoaderUtil.displayImage(bean.getPhoto(), mIvHead);
            UtilGlide.loadImgForIvHead(getActivity(), UtilTools.getStringEND(bean.getPhoto()), mIvHead);
        } else {
            mTvName.setText(UtilTools.getResourcesString(R.string.please_login_first));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setData();
    }

    @OnClick({R.id.ac_my_rl_my_infor,
            R.id.ac_my_rl_setting,
            R.id.ac_my_rl_share,
            R.id.ac_my_rl_history_record, R.id.ac_my_rl_my_collection, R.id.ac_my_rl_comment,
            R.id.ac_my_rl_my_lib})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_my_rl_my_infor:
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(MyInformationActivity.class);
                break;
            case R.id.ac_my_rl_history_record:
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(PlayRecordActivity.class);
                break;
            case R.id.ac_my_rl_my_collection:
                if (!UtilTools.judgeIsLogin(getActivity())) break;
                launch(MyCollectionActivity.class);
                break;
            case R.id.ac_my_rl_comment:
                break;
            case R.id.ac_my_rl_my_lib://我的图书馆
                launch(MyLibraryActivity.class);
//                launch(ReaderCardActivity.class,CommitContent.MY_LIB_CANSHU,CommitContent.MY_LIB);
                break;
            case R.id.ac_my_rl_setting:
                launch(SettingActivity.class);
                break;
            case R.id.ac_my_rl_share:
                ShareFragment.show(getActivity());
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
