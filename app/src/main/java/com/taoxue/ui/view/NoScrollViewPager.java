package com.taoxue.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * com.xjx.core.view.CustomizeViewPager
 * @author YuanChao <br/>
 * create at 2015年7月7日 上午10:13:06
 */
public class NoScrollViewPager extends ViewPager {
    private boolean canScroll = false;

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 设置是否可以滑动
     *
     * @param noScroll
     */
    public void setCanScrol(boolean noScroll) {
        this.canScroll = noScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
		/* return false;//super.onTouchEvent(arg0); */
        if (!canScroll)
            return false;
        else
            return super.onTouchEvent(arg0);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (!canScroll)
            return false;
        else {
            //如果使用PhotoView此处可能抛出异常
            try {
                return super.onInterceptTouchEvent(arg0);
            } catch (Exception e) {
                return false;
            }
        }
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        super.setCurrentItem(item, smoothScroll);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

}
