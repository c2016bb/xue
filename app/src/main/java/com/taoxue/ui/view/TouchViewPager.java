package com.taoxue.ui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2016/7/25.
 */
public class TouchViewPager extends ViewPager {
    private OnViewPagerClickEvent listener;
    public TouchViewPager(Context context) {
        super(context);
    }

    public TouchViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(listener != null){
                    listener.onTouchDown();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setOnViewPagerTouchListener(OnViewPagerClickEvent event){
        listener = event;
    }

    public interface OnViewPagerClickEvent{
        void onTouchDown();
    }
}
