package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.AppData;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * 必读清单
 */
public class GvReadingListAdapter extends BaseAdapter {
    private Context mContext;
    private List list;

    public GvReadingListAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        if (list == null) {
            this.list = list;
        } else {
            this.list =  AppData.getBdqdBeen(0);

        }
    }



    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ApiOneBean.BdqdBean  bean = (ApiOneBean.BdqdBean) list.get(position);
       ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_gv_new_release_two, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UtilGlide.loadImg(mContext,bean.getResource_picture(),holder.iIv);
        holder.iTvTitle.setText(bean.getTitle()+"");
        holder.iTvDescription.setText(bean.getDiscription()+"");

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_gv_new_2_iv)
        ImageView iIv;
        @BindView(R.id.item_fa_home_gv_new_2_tv_title)
        TextView iTvTitle;
        @BindView(R.id.item_fa_home_gv_new_2_tv_description)
        TextView iTvDescription;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
