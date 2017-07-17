package com.taoxue.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.taoxue.app.TaoXueApplication;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.module.classification.MessageEvent;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import butterknife.ButterKnife;

/**
 * Created by CC on 2016/5/25.
 */

public class BaseActivity extends AppCompatActivity {
    public BaseActivity mActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ButterKnife.setDebug(true);
        mActivity = this;
    }

    protected void setTranslucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
            win.setAttributes(winParams);
        }
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        ButterKnife.bind(this);
        onInit();
    }

    protected void onInit() {

    }

    public void showToast(String text) {
        UtilToast.showText(text);
    }

    @Override
    public void onBackPressed() {
        if (onBackPressListener != null) {
            if (!onBackPressListener.onBackPressed()) {
                dealOnBackPressed();
                super.onBackPressed();
            }
        } else {
            dealOnBackPressed();
            super.onBackPressed();
        }
    }

    protected void dealOnBackPressed() {

    }

    private OnBackPressListener onBackPressListener;

    public OnBackPressListener getOnBackPressListener() {
        return onBackPressListener;
    }

    public void setOnBackPressListener(OnBackPressListener onBackPressListener) {
        this.onBackPressListener = onBackPressListener;
    }

    public interface OnBackPressListener {
        /**
         * 如果返回 true 表示将此次点击事件拦截
         *
         * @return
         */
        boolean onBackPressed();
    }

    @Subscribe
    public void onEventMainThread(String string) {

    }
    @Subscribe
    public void onEventMainThread(MessageEvent event) {

    }
    @Subscribe
    public void onEventMainThread(MessageEvent.Duration event) {

    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        LogUtils.i("当前activity", this.getClass().getName().toString());
        super.onResume();
    }

    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void launch(Class clazz) {
//        startActivity(new Intent(this, clazz));
        UtilIntent.intentDIYLeftToRight(this, clazz);
    }

    public void launch(Class clazz, Intent intent) {
        intent.setClass(this, clazz);
        startActivity(intent);
    }

   public void launch(Class clazz,Object obj){
    Bundle bundle=new Bundle();
       if (obj instanceof Serializable) {
           bundle.putSerializable(getKek1(clazz),(Serializable) obj);
       }else{
           newThrow();
       }
    UtilIntent.intentDIYLeftToRight(this,clazz,bundle);
 }

    public void launch(Class clazz,Object obj1,Object obj2){
        Bundle bundle=new Bundle();
        if (obj1 instanceof Serializable) {
            bundle.putSerializable(getKek1(clazz),(Serializable) obj1);
        }else{
           newThrow();
        }
        if (obj2 instanceof Serializable) {
            bundle.putSerializable(getKek2(clazz),(Serializable)obj2);
        }else{
            newThrow();
        }
        UtilIntent.intentDIYLeftToRight(this,clazz,bundle);
    }
    private void newThrow(){
        throw  new IllegalArgumentException("argument must is String Or Serializable," +
                "if argument is int or float or double or boolean or double or byte " +
                "you can cast argument to  String");
    }

    public Serializable getIntentKey1(){
      return  getIntent().getSerializableExtra(getKek1(getClass()));
    }

   public Serializable getIntentKey2(){
       return  getIntent().getSerializableExtra(getKek2(getClass()));
   }

    private String getKek1(Class clazz){
        return TaoXueApplication.getActivityKey1(clazz);
    };

    private String getKek2(Class clazz){
        return TaoXueApplication.getActivityKey2(clazz);
    };



    //    将String 为null 值时转化为空
    public static String nullToSting(String str) {
        return TextUtils.isEmpty(str) ? "" : str.trim();
    }

}
