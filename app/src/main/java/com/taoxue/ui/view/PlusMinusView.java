package com.taoxue.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.taoxue.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/25.
 */

public class PlusMinusView extends FrameLayout {
    @BindView(R.id.tv_count)
    TextView tv_count;
    int count = 1;

    public PlusMinusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LayoutInflater.from(getContext()).inflate(R.layout.layout_puls_minus, this, true);
        ButterKnife.bind(this);
    }

    @OnClick(value = {R.id.btn_minus, R.id.btn_plus})
    public void onClick(View view) {
        if (!((View) getParent()).isSelected()) {
            return;
        }
        count = Integer.parseInt(tv_count.getText().toString());
        if (view.getId() == R.id.btn_minus) {
            if (count <= 1)
                return;
            count--;
            tv_count.setText(String.valueOf(count));
        } else {
            count++;
            tv_count.setText(String.valueOf(count));
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
        tv_count.setText(String.valueOf(count));
    }
}
