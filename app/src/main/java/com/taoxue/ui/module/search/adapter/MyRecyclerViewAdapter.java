package com.taoxue.ui.module.search.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

/**
 * Created by User on 2017/6/5.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Activity activity;
    private List<ApiOneBean.BdqdBean > list ;

    public MyRecyclerViewAdapter(Activity activity, List<ApiOneBean.BdqdBean> list) {
        this.activity = activity;
        list.add(0,new ApiOneBean.BdqdBean());
        list.add(4,new ApiOneBean.BdqdBean());
        list.add(8,new ApiOneBean.BdqdBean());
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
        if (viewType==1) {
            View v = LayoutInflater.from(activity).inflate(R.layout.item_fa_gv_title_search_home, parent, false);
            TitleViewHolder titleViewHolder = new TitleViewHolder(v);
            return titleViewHolder;

        } else {
            View v = LayoutInflater.from(activity).inflate(R.layout.item_fa_gv_search_home, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            return myViewHolder;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (position ==0) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.lib_home_guangcang_tuijian);
            return;
        }
        if (position ==4) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.lib_home_remeng_ziyuan);
            return;
        }
        if (position ==8) {
            TitleViewHolder titleViewHolder = (TitleViewHolder) holder;
            titleViewHolder.iv.setImageResource(R.mipmap.lib_home_caini_xihuan);
            return;
        }
        ApiOneBean.BdqdBean  bean =  list.get(position);
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
     * 每行设置几个单元格
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据3个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (isTitle(position)) {
                    return 3;
                }
                return 1;
            }
        });
    }

    /**
     * 判断是否是标题
     *
     * @param position
     * @return
     */
    public boolean isTitle(int position) {
        return position ==0 ||position ==4 ||position ==8  ? true : false;
    }

    @Override
    public int getItemViewType(int position) {
        if (isTitle(position)) {
            return 1;
        }
        return 2;
    }

    /**
     * 普通布局的viewholder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iIv;
        TextView iTvTitle;
        TextView iTvDescription;
        public MyViewHolder(View itemView) {
            super(itemView);
            iIv = (ImageView) itemView.findViewById(R.id.item_fa_home_gv_new_3_iv);
            iTvTitle = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_title);
            iTvDescription = (TextView) itemView.findViewById(R.id.item_fa_home_gv_new_3_tv_description);
        }
    }

    /**
     * 标题布局的viewholder
     */
    class TitleViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;

        public TitleViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.item_fa_home_gv_title);

        }
    }


}
