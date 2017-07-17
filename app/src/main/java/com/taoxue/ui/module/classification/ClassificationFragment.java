package com.taoxue.ui.module.classification;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseFragment;
import com.taoxue.ui.adapter.BaseAdapter.Base.ItemViewDelegate;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.MultiItemTypeAdapter;
import com.taoxue.ui.adapter.BaseAdapter.OnItemAdapterClickListener;
import com.taoxue.ui.adapter.BaseAdapter.utils.WrapperUtils;
import com.taoxue.ui.view.DividerGridItemDecoration;
import com.taoxue.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 */
public class ClassificationFragment extends BaseFragment {

    @BindView(R.id.classification)
    TextView title;
    @BindView(R.id.classification_lv)
    RecyclerView lvClass;

    Unbinder unbinder;
    private List<ClassItemBean> datas=new ArrayList<>();
//    int[]cols;
    MultiItemTypeAdapter adapter1;
    @Override
    protected int getLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.fragment_classification_list, null);
        return R.layout.fragment_classification;
    }
  private  class  ClassItemBean{
      String  itemName;
      int    imageResource;

      public ClassItemBean(String itemName, int imageResource) {
          this.itemName = itemName;
          this.imageResource = imageResource;
      }

      public String getItemName() {
          return itemName;
      }

      public void setItemName(String itemName) {
          this.itemName = itemName;
      }

      public int getImageResource() {
          return imageResource;
      }

      public void setImageResource(int imageResource) {
          this.imageResource = imageResource;
      }
  }


    @Override
    protected void onInit() {
        getdata();
        LogUtils.D("datas--->" + datas.toString());
//        cols=getCols();
        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(OrientationHelper. VERTICAL);
        lvClass.setLayoutManager(gridLayoutManager);
        adapter1=new MultiItemTypeAdapter(getActivity(),datas);
        adapter1.addItemViewDelegate(1,new ItemViewDelegate<ClassItemBean>(){
            @Override
            public int getItemViewLayoutId() {
                return R.layout.classification_list_item;
            }

            @Override
            public boolean isForViewType(ClassItemBean item, int position) {
                return !isEqualGuan(position);
            }

            @Override
            public void convert(ViewHolder holder, ClassItemBean classItemBean, int position) {
                holder.setText(R.id.title_tv,CommitContent.nullToSting(classItemBean.getItemName()));
                holder.setImageResource(R.id.classification_item_iv,classItemBean.getImageResource());
            }
        });


         adapter1.addItemViewDelegate(2,new ItemViewDelegate<ClassItemBean>() {
             @Override
             public int getItemViewLayoutId() {
                 return R.layout.fragment_classification_list;
             }
             @Override
             public boolean isForViewType(ClassItemBean item, int position) {
                 return isEqualGuan(position);
             }
             @Override
             public void convert(ViewHolder holder, ClassItemBean classItemBean, int position) {
                 holder.setText(R.id.classification_title_tv,CommitContent.nullToSting(classItemBean.getItemName()));
             }
         });

//
        adapter1.setOnItemAdapterClickListener(new OnItemAdapterClickListener() {
            @Override
            public void onItemClick(View view, ViewHolder holder, int position, int viewType) {
                if (viewType==1) {
                    launch(ClassificationDetailActivity.class,datas.get(position).getItemName());
                }
            }
        });
//        adapter1.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position, int viewType) {
//                if (viewType==1) {
//                    launch(ClassificationDetailActivity.class,datas.get(position).getItemName());
//                    Intent intent = new Intent(getActivity(), ClassificationDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("guanName", datas.get(position).getItemName());
//                    LogUtils.I("guanName-->" + datas.get(position));
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                }
//                else if(viewType==2){
//                   LogUtils.D("po___>"+position);
//                    if(datas.get(position).equals(guans[0])){
//                        if (isShowGuan1){
//                            isShowGuan1=false;
//                        }else {
//                            isShowGuan1=true;
//                        }
//                    }else if(datas.get(position).equals(guans[1])){
//                        if (isShowGuan2){
//                            isShowGuan2=false;
//                        }else {
//                            isShowGuan2=true;
//                        }
//                    }else if(datas.get(position).equals(guans[2])){
//                        if (isShowGuan3){
//                            isShowGuan3=false;
//                        }else {
//                            isShowGuan3=true;
//                        }
//                    }
//                   datas.clear();
//                    LogUtils.D("datas--->"+datas.toString());
//                    getdata();
//                    adapter1.notifyDataSetChanged();
//                    LogUtils.D("datas-->"+datas.toString());
//                }
//            }
//
//            @Override
//            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position, int viewType) {
//                return false;
//            }
//        });
   lvClass.addItemDecoration(new DividerGridItemDecoration(getContext()));

    lvClass.setAdapter(adapter1);
        WrapperUtils wu=new WrapperUtils();

//        adapter = new ClassAdapter(getActivity(), datas);
//        lvClass.setAdapter(adapter);
    }
    final  String[] guans={"畅听馆","视频馆","阅读馆"};
    final String[] guans1 = {"婴儿", "幼儿", "小学低年级", "小学高年级", "初中", "高中"};
    final String[] guans2 = {"才艺", "故事", "歌曲", "游戏", "影院", "其他"};
    final String[] guans3 = {"英语", "国学", "古诗词", "拼音汉字", "语文数学", "认知科学", "动植物", "启蒙教育", "其他"};

     final  int []images1={R.mipmap.baby,R.mipmap.child,R.mipmap.student,R.mipmap.pubmed,R.mipmap.collegestudent,R.mipmap.marketingmanagement};
     final  int []images2={R.mipmap.acqierement,R.mipmap.story,R.mipmap.music,R.mipmap.game,R.mipmap.cinema,R.mipmap.other};
    final  int []images3={R.mipmap.english,R.mipmap.archaeology,R.mipmap.poet,R.mipmap.pinyin,R.mipmap.yuwen,R.mipmap.science,R.mipmap.wildplant,R.mipmap.education,R.mipmap.other};


    private  boolean isEqualGuan(int position){
    for (int i=0;i<guans.length;i++){
        if (position>=datas.size()){
            return  true;
        }else {
            if (datas.get(position).getItemName().equals(guans[i])) {
                return true;
            }
        }
    }
    return  false;
}

    boolean  isShowGuan1=true;
    boolean isShowGuan2=true;
    boolean isShowGuan3=true;

    public void getdata() {
            datas.add(new ClassItemBean(guans[0],0));
        if (isShowGuan1) {
            for (int i = 0; i < guans1.length; i++) {
                datas.add(new ClassItemBean(guans1[i],images1[i]));
            }
        }
        datas.add(new ClassItemBean(guans[1],0));

        if (isShowGuan2){
        for (int i = 0; i < guans2.length; i++) {
            datas.add(new ClassItemBean(guans2[i],images2[i]));
        }}

        datas.add(new ClassItemBean(guans[2],0));
        if (isShowGuan3) {
            for (int i = 0; i < guans3.length; i++) {
                datas.add(new ClassItemBean(guans3[i],images3[i]));
            }
        }
    }

    ;
    @OnClick(value = {R.id.classification})
    public void onClick(View v) {
//       launch(SeccessBindCardActivity.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
