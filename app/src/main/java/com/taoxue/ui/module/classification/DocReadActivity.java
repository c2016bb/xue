package com.taoxue.ui.module.classification;

import android.os.AsyncTask;
import android.os.Bundle;

import com.github.barteksc.pdfviewer.PDFView;
import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.utils.LogUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocReadActivity extends BaseActivity {
    @BindView(R.id.pdfview)
    PDFView pdfView;
    private UrlPath urlPath;
    private ResourceModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_read);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        urlPath = (UrlPath) getIntentKey1();
        model = (ResourceModel) getIntentKey2();
        if (urlPath != null && urlPath.getUrlModel().size() > 0 && model != null) {
            httpInput(urlPath.getUrlModel().get(0).getUrl());
        } else {
            showToast("当前无音频资源");
        }
    }

    private void httpInput(final String path){
        LogUtils.D("zhixing");
    AsyncTask<String,String,InputStream>task=new AsyncTask<String, String, InputStream>() {
        @Override
        protected InputStream doInBackground(String... params) {
            InputStream is=null;
            try{
            // 新建一个URL对象
            URL url = new URL(path);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            // 开始连接
            urlConn.connect();
            is=urlConn.getInputStream();
             LogUtils.D("is--->"+is.toString());
//            // 判断请求是否成功
//            if (urlConn.getResponseCode() == HTTP_200) {
//                // 获取返回的数据
//                byte[] data = readStream(urlConn.getInputStream());
//                Log.i(TAG_GET, "Get方式请求成功，返回数据如下：");
//                Log.i(TAG_GET, new String(data, "UTF-8"));
//            } else {
//                Log.i(TAG_GET, "Get方式请求失败");
//            }
            }catch (Exception e){
                e.printStackTrace();
            }
            return is;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
//            pdfView.f
            pdfView.fromStream(inputStream) // stream is written to bytearray - native code cannot use Java Streams
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    .load();
        }
    };
  task.execute();
    }
}
