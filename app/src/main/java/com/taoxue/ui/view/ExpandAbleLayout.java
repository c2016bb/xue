package com.taoxue.ui.view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseAnimatorListener;
import com.taoxue.utils.CommonUtils;

/**
 * 可展开或者隐藏的线性布局。
 * Created by YuanChao on 2015/7/12.
 */
public class ExpandAbleLayout extends LinearLayout {

    private int totalHeight = -1;// 总高度
    private int animDuration = 300;

    private boolean animEnd = true;
    private boolean isOpen;
    private boolean fitScrollView;

    private LayoutParams lp;

    public ExpandAbleLayout(Context context) {
        super(context);
        init(context, null);
    }

    public ExpandAbleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ExpandAbleLayout);
            isOpen = ta.getBoolean(R.styleable.ExpandAbleLayout_isExpand, false);
            fitScrollView = ta.getBoolean(R.styleable.ExpandAbleLayout_fitScrollView, false);
            animDuration = ta.getInt(R.styleable.ExpandAbleLayout_anim_duration, animDuration);
            ta.recycle();
        }
        lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                isOpen ? ViewGroup.LayoutParams.WRAP_CONTENT : 0);
        getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        setVisibility(VISIBLE);
                        totalHeight = getHeight();
                        setLayoutParams(lp);
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        return false;
                    }
                });
    }

    private int getViewHeight(View view) {
        int w = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 展开或者隐藏动画
     * start
     *
     * @author cc
     */
    public void start() {
        if (isOpen)
            hide();
        else
            showAll();
        isOpen = !isOpen;
    }

    public void showAll() {
        startExpand();
    }

    public void hide() {
        endExpand();
    }

    public int getTotalHeight() {
        return totalHeight;
    }

    private void startExpand() {
        expandAnim(0f, totalHeight);
    }

    private void endExpand() {
        expandAnim(totalHeight, 0f);
    }

    private void expandAnim(float start, float end) {
        if (!animEnd)
            return;
        animEnd = false;
        ValueAnimator anim = ValueAnimator.ofFloat(start, end);
        anim.setDuration(animDuration);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float vlaue = (Float) animation.getAnimatedValue();
                lp.height = (int) vlaue;
                setLayoutParams(lp);
            }
        });
        anim.start();
        anim.addListener(new BaseAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator arg0) {
                animEnd = true;
                if (isOpen) {
                    if (listener != null)
                        listener.onExpandEnd(totalHeight);
                    if (fitScrollView)
                        onEnd(ExpandAbleLayout.this);
                }
            }
        });
    }

    private void open() {
        isOpen = true;
        if (totalHeight != -1) {
            lp.height = totalHeight;
            setLayoutParams(lp);
        }
    }

    private void close() {
        isOpen = false;
        if (totalHeight != -1) {
            lp.height = 0;
            setLayoutParams(lp);
        }
    }

    public void setOpen(boolean isOpen) {
        if (isOpen) {
            open();
        } else {
            close();
        }
    }

    /**
     * 检测布局层级中是否有ScrollView，如果有则向下滑动
     * onEnd
     *
     * @param view
     * @author cc
     */
    private void onEnd(View view) {
        if (view instanceof ScrollView) {
            ScrollView parent = (ScrollView) view;
            int y = parent.getScrollY();
            parent.smoothScrollTo(0, y + totalHeight);
        } else if (view instanceof ViewGroup) {
            if (view.getParent() != null)
                onEnd((View) view.getParent());
        }
    }

    private OnExpandEndListener listener;

    public void setOnExpandEndListener(OnExpandEndListener listener) {
        this.listener = listener;
    }

    public void setAttachView(final View view) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                start();
            }
        });
    }

    public void setAttachView(final View view, @DrawableRes final int openResId, @DrawableRes final int closeResId) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view instanceof TextView) {
                    if (isOpen())
                        CommonUtils.setDrawableLeft(view.getContext(), openResId,
                                ((TextView) view));
                    else
                        CommonUtils.setDrawableLeft(view.getContext(), closeResId,
                                ((TextView) view));
                }
                start();
            }
        });
    }

    /**
     * 是否已经展开
     * isOpen
     *
     * @return
     * @author cc
     */
    public boolean isOpen() {
        return isOpen;
    }

    public interface OnExpandEndListener {
        void onExpandEnd(int totalHeight);
    }
}
