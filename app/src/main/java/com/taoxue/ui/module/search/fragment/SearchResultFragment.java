package com.taoxue.ui.module.search.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.FragCollectionBean;
//import com.taoxue.ui.module.classification.AudioIndroductionActivity;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class SearchResultFragment extends BaseListFragment<SearchResultFragment.ExampleHolder, FragCollectionBean> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setVisibility(View.GONE);
    }

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().collection(TaoXueApplication.get().getUserModel().getUser_id(), page, pageSize)
                .enqueue(new OnRefreshListResponseListener<FragCollectionBean>(getActivity(), getRecyclerView(), getPageInfoModel()));
    }

    protected void bindAdapterItemView(SearchResultFragment.ExampleHolder holder, final FragCollectionBean bean, int position) {
        holder.iTvName.setText(bean.getResource_name() + "");
        holder.iTvSupplierName.setText("格式：" + bean.getFile_format());
        holder.iTvContent.setText(bean.getUpload_time() + "首次提出“刻意练习”这个概念的是佛罗里达州立大学（Florida Sta te University）心理学家 K. Anders Ericsson。这套练习方法的核心假设是，专家级 水平是逐渐地练出来的，而有效进步的关键在于找到一系列的小任务让受训者按顺序完成。 这些小任务必须是受训者正好不会做，但是又正好可以学习掌握的。完成这种练习要求受 训者思想高度集中，这就与那些例行公事或者带娱乐色彩的练习完全不同。“刻意练习” 的理论目前已经被广泛接受");
        UtilGlide.loadImg(getActivity(), bean.getResource_picture(), holder.iIvPic);
    }


    @Override
    protected void onInit() {
        super.onInit();
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toDetailActivity(getAdapter().getItem(position).getFile_type(), getAdapter().getItem(position).getResource_id());
            }
        });
    }


    @Override
    protected BaseRecyclerAdapter<ExampleHolder, FragCollectionBean> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<SearchResultFragment.ExampleHolder, FragCollectionBean> {

     

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_search_result;
        }

        @Override
        protected void bindView(SearchResultFragment.ExampleHolder holder, FragCollectionBean bean, int position) {
            bindAdapterItemView(holder, bean, position);
        }

        @Override
        protected SearchResultFragment.ExampleHolder getHolder(View itemView) {
            return new SearchResultFragment.ExampleHolder(itemView);
        }
    }


    public class ExampleHolder extends BaseViewHolder {

        @BindView(R.id.frg_search_result_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frg_search_result_tv_name)
        TextView iTvName;
        @BindView(R.id.frg_search_result_tv_score)
        TextView iTvScore;
        @BindView(R.id.frg_search_result_tv_content)
        TextView iTvContent;
        @BindView(R.id.frg_search_result_tv_supplier_name)
        TextView iTvSupplierName;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }


    private void toDetailActivity(String fileType, String id) {
//        if (fileType.equals("doc")) {
            activityToDetail(BookIntroductionActivity.class, id, fileType);
//        } else if (fileType.equals("audio")) {
//            activityToDetail(AudioIndroductionActivity.class, id, fileType);
//        } else if (fileType.equals("video")) {
//            activityToDetail(PlayActivity.class, id, fileType);
//        } else if (fileType.equals("image")) {
//            activityToDetail(BookIntroductionActivity.class, id, fileType);
//        }
    }

    /**
     * 跳转到详情页面
     *
     * @param clazz
     */
    //跳转页面
    private void activityToDetail(Class clazz, String id, String type) {
        launch(clazz,id);
//        Intent in = new Intent();
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("booklist", id);
//        bundle.putSerializable("booktype", type);
//        in.putExtras(bundle);
//        launch(clazz, in);
    }

}
