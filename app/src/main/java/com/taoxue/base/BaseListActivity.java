package com.taoxue.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.taoxue.R;

/**
 * Created by CC on 2016/12/11.
 */

public abstract class BaseListActivity<HolderFragment extends BaseListFragment> extends BaseActivity {

    HolderFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        switchFragment();
    }

    protected void switchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, fragment = (HolderFragment) Fragment.instantiate(this,
                getFragmentClass().getName(), getArguments()));
        transaction.commitAllowingStateLoss();
    }

    /**
     * 获取需要实例化的Class
     *
     * @return
     */
    protected abstract Class<HolderFragment> getFragmentClass();

    /**
     * 如果需要向Fragment中传入参数，请重写此方法
     *
     * @return
     */
    protected Bundle getArguments() {
        return null;
    }

    public HolderFragment getFragment() {
        return fragment;
    }


}
