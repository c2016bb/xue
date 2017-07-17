package com.taoxue.ui.module.home;

import com.taoxue.base.BaseListActivity;
import com.taoxue.ui.module.fragment.PlayRecordFragment;

public class PlayRecordActivity extends BaseListActivity<PlayRecordFragment> {
    @Override
    protected Class getFragmentClass() {
        return PlayRecordFragment.class;
    }
}

