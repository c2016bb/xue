package com.taoxue.ui.module.classification.resourceLib;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.DrResourceModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.module.classification.vpFragment.BaseVpFragment;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by User on 2017/6/21.
 */

public class ResourceLibResoFragment extends BaseVpFragment {
    ResourceLibModel model;
    @BindView(R.id.commit_view)
    RecyclerView commitView;
    @BindView(R.id.no_content_tv)
    TextView noContentTv;
    @BindView(R.id.no_content_ll)
    RelativeLayout noContentLl;
    Unbinder unbinder;

    @Override
    protected void getActiArguments(Serializable arguments) {
        model = (ResourceLibModel) arguments;
    }

    @Override
    protected void onInit(View view) {
   if (!("[]").equals(model.getDrdata_resource()+"")&&model.getDrdata_resource().size()>0){
       setAdapter();
   }else{
       noContentTv.setVisibility(View.VISIBLE);
       noContentTv.setText("暂无资源");
       commitView.setVisibility(View.GONE);
     }
    }

    private  void setAdapter() {
        RvCommonAdapter.Builder builder=new RvCommonAdapter.Builder<DrResourceModel>(getActivity(),R.layout.resource_lib_res_item,model.getDrdata_resource());
         builder.setOnLoadMoreListener(new RvCommonAdapter.Builder.OnLoadMoreListener() {
             @Override
             public void onLoadMore(ViewHolder holder) {
                 holder.setText(R.id.more_progress_text, "已经没有更多了");
                 holder.setVisible(R.id.more_progress_pb, View.GONE);
             }
         });

        builder.createAdapter(commitView, new RvCommonAdapter.InitViewCallBack<DrResourceModel>() {
            @Override
            public void convert(ViewHolder holder, DrResourceModel drResourceModel, int position) {
                holder.setImageUrl(R.id.lib_res_item_iv,drResourceModel.getResource_picture());
                holder.setText(R.id.lib_res_item_title_tv,drResourceModel.getName());
                holder.setText(R.id.lib_res_item_content_tv,drResourceModel.getDescription());
            }
        });
    }


    @Override
    protected int getLayout() {
        return R.layout.vp_book_commit;
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
