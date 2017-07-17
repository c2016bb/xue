package com.taoxue.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.taoxue.base.BaseActivity;


/**
 * Created by CC on 2016/6/2.
 */
public class BackButton extends ImageView {
    public BackButton(Context context) {
        super(context);
        init();
    }

    public BackButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BackButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BackButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getContext() instanceof BaseActivity)
                    ((BaseActivity) getContext()).onBackPressed();
            }
        });
    }
}
