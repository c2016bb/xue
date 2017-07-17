package com.taoxue.ui.module.classification;

import android.content.Context;
import android.view.View;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.module.classification.CollectionView;
import com.taoxue.utils.LogUtils;

import java.util.List;

import retrofit2.Call;

/**
 * Created by User on 2017/6/9.
 */

public class HttpRequest {
    //点击后收藏功能
    public static void addCollection(String user_id, String resource_id, String gys_id, final RequestCallBack callBack) {
        Call<CheckSignModel> call = HttpAdapter.getService().addCollection(user_id, resource_id, gys_id);
        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel model) {
                if (model.getCode() == 1) {
                   callBack.onSuccess(model.getMsg());
                } else {
                        if (model.getMsg().equals("已经收藏！")) {
                            callBack.onRequested(model.getMsg());
                        }else {
                            callBack.onUnSuccess(model.getMsg());
                        }
                    }

            }
        });
    }

    //点击后点赞
    public static void giveThumbs(String user_id, String resource_id, String gys_id, final RequestCallBack callBack) {
        Call<CheckSignModel> call = HttpAdapter.getService().giveThumb(resource_id,user_id,gys_id);
        call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
            @Override
            protected void onSuccess(CheckSignModel model) {
                if (model.getCode() == 1) {
                    callBack.onSuccess(model.getMsg());
                } else {
//                    if (model.getMsg().equals("已经点赞了！")) {
//                        callBack.onRequested(model.getMsg());
//                    }else {
                        callBack.onUnSuccess(model.getMsg());
//                    }
                }

            }
        });
    }



    //添加评论内容
    public static void addCommitContent(String user_id, String resource_id, String comment, final RequestCallBack callBack) {
            Call<CheckSignModel> call = HttpAdapter.getService().addResourceComment(resource_id, user_id, comment);
            call.enqueue(new OnResponseNoDialogListener<CheckSignModel>() {
                @Override
                protected void onSuccess(CheckSignModel checkSignModel) {
                    if (checkSignModel.getCode() == 1) {
                        callBack.onSuccess(checkSignModel.getMsg());
                    } else if (checkSignModel.getCode() == 400) {
                        if (null != checkSignModel.getMsg() && checkSignModel.getMsg().equals("已经评论")) {
                            callBack.onRequested(checkSignModel.getMsg());
                        }else {
                            callBack.onRequested(checkSignModel.getMsg());
                        }
                    }
                }
            });
    }



    public interface RequestCallBack{
        void onSuccess(String msg);//表示请求成功
        void onRequested(String msg); //表示已经请求了
        void onUnSuccess(String msg);//表示失败
    }


    public interface RequestMyLibCallBack{
        void onSuccess(List<MyLibInfo> myLibInfos);//表示请求成功
        void onUnSuccess(String msg);//表示失败
    }

    //查询我的图书馆信息
    public static void queryMyLibInfo(String user_id,final RequestMyLibCallBack callBack) {
        Call<BaseListModel<MyLibInfo>> call = HttpAdapter.getService().queryMyLibInfo(user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<MyLibInfo>>() {
            @Override
            protected void onSuccess(BaseListModel<MyLibInfo> myLibInfoBaseListModel) {
                if (myLibInfoBaseListModel.getCode() == 1&&null != myLibInfoBaseListModel.getItem() && !"[]".equals(myLibInfoBaseListModel.getItem()+"")&&myLibInfoBaseListModel.getItem().size()>0) {
                   callBack.onSuccess(myLibInfoBaseListModel.getItem());
                }else{
                    callBack.onUnSuccess(myLibInfoBaseListModel.getMsg());
                }
            }
        });
    }

    public interface RequestBaseModelCallBack{
        void onSuccess(BaseModel baseModel);//表示请求成功
        void onUnSuccess(String msg);//表示失败
    }

     //查询资源库详情
    public  static  void queryResourceLibDetail(Context context, final RequestBaseModelCallBack callBack){
        Call<BaseResultModel<ResourceLibModel>>call=HttpAdapter.getService().searchResourceDeatail();
        call.enqueue(new OnResponseListener<BaseResultModel<ResourceLibModel>>(context) {
            @Override
            protected void onSuccess(BaseResultModel<ResourceLibModel> model) {
                 if (model.getCode()==1&&null!=model.getData()){
                     callBack.onSuccess(model.getData());
                 }else{
                     callBack.onUnSuccess(model.getMsg());
                 }
            }
        });

    }

}
