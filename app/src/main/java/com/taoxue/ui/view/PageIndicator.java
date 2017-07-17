package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.taoxue.R;

/**
 * Created by gentl on 2016/11/13.
 */

public class PageIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
    private ViewPager viewPager;
    private int currentPosition;
    private int color, selColor;
    private int indicatorWidth, indicatorHeight, horSpace;
    private int indicatorWidthSel, indicatorHeightSel;
    private Drawable indicatorDrawable;

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        //初始值
        indicatorWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
        indicatorHeight = indicatorWidth / 2;
        horSpace = indicatorWidth / 2;
        color = Color.GRAY;
        selColor = Color.GREEN;

        if (attrs != null) {
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.PageIndicator);
            indicatorWidth = (int) ta.getDimension(R.styleable.PageIndicator_indicator_width, indicatorWidth);
            indicatorHeight = (int) ta.getDimension(R.styleable.PageIndicator_indicator_height, indicatorHeight);
            horSpace = (int) ta.getDimension(R.styleable.PageIndicator_indicator_padding, horSpace);
            color = ta.getColor(R.styleable.PageIndicator_indicator_color, color);
            selColor = ta.getColor(R.styleable.PageIndicator_indicator_sel_color, selColor);
            indicatorDrawable = ta.getDrawable(R.styleable.PageIndicator_indicator_drawable);
            ta.recycle();
        }
        //设置分割距离
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, null);
        gradientDrawable.setSize(horSpace, 5);
        setDividerDrawable(gradientDrawable);
        setShowDividers(SHOW_DIVIDER_MIDDLE);

        if (isInEditMode()) {
            initIndicators(4);
        }
    }

    /**
     * 绑定ViewPager
     *
     * @param viewPager
     */
    public void setWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        setAdapter(viewPager.getAdapter());
        viewPager.addOnPageChangeListener(this);
    }

    /**
     * 绑定ViewPager
     * 如果这个viewpager是通过将getCount()设置为很大来实现循环滑动的，则调用此方法。
     *
     * @param viewPager
     * @param totalCount 集合大小
     */
    public void setWithViewPager(ViewPager viewPager, int totalCount) {
        this.viewPager = viewPager;
        initIndicators(totalCount);
        viewPager.addOnPageChangeListener(this);
    }

    public void setAdapter(PagerAdapter pagerAdapter) {
        if (pagerAdapter != null) {
            initIndicators(pagerAdapter.getCount());
        }
    }

    private void initIndicators(int count) {
        removeAllViews();
        for (int i = 0; i < count; i++) {
            View indicatorView = new View(getContext());
            indicatorView.setLayoutParams(new LinearLayoutCompat.LayoutParams(indicatorWidth, indicatorHeight));
            if (indicatorDrawable != null) {
                //克隆一个新的drawable对象分配给每个子View,如果设置的drawable已经设置了大小 ，则使用drawable的尺寸作为indicator的尺寸
                Drawable bgDrawable = indicatorDrawable.getConstantState().newDrawable();
                if (bgDrawable.getIntrinsicWidth() > 0 && bgDrawable.getIntrinsicHeight() > 0) {
                    indicatorView.setLayoutParams(new LinearLayout.LayoutParams(bgDrawable.getIntrinsicWidth(), bgDrawable.getIntrinsicHeight()));
                }
                indicatorView.setBackgroundDrawable(bgDrawable);
                indicatorView.setSelected(currentPosition == i);
            } else {
                indicatorView.setBackgroundColor(currentPosition == i ? selColor : color);
            }
            addView(indicatorView);
        }
        setCurrentPosition(0);
    }

    private void setCurrentPosition(int position) {
        currentPosition = position;
        updateState();
    }

    private void updateState() {
        for (int i = 0; i < getChildCount(); i++) {
            View indicatorView = getChildAt(i);
            boolean isSelected = currentPosition == i;
            if (indicatorDrawable != null) {
                indicatorView.setSelected(isSelected);
            } else {
                indicatorView.setBackgroundColor(isSelected ? selColor : color);
            }
            updateIndicatorSize(indicatorView, isSelected);
        }
    }

    private void updateIndicatorSize(View view, boolean isSelected) {
        Drawable bgDrawable = indicatorDrawable.getConstantState().newDrawable();
        if (bgDrawable.getIntrinsicHeight() <= 0 || bgDrawable.getIntrinsicWidth() <= 0)
            return;
        if (isSelected) {
            bgDrawable.setState(new int[]{android.R.attr.state_selected});
        }
        if (bgDrawable.getIntrinsicWidth() > 0 && bgDrawable.getIntrinsicHeight() > 0) {
            LayoutParams params = (LayoutParams) view.getLayoutParams();
            params.width = bgDrawable.getIntrinsicWidth();
            params.height = bgDrawable.getIntrinsicHeight();
            view.setLayoutParams(params);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position > getChildCount() - 1) {
            setCurrentPosition(position % getChildCount());
        } else {
            setCurrentPosition(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
