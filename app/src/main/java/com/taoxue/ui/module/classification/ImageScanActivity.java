package com.taoxue.ui.module.classification;

import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.module.classification.Image.ImagePhotoView;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.CommonUtils;
import com.taoxue.utils.DownLoadFile;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.glide.UtilGlide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageScanActivity extends BaseActivity {
    @BindView(R.id.iamge_viewpager)
    ViewPager imageViewpager;
    @BindView(R.id.tipsBox)
    LinearLayout tipsBox;
    @BindView(R.id.introduction_topbar)
    TopBar topbar;

    @BindView(R.id.image_comment_Content_et)
    EditText imageCommentContentEt;
    @BindView(R.id.image_give_thumb_iv)
    ImageView imageGiveThumbIv;
    @BindView(R.id.image_ping_lun_iv)
    ImageView imagePingLunIv;
    @BindView(R.id.image_collection_iv)
    ImageView imageCollectionIv;
    @BindView(R.id.commit_ll)
    LinearLayout commitLl;
    @BindView(R.id.image_coment_ll)
    LinearLayout imageComentLl;
    @BindView(R.id.activity_image_scan)
    FrameLayout activityImageScan;

    private UrlPath urlPath;
    ImageView[] tips;//图片阅读页点的集合
    int currentPage = 0;
    //是否显示topbar,默认不显示
    boolean isShowTopBar = true;
    TranslateAnimation showAnimation;
    TranslateAnimation hideAnimation;
    TranslateAnimation hideCommentAnimation;
    TranslateAnimation showCommentAnimation;

    //dialog 的项目内容
    final String items[] = {"分享给朋友", "收藏", "保存图片"};//长按内容显示的dialog
    private SparseArray<ImagePhotoView> mViews;
    private ImagePhotoView im;
    private ResourceModel data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scan);
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        topbar.setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        mViews = new SparseArray<>();
        getIntentValue();
        imageCommentContentEt.setFocusable(false);
    }

    //获取intent传的值
    private void getIntentValue() {
        urlPath = (UrlPath) getIntentKey1();
        data = (ResourceModel) getIntentKey2();
        if (urlPath != null) {
            intView();
        } else {
            showToast("无图片资源");
        }
    }

    //初始化点
    private void initTip() {
        tips = new ImageView[urlPath.getUrlModel().size()];
        for (int i = 0; i < tips.length; i++) {
            ImageView img = new ImageView(this);
            img.setLayoutParams(new LinearLayout.LayoutParams(10, 10));
            tips[i] = img;
            if (i == 0) {
                img.setBackgroundResource(R.mipmap.u1735);
            } else {
                img.setBackgroundResource(R.mipmap.u1733);
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            params.leftMargin = 5;
            params.rightMargin = 5;
            tipsBox.addView(img, params);
        }
    }


    private void initViewPager() {

        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return urlPath.getUrlModel().size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object o) {
                //container.removeViewAt(position);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (position >= mViews.size()) {
                    im = new ImagePhotoView(ImageScanActivity.this);
                    mViews.put(position, im);
                    container.addView(im);
                } else {
                    im = mViews.get(position);
                }
                intImage(im, position);
//              im.setImageResource(urlPath.getUrlModel().get(position).getUrl());
                return im;
            }
        };

        imageViewpager.setAdapter(adapter);

        if (urlPath.getUrlModel().size() > 1) {
            //更改当前tip
            imageViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrollStateChanged(int arg0) {
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    tips[currentPage].setBackgroundResource(R.mipmap.u1733);
                    currentPage = position;
                    tips[position].setBackgroundResource(R.mipmap.u1735);
                }
            });
        }
    }


    @Override
    protected void onDestroy() {
        DownLoadFile.unregister();
        super.onDestroy();
    }

    //动画显示topBar
    private void showTopBar() {
        if (showAnimation == null) {
            showAnimation = new TranslateAnimation(0, 0, -topbar.getMeasuredHeight(), 0);
        }
        showAnimation.setDuration(350);
        topbar.startAnimation(showAnimation);
        topbar.setVisibility(View.VISIBLE);
        isShowTopBar = true;
    }

    //动画隐藏toprBar
    private void hideTopBar() {
        if (hideAnimation == null) {
            hideAnimation = new TranslateAnimation(0, 0, 0, -topbar.getMeasuredHeight());
        }
        hideAnimation.setDuration(350);
        topbar.startAnimation(hideAnimation);
        topbar.setVisibility(View.GONE);
        isShowTopBar = false;
    }


    private void hideComment() {
        if (hideCommentAnimation == null) {
            hideCommentAnimation = new TranslateAnimation(0, 0, 0, imageComentLl.getMeasuredHeight());
        }
        hideCommentAnimation.setDuration(350);
        imageComentLl.startAnimation(hideCommentAnimation);
        imageComentLl.setVisibility(View.GONE);
    }

    private void showComment() {
        if (showCommentAnimation == null) {
            showCommentAnimation = new TranslateAnimation(0, 0, imageComentLl.getMeasuredHeight(), 0);
        }
        showCommentAnimation.setDuration(350);
        imageComentLl.startAnimation(showCommentAnimation);
        imageComentLl.setVisibility(View.VISIBLE);
    }


    private void intImage(ImagePhotoView photoView, int position) {
        UtilGlide.loadIntoUseFitWidth(this, urlPath.getUrlModel().get(position).getUrl(), photoView);
//        ImageLoaderUtil.displayImageByWid(urlPath.getUrlModel().get(position).getUrl(), photoView);
        photoView.enable();
        photoView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isShowTopBar) {
                    showTopBar();
                    showComment();
                } else {
                    hideTopBar();
                    hideComment();
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogUtils.D("changan");
                createListDialog();
                return true;
            }
        });

    }


    private void createListDialog() {

        //dialog参数设置
        AlertDialog.Builder builder = new AlertDialog.Builder(this);  //先得到构造器
//     builder.setTitle("提示"); //设置标题
        //builder.setMessage("是否确认退出?"); //设置内容
//     builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        //设置列表显示，注意设置了列表显示就不要设置builder.setMessage()了，否则列表不起作用。
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LogUtils.D("点击了" + which);
                diaologItemsClick(which);
            }
        });
        AlertDialog dialog = builder.create();
