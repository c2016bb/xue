package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/4/27.
 */

public class AlignTextView extends TextView {
    /**
     * 需要绘制的文字
     */
    private String mText;
    /**
     * 文本的颜色
     */
    private int mTextColor;
    /**
     * 文本的大小
     */
    private float mTextSize;
    /**
     * 绘制时控制文本绘制的范围
     */
    private Rect mBound;

    private Paint mPaint;

    private  int max;

    public AlignTextView(Context context) {
        this(context,null);
    }

    public AlignTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AlignTextView, defStyleAttr, 0);
        mTextColor = a.getColor(R.styleable.AlignTextView_AlignTextColor,Color.BLACK);
        mTextSize = a.getDimension(R.styleable.AlignTextView_AlignTextSize, 30);
        max=a.getInteger(R.styleable.AlignTextView_Max,3);

        a.recycle();  //注意回收

        mPaint=new Paint();

        if (mTextSize==40) {
            mTextSize = getTextSize();
        }
        mPaint.setTextSize(mTextSize);
        mPaint.setColor(mTextColor);
    }
    @Override
    protected void onDraw(Canvas canvas) {
        mText=getText().toString();
        //获得绘制文本的宽和高
        mBound = new Rect();
        int mWidth;//单个元素的宽度
        mPaint.setTypeface(getTypeface());
        mPaint.setTextAlign(Paint.Align.LEFT);
        LogUtils.D("mText--->"+mText);
        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
    if (mText.length()==3&&max>2) {
        mWidth=getMeasuredWidth()/(max*2-1);//单个元素的宽度
        LogUtils.D("mText.length()--->"+mText.length());
        mPaint.getTextBounds(mText.charAt(0)+"", 0, 1, mBound);
        LogUtils.D("mBound.width"+mBound.width());
        canvas.drawText(mText.charAt(0)+"",mWidth-mBound.width()/2,baseline, mPaint);
        mPaint.getTextBounds(mText.substring(1,2), 0, mText.substring(1,2).length(), mBound);
        canvas.drawText(mText.substring(1,2)+"",2*(max-2)*mWidth+mWidth-mBound.width()/2,baseline, mPaint);
        mPaint.getTextBounds(mText.substring(2), 0, mText.substring(2).length(), mBound);
        canvas.drawText(mText.substring(2)+"",2*(max-1)*mWidth+mWidth/2-mBound.width()/2,baseline, mPaint);
    }

        if (mText.length()>3&&mText.length()<=max){
            mWidth=getMeasuredWidth()/((mText.length()*2)-1);//单个元素的一半宽度
            for (int i=0;i<mText.length();i++){
                LogUtils.D("mBound.width"+mBound.width());
                mPaint.getTextBounds(mText.substring(i,i+1)+"", 0,mText.substring(i,i+1).length(), mBound);
                if(i==mText.length()-1){
                    canvas.drawText(mText.substring(i,i+1)+"",i*2*mWidth+mWidth/2-mBound.width()/2,baseline, mPaint);
                }else {
                    canvas.drawText(mText.substring(i, i + 1) + "", i * 2 * mWidth + mWidth - mBound.width() / 2, baseline, mPaint);
                }
            }
        }
        LogUtils.D("mText.length()--->"+mText.length());
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }
}
