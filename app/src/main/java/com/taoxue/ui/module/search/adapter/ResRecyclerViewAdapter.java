package com.taoxue.ui.module.search.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.adapter.BaseAdapter.MultiItemTypeAdapter;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by User on 2017/6/5.
 */

public class ResRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<ApiOneBean.BdqdBean> list;

    protected OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    public ResRecyclerViewAdapter(Activity activity, List<ApiOneBean.BdqdBean> list) {
        this.activity = activity;
        this.list = list;
    }

    /**
     * 添加指定item和 viewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(activity).inflate(R.layout.item_fa_search_reclv_resour, parent, false);
        final MyViewHolder titleViewHolder = new MyViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v,titleViewHolder.getAdapterPosition());
                }
            }
        });
        return titleViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ApiOneBean.BdqdBean bean = (ApiOneBean.BdqdBean) list.get(position);

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.iTvDescription.setText(bean.getDiscription() +"");
        myViewHolder.iTvTitle.setText(bean.getTitle() + "");
        UtilGlide.showImageView(activity, bean.getResource_picture(), myViewHolder.iIv);


    }

    /**
     * 总共多少个item
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return (list.size());
    }


    /**
     * 普通布局的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_fa_home_history_iv)
        ImageView iIv;
        @BindView(R.id.item_fa_home_history_tv_title)
        TextView iTvTitle;
        @BindView(R.id.item_fa_home_history_tv_content)
        TextView iTvDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            
        }
    }


}
