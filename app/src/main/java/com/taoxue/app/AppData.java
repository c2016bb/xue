package com.taoxue.app;

import android.os.Environment;
import android.support.annotation.NonNull;

import com.taoxue.ui.model.homefrag.ApiOneBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanks7 on 2017/4/1.
 */

public class AppData {

    /**
     * 是否上线
     */
    public final static Boolean IS_TEST = true;
    /**
     * 是否关闭打印日志
     */
    public final static Boolean IS_LOG = true;
    /**
     * 轮播图图片
     *
     */
    public  static ArrayList<String> textUrlList() {

        ArrayList<String> list = new ArrayList<>();

        list.add("https://i3.mifile.cn/a4/xmad_14964017903819_apNQP.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14965733100139_wcKQo.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14962268945869_klEsv.jpg");

        list.add("https://i3.mifile.cn/a4/xmad_14952089770715_otDpj.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491478616461&di=0f811e7fcd91b51ee487a5744662b5b5&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F13%2F86%2F41%2F06B58PICs5K_1024.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491478619967&di=72f047961c51608c680a9a5fb1529680&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F12%2F33%2F69%2F03I58PICjQ4.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491478623293&di=06f6659c7f3700ffb252cc7056e57f0e&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F16%2F56%2F92%2F48w58PICi93_1024.jpg");
//        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491478683116&di=3a8f2e31755412058246a99f02e737f0&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F01%2F86%2F56R58PIC9D8_1024.jpg");


        return list;

    }
    /**
     *
     */
    public final static String APP_INDEX = "taoxue";
    /**
     *测试图片
     */
    public final static String TEST_PIC_URL = "http://p0.ifengimg.com/pmop/2017/0612/A84E90556D21AAA0C4D70FBB718D92AD8E4CB018_size96_w593_h385.jpeg";
    /**
     * apk安装名称
     */
    public final static String apkName = APP_INDEX+".apk";
    /**
     * 下载路径
     */
    public static String apkURL = "";
    /**
     * imageload缓存路径
     * 可读写的缓存路径
     * /storage/emulated/0/ebookcar/
     */
    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + APP_INDEX + "/";
    public static String FILE_PATH = PATH + "file/";

    /**
     *测试添加的假数据
     * @param t 0横着图片
     * @return
     */
    @NonNull
    public static List<ApiOneBean.BdqdBean> getBdqdBeen(int t) {
        List<ApiOneBean.BdqdBean> list2;
        list2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            ApiOneBean.BdqdBean bdqdBean = new ApiOneBean.BdqdBean();
            bdqdBean.setDiscription("1961年4月12日莫斯科时间上午9点零7分，苏联的东方1号飞船发射升空，抵达300公里高的轨道，并绕地球一周， 这是人类**次飞出大气层，进入太空。此时距离人类**次发明比空气重，而能飞离地面的机械，才过去了58年。");
            bdqdBean.setFile_type("");
            bdqdBean.setTitle("测试不要点击图片");
            if(t!=0){
                bdqdBean.setResource_picture("https://img.alicdn.com/bao/uploaded/i1/TB1gmT8KFXXXXa3XpXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");//竖的图片
            }else{
                bdqdBean.setResource_picture(AppData.TEST_PIC_URL);//横着图片
            }
            list2.add(bdqdBean);
        }
        return list2;
    }


}
