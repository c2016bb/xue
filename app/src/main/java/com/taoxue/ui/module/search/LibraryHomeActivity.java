package com.taoxue.ui.module.search;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.module.home.ZXingActivity;
import com.taoxue.ui.module.search.fragment.ClassifyFragment;
import com.taoxue.ui.module.search.fragment.LibraryHomeFragment;
import com.taoxue.ui.module.search.fragment.ResourceManagementFragment;
import com.taoxue.ui.view.CircleImageView;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.permission.UtilPermission;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.taoxue.R.id.viewpager;

/**
 * 图书馆首页
 */
public class LibraryHomeActivity extends BaseActivity {

    @BindView(R.id.et_search)
    EditText mEdtSearch;//搜索
    @BindView(R.id.fa_home_edt_search_selector)
    TextView edt_search_selector;//下拉选择
    @BindView(R.id.fa_home_iv_zxing)
    ImageView mIvZxing;//跳转二维码
    @BindView(R.id.fa_home_iv_more)
    ImageView mIvMore;//点击更多
    @BindView(viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ac_my_iv_head)
    CircleImageView acMyIvHead;//头像
    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tablayout)
    SlidingTabLayout mTablayout;

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    public static final String[] mTitles = {"首页", "资源库", "分类"};
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_app_bar);
        ButterKnife.bind(this);
        new PopWinMenu(this, edt_search_selector);//初始化popwinMenu
        mEdtSearch.setFocusable(false);
        edt_search_selector.setText("资源");
        mEdtSearch.setHint("请输入资源名称");
        initViews();


    }

    private void initViews() {
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i]));
        }
        setSupportActionBar(mToolBar);
        BaseFragment fragment1 = new LibraryHomeFragment();
        BaseFragment fragment2 = new ResourceManagementFragment();
        BaseFragment fragment3 = new ClassifyFragment();
        mFragments = new ArrayList<>();
        mFragments.add(fragment1);
        mFragments.add(fragment2);
        mFragments.add(fragment3);

        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return mFragments.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return mFragments.get(arg0);
            }
        };
        mViewPager.setOffscreenPageLimit(3);// 防止ViewPager中的Fragment被销毁
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(0);
        mTablayout.setViewPager(mViewPager, mTitles, this, mFragments);

    }

    @OnClick({R.id.fa_home_iv_head,
            R.id.et_search,
            R.id.fa_home_iv_zxing,
            R.id.fa_home_iv_more,
            R.id.ac_my_iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fa_home_iv_head:
                onBackPressed();
                break;
            case R.id.et_search:
                UtilIntent.intentDIYLeftToRight(this,
                        SearchResourcesActivity.class,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.fa_home_iv_zxing:
                if (!UtilPermission.judgePermisson(this, Manifest.permission.CAMERA))
                    break;
                Intent intent = new Intent(this, ZXingActivity.class);
                this.startActivityForResult(intent, 103);
                break;
            case R.id.ac_my_iv_head:
                break;
        }
    }

    /**
     * 自定义fragment适配器
     */
    class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        /**
         * 覆盖destroyItem方法可阻止销毁Fragment
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }


    /**
     * 扫尾二维码回调放回
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        showToast(resultCode + "");

    }


}
