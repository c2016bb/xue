package com.taoxue.ui.module.fragment;

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
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class MyCollectionFragment extends BaseListFragment<MyCollectionFragment.ExampleHolder, FragCollectionBean> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle(R.string.my_collection);
    }

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().collection(TaoXueApplication.get().getUserModel().getUser_id(), page, pageSize)
                .enqueue(new OnRefreshListResponseListener<FragCollectionBean>(getActivity(),getRecyclerView(), getPageInfoModel()));
    }

    protected void bindAdapterItemView(MyCollectionFragment.ExampleHolder holder, final FragCollectionBean bean, int position) {
        holder.iTvBookName.setText(bean.getResource_name()+"");
        holder.iTvFormatName.setText("格式："+bean.getFile_format());
        holder.iTvTime.setText(bean.getUpload_time()+"");
//        ImageLoaderUtil.displayImage(bean.getResource_picture(),holder.iIvPic);
        UtilGlide.loadImg(getActivity(),bean.getResource_picture(),holder.iIvPic);

//        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                toDetailActivity(bean.getFile_type(),bean.getResource_id());
//            }
//        });
    }


    @Override
    protected void onInit() {
        super.onInit();
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                toDetailActivity(getAdapter().getItem(position).getFile_type(),getAdapter().getItem(position).getResource_id());
            }
        });
    }


    @Override
    protected BaseRecyclerAdapter<ExampleHolder, FragCollectionBean> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<MyCollectionFragment.ExampleHolder, FragCollectionBean> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_my_collection;
        }

        @Override
        protected void bindView(MyCollectionFragment.ExampleHolder holder, FragCollectionBean bean, int position) {
            bindAdapterItemView(holder, bean, position);
        }

        @Override
        protected MyCollectionFragment.ExampleHolder getHolder(View itemView) {
            return new MyCollectionFragment.ExampleHolder(itemView);
        }
    }



    public class ExampleHolder extends BaseViewHolder {

        @BindView(R.id.frag_collection_iv_pic)
        ImageView iIvPic;
        @BindView(R.id.frag_collection_tv_book_name)
        TextView iTvBookName;
        @BindView(R.id.frag_collection_tv_format_name)
        TextView iTvFormatName;
        @BindView(R.id.frag_collection_tv_time)
        TextView iTvTime;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }


    private void toDetailActivity(String fileType,String id){
        launch(BookIntroductionActivity.class,id);
    }

    /**
     * 跳转到详情页面
     *
     * @param clazz
     */
    //跳转页面
    private void activityToDetail(Class clazz,String id,String type) {
        Intent in = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("booklist", id);
        bundle.putSerializable("booktype", type);
        in.putExtras(bundle);
        launch(clazz, in);
    }

}
