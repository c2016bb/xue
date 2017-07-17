package com.taoxue.ui.module.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragmentActivity;
import com.jph.takephoto.model.TResult;
import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.GysDataBean;
import com.taoxue.ui.module.classification.BookIntroductionActivity;
import com.taoxue.utils.LogUtils;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * Created by Administrator on 2016/12/21.
 */

public class ZXingActivity extends TakePhotoFragmentActivity {

    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zxing);
        CaptureFragment captureFragment = new CaptureFragment();
        captureFragment.setAnalyzeCallback(analyzeCallback);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_zxing_container, captureFragment).commit();
    }

    /**
     * 二维码解析回调函数
     */
    CodeUtils.AnalyzeCallback analyzeCallback = new CodeUtils.AnalyzeCallback() {
        @Override
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Log.e("zp",result);
            final String id = result.substring(result.lastIndexOf("type_value="));
            Log.e("zp",result.substring(result.lastIndexOf("type_value=")));
            HttpAdapter.getService().getGys(id.replace("type_value=",""), "app").enqueue(new OnResponseNoDialogListener<BaseResultModel<GysDataBean>>() {
                @Override
                protected void onSuccess(BaseResultModel<GysDataBean> baseResultModel) {
                    Log.e("zp", "success");
//                    if(baseResultModel.getData().getFile_type().equals("doc")) {
                        activityToDetail(BookIntroductionActivity.class, id.replace("type_value=",""), baseResultModel.getData().getFile_type());
//                    }else if(baseResultModel.getData().getFile_type().equals("audio")){
//                        activityToDetail(AudioIndroductionActivity.class, id.replace("type_value=",""), baseResultModel.getData().getFile_type());
//                    }else if(baseResultModel.getData().getFile_type().equals("video")){
//                        activityToDetail(PlayActivity.class, id.replace("type_value=",""), baseResultModel.getData().getFile_type());
//                    }else if(baseResultModel.getData().getFile_type().equals("image")){
//                        activityToDetail(BookIntroductionActivity.class, id.replace("type_value=",""), baseResultModel.getData().getFile_type());
//                    }
                }

                @Override
                protected void onFailure(int code) {
                    super.onFailure(code);
                }
            });
        }

        @Override
        public void onAnalyzeFailed() {
            Log.e("zp","找不到资源");
        }
    };

    private void setResult(Bundle bundle) {
        Intent resultIntent = new Intent();
        resultIntent.putExtras(bundle);
        ZXingActivity.this.setResult(103, resultIntent);
        ZXingActivity.this.finish();
    }

    public void back(View view) {
        onBackPressed();
    }

    boolean isLightOn;
    Camera m_Camera;

    public void flashLight(View view) {
        isLightOn = !isLightOn;
        CodeUtils.isLightEnable(isLightOn);
    }

    public void choosePhoto(View view) {
        TakePhoto takePhoto = getTakePhoto();
        takePhoto.onPickMultiple(1);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (m_Camera != null) {
            m_Camera.stopPreview();
            m_Camera.release();
        }
    }

    //跳转页面
    private void activityToDetail(Class clazz,String id,String type) {


        LogUtils.I("点击了Item");
        Intent in = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(TaoXueApplication.get().getActivityKey1(clazz), id);
//        bundle.putSerializable("booktype", type);
        in.putExtras(bundle);
        in.setClass(this, clazz);
        startActivity(in);
    }
}
