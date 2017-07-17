package com.taoxue.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.DisplayMetrics;

import com.danikula.videocache.HttpProxyCacheServer;
import com.taoxue.http.HttpAdapter;
import com.taoxue.ui.model.UserModel;
import com.taoxue.utils.CacheKey;
import com.taoxue.utils.ImageLoaderUtil;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.UtilGson;
import com.taoxue.utils.UtilSystem;
import com.uuzuche.lib_zxing.DisplayUtil;

import java.io.File;

/**
 * Created by gentl on 2016/11/16.
 *
 *
 *
 密钥库类型: JKS
 密钥库提供方: SUN

 您的密钥库包含 1 个条目

 别名: qwe
 创建日期: 2014-10-17
 条目类型: PrivateKeyEntry
 证书链长度: 1
 证书[1]:
 所有者: CN=11111111
 发布者: CN=11111111
 序列号: 9ab9fde
 有效期开始日期: Fri Oct 17 17:59:27 CST 2014, 截止日期: Tue Oct 11 17:59:27 CST 2039
 证书指纹:
 MD5: B5:B0:AE:AD:05:25:19:B9:36:BB:B2:92:1C:53:C8:AD
 SHA1: 45:C3:91:60:1F:3B:EC:73:7B:68:9D:F4:4C:65:9D:40:17:85:66:6C
 SHA256: C5:72:8F:25:E1:6C:2D:D0:BC:48:7F:E1:57:E2:5E:EA:48:BC:08:29:AA:A5:FC:A4:13:54:CF:1B:BA:E1:67:3F
 签名算法名称: SHA256withRSA
 版本: 3

 扩展:

 #1: ObjectId: 2.5.29.14 Criticality=false
 SubjectKeyIdentifier [
 KeyIdentifier [
 0000: DD C6 90 F9 23 73 06 92   D0 ED 68 9D 14 BC 12 3B  ....#s....h....;
 0010: 45 1A 7E E8                                        E...
 ]
 ]



 *******************************************
 *******************************************

 */

public class TaoXueApplication extends MultiDexApplication{
    private static TaoXueApplication app;
    private UserModel userModel;
    private SharedPreferences sp;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        app = this;
        HttpAdapter.init();
        ImageLoaderUtil.init(this, AppConfig.getAppCachePath());
        initStorage();
        initDisplayOpinion();
    }



    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = DisplayUtil.px2dip(getApplicationContext(), dm.widthPixels);
        DisplayUtil.screenHightDip = DisplayUtil.px2dip(getApplicationContext(), dm.heightPixels);
    }

    public static TaoXueApplication get() {
        return app;
    }


    public void setUserModel(UserModel userModel) {

        this.userModel = userModel;
        SPUtil.put("UserModel", UtilGson.toJson(userModel));

    }

    public UserModel getUserModel() {
        if (!isLogin()) return new UserModel();
        userModel = (UserModel) UtilGson.fromJson((String) SPUtil.get("UserModel", ""), UserModel.class);
        return userModel;

    }

    public boolean isLogin() {
        return !(SPUtil.get("UserModel", "")).equals("");
    }

    public void quitLogin() {
        SPUtil.clear(getApplicationContext());
        getUserModel();
    }

    public boolean isFirstEnter() {
        sp = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        return sp.getInt(CacheKey.FIRST_ENTER, 0)!=UtilSystem.getVersionCode();
    }

    public void setNotFirstEnter() {
        sp = getApplicationContext().getSharedPreferences("info", Context.MODE_PRIVATE);
        sp.edit().putInt(CacheKey.FIRST_ENTER, UtilSystem.getVersionCode()).commit();
    }

    private boolean initStorage() {

        // 图片存储路径
        String imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TaoXue/" + "/image/";
        File fileImage = new File(imagePath);
        if (!fileImage.exists()) {
            if (!fileImage.mkdirs()) {
                return false;
            }
        }

        return true;
    }

    private HttpProxyCacheServer proxy;

    public static HttpProxyCacheServer getProxy() {
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

//    private HttpProxyCacheServer newProxy() {
//        return new HttpProxyCacheServer(this);
//    }

    /**
     * 创建缓存代理服务,带文件目录的.
     */
    private HttpProxyCacheServer newProxy() {
        HttpProxyCacheServer.Builder builder = new HttpProxyCacheServer.Builder(this);
        builder.cacheDirectory(new File(AppConfig.getAppCachePath()));
        return builder.build();
    }
      //activity 跳转参数
    public static String  getActivityKey1(Class clazz){
       return "key:"+clazz.getSimpleName()+":one";
    }
    public static String  getActivityKey2(Class clazz){
        return "key:"+clazz.getSimpleName()+":two";
    }
}
