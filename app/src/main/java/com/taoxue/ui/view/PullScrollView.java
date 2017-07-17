package com.taoxue.ui.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;

import com.taoxue.R;
import com.taoxue.utils.LogUtils;

/**
 * Created by User on 2017/5/9.
 */

public class PullScrollView extends FrameLayout  {
    private  int mTopBarHeight=150;
    private  int mAudioImageHeight= 660;

    public void setmAudioImageHeight(int mAudioImageHeight) {

        this.mAudioImageHeight = mAudioImageHeight;
    }

    public int getmTopBarHeight() {
        return mTopBarHeight;
    }

    public void setTopBarHeight(int mTopBarHeight) {
        this.mTopBarHeight = mTopBarHeight;
    }

    private  boolean isDetailScrollEnable=true;  //详情页是否可拉动
    private  boolean isUp=true;  //是否上拉


    //    // 记录首次按下位置
//    private float mFirstPosition = 0;
//    // 是否正在放大
//    private Boolean mScaling = false;
//
//    private ImageView dropZoomView;
//
//    private int dropZoomViewWidth;
//    private int dropZoomViewHeight;
    private int mTouchSlop;
    private int mMaximumVelocity, mMinimumVelocity;
    private OverScroller mScroller;

    public PullScrollView(Context context) {
        this(context, null);
    }

    public PullScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        setOrientation(LinearLayout.VERTICAL);
        mScroller = new OverScroller(context);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mMaximumVelocity = ViewConfiguration.get(context)
                .getScaledMaximumFlingVelocity();
        mMinimumVelocity = ViewConfiguration.get(context)
                .getScaledMinimumFlingVelocity();

//        init();
    }

//    private int mTopbarHeight;
//    private int mAudioImageHeight;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        ViewGroup.LayoutParams layoutParams=mCommentLl.getLayoutParams();
        layoutParams.height=getMeasuredHeight()-mTopBarHeight;
        setMeasuredDimension(getMeasuredWidth(), mAudioImageHeight +mDetail.getMeasuredHeight() + mCommentLl.getMeasuredHeight());
        LogUtils.D("mCommentView.getMeasuredHeight()--->"
        +"mDetail.getMeasuredHeight()---->"+mDetail.getMeasuredHeight()+"mCommentLl.getMeasuredHeight()---->"+mCommentLl.getMeasuredHeight());

    }


//        getChildAt(0).measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));

//
//        setMeasuredDimension(getMeasuredWidth(), getChildAt(0).getMeasuredHeight() +mDetail.getMeasuredHeight() + mRecyclerView.getMeasuredHeight());
//        LogUtils.D("layoutParams.height--->"+layoutParams.height);
////
//        LogUtils.D("getChildAt(0).getMeasuredHeight()--->"+getChildAt(0).getMeasuredHeight()+"mCommentView.getMeasuredHeight()--->"+mCommentView.getMeasuredHeight()
//        +"mDetail.getMeasuredHeight()---->"+mDetail.getMeasuredHeight()+"mRecyclerView.getMeasuredHeight()---->"+mRecyclerView.getMeasuredHeight()
//        );



    float mXDown;
    float mYDown;

    float mYLastMove;
    float mXLastMove;

    float mYMove;
    float mXMove;

    float diffY;
    float diffX;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mYDown = ev.getRawY();
//                mXDown=ev.getRawX();
//       LogUtils.D("mYDown--->"+mYDown+"mAudioImageHeight"+mAudioImageHeight);
//                mYLastMove = mYDown;
//                mXLastMove=mXMove;
//                diffY=0;
//                diffX=0;
//                LogUtils.D("ddvdsx");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mYMove = ev.getRawY();
//                mXMove=ev.getRawX();
//
//                diffY = Math.abs(mYMove - mYDown);
//               diffX=Math.abs(mXMove-mXDown);
//                LogUtils.D("diffY-->"+diffY+"diffX--->"+diffX);
//                mYLastMove = mYMove;
//                mXLastMove=mXMove;
//
//                // 当手指拖动值大于TouchSlop值时，认为应该进行滚动，拦截子控件的事件
//                if (diffY > mTouchSlop&&diffX<diffY&&mYDown>mAudioImageHeight) { //表示纵向滑动 拦截
//                    LogUtils.D("diff");
//                    return true;
//                }
//                break;
//        }
//        return super.onInterceptTouchEvent(ev);
//    }



//    private View mTopBar;
//    private View mAudioImage;
    private View mDetail;//内容区域， 不包含图片区域与评价区域
    private  View mAudioContentView;
    private RecyclerView mRecyclerView;
    private View mCommentLl;//评价区域

    private  View mCommitView;
//  private  View mAudioRl;
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
//        mTopBar = findViewById(R.id.introduction_topbar);
//        mAudioImage = findViewById(R.id.audio_image_iv);

        mDetail = findViewById(R.id.audio_detail_ll);
         mAudioContentView=findViewById(R.id.audio_content_ll);
//        mAudioRl=findViewById(R.id.audio_rl);


//         mRecyclerView=(RecyclerView)findViewById(R.id.book_commit_lv);
//        mCommentLl=findViewById(R.id.comment_ll);
//        mCommitView=findViewById(R.id.commit_view);

