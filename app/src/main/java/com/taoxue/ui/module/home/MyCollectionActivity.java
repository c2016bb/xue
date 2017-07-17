package com.taoxue.ui.module.home;

import com.taoxue.base.BaseListActivity;
import com.taoxue.ui.module.fragment.MyCollectionFragment;

public class MyCollectionActivity extends BaseListActivity<MyCollectionFragment> {
    @Override
    protected Class getFragmentClass() {
        return MyCollectionFragment.class;
    }
}

