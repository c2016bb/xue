package com.taoxue.ui.module.classification.vpFragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.utils.LogUtils;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 2017/6/8.
 */

public class BookDeatilFragment extends BaseVpFragment {
    @BindView(R.id.book_author_tv)
    TextView bookAuthorTv;
    @BindView(R.id.book_content_tv)
    TextView bookContentTv;
    Unbinder unbinder;
    int intds=5;
    boolean aBoolean=false;
    float vflot=0.5f;

    ResourceModel data;
    @Override
    protected void getActiArguments(Serializable arguments) {
            data = (ResourceModel) arguments;
    }

    @Override
    protected void onInit(View view) {
      if (data!=null){
          bookAuthorTv.setText(Html.fromHtml(data.getCatalog()));
          bookContentTv.setText(CommitContent.nullToSting(data.getDiscription()));
      }
    }

    @Override
    protected int getLayout() {
        return R.layout.vp_book_detail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
