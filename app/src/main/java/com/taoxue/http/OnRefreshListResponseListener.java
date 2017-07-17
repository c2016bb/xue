package com.taoxue.http;

import android.content.Context;

import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.PageInfoModel;
import com.taoxue.ui.view.SuperRecyclerView;
import com.taoxue.utils.UtilToast;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cc on 16-7-9.
 */
public class OnRefreshListResponseListener<Bean extends Serializable> extends OnResponseListener<BasePageModel<Bean>> {

    private SuperRecyclerView recyclerView;
    /**
     * 分页信息
     */
    private PageInfoModel pageInfoModel;

    public OnRefreshListResponseListener(Context context, SuperRecyclerView recyclerView) {
        super(context);
        this.recyclerView = recyclerView;
        if (!(recyclerView.getAdapter() instanceof BaseRecyclerAdapter)) {
            throw new IllegalStateException("Adapter必须继承自BaseRecyclerAdapter");
        }
    }

    @Override
    protected boolean needDialog() {
        return false;
    }

    public OnRefreshListResponseListener(Context context, SuperRecyclerView recyclerView, PageInfoModel pageInfoModel) {
        this(context, recyclerView);
        this.pageInfoModel = pageInfoModel;
        recyclerView.hideMoreProgress();
    }

    @Override
    protected void onSuccess(BasePageModel<Bean> beanBasePageModel) {
        if(beanBasePageModel.getPage()==null){
            onRequestFailure();
            UtilToast.showText(beanBasePageModel.getMsg());
            return;
        }
        onGetListData(beanBasePageModel.getPage().getResult());
        if (pageInfoModel != null) {
            if (pageInfoModel.isRefresh()) {
                refreshData(beanBasePageModel.getPage().getResult());
            } else {
                loadMode(beanBasePageModel.getPage().getResult());
                recyclerView.hideMoreProgress();
            }
            /**
             * 当数据不足一屏时，隐藏底部
             */
            if (beanBasePageModel.getPage() != null && beanBasePageModel.getPage().getResult() != null &&
                    beanBasePageModel.getPage().getResult().size() < pageInfoModel.getPageSize()) {
                recyclerView.setLoadMoreEnable(false);
            } else {
                recyclerView.setLoadMoreEnable(true);
            }
        } else {
            refreshData(beanBasePageModel.getPage().getResult());
        }
        recyclerView.setRefreshing(false);
    }

    protected void onGetListData(List<Bean> list) {
    }

    @Override
    protected void onRequestFailure() {
        recyclerView.setRefreshing(false);
        recyclerView.hideMoreProgress();
    }

    @SuppressWarnings("unchecked")
    protected void refreshData(List<Bean> list) {
        ((BaseRecyclerAdapter) recyclerView.getAdapter()).refresh(list);
    }

    @SuppressWarnings("unchecked")
    protected void loadMode(List<Bean> list) {
        ((BaseRecyclerAdapter) recyclerView.getAdapter()).loadMore(list);
    }
}
