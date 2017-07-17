package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.taoxue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanChao on 2016/5/5.
 */
public abstract class EasyAbsListView extends LinearLayout {
    protected Drawable horizontalDivider;
    protected Drawable verticalDivider;
    protected OnItemClickListener onItemClickListener;
    protected LinearLayout currentLine;
    protected Adapter adapter;
    protected List<View> childViews = new ArrayList<>();
    private View headView;
    private View footerView;

    public EasyAbsListView(Context context) {
        super(context);
        init(null);
    }

    public EasyAbsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EasyAbsListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    protected void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.EasyAbsListView);
            verticalDivider = ta.getDrawable(R.styleable.EasyAbsListView_vertical_divider);
            horizontalDivider = ta.getDrawable(R.styleable.EasyAbsListView_divider_horizontal);
            ta.recycle();
        }
        setOrientation(VERTICAL);
        if (horizontalDivider != null)
            setDividerDrawable(horizontalDivider);
        setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, int position);
    }

    protected void updateDataAdded() {

    }

    public void setVerticalDivider(Drawable verticalDivider) {
        this.verticalDivider = verticalDivider;
    }

    public void setHorizontalDivider(Drawable horizontalDivider) {
        this.horizontalDivider = horizontalDivider;
        setDividerDrawable(horizontalDivider);
    }

    public View getChild(int position) {
        return childViews.get(position);
    }

    /**
     * 添加一行。
     *
     * @return
     */
    protected LinearLayout addLine() {
        LinearLayout line = new LinearLayout(getContext());
        if (verticalDivider != null)
            line.setDividerDrawable(verticalDivider);
        line.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        line.setOrientation(HORIZONTAL);
        line.setGravity(Gravity.CENTER_VERTICAL);
        line.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        currentLine = line;
        addView(line);
        return line;
    }

    protected Drawable getDivider(int dividerRes) {
        return getResources().getDrawable(dividerRes);
    }

    public abstract void setAdapter(Adapter adapter);


    /**
     * 不需要在adapter的getView方法中使用缓存写法。
     *
     * @return
     */
    public Adapter getAdapter() {
        return adapter;
    }

    public void addHeader(View view) {
        addView(view = headView, 0);
    }

    public void removeHeaderView() {
        if (headView != null)
            removeView(headView);
    }

    public static abstract class EasyAdapter<T> extends BaseAdapter {
        List<T> data = new ArrayList<>();

        public EasyAdapter(List<T> data) {
            if (data != null)
                this.data.addAll(data);
        }

        public void add(T t, EasyAbsListView view) {
            data.add(t);
            view.updateDataAdded();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public T getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
