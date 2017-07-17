package com.taoxue.ui.module.fragment;

import android.view.View;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseListFragment;
import com.taoxue.base.BaseRecyclerAdapter;
import com.taoxue.base.BaseViewHolder;
import com.taoxue.http.OnRefreshListResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.view.TopBar;

/**
 * Created by hopshine on 2017/1/5.PlayRecordFragment
 */

public class PlayRecordFragment extends BaseListFragment<PlayRecordFragment.ExampleHolder,BaseModel> {

    @Override
    protected void onInitTopBar(TopBar topBar) {
        topBar.setTitle(R.string.play_record);
    }

    @Override
    protected void loadData(int page, int pageSize) {
        getServer().read(TaoXueApplication.get().getUserModel().getUser_id(),page,pageSize)
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

    private class DiaryListAdapter extends BaseRecyclerAdapter<PlayRecordFragment.ExampleHolder, BaseModel> {

        @Override
        protected int getItemLayout() {
            return R.layout.ui_frag_my_collection;
        }

        @Override
        protected void bindView(PlayRecordFragment.ExampleHolder holder, BaseModel BaseModel, int position) {
            bindAdapterItemView(holder, BaseModel, position);
        }

        @Override
        protected PlayRecordFragment.ExampleHolder getHolder(View itemView) {
            return new PlayRecordFragment.ExampleHolder(itemView);
        }
    }

    protected void bindAdapterItemView(PlayRecordFragment.ExampleHolder holder, BaseModel BaseModel, int position) {
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
