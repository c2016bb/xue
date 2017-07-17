package com.taoxue.ui.model;

/**
 * Created by CC on 2016/12/11.
 */

public class PageInfoModel extends BaseModel {

    private boolean isRefresh = true;

    /**
     * 分页大小
     */
    private int pageSize = 10;

    /**
     * 当前页
     */
    private int currentPage = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void reset() {
        setCurrentPage(1);
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void setRefresh(boolean refresh) {
        isRefresh = refresh;
    }
}
