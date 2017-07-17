package com.taoxue.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.taoxue.ui.view.CustomDialog;

/**
 * Created by User on 2017/4/20.
 */

public class Utildialog {
    /**
     * 构造方法私有化 不允许new对象
     */
    private Utildialog() {
    }

    /**
     * 显示Toast
     */
    public static void show(Context context, String tvTitle, String strMessage, String confirm, String cancel, final Runnable confirmRun) {
        new CustomDialog.Builder(context).setTitle(tvTitle)
                .setMessage(strMessage + "")
                .setPositiveButton(confirm, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                                confirmRun.run();
                    }
                }).setNegativeButton(cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        }).create().show();
    }

    /**
     * 显示Toast
     */
    public static void show(Context context, String strMessage, final Runnable confirmRun) {
        Utildialog.show(context, "提示", strMessage, "确定", "取消", confirmRun);
    }
}
