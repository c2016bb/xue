package com.taoxue.ui.view;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.taoxue.R;
import com.taoxue.ui.adapter.BaseAdapter.Base.ViewHolder;
import com.taoxue.ui.adapter.BaseAdapter.RvCommonAdapter;
import com.taoxue.ui.model.MyLibBean;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.UtilGson;

import java.util.List;

/**
 * Created by User on 2017/6/7.
 */

public class CommentDialog extends FullCommonDialog {
//    private int layoutId;
//    private Context context;
     private View dialogView;
    private SparseArray<RadioButton> mRadioBtns;
    private  MyLibBean myLibBean;//我的图书馆缓存信息
    private RecyclerView rv;
    String defaultLibName;//默认图书馆
    private CheckBox cb;
    private boolean isSelectedCheckBox =false;

    public CommentDialog(Context context, int layoutId, FullCommonDialog.OnSureClickListener onSureClickListener) {
        super(context,layoutId,0);
        this.onSureClickListener=onSureClickListener;
    }

    @Override
    public void onitView(View view) {
        mRadioBtns=new SparseArray<>();
        this.dialogView=view;
         setAdpater();
    }
//    public CommentDialog(Context context, int layoutId, OnSureClickListener onSureClickListener) {
//        this(context,R.style.commondialog,layoutId,onSureClickListener);
////
//    }
//
//    public CommentDialog(Context context, int themeResId, int layoutId, OnSureClickListener onSureClickListener) {
//        super(context, themeResId);
//        this.layoutId=layoutId;
//        this.context=context;
//        mViews = new SparseArray<View>();
//        mRadioBtns=new SparseArray<>();
//        onitView();
//        this.onSureClickListener=onSureClickListener;
//    }
//
//    private void onitView(){
//        dialogView = LayoutInflater.from(context).inflate(layoutId,null);
//        //获得dialog的window窗口
//        Window window = getWindow();
//
//        //设置dialog在屏幕底部
//        window.setGravity(Gravity.CENTER);
////        //设置dialog弹出时的动画效果，从屏幕底部向上弹出
////        window.setWindowAnimations(R.style.dialogStyle);
//        window.getDecorView().setPadding(0, 0, 0, 0);
//        //获得window窗口的属性
//        android.view.WindowManager.LayoutParams lp = window.getAttributes();
//        //设置窗口宽度为充满全屏
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        //设置窗口高度为包裹内容
//        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
//        //将设置好的属性set回去
//        window.setAttributes(lp);
//        //将自定义布局加载到dialog上
//        setContentView(dialogView);
//        setAdpater();
//    }



    private void setAdpater() {
//        android.R.id.button1
        rv = getView(android.R.id.keyboardView);
        cb = getView(R.id.my_lib_checkbox);
        Button btn = getView(android.R.id.button1);
        btn.setOnClickListener(new SureClick());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isSelectedCheckBox) {
                    isSelectedCheckBox = false;
                } else {
                    isSelectedCheckBox = true;
                }
            }
        });
        myLibBean = (MyLibBean) UtilGson.fromJson((String) SPUtil.get(SPUtil.MY_LIB_INFO_LIST_KEY, ""), MyLibBean.class);
        if (null == myLibBean) {
            return;
        }

        RvCommonAdapter.Builder builder = new RvCommonAdapter.Builder<MyLibInfo>(getContext(), R.layout.my_lib_choice_item, myLibBean.getMyLibInfos());


        builder.createAdapter(rv, new RvCommonAdapter.InitViewCallBack<MyLibInfo>() {
            @Override
            public void convert(ViewHolder holder, MyLibInfo myLibInfo, int position) {
                holder.setText(R.id.my_lib_name_tv, myLibInfo.getName());
//                holder.setOnClickListener(android.R.id.button1,new SureClick());

                RadioButton rb = holder.getView(R.id.my_lib_set_rb);
                defaultLibName = (String) SPUtil.get(SPUtil.MY_LIB_INFO_DEFAULT_Lib_KEY, "");
                LogUtils.D("defaultLibName---->" + defaultLibName);
                if (null == defaultLibName) {
                    if (position == 0) {
                        rb.setChecked(true);
                        saveDefaultLibName(myLibInfo.getName());
                    }
                } else {
                    if (defaultLibName.equals(myLibInfo.getName())) {
                        rb.setChecked(true);
                    }
                }

                if (myLibBean.getMyLibInfos().size() == 1) {
                    rb.setChecked(true);
                    saveDefaultLibName(myLibInfo.getName());
                } else {
                    rb.setOnClickListener(new RbClickLisenter(position));
//                   rb.setOnCheckedChangeListener(new RbClickLisenter(position));
                    mRadioBtns.put(position, rb);
                }
//                     holder.setOnClickListener(R.id.my_lib_set_rb,new RbClickLisenter(position))
            }
        });

    }

    private  class SureClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            SPUtil.put(SPUtil.NO_SECLECT_PAY_KEY,isSelectedCheckBox);
            if (onSureClickListener!=null){
                onSureClickListener.onClick(v);
            }
        }
    }

//
// public interface  OnSureClickListener {
//     void onClick(View v);
// }
//  public OnSureClickListener onSureClickListener;
//
//  public  void setOnSureClickListener(OnSureClickListener onSureClickListener){
//    this.onSureClickListener=onSureClickListener;
//  }



 private  void saveDefaultLibName(String name){
     SPUtil.put(SPUtil.MY_LIB_INFO_DEFAULT_Lib_KEY,name);
 }
private  class  RbClickLisenter implements RadioButton.OnClickListener{
    int position;

    public RbClickLisenter(int position) {
        this.position = position;
    }

    @Override
    public void onClick(View v) {
                for (int i=0;i<mRadioBtns.size();i++){
            if (i==position) {
                LogUtils.D("POSITION--"+position);
                mRadioBtns.get(position).setChecked(true);
                saveDefaultLibName(myLibBean.getMyLibInfos().get(position).getName());
            }else{
                LogUtils.D("keyAt--"+mRadioBtns.keyAt(i)+"value-->"+mRadioBtns.get(i));
                mRadioBtns.get(i).setChecked(false);
            }
        }
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        LogUtils.D("mRadioBtns--->"+mRadioBtns.size());
//        saveDefaultLibName(myLibBean.getMyLibInfos().get(position).getName());
//        for (int i=0;i<mRadioBtns.size();i++){
//            if (i==position) {
//                LogUtils.D("POSITION--"+position);
//                mRadioBtns.get(position).setChecked(true);
//                saveDefaultLibName(myLibBean.getMyLibInfos().get(position).getName());
//            }else{
//                LogUtils.D("keyAt--"+mRadioBtns.keyAt(i)+"value-->"+mRadioBtns.get(i));
//                mRadioBtns.get(i).setChecked(false);
//            }
//        }
//    }
}



//    public <T extends View> T getView(int viewId)
//    {
//        View view = mViews.get(viewId);
//        if (view == null)
//        {
//            view = dialogView.findViewById(viewId);
//            mViews.put(viewId, view);
//        }
//        return   (T) view;
//    }



}
