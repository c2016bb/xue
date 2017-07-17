package com.taoxue.ui.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.taoxue.R;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.OnItemAdapterClickListener;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by User on 2017/6/12.
 */

public class AudioListDialog extends FullCommonDialog {
    private  Context context;
   private RecyclerView mRecyclerView;
    private UrlPath urlPath;
    private RvCommonAdapter adapter;
    private int audioIndex=0;


    public AudioListDialog(Context context, int layoutId, int animationStyle) {
        super(context, layoutId, animationStyle);
    }

    public AudioListDialog(Context context, int layoutId,UrlPath urlPath , OnItemClickListener onItemClickListener) {
        this(context, layoutId,R.style.dialogStyle);
        this.onItemClickListener=onItemClickListener;
        this.urlPath=urlPath;
        LogUtils.D("urlPath--->"+urlPath.toString());
    }



    @Override
    public void onitView(View view) {

        mRecyclerView=getView(R.id.audiolist_recyclerview);
        setAdapter();
      if (urlPath==null){
LogUtils.D("onitView(dialogView);");

      }else {
          LogUtils.D("urlPath--->" + urlPath.toString());
      }
        View closeView=getView(R.id.audiolist_close_tv);
        closeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AudioListDialog.this.dismiss();
            }
        });
    }

  public  void setIndex(int index){
       audioIndex=index;
      adapter.notifyDataSetChangedRv();
      LogUtils.D("设置执行");
  }


    private class OnItemClick implements OnItemAdapterClickListener{
        @Override
        public void onItemClick(View view, ViewHolder holder, int position, int viewType) {
            onItemClickListener.onClick(view,position);
        }
    }
    private  void setAdapter(){
       adapter=new RvCommonAdapter.Builder<>(getContext(),R.layout.audio_list_item,urlPath.getUrlModel())
                .setOnItemAdapterClickListener(new OnItemClick())
                .createAdapter(mRecyclerView, new RvCommonAdapter.InitViewCallBack<UrlModel>() {
                    @Override
                    public void convert(ViewHolder holder, UrlModel urlModel, int position) {
                        holder.setText(R.id.audio_list_title_tv,urlModel.getResource_name());
                        holder.setText(R.id.audio_list_index_tv,position+1+"");
                        if (audioIndex==position){
                            holder.setTextColor(R.id.audio_list_title_tv,R.color.colorPrimary);
                            holder.setTextColor(R.id.audio_list_index_tv,R.color.colorPrimary);
                        }
                        LogUtils.D("urlModel.getResource_name()--->"+urlModel.getResource_name());
                    }
                });
    }

}
