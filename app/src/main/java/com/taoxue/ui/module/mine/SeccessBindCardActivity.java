package com.taoxue.ui.module.mine;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.BindSuceessInfo;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.view.MultiView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SeccessBindCardActivity extends AppCompatActivity {

    @BindView(R.id.classification)
    TextView classification;
    @BindView(R.id.zi)
    MultiView zi;
    @BindView(R.id.unbind_BTN)
    RelativeLayout unbindBTN;
    @BindView(R.id.activity_seccess_bind_card)
    LinearLayout activitySeccessBindCard;
  private BindSuceessInfo  info;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seccess_bind_card);
        ButterKnife.bind(this);
     getIntentData();
    }
    Handler handler =new Handler();
    Runnable runnable =new Runnable() {
        @Override
        public void run() {
            finish();
        }
    };

private void  getIntentData(){
    info=(BindSuceessInfo) getIntent().getSerializableExtra(CommitContent.BIND_READER_CARD_ID_SUCCESS);
    if(info!=null){
        zi.setText(CommitContent.nullToSting(info.getReaderName()),0);
        zi.setText(CommitContent.nullToSting(info.getReader_card_id()),1);
        zi.setText(CommitContent.nullToSting(info.getMyLib_name()),2);
        handler.postDelayed(runnable,5000);
    }
}

    @OnClick(R.id.unbind_BTN)
    public void onViewClicked() {
        zi.setText("asajbsacc",2);
    }
}
