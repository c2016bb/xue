package com.taoxue.http;

import com.taoxue.http.gson.GsonConverterFactory;
import com.taoxue.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by CC on 2016/5/28.
 */
public class HttpAdapter {

    public final static String FORMAL_URL = "http://www.hopshine.net:11000/DRIS_frontend_V1.0.1/";//正式环境地址
    public final static String TEST_URL = "http://117.71.57.47:9000/DRIS_frontend_V1.0.1/";//测试环境地址
    public static String BASE_URL =FORMAL_URL;

    private static HttpApis service;

    private static OkHttpClient client = new OkHttpClient();

    public static void init() {

        client = client.newBuilder()
                .addNetworkInterceptor(new HttpInterceptor())
                .addInterceptor
                        (new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
        service = retrofit.create(HttpApis.class);
    }


    public static HttpApis getService() {
        if (service == null) {
            init();
        }
        return service;
    }

    public static class HttpInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            String url = request.url().toString();
//            if (!url.contains("api/User")) {
//                request = request.newBuilder().addHeader("clientId", "bTVvVFZCMVpTYTRnZFppbTlPTFlHOVBu").addHeader("tokenId", TaoXueApplication.get().getTokenId()).build();
//            }
            LogUtils.i("url", url);
            return chain.proceed(request);
        }
    }

}
