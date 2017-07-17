package com.taoxue.ui.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.taoxue.R;
import com.taoxue.ui.module.classification.ClassificationDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by User on 2017/4/19.
 */

public class toppicLayoutView extends LinearLayout {

    public toppicLayoutView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public toppicLayoutView(Context context) {
        this(context, null);
    }

    public toppicLayoutView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.topic_category, this);
        ButterKnife.bind(this, view);
    }
    private  void startActivity(String name,String type){
        Intent intent =new Intent(getContext(),ClassificationDetailActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("guanName",name);
        bundle.putString("type",type);
        intent.putExtras(bundle);
       getContext().startActivity(intent);
    }
    @OnClick({R.id.classification_e_book, R.id.classification_video, R.id.classification_audio, R.id.classification_picture})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.classification_e_book:
                startActivity("doc","type");
                break;
            case R.id.classification_video:
                startActivity("video","type");
                break;
            case R.id.classification_audio:
                startActivity("radio","type");
                break;
            case R.id.classification_picture:
                startActivity("image","type");
                break;
        }
    }
}
