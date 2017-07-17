package com.taoxue.ui.module.login.welcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 *
 * @author yysleep
 */

public class WelcomeActivity extends BaseActivity {
    public static final String CURRENT_ITEM = "CurrentItem";
    public static final int FRIST_FRAGMENT = 0;
    public static final int CENTER_FRAGMENT = 1;
    public static final int LAST_FRAGMENT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();
    }

    private void init() {
        TaoXueApplication.get().setNotFirstEnter();
        ViewPager pager = (ViewPager) findViewById(R.id.welcome_vp);
        pager.setAdapter(new WelcomePagerAdapter(getSupportFragmentManager()));
        pager.setCurrentItem(FRIST_FRAGMENT);
    }

    class WelcomePagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;

        WelcomePagerAdapter(FragmentManager fm) {
            super(fm);
            init();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        private void init() {
            fragmentList = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Fragment fragment = new WelcomeFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CURRENT_ITEM, i);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
        }
    }
}
