package com.taoxue.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;

/**
 * Created by User on 2017/5/4.
 */

public class LimitLineTextView extends FrameLayout{
    private   int count;
    private   TextView tv;
    private   ImageView iv;
    private   int  textsize;
    private  int textColor;
    private  int resId;
    private  int MaxLine;

    FrameLayout fl;

    public LimitLineTextView(Context context) {
        this(context,null);
    }

    public LimitLineTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LimitLineTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LimitLineTextView);
           textsize=(int) a.getDimension(R.styleable.LimitLineTextView_textsize,14);
           textColor=a.getColor(R.styleable.LimitLineTextView_llTextColor,Color.GRAY);
          resId=a.getResourceId(R.styleable.LimitLineTextView_arrowImage,R.mipmap.ic_arrow_tv_down);
        MaxLine=a.getInteger(R.styleable.LimitLineTextView_displayMaxLine,3);

        tv=new TextView(getContext());
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,textsize);
        tv.setTextColor(textColor);
        addView(tv,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        fl=new FrameLayout(getContext());
        FrameLayout.LayoutParams lp1=new FrameLayout.LayoutParams(160,60);

        iv=new ImageView(getContext());
        iv.setImageResource(resId);
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity=Gravity.RIGHT|Gravity.CENTER_VERTICAL;
        fl.addView(iv,lp);
        lp1.gravity= Gravity.BOTTOM|Gravity.RIGHT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //当版本大于16时
            fl.setBackground(getResources().getDrawable(R.drawable.shape_text_bg));
        }
        addView(fl,lp1);
//        fl.measure(0,0);
        fl.setVisibility(View.GONE);
    }

    private void onClick(){
        setOnClickListener(new OnClickListener() {
            Boolean flag = true;
            @Override
            public void onClick(View v) {
                if (flag) {
                    flag = false;
                    tv.setEllipsize(null); // 展开
                    tv.setLines(count);
                    fl.setVisibility(View.GONE);
                } else {
                    flag = true;
                    tv.setEllipsize(TextUtils.TruncateAt.END); // 收缩
                    tv.setLines(3);
                    fl.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    public  void setImageResource(int resId){
        iv.setImageResource(resId);
    }

  public  void setTextsize(int size){
       tv.setTextColor(size);
   }
public  void setTextColor(@ColorInt int color){
    tv.setTextColor(color);
}

  public  void setChangeLinesText(String str){
      if(str!=null&&!("").equals(str)){

          tv.setText(str);

          updateLines();
      }
  }


    public  void updateLines(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN ) { //当版本大于=16时
        StaticLayout staticLayout = new StaticLayout(tv.getText(), tv.getPaint(),
                getWidth(), Layout.Alignment.ALIGN_NORMAL,
                tv.getLineSpacingMultiplier(), tv.getLineSpacingExtra(), true);
        count=staticLayout.getLineCount();
        LogUtils.D("count"+count);
            if (count>3){
                fl.setVisibility(View.VISIBLE);
                tv.setLines(3);
                onClick();
            }
        }
    }
}
