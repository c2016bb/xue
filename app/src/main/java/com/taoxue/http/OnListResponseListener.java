package com.taoxue.http;

import android.content.Context;

import com.taoxue.ui.model.BasePageModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cc on 16-7-9.
 */
public abstract class OnListResponseListener<Bean extends Serializable> extends OnResponseListener<BasePageModel<Bean>> {

    private List<Bean> listData;

    protected OnListResponseListener(Context context) {
        super(context);
    }
}
