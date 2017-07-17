package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gentl on 2016/12/14.
 */

public class TagView extends LinearLayout {
    private int maxLine = Integer.MAX_VALUE;
    private Adapter adapter;
    private Drawable divider;
    private int parentWidth;
    private View selectView;
    private List<View> childList;

    private int selectPosition = -1;

    public TagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagView);
            maxLine = ta.getInt(R.styleable.TagView_maxLines, Integer.MAX_VALUE);
            divider = ta.getDrawable(R.styleable.TagView_horizontalDivider);
            ta.recycle();
        }
        setClipChildren(false);
        childList = new ArrayList<>();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOrientation(VERTICAL);
        if (isInEditMode()) {
            parentWidth = getResources().getDisplayMetrics().widthPixels;
            editModeTest();
        }
    }


    public void setAdapter(final Adapter adapter) {
        if (isInEditMode()) {
            doSetAdapter(adapter);
            return;
        }
        if (getAdapter() != null) {
            doSetAdapter(adapter);
        } else {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if ((parentWidth = getWidth()) != 0) {
                        getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        doSetAdapter(adapter);
                        if (selectPosition != -1) {
                            childList.get(selectPosition).performClick();
                        }
                    }
                }
            });
        }
    }

    public Adapter getAdapter() {
        return adapter;
    }

    private void editModeTest() {
        setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 100;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView contentView = new TextView(getContext());
                LinearLayout.LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                contentView.setLayoutParams(layoutParams);
                int leftPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
                int topPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
                contentView.setPadding(leftPadding, topPadding, leftPadding, topPadding);
                contentView.setText("Tag" + i);
                return contentView;
            }
        });
    }

    public void setSelectPosition(int position) {
        selectPosition = position;
        if (childList.size() > 0) {
            childList.get(selectPosition).performClick();
        }
    }

    private void doSetAdapter(Adapter adapter) {
        this.adapter = adapter;
        removeAllViews();
        childList.clear();
        LinearLayout currentLine = newLine();
        int childTotalWidth = 0;
        int lineCount = 1;
        for (int i = 0; i < adapter.getCount(); i++) {
            final View childView = adapter.getView(i, null, currentLine);
            final int finalI = i;
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(childView, finalI);
                    }
                    doSelect(v);
                }
            });
            childList.add(childView);
            int dividerTotalWidth = 0;
            int drawableCount = currentLine.getChildCount() - 1;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                if (drawableCount >= 0 && currentLine.getDividerDrawable() != null) {
                    int drawableWdith = currentLine.getDividerDrawable().getIntrinsicWidth();
                    dividerTotalWidth = drawableWdith * (drawableCount + 1);
                }
            }
            int childWidth = getWidth(childView);
            childTotalWidth += childWidth;
            int space = parentWidth - getPaddingLeft() - getPaddingRight() - dividerTotalWidth;
            if (childTotalWidth > space) {
                if (lineCount >= maxLine) {
                    if (currentLine.getChildCount() == 0) {
                        currentLine.addView(childView);
                    }
                    return;
                }
                lineCount++;
                childTotalWidth = childWidth;
                currentLine = newLine();
            }
            currentLine.addView(childView);
        }
    }

    private void doSelect(View view) {
        if (selectView != null && view != selectView) {
            selectView.setSelected(false);
        }
        selectView = view;
        selectView.setSelected(true);
    }

    private int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        view.measure(w, w);
        return view.getMeasuredWidth();
    }

    private LinearLayout newLine() {
        LinearLayout view = new LinearLayout(getContext());
        if (divider != null) {
            view.setDividerDrawable(divider);
            view.setShowDividers(SHOW_DIVIDER_MIDDLE);
        }
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(view);
        return view;
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
