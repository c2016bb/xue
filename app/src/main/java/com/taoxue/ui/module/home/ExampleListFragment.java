package com.taoxue.ui.module.home;

import android.view.View;

import com.taoxue.R;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.view.TopBar;

/**
 * Created by hopshine on 2017/1/5.
 */

public class ExampleListFragment extends BaseListFragment<ExampleListFragment.ExampleHolder,BaseModel> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle("实例列表");
    }

    @Override
    protected void loadData(int page, int pageSize) {
//        String cityCode = XiaoLuApplication.get().getCityCode();
//        String cityname = XiaoLuApplication.get().getCity().getCityname();
        getServer().getCatalogType()
                .enqueue(new OnRefreshListResponseListener<BaseModel>(getActivity(),
                        getRecyclerView(), getPageInfoModel()));
    }

    @Override
    protected void onInit() {
        super.onInit();
        setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
    }

    @Override
    protected BaseRecyclerAdapter<ExampleHolder, BaseModel> createAdapter() {
        return new DiaryListAdapter();
    }

    private class DiaryListAdapter extends BaseRecyclerAdapter<ExampleListFragment.ExampleHolder, BaseModel> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_example_item;
        }

        @Override
        protected void bindView(ExampleListFragment.ExampleHolder holder, BaseModel BaseModel, int position) {
            bindAdapterItemView(holder, BaseModel, position);
        }

        @Override
        protected ExampleListFragment.ExampleHolder getHolder(View itemView) {
            return new ExampleListFragment.ExampleHolder(itemView);
        }
    }

    protected void bindAdapterItemView(ExampleListFragment.ExampleHolder holder, BaseModel BaseModel, int position) {
//        holder.diaryTitle.setText(BaseModel.getWordcode());
//        holder.diaryIntro.setText(BaseModel.getWordname());
//        holder.diaryTime.setText(BaseModel.getClasscode());
    }

    public class ExampleHolder extends BaseViewHolder {

//        @BindView(R.id.tv_diary_title)
//        TextView diaryTitle;
//
//        @BindView(R.id.tv_diary_intro)
//        TextView diaryIntro;
//
//        @BindView(R.id.tv_diary_time)
//        TextView diaryTime;

        public ExampleHolder(View itemView) {
            super(itemView);
        }
    }

}
