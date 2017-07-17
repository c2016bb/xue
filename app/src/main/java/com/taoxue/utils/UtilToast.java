package com.taoxue.utils;

import android.widget.Toast;

import com.taoxue.app.TaoXueApplication;

/**
 * Created by User on 2017/4/6.
 */

public class UtilToast {
    // 构造方法私有化 不允许new对象
    private UtilToast() {
    }

    // Toast对象
    private static Toast toast = null;

    /**
     * 显示Toast
     */
    public static void showText( Object strToast) {
        LogUtils.i("Toast",strToast);
        if (toast == null) {
            toast = Toast.makeText(TaoXueApplication.get(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(strToast+"");
        toast.show();
    }
}
