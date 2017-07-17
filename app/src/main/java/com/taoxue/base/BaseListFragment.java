package com.taoxue.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.taoxue.R;
import com.taoxue.ui.model.PageInfoModel;
import com.taoxue.ui.view.OnLoadMoreListener;
import com.taoxue.ui.view.SuperRecyclerView;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.DividerItemDecoration;
import com.taoxue.utils.IntentKey;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by CC on 2016/12/11.
 */

public abstract class BaseListFragment<Holder extends RecyclerView.ViewHolder, T> extends BaseFragment {
    @BindView(R.id.topbar)
    TopBar topBar;

    @BindView(R.id.rv_list)
    SuperRecyclerView recyclerView;

    private PageInfoModel pageInfoModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected void onInit() {
        pageInfoModel = new PageInfoModel();
        if (getArguments() != null) {
            boolean topBarEnable = getArguments().getBoolean(IntentKey.TOPBAR_ENABLE, true);
            if (!topBarEnable) {
                topBar.setVisibility(GONE);
            }
        }
        onInitTopBar(topBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(createAdapter());
        onInitRecyclerView(recyclerView);
    }

    /**
     * 初始化TopBar
     *
     * @param topBar
     */
    protected abstract void onInitTopBar(TopBar topBar);

    protected void onInitRecyclerView(final SuperRecyclerView recyclerView) {
        recyclerView.getSwipeToRefresh().setColorSchemeResources(R.color.colorPrimary);
        RecyclerView.ItemDecoration decoration = getItemDecoration();
        if (decoration != null) {
            recyclerView.addItemDecoration(decoration);
        }
        recyclerView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        if (needLoadMore()) {
            recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                    pageInfoModel.setCurrentPage(pageInfoModel.getCurrentPage() + 1);
                    BaseListFragment.this.onLoadMore(pageInfoModel.getCurrentPage(), pageInfoModel.getPageSize());
                    recyclerView.showMoreProgress();
                }
            });
        }
        recyclerView.setRefreshing(true);
        onRefresh(pageInfoModel.getCurrentPage(), pageInfoModel.getPageSize());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL, R.drawable.divider_hor);
    }

    public void setOnItemClickListener(BaseRecyclerAdapter.OnItemClickListener onItemClickListener) {
        getAdapter().setOnItemClickListener(onItemClickListener);
    }

    /**
     * 是否需要加载更多
     *
     * @return
     */
    protected boolean needLoadMore() {
        return true;
    }

    protected void onRefresh(int page, int pageSize) {
        recyclerView.hideMoreProgress();
        pageInfoModel.setRefresh(true);
        loadData(page, pageSize);
    }

    protected void onLoadMore(int page, int pageSize) {
        pageInfoModel.setRefresh(false);
        loadData(page, pageSize);
    }

    public void refresh() {
        pageInfoModel.reset();
        BaseListFragment.this.onRefresh(pageInfoModel.getCurrentPage(), pageInfoModel.getPageSize());
        recyclerView.hideMoreProgress();
    }

    protected abstract void loadData(int page, int pageSize);

    public PageInfoModel getPageInfoModel() {
        return pageInfoModel;
    }

    public SuperRecyclerView getRecyclerView() {
        return recyclerView;
    }

    protected abstract BaseRecyclerAdapter<Holder, T> createAdapter();

    public BaseRecyclerAdapter<Holder, T> getAdapter() {
        return (BaseRecyclerAdapter) recyclerView.getAdapter();
    }
}
