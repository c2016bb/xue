package com.taoxue.ui.module.search.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.classification.resourceLib.ResourceLibraryActivity;
import com.taoxue.ui.module.search.CopyMeiTuanCityStyle.adapter.ResultListAdapter;
import com.taoxue.ui.module.search.adapter.ResRecyclerViewAdapter;
import com.taoxue.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ResourceManagementFragment extends BaseFragment {


    @BindView(R.id.myrv)
    RecyclerView rlsv;

    Unbinder unbinder;

    List<ApiOneBean.BdqdBean> list;
    private ResRecyclerViewAdapter myAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_serach_home;
    }

    @Override
    protected void onInit() {
        initData();
        initRecyclerView();

    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);
        rlsv.setLayoutManager(manager);
        myAdapter = new ResRecyclerViewAdapter(getActivity(), list);
        rlsv.setAdapter(myAdapter);

        rlsv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    loadMoreData();
                }
            }
        });

        myAdapter.setOnItemClickListener(new ResRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                LogUtils.D("POSITION--->"+position);
                launch(ResourceLibraryActivity.class);
            }
        });

    }

    /**
     * 判断recyclerView
     * @param recyclerView
     * @return
     */
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }



    private void loadMoreData() {
        ApiOneBean.BdqdBean bean = new ApiOneBean.BdqdBean();
        bean.setDiscription("测试测试测试222");
        bean.setTitle("testtest2222");
        bean.setResource_picture("https://img3.doubanio.com/view/photo/photo/public/p2332092960.webp");
        ArrayList<ApiOneBean.BdqdBean> moreList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            moreList.add(bean);
        }
        list.addAll(moreList);
        myAdapter.notifyDataSetChanged();
    }

    /**
     * 添加模拟数据
     */
    private void initData() {
        ApiOneBean.BdqdBean bean = new ApiOneBean.BdqdBean();
        bean.setDiscription("指受雇于特定的国家机关或者企业，通过伪装成普通网民或消费者，" +
                "借助搜索链接，在网页评论或论坛中发帖、" +
                "回帖等对他人施加影响的人。有时也泛指论坛自动回帖机的回复、" +
                "网友评论过滤器以及对不同IP的计算机做出不同页面显示的软");
        bean.setTitle("网络水军");
        bean.setResource_picture("http://photocdn.sohu.com/20101126/Img277918800.jpg");
        list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(bean);
        }
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