//     WindowManager.LayoutParams params =
//             dialog.getWindow().getAttributes();
//     params.width = 100;
//     params.height = 200 ;
//     dialog.getWindow().setAttributes(params);  //可设置dialog的大小
        dialog.show();
    }

    private void diaologItemsClick(int which) {
        switch (which) {
            case 0:
                showToast("暂未实现此功能");
                break;
            case 1:
                showToast("暂未实现此功能");
                break;
            case 2:
                DownLoadFile.downloadImage(this, urlPath.getUrlModel().get(currentPage).getUrl());
                break;
        }
    }

    private void intView() {
        //初始化 提示点点
        if (urlPath.getUrlModel().size() > 1) {
            initTip();
        }
        initViewPager();
    }

    private PopupWindow popupWindow;
    private View contentView;
    EditText commentContentEt;
    StarBar starBar;
    RelativeLayout fabuRl;

    private void commitComment() {
        if (popupWindow != null) {
            return;
        }
        contentView = LayoutInflater.from(this).inflate(
                R.layout.ui_comment, null);
        commentContentEt = (EditText) contentView.findViewById(R.id.comment_Content_et);
        fabuRl = (RelativeLayout) contentView.findViewById(R.id.coment_commit_ll);
        starBar = (StarBar) contentView.findViewById(R.id.comment_start_bar);
//        ButterKnife.bind(this, contentView);
        LogUtils.D("加载布局");
        //设置弹出框的宽度和高度
        popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
       CommitContent.addTextChange(this,commentContentEt);
        popupWindow.setFocusable(true);// 取得焦点
        //软键盘不会挡着popupwindow
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

//        //注意  要是点击外部空白处弹框消息  那么必须给弹框设置一个背景色  不然是不起作用的
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        //点击外部消失
        popupWindow.setOutsideTouchable(true);
        //设置可以点击
        popupWindow.setTouchable(true);

        fabuRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.D("添加评论");
                addCommitContent();
            }
        });
        starBar.setOnStarChangeListener(new StarBar.OnStarChangeListener() {
            @Override
            public void onStarChange(float mark) {

            }
        });


//        让底部的菜单栏不被软件盘顶上来要在AndroidManifest.xml中设置Android:windowSoftInputMode=”adjustResize”
//        //进入退出的动画
//        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style);

//        // 按下android回退物理键 PopipWindow消失解决
//        bookCommitLv.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//                    if (popupWindow != null && popupWindow.isShowing()) {
//                        popupWindow.dismiss();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
    }

    //添加评论
    private void addCommitContent() {
        LogUtils.D("发送评论请求");
        String user_id = CommitContent.isLogin(this);
        String comment = commentContentEt.getText().toString();
        if (TextUtils.isEmpty(comment)) {
            showToast("请先输入内容");
            return;
        }
        if (user_id != null) {
            HttpRequest.addCommitContent(user_id, data.getResource_id(), comment, new HttpRequest.RequestCallBack() {
                @Override
                public void onSuccess(String msg) {
                    showToast("评论成功");
                    popupWindow.dismiss();
                     commentContentEt.setText("");

                }

                @Override
                public void onRequested(String msg) {
                    showToast("您已经评论过了，本评论不显示");
                    popupWindow.dismiss();
                    commentContentEt.setText("");
                }

                @Override
                public void onUnSuccess(String msg) {
                   showToast(msg);
                }
            });
        }
    }




    Handler handler=new Handler();


    private void popupInputMethodWindow() { //弹出软键盘
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CommonUtils.showKeyboard(commentContentEt);
//                if (null==imm) {
//                    imm = (InputMethodManager) commentContentEt.getContext().getSystemService(Service.INPUT_METHOD_SERVICE);
//                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
//                }
            }
        }, 0);
    }

    @OnClick({R.id.image_comment_Content_et, R.id.image_give_thumb_iv, R.id.image_ping_lun_iv, R.id.image_collection_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_comment_Content_et:
                commitComment();
                popupWindow.showAtLocation(activityImageScan,Gravity.BOTTOM,0,0);
                commentContentEt.setFocusable(true);
                popupInputMethodWindow();//弹出输入法
                break;
            case R.id.image_give_thumb_iv:

                break;
            case R.id.image_ping_lun_iv:
                if (data != null) {
                    launch(CommentActivity.class, data);
                }
                break;
            case R.id.image_collection_iv:


                break;
        }
    }
}
