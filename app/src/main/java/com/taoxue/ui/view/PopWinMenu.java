package com.taoxue.ui.view;

/**
 * Created by Administrator on 2017/3/17.
 */

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.taoxue.MainActivity;
import com.taoxue.R;
import com.taoxue.ui.module.search.SearchLibActivity;
import com.taoxue.ui.module.search.SearchResourcesActivity;
import com.taoxue.ui.module.search.SearchZiYuanKuActivity;
import com.taoxue.ui.module.search.SearchSupplierActivity;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.UtilIntent;


/**
 * 项目名称：translate
 * 实现功能：  翻译详情界面，分享弹出窗口
 * 类名称：PopWinMenu
 * 类描述：(该类的主要功能)
 * 创建人：徐纪伟
 * E-mail: xujiwei558@126.com
 * 创建时间：2014年10月18日 下午4:37:25
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class PopWinMenu extends PopupWindow {
    private Activity activity;
    private View contentView;
    private View onclickView;

    /**
     * @param activity
     * @param onclickView 需要点击出现popwindow的View
     */
    public PopWinMenu(Activity activity, View onclickView) {
        this.activity = activity;
        this.onclickView = onclickView;
        contentView = View.inflate(activity, R.layout.popwin_menu, null);

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(activity.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.popwin_menu, null);
        int h = activity.getWindowManager().getDefaultDisplay().getHeight();
        int w = contentView.getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(contentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimationPreview);

        contentView.findViewById(R.id.layout_pop_one).setOnClickListener(new OnClickLintener(onclickView));
        contentView.findViewById(R.id.layout_pop_two).setOnClickListener(new OnClickLintener(onclickView));
        contentView.findViewById(R.id.layout_pop_three).setOnClickListener(new OnClickLintener(onclickView));
        contentView.findViewById(R.id.layout_pop_four).setOnClickListener(new OnClickLintener(onclickView));
        onclickView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow();
            }
        });
    }


    /**
     * 显示popupWindow
     */
    public void showPopupWindow() {
        if (!this.isShowing()) {

            int x = 0;
            int y = 15;
//            int x=view.getLayoutParams().width;
//            int y=(int)UtilTools.getResourcesDimension(R.dimen.tab_height)/2;
            LogUtils.i(x, y);
            this.showAsDropDown(onclickView, x, y);// 以下拉方式显示popupwindow
        } else {
            this.dismiss();
        }
    }

    class OnClickLintener implements View.OnClickListener {
        View onclickView;

        public OnClickLintener(View onclickView) {
            this.onclickView = onclickView;
        }

        @Override
        public void onClick(View v) {
            showPopupWindow();
            switch (v.getId()) {
                case R.id.layout_pop_one://供应商
                    if (activity instanceof SearchSupplierActivity)break;
                    fjakdjsflsa(SearchSupplierActivity.class);
                    break;
                case R.id.layout_pop_two://资源
                    if (activity instanceof SearchResourcesActivity)break;
                    fjakdjsflsa(SearchResourcesActivity.class);
                    break;
                case R.id.layout_pop_three://数字资源库
                    if (activity instanceof SearchZiYuanKuActivity)break;
                    fjakdjsflsa(SearchZiYuanKuActivity.class);
                    break;
                case R.id.layout_pop_four://图书馆
                    if (activity instanceof SearchLibActivity)break;
                    fjakdjsflsa(SearchLibActivity.class);
                    break;

            }


        }

        /**
         * 调转到下一页并修改动画
         * @param classes
         */
        private void fjakdjsflsa( Class<?> classes) {

            UtilIntent.intentDIYLeftToRight(activity,
                    classes,
                    android.R.anim.fade_in, android.R.anim.fade_out);
            if (!(activity instanceof MainActivity))
                activity.finish();
        }

    }


}