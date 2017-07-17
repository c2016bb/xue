package com.taoxue.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.app.TaoXueApplication;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.HttpApis;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.utils.AppUtils;
import com.taoxue.utils.CommonUtils;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by cc on 16-7-9.
 */
public abstract class BaseFragment extends Fragment {
    protected View mainView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        solveFragmentBug(savedInstanceState);
    }

    /**
     * 解决fragment重影
     * @param savedInstanceState
     */
    private void solveFragmentBug(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);

            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    public HttpApis getServer() {
        return HttpAdapter.getService();
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

    private void setFitSystem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = CommonUtils.getStatusHeight(getActivity());
            View view = getView();
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 设置控件适应沉浸式
     *
     * @param view
     */
    public void setFitSystem(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = CommonUtils.getStatusHeight(getActivity());
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin, statusBarHeight, layoutParams.rightMargin, layoutParams.bottomMargin);
        }
    }

    /**
     * 是否适应沉浸式
     *
     * @return
     */
    public boolean getFitSystem() {
        return false;
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
        if (getFitSystem()) {
            setFitSystem();
        }
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
//        startActivity(new Intent(getActivity(), clazz));
        UtilIntent.intentDIYLeftToRight(getActivity(), clazz);
    }

    protected void launch(Class clazz, Intent intent) {
        intent.setClass(getActivity(), clazz);
        startActivity(intent);
    }

    public void launch(Class clazz,Object obj){
        Bundle bundle=new Bundle();
        if (obj instanceof Serializable) {
            bundle.putSerializable(getKey1(clazz),(Serializable) obj);
        }else{
           newThrow();
        }
        UtilIntent.intentDIYLeftToRight(getActivity(),clazz,bundle);
    }
    public void launch(Class clazz,Object obj1,Object obj2){
        Bundle bundle=new Bundle();
        if (obj1 instanceof Serializable) {
            bundle.putSerializable(getKey1(clazz),(Serializable) obj1);
        }else{
           newThrow();
        }
        if (obj2 instanceof Serializable){
            bundle.putSerializable(getKey2(clazz),(Serializable) obj2);
        }else{
            newThrow();
        }
        UtilIntent.intentDIYLeftToRight(getActivity(),clazz,bundle);
    }

    private void newThrow(){
        throw  new IllegalArgumentException("argument must is String Or Serializable," +
                "if argument is int or float or double or boolean or double or byte " +
                "you can cast argument to  String");
    }

    public String getKey1(Class clazz){
            return TaoXueApplication.getActivityKey1(clazz);
    };
    public String getKey2(Class clazz){
        return TaoXueApplication.getActivityKey2(clazz);
    };

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
