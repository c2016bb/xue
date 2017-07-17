package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/6/5.
 */

public class MultiTextViewLayout extends LinearLayout{

    View view;
    int rowNum;
    boolean isDivder;
    LayoutInflater inflater;
    int mDivderHeight;
    String  firstString;

    int  multiViewWidth;
    int multiViewHeight;
    String []rowWeights;
    int[]weights;
    String []strs;
    int sum=0;

    public MultiTextViewLayout(Context context) {
        this(context,null);
    }

    public MultiTextViewLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultiTextViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MultiView);
        rowNum=a.getInteger(R.styleable.MultiView_rownum,1);
        isDivder=a.getBoolean(R.styleable.MultiView_isDivder,true);
        mDivderHeight=(int)a.getDimension(R.styleable.MultiView_dividerHeight,2);
        firstString=a.getString(R.styleable.MultiView_firstString);
        multiViewWidth=(int)a.getDimension(R.styleable.MultiView_multiViewWidth,100);
        multiViewHeight=(int)a.getDimension(R.styleable.MultiView_multiViewHeight,60);
        LogUtils.D("isDivder--->"+isDivder);
        String rowWeight=a.getString(R.styleable.MultiView_rowWeight);
        if(!TextUtils.isEmpty(rowWeight)){
            rowWeights=rowWeight.split(":");
            weights=new int[rowWeights.length];
            for (int i=0;i<rowWeights.length;i++){
                if (!TextUtils.isEmpty(rowWeights[i]))
                    weights[i]=Integer.parseInt(rowWeights[i]);
                sum+=weights[i];
            }
        }else{
            weights=new int[rowNum];
            sum=rowNum;
            for (int i=0;i<sum;i++){
                weights[i]=1;
            }
        }
        LogUtils.D("sum--->"+sum);
        initView();
        a.recycle();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        measureChildren(MeasureSpec.AT_MOST,MeasureSpec.EXACTLY);
//          int coumn=getChildCount();
//        for(int i=0;i<coumn;i++){
//            View view =getChildAt(i);
//            view.measure();
//
//
//
//
//        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
//                  int coumn=getChildCount();
//        for(int i=0;i<coumn;i++){
//            View view =getChildAt(i);
//            view.setLayoutParams(new ViewGroup.LayoutParams(getMeasuredWidth(),60));
//        }
    }

    int width;
    int  mHeight;
    TextView titletv;
    TextView nameTv;
     int height;
    private void initView(){
        measure(0,0);
        height=getMeasuredHeight();
        LogUtils.D("height---->"+height+"getHeight()"+getHeight());
        strs=firstString.split(";");//以“；”拆分
//        setOrientation(LinearLayout.VERTICAL);

        for (int i=0;i<rowNum;i++) {
            view=null;

//            llLaout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view = inflater.inflate(R.layout.textview_multi,this);

            LogUtils.D("view-->"+view.toString());
//       LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.heng_layout,null);
            addView(view);
            titletv=(TextView) view.findViewById(R.id.textview_title_tv);
            titletv.setText(strs[i]+"");
            nameTv=(TextView) view.findViewById(R.id.textview_content_tv);
            nameTv.setTag("NameTv"+i);
            LogUtils.D("123--->"+123);
            if (isDivder) {//设置分隔
                if (i < rowNum - 1) {
                    LinearLayout llLaout1 = new LinearLayout(getContext());
                    llLaout1.setLayoutParams(new ViewGroup.LayoutParams(width, mDivderHeight));
                    View view1 = inflater.inflate(R.layout.layout_divider_hor, this);
                    addView(view1);
                }
            }
        }
    }

}
