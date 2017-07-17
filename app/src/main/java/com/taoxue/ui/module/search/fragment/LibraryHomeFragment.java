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
import com.taoxue.ui.module.search.adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LibraryHomeFragment extends BaseFragment {


    @BindView(R.id.myrv)
    RecyclerView rlsv;

    Unbinder unbinder;

    List<ApiOneBean.BdqdBean > list;

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
        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        rlsv.setLayoutManager(manager);
        MyRecyclerViewAdapter myAdapter = new MyRecyclerViewAdapter(getActivity(),list);
        rlsv.setAdapter(myAdapter);
    }

    /**
     * 添加模拟数据
     */
    private void initData() {

        ApiOneBean.BdqdBean bean=   new ApiOneBean.BdqdBean();
        bean.setDiscription("指受雇于特定的国家机关或者企业，通过伪装成普通网民或消费者，" +
                "借助搜索链接，在网页评论或论坛中发帖、" +
                "回帖等对他人施加影响的人。有时也泛指论坛自动回帖机的回复、" +
                "网友评论过滤器以及对不同IP的计算机做出不同页面显示的软");
        bean.setTitle("网络水军");
        bean.setResource_picture("http://photocdn.sohu.com/20101126/Img277918800.jpg");
        list = new ArrayList<>();
        for(int i=0;i<12;i++){
            list.add(bean );
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
