package com.taoxue.ui.adapter.BaseAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;

/**
 * Created by User on 2017/7/13.
 */

public interface OnItemAdapterLongClickListener {
    boolean onItemLongClick(View view, ViewHolder holder, int position, int viewType);
}
