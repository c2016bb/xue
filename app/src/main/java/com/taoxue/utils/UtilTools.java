package com.taoxue.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.ui.module.login.LoginActivity;

import static com.taoxue.http.HttpAdapter.BASE_URL;

/**
 * Created by User on 2017/4/17.
 */

public class UtilTools {
    public static Context context = TaoXueApplication.get();

    public static boolean judgeIsLogin(Activity activity) {
        if (!TaoXueApplication.get().isLogin()) {
            UtilIntent.intentDIYLeftToRight(activity, LoginActivity.class);
        }
        return TaoXueApplication.get().isLogin();
    }

    public static String getResourcesString(int id) {
        return context.getResources().getString(id);
    }
    public static float getResourcesDimension(int id) {
        return context.getResources().getDimension(id);
    }

    /**
     * 带 X 图标的 输入框 有文字 显示x 没文字 x消失
     *
     * @param sEdtInput
     * @param sIvClear
     */
    public static void clearEditText(final EditText sEdtInput, final ImageView sIvClear) {

        sIvClear.setVisibility(View.INVISIBLE);
        sEdtInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    sIvClear.setVisibility(View.VISIBLE);
                } else {
                    sIvClear.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sIvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sEdtInput.setText("");
            }
        });
    }

    /**
     * 补全图片地址
     * @param url
     * @return
     */
    @NonNull
    public static String getStringEND(String url) {
        if (TextUtils.isEmpty(url)) {
            url = "";
        }
        if (!url.startsWith("http")) {
            url = BASE_URL + url;
        }
        return url;
    }

    /**
     * 播放音频或者视频的缓存路径.
     * @param url
     * @return
     */
    public static  String getProxyUrl(String url) {
        HttpProxyCacheServer proxy = TaoXueApplication.getProxy();
        return proxy.getProxyUrl(url);
    }
}
