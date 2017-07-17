package com.taoxue.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.utils.AppUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;

/**
 * Created by CC on 2016/12/10.
 */

public abstract class BaseDialogFragment extends DialogFragment {
    protected View mainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (getLayout() <= 0) {
            mainView = createMainView();
        } else {
            mainView = inflater.inflate(getLayout(), container, false);
        }
        if (mainView == null)
            throw new NullPointerException("hey,you forget set a view for " + getClass().getName());
        ButterKnife.bind(this, mainView);
        return mainView;
    }

    @Subscribe
    public void onEvent(Void voidParams) {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInit();
        if (onInitCompleteListener != null)
            onInitCompleteListener.onInitComplete();
    }

    protected View findViewById(@IdRes int id) {
        return mainView.findViewById(id);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected View createMainView() {
        return null;
    }

    /**
     * 数据初始化
     */
    protected abstract void onInit();

    protected void launch(Class clazz) {
        startActivity(new Intent(getActivity(), clazz));
    }

    protected void showToast(String msg) {
        AppUtils.showToast(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    OnInitCompleteListener onInitCompleteListener;

    public void setOnInitCompleteListener(OnInitCompleteListener onInitCompleteListener) {
        this.onInitCompleteListener = onInitCompleteListener;
    }

    public interface OnInitCompleteListener {
        void onInitComplete();
    }

}
