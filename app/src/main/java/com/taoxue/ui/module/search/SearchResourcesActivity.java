package com.taoxue.ui.module.search;

import android.os.Bundle;

import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.module.classification.ClassificationDetailActivity;
import com.taoxue.ui.module.search.bean.ResoureHotSearchKeyBean;
import com.taoxue.utils.UtilIntent;

import java.util.ArrayList;
import java.util.List;

import static com.taoxue.utils.AppUtils.showToast;

public class SearchResourcesActivity extends SearchActivity {

    @Override
    public void setSelectorTasks() {
        mTvSearchSelector.setText("资源");
        et_search.setHint("请输入资源名称");
        netWork();
    }

    public List<String> getHttpData(List<ResoureHotSearchKeyBean.ItemBean> list) {
        final List<String> tags = new ArrayList<>();
        for (ResoureHotSearchKeyBean.ItemBean bean : list) {
            tags.add(bean.getKeyword());
        }
        return tags;
    }

    /**
     * 网络请求
     */
    private void netWork() {
        HttpAdapter.getService().resource()
                .enqueue(new OnResponseNoDialogListener<ResoureHotSearchKeyBean>() {
                    @Override
                    protected void onSuccess(ResoureHotSearchKeyBean bean) {
                        initTagView(getHttpData( bean.getItem()));
                    }
                });
    }

    /**
     * 点击处理的业务
     * @param name
     */
    @Override
    public void onclickSearch(String name) {
        showToast(name);
        Bundle bundle = new Bundle();
        bundle.putString("guanName", name);
        bundle.putString("type", "null");
        UtilIntent.intentDIY(this, ClassificationDetailActivity.class, bundle);
    }
}
