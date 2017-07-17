package com.taoxue.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.taoxue.R;
import com.taoxue.app.AppData;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.utils.glide.MyBitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by hanks7 on 2017/4/5.
 * 馆藏推荐
 */
public class GvCollectionRecommendationAdapter extends BaseAdapter {
    private final ArrayList<Integer> mipmapList;
    private Context mContext;

    public GvCollectionRecommendationAdapter(Context mContext, List<ApiOneBean.BdqdBean> list) {
        this.mContext = mContext;
        if (list == null) {
            this.list = list;
        } else {
            this.list = AppData.getBdqdBeen(0);
        }
        mipmapList = new ArrayList<>();
        mipmapList.add(R.mipmap.frag_home_shuiqian);
        mipmapList.add(R.mipmap.frag_home_kexue);
        mipmapList.add(R.mipmap.frag_home_yousheng);
        mipmapList.add(R.mipmap.frag_home_youer);
    }

    private List list;

    @Override
    public int getCount() {
        return 4;
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
        ApiOneBean.BdqdBean bean = (ApiOneBean.BdqdBean) list.get(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_fa_home_gv_new_release_one, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext)
                .load(mipmapList.get(position))
                .asBitmap()
                .placeholder(R.mipmap.image_default)
                .into(new MyBitmapImageViewTarget(holder.iIv));
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_fa_home_gv_new_iv)
        ImageView iIv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
