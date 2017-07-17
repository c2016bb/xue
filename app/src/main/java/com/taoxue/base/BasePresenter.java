package com.taoxue.base;

import android.content.Context;

/**
 * Created by gentl on 2016/12/20.
 */

public class BasePresenter<View extends BaseView> {
    View baseView;
    Context context;

    public BasePresenter(Context context, View baseView) {
        this.context = context;
        this.baseView = baseView;
    }

    public Context getContext() {
        return context;
    }

    public View getView() {
        return baseView;
    }
}
