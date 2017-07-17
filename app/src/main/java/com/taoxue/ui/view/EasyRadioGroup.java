package com.taoxue.ui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.taoxue.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YuanChao on 2016/4/29.
 */
public class EasyRadioGroup extends LinearLayout implements View.OnClickListener {

    private boolean cancelAble;
    private boolean tagOnly;
    private View mCheckedView;
    private int viewIndex;
    private final int KEY_POSITION = R.id.key_easy_radiogroup;

    private int tabBgResId;

    private List<View> childList = new ArrayList<>();

    public EasyRadioGroup(Context context) {
        super(context);
    }

    public EasyRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public EasyRadioGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EasyRadioGroup(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.EasyRadioGroup);
        cancelAble = ta.getBoolean(R.styleable.EasyRadioGroup_cancelAble, false);
        tabBgResId = ta.getResourceId(R.styleable.EasyRadioGroup_tab_bg, -1);
        tagOnly = ta.getBoolean(R.styleable.EasyRadioGroup_tagOnly, false);
        ta.recycle();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initViews(View view) {
        if (tagOnly) {
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                if ("root".equals(viewGroup.getTag())) {
                    addChild(viewGroup);
                } else {
                    for (int i = 0; i < viewGroup.getChildCount(); i++) {
                        initViews(viewGroup.getChildAt(i));
                    }
                }
            } else if ("root".equals(view.getTag())) {
                addChild(view);
            }
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                addChild(viewGroup.getChildAt(i));
            }
        }
        if (mCheckedView != null)
            onSelect(mCheckedView, false);
    }

    private void addChild(View view) {
        childList.add(view);
        if (view.getBackground() == null && tabBgResId != -1)
            view.setBackgroundDrawable(getResources().getDrawable(tabBgResId));
        if (view.getId() == View.NO_ID) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                view.setId(generateViewId());
            }
        }
        view.setClickable(true);
        view.setOnClickListener(this);
        view.setTag(KEY_POSITION, viewIndex);
        if (viewIndex == 0)
            mCheckedView = view;
        viewIndex++;
    }

    @Override
    public void onClick(View v) {
        onSelect(v, true);
    }

    public void setPosition(int position) {
        if (position > childList.size())
            position = childList.size() - 1;
        onSelect(childList.get(position), false);
    }

    private void onSelect(View v, boolean needCallBack) {
        if (v == mCheckedView && cancelAble) {
            mCheckedView.setSelected(!mCheckedView.isSelected());
        } else {
            if (mCheckedView != null) {
                mCheckedView.setSelected(false);
            }
            v.setSelected(true);
        }
        mCheckedView = v;
        if (onTabSelectListener != null && needCallBack)
            onTabSelectListener.onSelect(v, (Integer) v.getTag(KEY_POSITION));
    }

    OnTabSelectListener onTabSelectListener;

    public void setOnTabSelectListener(OnTabSelectListener onTabSelectListener) {
        this.onTabSelectListener = onTabSelectListener;
    }

    public interface OnTabSelectListener {
        void onSelect(View view, int position);
    }

    public List<View> getChildList() {
        return childList;
    }
}
