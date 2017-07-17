package com.taoxue.ui.module.home;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.taoxue.R;
import com.taoxue.app.AppData;
import com.taoxue.base.BaseFragment;
import com.taoxue.http.HttpAdapter;
import com.taoxue.ui.adapter.GvCollectionRecommendationAdapter;
import com.taoxue.ui.adapter.GvNewReleasAdapter;
import com.taoxue.ui.adapter.GvReadingListAdapter;
import com.taoxue.ui.adapter.LsvHomeAdapter;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.Slider;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.module.classification.ClassificationDetailActivity;
import com.taoxue.ui.module.mine.MyInformationActivity;
import com.taoxue.ui.module.search.SearchLibActivity;
import com.taoxue.ui.module.search.SearchResourcesActivity;
import com.taoxue.ui.module.search.SearchResultActivity;
import com.taoxue.ui.view.PopWinMenu;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.glide.UtilGlide;
import com.taoxue.utils.permission.UtilPermission;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by CC on 2016/11/16.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.fa_home_convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.fa_home_lsv_history_reading)
    ListView mLsvPopularBooks;
    @BindView(R.id.fa_home_gv_new_release)
    GridView mGv_new_release;
    @BindView(R.id.fa_home_gv_Library_recommendation)
    GridView mGv_Library_recommendation;
    @BindView(R.id.fa_home_gv_new_release_2)
    GridView mGvReadingList;
    @BindView(R.id.fa_home_gv_new_release_3)
    ListView mGvGuessULike;
    @BindView(R.id.fa_home_fg_ptr_layout)
    PtrClassicFrameLayout ptrLayout;
    @BindView(R.id.fa_home_fg_shunfeng_ScrollView)
    ScrollView mScrollView;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.fa_home_iv_zxing)
    ImageView mIvZxing;
    @BindView(R.id.fa_home_edt_search_selector)
    TextView mTvSearchSelector;
    private ArrayList libraryRecommendationList;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onInit() {

        initBanner(AppData.textUrlList());//广告轮播
        intPtrLayout();//初始化下拉刷新
        intAdapter();//初始化adapter
        netWork();//网络请求
        new PopWinMenu(getActivity(), mTvSearchSelector);//初始化popwinMenu
        mTvSearchSelector.setText("资源");
        etSearch.setHint("请输入资源名称");
        UtilPermission.needPermission(getActivity(), code,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
        );//弹出权限提示(一但被取消,只能通过应用设置找回权限)

    }

    @OnClick({
            R.id.fa_home_ll_history_reading,
            R.id.fa_home_ll_new_release,
            R.id.fa_home_ll_new_release_2,
            R.id.fa_home_iv_head,
            R.id.fa_home_iv_zxing,
            R.id.et_search,
            R.id.classification_e_book,
            R.id.classification_video,
            R.id.classification_audio,
            R.id.classification_picture,
            R.id.fa_home_iv_history_classical,
            R.id.fa_home_iv_preschool_education,
            R.id.fa_home_iv_guoxue_classical,
            R.id.fa_home_iv,
            R.id.fa_home_edt_search_selector
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fa_home_ll_history_reading://点击热门好书标题
                if (!AppData.IS_TEST) return;
                UtilIntent.intentDIYLeftToRight(getActivity(), SearchLibActivity.class);
                break;
            case R.id.fa_home_ll_new_release://最新上线
                launch(SearchResultActivity.class);
                break;
            case R.id.fa_home_ll_new_release_2:
                break;
            case R.id.fa_home_iv_head://点击头像
                if (UtilTools.judgeIsLogin(getActivity())) {
                    launch(MyInformationActivity.class);
                }
                break;
            case R.id.fa_home_iv_zxing://点击扫描二维码
                if (!UtilPermission.judgePermisson(getActivity(), Manifest.permission.CAMERA))
                    break;
                Intent intent = new Intent(getActivity(), ZXingActivity.class);
                this.startActivityForResult(intent, 103);

                break;
            case R.id.et_search://点击搜索
                UtilIntent.intentDIYLeftToRight(getActivity(),
                        SearchResourcesActivity.class,
                        android.R.anim.fade_in, android.R.anim.fade_out);
                break;
            case R.id.classification_e_book:
                startActivity("doc", "type");
                break;
            case R.id.classification_video:
                startActivity("video", "type");
                break;
            case R.id.classification_audio:
                startActivity("audio", "type");
                break;
            case R.id.classification_picture:
                startActivity("image", "type");
                break;
            case R.id.fa_home_iv_history_classical:
                startActivity("历史名著", "tag");
                break;
            case R.id.fa_home_iv_guoxue_classical:
                startActivity("国学经典", "tag");
                break;
            case R.id.fa_home_iv:
                startActivity("绘本", "tag");
                break;
            case R.id.fa_home_iv_preschool_education:
                startActivity("绘本", "tag");
                break;
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

    private BaseAdapter lsvPopularBooksAdapter;
    private BaseAdapter GvGCTJAdapter;
    private BaseAdapter gvReadingListAdapter;
    private BaseAdapter gvCollectionRecommendationAdapter;
    private BaseAdapter gvGuessULikeAdapter;
    /**
     * adapter使用的集合
     */
    private List<ApiOneBean.BdqdBean> bdqdBeanList;
    private List<ApiOneBean.BdqdBean> cnxhBeanList;
    private List<ApiOneBean.BdqdBean> gctjBeanList;
    private List<ApiOneBean.BdqdBean> rmhsBeanList;

    /**
     * 初始化Adapter
     */
    private void intAdapter() {
        bdqdBeanList = new ArrayList();
        cnxhBeanList = new ArrayList();
        gctjBeanList = new ArrayList();
        rmhsBeanList = new ArrayList();
        libraryRecommendationList = new ArrayList();

        lsvPopularBooksAdapter = new LsvHomeAdapter(getActivity(), rmhsBeanList);//热门好书2
        GvGCTJAdapter = new GvNewReleasAdapter(getActivity(), gctjBeanList);//最新上线3
        gvReadingListAdapter = new GvReadingListAdapter(getActivity(), bdqdBeanList);//必读清单6
        gvGuessULikeAdapter = new LsvHomeAdapter(getActivity(), cnxhBeanList);//猜你喜欢2
        gvCollectionRecommendationAdapter = new GvCollectionRecommendationAdapter(getActivity(), libraryRecommendationList);//馆藏推荐4

        mLsvPopularBooks.setAdapter(lsvPopularBooksAdapter);
        mGv_new_release.setAdapter(GvGCTJAdapter);
        mGvReadingList.setAdapter(gvReadingListAdapter);
        mGvGuessULike.setAdapter(gvGuessULikeAdapter);
        mGv_Library_recommendation.setAdapter(gvCollectionRecommendationAdapter);

        mLsvPopularBooks.setOnItemClickListener(new AdapterViewOnitmClickListener(rmhsBeanList));
        mGv_new_release.setOnItemClickListener(new AdapterViewOnitmClickListener(gctjBeanList));
        mGvReadingList.setOnItemClickListener(new AdapterViewOnitmClickListener(bdqdBeanList));
        mGvGuessULike.setOnItemClickListener(new AdapterViewOnitmClickListener(cnxhBeanList));
        mGv_Library_recommendation.setOnItemClickListener(new AdapterViewOnitmClickListener(libraryRecommendationList));
    }

    public class AdapterViewOnitmClickListener implements AdapterView.OnItemClickListener {
        List<ApiOneBean.BdqdBean> list;

        public AdapterViewOnitmClickListener(List<ApiOneBean.BdqdBean> bdqdBeanList) {
            this.list = bdqdBeanList;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (list.size() > 0) toDetailActivity(list.get(position).getId());
        }
    }

    private void toDetailActivity(String id) {
        launch(BookIntroductionActivity.class, id);
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().getHome1().enqueue(new OnResponseListener<BaseResultModel<ApiOneBean>>() {
            @Override
            protected void onSuccess(BaseResultModel<ApiOneBean> resultModel) {
                ptrLayout.refreshComplete();//刷新停止
                ApiOneBean bean = resultModel.getData();
                if (bean.getBdqd() != null) {
                    bdqdBeanList.clear();
                    bdqdBeanList.addAll(bean.getBdqd());
                    lsvPopularBooksAdapter.notifyDataSetChanged();
                }
                if (bean.getCnxh() != null) {
                    cnxhBeanList.clear();
                    cnxhBeanList.addAll(bean.getCnxh());
                    GvGCTJAdapter.notifyDataSetChanged();
                }
                if (bean.getGctj() != null) {
                    gctjBeanList.clear();
                    gctjBeanList.addAll(bean.getGctj());
                    gvReadingListAdapter.notifyDataSetChanged();
                }
                if (bean.getRmhs() != null) {
                    rmhsBeanList.clear();
                    rmhsBeanList.addAll(bean.getRmhs());
                    gvGuessULikeAdapter.notifyDataSetChanged();
                }

            }

        });
    }

    /**
     * 初始广告轮播页
     */
    private void initBanner(ArrayList<String> textUrlList) {
        convenientBanner.setFocusable(true);
        convenientBanner.setFocusableInTouchMode(true);
        convenientBanner.requestFocus();

        ArrayList<Slider> sliders = new ArrayList<Slider>();

        for (String str : textUrlList) {
            Slider slider = new Slider();
            slider.setCompanyImg(str);
            sliders.add(slider);
        }

        convenientBanner.setPages(
                new CBViewHolderCreator<LocalIImageHolderView>() {
                    @Override
                    public LocalIImageHolderView createHolder() {
                        return new LocalIImageHolderView();
                    }
                }, sliders)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.mipmap.u1733, R.mipmap.u1735})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        convenientBanner.startTurning(3000);

    }

    /**
     * 广告轮播内部类
     */
    class LocalIImageHolderView implements Holder<Slider> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Slider data) {
            UtilGlide.loadImg(getActivity(), data.getCompanyImg(), imageView);
        }


    }

    /**
     * 初始化下拉刷新
     */
    private void intPtrLayout() {

        mScrollView.setVerticalScrollBarEnabled(false);
        ptrLayout.setLastUpdateTimeRelateObject(this);
        ptrLayout.disableWhenHorizontalMove(true);
        ptrLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mScrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                netWork();

            }
        });
    }

    /**
     * 网络请求回调接口
     *
     * @param <T>
     */
    public abstract class OnResponseListener<T> implements Callback<T> {

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            ptrLayout.refreshComplete();
            if (response.code() == 200) {
                if (response.body() instanceof BaseResultModel) {
                    BaseResultModel commonBean = (BaseResultModel) response.body();
                    if (commonBean.getCode() == 1) {
                        onSuccess((T) commonBean);
                    } else {
                        showToast(commonBean.getMsg() + "");
                    }
                } else {
                    onSuccess(response.body());
                }
            } else {
                showToast("请求异常:" + response.code());
                onRequestFailure();
            }
        }

        protected void onRequestFailure() {

        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            ptrLayout.refreshComplete();
            LogUtils.E(t.getMessage());
            String msg = t.getMessage();
            if (TextUtils.isEmpty(msg))
                msg = "请求异常";
            showToast(msg);
            onRequestFailure();
        }


        protected abstract void onSuccess(T t);

    }

    /**
     * 判断获取文件类型执行跳转
     *
     * @param name
     * @param type
     */
    private void startActivity(String name, String type) {
        launch(ClassificationDetailActivity.class,name,type);
//        Intent intent = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putString("guanName", name);
//        bundle.putString("type", type);
//        LogUtils.i("guanName",name);
//        LogUtils.i("type",type);
//        intent.putExtras(bundle);
//        launch(ClassificationDetailActivity.class, intent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        etSearch.setFocusable(false);
        return rootView;
    }

    //*************************************处理权限******************************************
    private final int code = 3;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        UtilPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    //*************************************处理权限******************************************

}
