package com.taoxue.ui.module.classification.resourceLib;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.module.classification.HttpRequest;
import com.taoxue.ui.module.classification.Image.tabs.ZTabLayout;
import com.taoxue.ui.module.classification.vpFragment.BookDeatilFragment;
import com.taoxue.ui.module.classification.vpFragment.CommentFragment;
import com.taoxue.ui.module.classification.vpFragment.ViewPagerAdapter;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResourceLibraryActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.resource_lib_image_iv)
    ImageView resourceLibImageIv;
    @BindView(R.id.resource_lib_title_tv)
    TextView resourceLibTitleTv;
    @BindView(R.id.resource_lib_supplier_tv)
    TextView resourceLibSupplierTv;
    @BindView(R.id.resource_lib_total_resource_tv)
    TextView resourceLibTotalResourceTv;
    @BindView(R.id.resource_lib_total_reading_tv)
    TextView resourceLibTotalReadingTv;
    @BindView(R.id.resource_lib_jieshao_ll)
    LinearLayout resourceLibJieshaoLl;
    @BindView(R.id.resource_lib_tablayout)
    ZTabLayout resourceLibTablayout;
    @BindView(R.id.resource_lib_viewpager)
    ViewPager resourceLibViewpager;

    ResourceLibModel model;
    private String[] mTitles = new String[]{"详情", "资源"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_library);
        ButterKnife.bind(this);
        queryResourceLib();
    }
      //查询资源库详情
    private  void queryResourceLib(){
        HttpRequest.queryResourceLibDetail(this, new HttpRequest.RequestBaseModelCallBack() {
            @Override
            public void onSuccess(BaseModel baseModel) {
                model=(ResourceLibModel) baseModel;
                initView();
            }
            @Override
            public void onUnSuccess(String msg) {
                showToast(msg);
            }
        });
    }
    private  void initView(){
        UtilGlide.loadImg(this,model.getLogo(),resourceLibImageIv);
       resourceLibTitleTv.setText(nullToSting(model.getName()));
        resourceLibSupplierTv.setText(nullToSting(model.getGys_name()));
        resourceLibTotalResourceTv.setText(nullToSting(model.getResource_num()));


    initPager();
    }
    private void initPager() {
        mTitles[1]="资源"+"("+model.getResource_num()+")";
        ViewPagerAdapter vpAdapter=new ViewPagerAdapter(getSupportFragmentManager(),mTitles);
        ResourceLibDetailFragment rldf=new ResourceLibDetailFragment();
        rldf.setArgumentsObj(model);
        vpAdapter.addFrament(rldf);
        ResourceLibResoFragment cf=new ResourceLibResoFragment();
        cf.setArgumentsObj(model);
        vpAdapter.addFrament(cf);
//        CommentFragment cf1=new CommentFragment();
//        cf1.setArgumentsObj(data);
//        LogUtils.D("data--->"+data.toString());
//        vpAdapter.addFrament(cf1);
        resourceLibTablayout.addTab(resourceLibTablayout.newTab().setText(mTitles[0]));
        resourceLibTablayout.addTab(resourceLibTablayout.newTab().setText(mTitles[1]));

        resourceLibViewpager.setAdapter(vpAdapter);
        resourceLibTablayout.setupWithViewPager(resourceLibViewpager);
        resourceLibViewpager.setCurrentItem(0);
    }

}
