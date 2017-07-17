package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.danxx.mdplayer.utils.DeviceUtils;
import com.taoxue.R;
import com.uuzuche.lib_zxing.DisplayUtil;

/**
 * Created by User on 2017/6/20.
 */

public class WLinearLayout extends LinearLayout {
    private  float weight;
    public WLinearLayout(Context context) {
        this(context,null);
    }

    public WLinearLayout(Context context, AttributeSet attrs) {
       this(context, attrs,0);
    }

    public WLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WLinearLayout, defStyleAttr, 0);
         weight=a.getFloat(R.styleable.WLinearLayout_mWeight,1);
        a.recycle();  //注意回收
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(LayoutParams.MATCH_PARENT, (int)(DisplayUtil.screenWidthPx/weight));
    }
}
