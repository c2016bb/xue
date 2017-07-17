
package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

/**
 * Created by YuanChao on 2016/5/5.
 * 自定义不带滑动的列表控件，适合在ScrollView中使用。
 */
public class EasyLineView extends EasyAbsListView {
    private boolean selectedMode;
    private View selectView;

    public EasyLineView(Context context) {
        super(context);
    }

    public EasyLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EasyLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setSelectedMode(boolean selectedMode) {
        this.selectedMode = selectedMode;
    }

    public boolean isSelectedMode() {
        return selectedMode;
    }

    @Override

    protected void init(AttributeSet attrs) {
        super.init(attrs);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (adapter.getCount() == 0) {
            removeAllViews();
            return;
        }
        removeAllViews();
        childViews.clear();
        this.adapter = adapter;
        for (int i = 0; i < adapter.getCount(); i++) {
            createLine(i);
        }
        if (isSelectedMode()) {
            ((View) childViews.get(0).getParent()).performClick();
        }
    }

    public void notifyDataSetChanged() {
        if (getAdapter() != null) {
            setAdapter(getAdapter());
        }
    }

    public View getSelectView() {
        return selectView;
    }

    @Override
    protected void updateDataAdded() {
        createLine(adapter.getCount() + 1);
    }

    private void createLine(int position) {
        final LinearLayout parent = addLine();
        final View childView = adapter.getView(position, parent, this);
        childViews.add(childView);
        parent.addView(childView);
        childView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        final int finalI = position;
        parent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(parent, childView, finalI);
                if (isSelectedMode()) {
                    if (selectView != null && v != selectView) {
                        selectView.setSelected(false);
                    }
                    selectView = v;
                    selectView.setSelected(true);
                }
            }
        });
    }
}
