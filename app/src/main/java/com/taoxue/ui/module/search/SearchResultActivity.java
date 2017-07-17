package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.module.search.fragment.SearchResultFragment;
import com.taoxue.utils.UtilToast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchResultActivity extends BaseActivity {

    @BindView(R.id.pop_cilck_ll)
    LinearLayout popCilckLl;
    @BindView(R.id.activity_search_test)
    LinearLayout activitySearchTest;
    @BindView(R.id.pop_click_tv)
    TextView pop_click_tv;

    SearchResultFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_test);
        ButterKnife.bind(this);
        switchFragment();
        initView();

    }

    protected void switchFragment() {
        fragment=new SearchResultFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frag_container, fragment , null);
        transaction.commitAllowingStateLoss();
    }

    private void initView() {
        final ArrayList<String> list = new ArrayList();
        list.add("综合排序");
        list.add("热门");
        list.add("评分");
        new PopWinSearchResultMenu(this, popCilckLl, list, new PopWinSearchResultMenu.ItemClickListener() {
            @Override
            public void itemClickListener(int position) {
                UtilToast.showText("" + list.get(position));
                pop_click_tv.setText("" + list.get(position));
                Bundle bundle=new Bundle();
                bundle.putString("arg",list.get(position));
            }
        });
    }


}
