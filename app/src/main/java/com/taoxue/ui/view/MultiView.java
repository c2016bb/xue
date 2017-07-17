package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
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
 * Created by User on 2017/4/28.
 */

public class MultiView extends ViewGroup{

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
    public MultiView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MultiView(Context context) {
        this(context,null);
    }

    public MultiView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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
    int width;
    int  mHeight;
    private void initView(){
        strs=firstString.split(";");//以“；”拆分
//        setOrientation(LinearLayout.VERTICAL);
        for (int i=0;i<rowNum;i++) {
            view=null;
            llLaout=null;
            llLaout = new LinearLayout(getContext());
//            llLaout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            view = inflater.inflate(R.layout.heng_layout, llLaout);
            LogUtils.D("view-->"+view.toString());
//       LinearLayout view = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.heng_layout,null);
            addView(view);
            tv=(TextView) view.findViewById(R.id.alignTextView);
            tv.setText(strs[i]+"");
            nameTv=(TextView) view.findViewById(R.id.name_tv);
            nameTv.setTag("NameTv"+i);
            LogUtils.D("123--->"+123);
            if (isDivder) {//设置分隔
                if (i < rowNum - 1) {
                    LinearLayout llLaout1 = new LinearLayout(getContext());
                    llLaout1.setLayoutParams(new ViewGroup.LayoutParams(width, mDivderHeight));
                    View view1 = inflater.inflate(R.layout.layout_divider_hor, llLaout1);
                    addView(view1);
                }
            }
        }
    }

    LinearLayout llLaout;
    TextView tv;
    TextView nameTv;
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        width=getMeasuredWidth();
        mHeight=(getMeasuredHeight()-mDivderHeight*(weights.length-1))/sum;
        LogUtils.D("width"+width);
        LogUtils.D("mHeight"+mHeight);
//        removeAllViews();
        int count =getChildCount();
        int sumHeight=0;
        for(int i=0;i<count;i++){
            LogUtils.D("count-->"+count);
            View child=getChildAt(i);
            if(isDivder) {
                if (i % 2 == 1) {
                    child.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mDivderHeight));
                    sumHeight+=mDivderHeight;
                    child.layout(0, sumHeight-mDivderHeight, width, sumHeight);
                } else {
                    child.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,weights[i/2]*mHeight));
                    sumHeight+=weights[(int)(i/2)]*mHeight;
                    LogUtils.D("i/2-->"+(int)(i/2));
                    child.layout(0, sumHeight-weights[i/2]*mHeight, width, sumHeight);
                }
            }else{
                sumHeight+=weights[i]*mHeight;
                child.layout(0, sumHeight-weights[i]*mHeight, width, sumHeight);
            }
        }
//        super.onLayout(changed, l, t, r, b);
    }
public  void setText(String str,int position){
    if(position>=0&&position<rowNum) {
        TextView TV = (TextView) findViewWithTag("NameTv" + position);
        TV.setText(str);
    }
}
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}
