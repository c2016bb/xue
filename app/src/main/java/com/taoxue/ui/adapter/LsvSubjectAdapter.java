package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.taoxue.R;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.UtilGlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 */

public class LsvSubjectAdapter extends BaseAdapter {
    private Context mContext;
    private List<Integer> listPic;
    private List list;
    public LsvSubjectAdapter(Context mContext,List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        this.list = list;


        this.mContext = mContext;
        listPic=new ArrayList<>();
//        listPic.add(R.mipmap.u1864);
//        listPic.add(R.mipmap.u1866);
//        listPic.add(R.mipmap.u1868);
    }

    @Override
    public int getCount() {
        return list.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_lsv_subject, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
//        holder.iv.setImageDrawable(mContext.getResources().getDrawable(listPic.get(position)));
//        ImageLoaderUtil.displayPhotoImage(bean.getResource_picture(),holder.iv);
        UtilGlide.loadImg(mContext,bean.getResource_picture(),holder.iv);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_subject_iv)
        ImageView iv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

