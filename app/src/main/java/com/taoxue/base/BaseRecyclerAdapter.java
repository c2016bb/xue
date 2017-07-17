package com.taoxue.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuanc on 2016/8/16.
 */
public abstract class BaseRecyclerAdapter<Holder extends RecyclerView.ViewHolder, Data> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;


    private View mHeaderView;

    public void addHeaderView(View headerView) {
        mHeaderView = headerView;
//        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }


    List<Data> list;

    public BaseRecyclerAdapter(List<Data> list) {
        this.list = list;
    }

    public BaseRecyclerAdapter() {
        this.list = new ArrayList<>();
    }

    public void refresh(List<Data> list) {
        this.list.clear();
        if (list != null)
            this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMore(List<Data> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public Data getItem(int position) {
        return list.get(position);
    }

    public List<Data> getList() {
        return list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        parent.setClipChildren(false);
        parent.setClickable(true);
        View itemView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        if (mHeaderView != null && viewType == TYPE_HEADER)
            return new InnerHolder(getHeaderView());
        return getHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) return TYPE_NORMAL;
        if (position == 0) return TYPE_HEADER;
        return TYPE_NORMAL;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        int pos = position;
        boolean hasHeader = getHeaderView() != null;
        boolean isHeader = getItemViewType(position) == TYPE_HEADER;
        if (hasHeader && isHeader) {
            return;
        }
        if (hasHeader) {
            pos = position - 1;
        }
        final int finalPos = pos;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getOnItemClickListener() != null) {
                    onItemClickListener.onItemClick(holder.itemView, finalPos);
                }
            }
        });
        bindView((Holder) holder, list.get(pos), pos);
    }

    protected abstract int getItemLayout();

    protected abstract void bindView(Holder holder, Data data, int position);

    protected abstract Holder getHolder(View itemView);

    @Override
    public int getItemCount() {
        if (getHeaderView() != null)
            return list.size() + 1;
        return list.size();
    }

    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        public InnerHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }
}
