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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * 最新上线
 */
public class GvNewReleasAdapter extends BaseAdapter {
    private Context mContext;

    public GvNewReleasAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        if (list == null) {
            this.list = list;
        } else {
            this.list =  AppData.getBdqdBeen(0);
        }

    }

    private List list;

    @Override
    public int getCount() {
        return 3;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_gv_new_release_three, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.iTvTitle.setText(bean.getTitle()+"");
        UtilGlide.loadImg(mContext,bean.getResource_picture(),holder.iIv);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_gv_new_3_iv)
        ImageView iIv;
        @BindView(R.id.item_fa_home_gv_new_3_tv_title)
        TextView iTvTitle;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