//        if (!(view instanceof ViewPager))
//        {
//            throw new RuntimeException(
//                    "id_stickynavlayout_viewpager show used by ViewPager !");
//        }
//        mViewPager = (ViewPager) view;
    }



        /**
         * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
         */
        public  int dip2px( float dpValue) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }

        /**
         * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
         */
        public  int px2dip(float pxValue) {
            final float scale = getContext().getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
}


























//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//
//    }
//    @Override
//    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes)
//    {  //nestedScrollAxes 滚动方向
//        LogUtils.D( "onStartNestedScroll");
//        return true;
//    }
//
//    @Override
//    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes)
//    {
//        LogUtils.D( "onNestedScrollAccepted");
//    }
//
//    @Override
//    public void onStopNestedScroll(View target)
//    {
//        LogUtils.D( "onStopNestedScroll");
//    }
//
//    @Override
//    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed)
//    {
//        LogUtils.D( "onNestedScroll");
//    }
//
//    @Override
//    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed)
//    {
////        Log.e(TAG, "onNestedPreScroll");
//        LogUtils.D( "dy--->"+dy+" dx--->"+dx +"getScrollY()--->"+getScrollY()+"mTopViewHeight---->"+mTopViewHeight);
////          dy<0  上拉
//
//        boolean hiddenTop = dy > 0 && getScrollY() < mTopViewHeight;
//        boolean showTop = dy < 0 && getScrollY() >= 0 && !ViewCompat.canScrollVertically(target, -1);
//        LogUtils.D("hiddenTop--->"+hiddenTop+"showTop---->"+showTop);
//
//
////        * consumed[0]  为0 时 表示 x 轴方向上事件 没有被消费
////        *              不为0 时 表示 x 轴方向上事件 被消费了, 值表示 被消费的滑动距离
////            * consumed[1]  为0 时 表示 y 轴方向上事件 没有被消费
////        *              不为0 时 表示 y 轴方向上事件 被消费了, 值表示 被消费的滑动距离
//        if (hiddenTop || showTop)
//        {
//            scrollBy(0, dy);
//            consumed[1] = dy;
//        }
//    }
//
//    @Override
//    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed)
//    {
//        Log.e(TAG, "onNestedFling");
//        return false;
//    }
//
//    @Override
//    public boolean onNestedPreFling(View target, float velocityX, float velocityY)
//    {
//        LogUtils.D( "--------------------------------------------------------------->onNestedPreFling");
//        //down - //up+
//        if (getScrollY() >= mTopViewHeight) return false;
//        fling((int) velocityY);
//        return true;
//    }
//
//    @Override
//    public int getNestedScrollAxes()
//    {
//        LogUtils.D( "getNestedScrollAxes");
//        return 0;
//    }










    //
//    @Override
//    protected void onFinishInflate() {
//        super.onFinishInflate();
//        dropZoomView = (ImageView) findViewById(R.id.audio_image_iv);
//    }
//    private void init() {
//        setOverScrollMode(OVER_SCROLL_NEVER);
//                setOnTouchListener(this);
//        }
//
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        if (dropZoomViewWidth <= 0 || dropZoomViewHeight <= 0) {
//            dropZoomViewWidth = dropZoomView.getMeasuredWidth();
//            dropZoomViewHeight = dropZoomView.getMeasuredHeight();
//        }
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_UP:
//                //手指离开后恢复图片
//                mScaling = false;
//                replyImage();
//                break;
//            case MotionEvent.ACTION_MOVE:
//                if (!mScaling) {
//                    if (getScrollY() == 0) {
//                        mFirstPosition = event.getY();// 滚动到顶部时记录位置，否则正常返回
//                        LogUtils.D("mFirstPosition--->"+mFirstPosition);
//                    } else {
//                        break;
//                    }
//                }
//                int distance = (int) ((event.getY() - mFirstPosition) * 0.6); // 滚动距离乘以一个系数
//                if (distance < 0) { // 当前位置比记录位置要小，正常返回
//                    break;
//                }
//
//                // 处理放大
//                mScaling = true;
//                setZoom(1 + distance);
//                return true; // 返回true表示已经完成触摸事件，不再处理
//        }
//        return super.onTouchEvent(event);
//    }
//
//    // 回弹动画 (使用了属性动画)
//    public void replyImage() {
//        final float distance = dropZoomView.getMeasuredHeight() - dropZoomViewHeight;
//        LogUtils.D("dropZoomView.getMeasuredHeight()--->"+dropZoomView.getMeasuredHeight()+"dropZoomViewHeight--->"+dropZoomViewHeight);
//        // 设置动画
//        ValueAnimator anim = ObjectAnimator.ofFloat(0.0F, 1.0F).setDuration((long) (distance * 0.7));
//
//        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                float cVal = (Float) animation.getAnimatedValue();
//                setZoom(distance - ((distance) * cVal));
//            }
//        });
//        anim.start();
//
//    }
//
//    //缩放
//    public void setZoom(float s) {
//        if (dropZoomViewHeight <= 0 || dropZoomViewWidth <= 0) {
//            return;
//        }
//        ViewGroup.LayoutParams lp = dropZoomView.getLayoutParams();
////        lp.width = (int) (dropZoomViewWidth + s); //处理水平缩放
//        lp.height = (int) (dropZoomViewHeight * ((dropZoomViewWidth + s) / dropZoomViewWidth));
//        dropZoomView.setLayoutParams(lp);
////        dropZoomView.setMaxHeight();
//    }


