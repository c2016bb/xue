package com.taoxue.ui.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CC on 2016/6/5.
 */

public class TopBar extends Toolbar {

    @BindView(R.id.btn_back)
    BackButton backButton;

    @BindView(R.id.tv_topTitle)
    TextView topTitle;

    @BindView(R.id.btn_top_right)
    ImageView btnTopRight;
    @BindView(R.id.tv_top_right)
    TextView tvTopRight;

    String title;
    @BindView(R.id.top_background_fl)
    FrameLayout topBackgroundFl;

    public TopBar(Context context) {
        super(context);
        init();
    }

    public TopBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TopBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setContentInsetsAbsolute(0, 0);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        boolean hasChild = getChildCount() != 0;
        if (!hasChild) {
            LayoutInflater.from(getContext()).inflate(R.layout.layout_toolbar, this, true);
        } else {
            View contentView = LayoutInflater.from(getContext()).inflate(R.layout.layout_toolbar, this, false);
            int position = getTitle() == null ? 0 : 1;
            if (!(getChildAt(position) instanceof ViewGroup)) {
                throw new IllegalArgumentException("TopBar的子元素必须是一个ViewGroup");
            }
            if (getChildCount() != 0) {
                ViewGroup view = (ViewGroup) getChildAt(position);
                view.addView(contentView, 0);
            }
        }
        ButterKnife.bind(this);
        if (!TextUtils.isEmpty(title)) {
            topTitle.setText(title);
        } else {
            if (isInEditMode()) {
                topTitle.setText(getContext().getString(R.string.app_name));
            } else {
                topTitle.setText(((Activity) getContext()).getTitle());
            }
        }
        if (getBackground() != null) {
            ((View) backButton.getParent()).setBackgroundDrawable(getBackground());
        }
    }

    @Override
    public void setTitle(@StringRes int resId) {
        if (topTitle != null)
            topTitle.setText(getResources().getString(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title.toString();
        if (topTitle != null) {
            topTitle.setText(title);
        }
    }

    /**
     * 右边图片按钮点击事件
     *
     * @param resId
     */
    public void setBtnTopRightIcon(@DrawableRes int resId) {
        btnTopRight.setImageResource(resId);
    }

    public ImageView getTopRight() {
        return btnTopRight;
    }

    public void setBtnTopRightClickListener(OnClickListener clickListener) {
        btnTopRight.setVisibility(VISIBLE);
        btnTopRight.setOnClickListener(clickListener);
    }

    /**
     * 右边文字按钮点击事件
     *
     * @param str
     * @param clickListener
     */
    public void setTvTopRightIcon(String str, OnClickListener clickListener) {
        tvTopRight.setText(str);
        tvTopRight.setVisibility(VISIBLE);
        tvTopRight.setOnClickListener(clickListener);
    }

    public TextView getTvTopRight() {
        return tvTopRight;
    }

    /**
     * 返回按钮点击事件
     *
     * @return
     */
    public BackButton getBackButton() {
        return backButton;
    }

    public void setBackIcon(int resId) {
        backButton.setImageResource(resId);
    }

    public void setBackButtonListener(OnClickListener onClickListener) {
        backButton.setOnClickListener(onClickListener);
    }

    public void setBackButtonBackGround(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //当版本大于=21时
            backButton.setBackground(getResources().getDrawable(resId, null));
        }
    }

    public int getTitleHeight() {
        return topTitle.getMeasuredHeight();
    }

    public float getTitleTextSize() {
        return topTitle.getTextSize();
    }

    public TextView getTopTitle() {
        return topTitle;
    }

    public void setBackGround(int color){
//        LogUtils.D("topBackgroundFl-->"+topBackgroundFl.toString());
        topBackgroundFl.setBackgroundColor(color);
    }

    public void setTitleTextColor(int color){
        topTitle.setTextColor(color);
    }
}
