package com.taoxue.ui.module.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.search.adapter.ResRecyclerViewAdapter;
import com.taoxue.utils.UtilToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

public class TestSearchActivity extends AppCompatActivity {

    @BindView(R.id.myrv)
    RecyclerView rlsv;

    Unbinder unbinder;

    List<ApiOneBean.BdqdBean> list;
    private ResRecyclerViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_serach_home);
        ButterKnife.bind(this);
        initData();
        initRecyclerView();

    }

    /**
     * 初始化recyclerView
     */
    private void initRecyclerView() {
        final GridLayoutManager manager = new GridLayoutManager(this, 1);
        rlsv.setLayoutManager(manager);
        myAdapter = new ResRecyclerViewAdapter(this, list);
        rlsv.setAdapter(myAdapter);
        //创建一个LinearLayoutManager对象
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rlsv.setLayoutManager(linearLayoutManager);
        rlsv.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                simulateLoadMoreData();
            }
        });
    }

    private void simulateLoadMoreData() {
        Observable
                .timer(2, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .map(new Func1<Long, Object>() {
                    @Override
                    public Object call(Long aLong) {
                        loadMoreData();
                        myAdapter.notifyDataSetChanged();
                        UtilToast.showText("Load Finished!");
                        return null;
                    }
                }).subscribe();
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
    }

    /**
     * 添加模拟数据
     */
    private void initData() {
        ApiOneBean.BdqdBean bean = new ApiOneBean.BdqdBean();
        bean.setDiscription("测试测试测试");
        bean.setTitle("testtest");
        bean.setResource_picture("http://photocdn.sohu.com/20101126/Img277918800.jpg");
        list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(bean);
        }
    }


    public abstract class EndlessRecyclerOnScrollListener extends
            RecyclerView.OnScrollListener {

        private int previousTotal = 0;
        private boolean loading = true;
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int currentPage = 1;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(
                LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading
                    && (totalItemCount - visibleItemCount) <= firstVisibleItem) {
                currentPage++;
                onLoadMore(currentPage);
                loading = true;
            }
        }

        public abstract void onLoadMore(int currentPage);
    }


}