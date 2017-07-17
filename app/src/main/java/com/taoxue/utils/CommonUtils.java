package com.taoxue.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.taoxue.BuildConfig;
import com.taoxue.app.TaoXueApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by CC on 2016/6/4.
 */
public class CommonUtils {


    public static boolean callPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phone));
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有可打开的相关应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /***
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideKeyboard(Activity activity) {
        ((InputMethodManager) TaoXueApplication.get().getSystemService(TaoXueApplication.get().INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /***
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        ((InputMethodManager) TaoXueApplication.get().getSystemService(TaoXueApplication.get().INPUT_METHOD_SERVICE))
                .showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }


    public static final int KEY_CLICKLISTENER = 1;
    public static final int REQ_CODE_CALL = 2;

    public static void inputMehtod(Context context, View view, boolean isShow) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    public static String getCurrentDate() {
        if (BuildConfig.DEBUG)
            return "2016-06-01";
        else
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    public static String getFormatValue2(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }
    public static String getFormatValue5(double d) {
        DecimalFormat df = new DecimalFormat("#.#####");
        return df.format(d);
    }
    public static String getFormatValue1(double d) {
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(d);
    }

    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            if (options < 0)
                options = 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    public static String formatNum(float num) {
        if (num == 0)
            return "0.00";
        DecimalFormat decimalFormat = new DecimalFormat(".00");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(num);// format 返回的是字符串
    }

    public static void setDrawableLeft(Context context, int resid, TextView tv) {
        Drawable left = null;
        if (resid != 0) {
            left = context.getResources().getDrawable(resid);
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        }
        tv.setCompoundDrawables(left, null, null, null); // 设置左图标
    }

    public static void setDrawableRight(Context context, int resid, TextView tv) {
        Drawable right = null;
        if (resid != 0) {
            right = context.getResources().getDrawable(resid);
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
        }
        tv.setCompoundDrawables(null, null, right, null); // 设置右图标
    }

    public static void setDrawableRight(Context context, int leftResid,
                                        int rightResid, TextView tv) {
        Drawable right = null;
        Drawable left = null;
        if (rightResid != 0 && leftResid != 0) {
            right = context.getResources().getDrawable(rightResid);
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());

            left = context.getResources().getDrawable(leftResid);
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        }
        tv.setCompoundDrawables(left, null, right, null); // 设置左右图标
    }

    /**
     * 设置DrawableTop setDrawableTop
     *
     * @param context
     * @param topResid
     * @param tv
     * @author Administrator
     */
    public static void setDrawableTop(Context context, int topResid, TextView tv) {
        Drawable top = null;
        if (topResid != 0) {
            top = context.getResources().getDrawable(topResid);
            top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        }
        tv.setCompoundDrawables(null, top, null, null); // 设置上图标
    }

    public static void setDrawableLeftRight(Context context, int leftResid,
                                            int rightResid, TextView tv) {
        Drawable right = null;
        Drawable left = null;
        if (leftResid != 0) {
            left = context.getResources().getDrawable(leftResid);
            left.setBounds(0, 0, left.getMinimumWidth(),
                    left.getMinimumHeight());
        }
        if (rightResid != 0) {
            right = context.getResources().getDrawable(rightResid);
            right.setBounds(0, 0, right.getMinimumWidth(),
                    right.getMinimumHeight());
        }
        tv.setCompoundDrawables(left, null, right, null); // 设置左右图标
    }



    public static int[] getViewSize(int layoutId) {
        LayoutInflater inflater = (LayoutInflater) TaoXueApplication.get()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        return getViewSize(view);
    }

    public static int[] getViewSize(View view) {
        int[] size = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }

    /**
     * 过滤特殊字符
     *
     * @param str
     * @return
     * @throws PatternSyntaxException
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[/\\:*?<>|\"\n\t]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("");
    }


    /**
     * 判断是否是中文字字符
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        // GENERAL_PUNCTUATION 判断中文的"号
        // CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号
        // HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }


    private static boolean sendCallPhone(Context context, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + phone));
        try {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (context instanceof Activity) {
                ((Activity) context).startActivityForResult(intent,
                        REQ_CODE_CALL);
            } else {
                context.startActivity(intent);
            }
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有可打开的相关应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    private static boolean sendShortMsg(Context context, String phone,
                                        String content) {

        Uri uri = Uri.parse("smsto:" + phone);
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            if (!TextUtils.isEmpty(content))
                intent.putExtra("sms_body", content);// 短信内容
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "没有可打开的相关应用", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }

    /**
     * 将数字以千分位显示 formatNum
     *
     * @param
     * @return
     * @author Administrator
     */
    public static String formatMillesimalNum(double num) {
        DecimalFormat df = new DecimalFormat(",###");
        return df.format(num);
    }

    /**
     * setShapeColor: 设置shape 颜色 <br/>
     *
     * @param view
     * @param color
     * @author Administrator
     */
    public static void setShapeColor(View view, int color) {
        GradientDrawable myGrad = (GradientDrawable) view.getBackground();
        if (myGrad != null)
            myGrad.setColor(color);
    }

    /**
     * openURL
     *
     * @author Administrator
     */
    public static void openBrowser(Context context, String url) {
        if (TextUtils.isEmpty(url))
            return;
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    // 固定ListView的高度
    public static void resetHeight(ListView listview) {
        ListAdapter listAdapter = listview.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listview);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listview.getLayoutParams();
        params.height = totalHeight
                + (listview.getDividerHeight() * (listAdapter.getCount() - 1));
        // ((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
        listview.setLayoutParams(params);
    }

    public static void resetGridViewHeight(GridView listView) {
        // 获取listview的adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        // 固定列宽，有多少列
        int col = 4;// listView.getNumColumns();
        int totalHeight = 0;
        // i每次加4，相当于listAdapter.getCount()小于等于4时 循环一次，计算一次item的高度，
        // listAdapter.getCount()小于等于8时计算两次高度相加
        for (int i = 0; i < listAdapter.getCount(); i += col) {
            // 获取listview的每一个item
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            // 获取item的高度和
            totalHeight += listItem.getMeasuredHeight();
        }

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        listView.setLayoutParams(params);
    }

    /**
     * 设置SpannableString
     *
     * @param orgText
     * @param text
     * @param color
     * @return
     */
    public static SpannableString setTextSpan(String orgText, String text,
                                              int color) {
        SpannableString sbString = new SpannableString(orgText);
        sbString.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sbString;
    }

    /**
     * 获得状态栏的高度
     *
     * @param context
     * @return
     */
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    public static Bitmap getDiskBitmap(String pathString) {
        Bitmap bitmap = null;
        try {
            File file = new File(pathString);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(pathString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static List<String> createTestList(int size) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("呵呵：" + i);
        }

        return list;
    }


    public static int[] getTelephoneInfo() {
        TelephonyManager mTelephonyManager = (TelephonyManager) TaoXueApplication.get().getSystemService(Context.TELEPHONY_SERVICE);

        String imsi = mTelephonyManager.getSubscriberId();
        int[] infos = new int[2];
        if (imsi != null) {


            // 返回值MCC + MNC
            String operator = mTelephonyManager.getNetworkOperator();
            int lac = 0;
            int cellId = 0;
            if (operator != null) {

                if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007") || operator.equals("46001")) {
                    //中国移动
                    int mcc = Integer.parseInt(operator.substring(0, 3));
                    int mnc = Integer.parseInt(operator.substring(3));
                    // 中国移动和中国联通获取LAC、CID的方式
                    GsmCellLocation location = (GsmCellLocation) mTelephonyManager.getCellLocation();
                    lac = location.getLac();
                    cellId = location.getCid();

                }
//                else if (operator.equals("46001")) {
//
//
//
//                }
                else if (operator.equals("46003")) {
                    //中国电信
                    // 中国电信获取LAC、CID的方式
                    CdmaCellLocation location1 = (CdmaCellLocation) mTelephonyManager.getCellLocation();
                    lac = location1.getNetworkId();
                    cellId = location1.getBaseStationId();
                }

                infos[0] = lac;
                infos[1] = cellId;


                LogUtils.I("\t LAC = " + lac + "\t CID = " + cellId);


                // 获取邻区基站信息
//                List<NeighboringCellInfo> infos = mTelephonyManager.getNeighboringCellInfo();
//                StringBuffer sb = new StringBuffer("总数 : " + infos.size() + "\n");
//                for (NeighboringCellInfo info1 : infos) { // 根据邻区总数进行循环
//                    sb.append(" LAC : " + info1.getLac()); // 取出当前邻区的LAC
//                    sb.append(" CID : " + info1.getCid()); // 取出当前邻区的CID
//                    sb.append(" BSSS : " + (-113 + 2 * info1.getRssi()) + "\n"); // 获取邻区基站信号强度
//                }
//                LogUtils.I(" 获取邻区基站信息:" + sb.toString());
            }

        }
        return infos;
    }
}
