package com.taoxue.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by CC on 2016/7/7.
 */
public class BaseView extends View {

    protected RectF mainRect;
    protected int DEF_SIZE = 50;//dp size

    public BaseView(Context context) {
        super(context);
        init(context, null);
    }

    public BaseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BaseView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    protected void init(Context context, AttributeSet attrs) {
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mainRect = new RectF(0, 0, getSize(widthMeasureSpec), getSize(heightMeasureSpec));
        calSize(widthMeasureSpec, heightMeasureSpec);
    }

    protected void calSize(int widthMeasureSpec, int heightMeasureSpec) {
        onSetDimension((int) mainRect.width(), (int) mainRect.height());
    }

    protected void onSetDimension(int width, int height) {
        setMeasuredDimension(width, height);
    }

    protected int getSize(int measureSpec) {
        int mode = MeasureSpec.getMode(measureSpec);
        int size = 0;
        if (mode == MeasureSpec.EXACTLY) {
            size = MeasureSpec.getSize(measureSpec);
        } else {
            size = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEF_SIZE, getResources().getDisplayMetrics());
        }
        return size;
    }
}
