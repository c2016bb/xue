package com.taoxue.http;

import android.content.Context;
import android.text.TextUtils;

import com.taoxue.ui.dialog.LoadingDialog;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CC on 2016/5/28.
 */
public abstract class OnResponseListener<T> implements Callback<T> {

    LoadingDialog dialog;

    public OnResponseListener(Context context) {
        dialog = new LoadingDialog(context);
        if (needDialog())
            dialog.show();
    }

    protected boolean needDialog() {
        return true;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {
            if (response.body() instanceof BaseResultModel) {
                BaseResultModel commonBean = (BaseResultModel) response.body();
                if (commonBean.getCode() == 1) {
                    onSuccess((T) commonBean);
                } else {
                    onFailure(commonBean.getMsg());
                }
            } else {
                onSuccess(response.body());
            }
        } else {
            onFailure(response.code());
            onRequestFailure();
        }
        if (dialog.isShowing())
            dialog.dismiss();
    }

    protected void onRequestFailure() {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        LogUtils.E(t.getMessage());
        if (dialog.isShowing())
            dialog.dismiss();
        String msg = t.getMessage();
        if (TextUtils.isEmpty(msg))
            msg = "请求异常";
        UtilToast.showText(msg);
        onRequestFailure();
    }

    protected void onFailure(int code) {
        UtilToast.showText("请求异常:" + code);
    }

    protected void onFailure(String msg) {
        UtilToast.showText(msg);
    }

    protected abstract void onSuccess(T t);

    public void dismiss() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}