package com.taoxue.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.FrameLayout;

import com.taoxue.R;


/**
 * Created by YuanChao on 2016/5/5.
 * 不可滑动的网格布局控件
 */
public class EasyGridView extends EasyAbsListView {

    /**
     * 默认行数为4
     */
    private int column = 4;

    public EasyGridView(Context context) {
        super(context);
    }

    public EasyGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(AttributeSet attrs) {
        super.init(attrs);
        if (attrs != null) {
            TypedArray ta = getResources().obtainAttributes(attrs, R.styleable.EasyGridView);
            column = ta.getInt(R.styleable.EasyGridView_columnCount, 4);
            ta.recycle();
        }
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        removeAllViews();
        addLine();
        int count = 0;
        if (adapter.getCount() % column == 0) {
            count = adapter.getCount();
        } else {
            int row = adapter.getCount() / column;
            count = column * (row + 1);
        }
        for (int i = 0; i < count; i++) {
            final FrameLayout parent = createParent();
            if (adapter.getCount() - 1 >= i) {
                final View childView = adapter.getView(i, parent, this);
                parent.addView(childView);
                childViews.add(childView);
                childView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                final int finalI = i;
                parent.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null)
                            onItemClickListener.onItemClick(parent, childView, finalI);
                    }
                });
            }
            currentLine.addView(parent);
            if ((i + 1) % column == 0) {
                addLine();
            }

        }
    }


    /**
     * item父容器
     *
     * @return
     */
    protected FrameLayout createParent() {
        FrameLayout parent = new FrameLayout(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.weight = 1;
        parent.setLayoutParams(params);
        return parent;
    }

}
