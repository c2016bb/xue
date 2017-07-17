package com.taoxue.ui.module.classification;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.MyLibBean;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.model.UrlPath;
import com.taoxue.ui.module.classification.Image.tabs.ZTabLayout;
import com.taoxue.ui.module.classification.vpFragment.BookDeatilFragment;
import com.taoxue.ui.module.classification.vpFragment.CommentFragment;
import com.taoxue.ui.module.classification.vpFragment.ViewPagerAdapter;
import com.taoxue.ui.module.home.PlayActivity;
import com.taoxue.ui.view.CommentDialog;
import com.taoxue.ui.view.FullCommonDialog;
import com.taoxue.ui.view.StarBar;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;
import com.taoxue.utils.OpenFileUtils;
import com.taoxue.utils.SPUtil;
import com.taoxue.utils.glide.UtilGlide;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

/**
 * Created by User on 2017/4/10.
 */

public class BookIntroductionActivity extends BaseActivity {

    @BindView(R.id.introduction_topbar)
    TopBar introductionTopbar;
    @BindView(R.id.book_image_iv)
    ImageView bookImageIv;
    @BindView(R.id.book_title_tv)
    TextView bookTitleTv;
    @BindView(R.id.book_start_read_btn)
    Button startReadBtn;
    @BindView(R.id.book_jieshao_ll)
    LinearLayout bookJieshaoLl;
    @BindView(R.id.book_tablayout)
    ZTabLayout bookTablayout;
    @BindView(R.id.boo_viewpager)
    ViewPager booViewpager;
    @BindView(R.id.book_starbar_pinfen)
    StarBar bookStarbar;
    @BindView(R.id.book_pingfen_coun_tv)
    TextView bookPingfenCounTv;
    @BindView(R.id.book_supplier_tv)
    TextView bookSupplierTv;
    @BindView(R.id.book_total_reading_tv)
    TextView bookTotalReadingTv;
    @BindView(R.id.book_collection_iv)
    ImageView bookCollectionIv;
    @BindView(R.id.book_collection_coumn_tv)
    TextView bookCollectionCoumnTv;
    @BindView(R.id.book_giv_thumb_iv)
    ImageView bookGivThumbIv;
    @BindView(R.id.book_giv_thumb_coumn_tv)
    TextView bookGivThumbCoumnTv;
    //    @BindView(R.id.book_content_tv)
//    LimitLineTextView bookContentTv;      //sedfdvdv
//    @BindView(R.id.commit_view)
//    CommitContentView commitView;
//    @BindView(R.id.book_starbar)
//    StarBar starBar;
    //    @BindView(R.id.book_sv)
//    ScrollView bookSv;

    //    @BindView(R.id.book_ll)
//    LinearLayout bookLl;
//    @BindView(R.id.top_fl)
//    FrameLayout topFl;
//    @BindView(R.id.book_pingfen_coun_tv)
//    TextView bookPingfenCounTv;
//    @BindView(R.id.book_stickynavlayout_indicator)
//    SimpleViewPagerIndicator bookIndicator;
//    @BindView(R.id.book_stickynavlayout_viewpager)
//    ViewPager bookViewpager;

    //    @BindView(R.id.book_author_tv)
    TextView bookAuthorTv;//作者
//    //    @BindView(R.id.book_press_tv)
//    TextView bookPressTv;//出版社
//
//    //    @BindView(R.id.publication_date_tv)
//    TextView publicationDateTv;//出版日期
//    TextView bookISBNTv;//书号
//    TextView bookSuitYearTv;//适合年龄阅读


    RecyclerView commitView;


    private MyLibBean myLibBean;//我的图书馆信息
    private String id;
    private ResourceModel data;
    private Context context = this;
    private boolean isResource = false;
    private File mFile;
    //topbar的 title 是否设置了本文标题  默认显示图书介绍
    boolean isSetTile = false;
    /**
     * 文件路径
     */
    private List<UrlModel> urls;

    private String[] mTitles = new String[]{"详情", "评论"};
    private CommentDialog dialog;
    private String fileType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_introduction);
        ButterKnife.bind(this);
        getIntentData();//得到intent中的数据
        //    /判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            //路径  /storage/emulated/0/Download
            mFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            LogUtils.D("mFile--->" + mFile.getAbsolutePath());
        }
//        //设置向右滑动退出
        CommitContent.setColorLeftSilde(this);

    }
    private void initPager() {
        mTitles[1]="评论"+"("+data.getComment_num()+")";
        ViewPagerAdapter vpAdapter=new ViewPagerAdapter(getSupportFragmentManager(),mTitles);
        BookDeatilFragment bdf=new BookDeatilFragment();
        bdf.setArgumentsObj(data);
        vpAdapter.addFrament(bdf);
        CommentFragment cf=new CommentFragment();
        cf.setArgumentsObj(data);
        LogUtils.D("data--->"+data.toString());
        vpAdapter.addFrament(cf);
//        CommentFragment cf1=new CommentFragment();
//        cf1.setArgumentsObj(data);
//        LogUtils.D("data--->"+data.toString());
//        vpAdapter.addFrament(cf1);
        bookTablayout.addTab(bookTablayout.newTab().setText(mTitles[0]));
        bookTablayout.addTab(bookTablayout.newTab().setText(mTitles[1]));

        booViewpager.setAdapter(vpAdapter);
        bookTablayout.setupWithViewPager(booViewpager);
        booViewpager.setCurrentItem(0);

    }

    //获取文件路径
    private void initFileUrl(String resource_id, String user_id) {
        Call<BaseListModel<UrlModel>> call = HttpAdapter.getService().getFileUrl(resource_id, user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<UrlModel>>() {
            @Override
            protected void onSuccess(BaseListModel<UrlModel> model) {
                if (model.getCode()==1&&null!=model.getItem()&&!"[]".equals(model.getItem()+"")&&model.getItem().size()>0){
                    UrlPath urlBean=new UrlPath();
                    urlBean.setUrlModel(model.getItem());
                    toActivityByFileType(urlBean);
                }else{
                    showToast("未获取到文件");
                }

            }
        });
    }






    //根据文件类型跳转至不同的activity
    private void toActivityByFileType(UrlPath urlBean){
        if (fileType.equals("audio")){
            launch(AudioPlayActivity.class,urlBean,data);
        }else if(fileType.equals("image")){
             launch(ImageScanActivity.class,urlBean,data);
        }else if(fileType.equals("video")){
            launch(PlayActivity.class,urlBean,data);
        }else if (fileType.equals("doc")){
            launch(DocReadActivity.class,urlBean,data);
        }
    }


    @OnClick({R.id.book_collection_iv, R.id.book_giv_thumb_iv,R.id.book_start_read_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.book_collection_iv:
                break;
            case R.id.book_giv_thumb_iv:
                break;
            case R.id.book_start_read_btn:
                final String user_id = CommitContent.isLogin(BookIntroductionActivity.this);
                if (null != user_id) {
                    boolean isSeclected=(boolean)SPUtil.get(SPUtil.NO_SECLECT_PAY_KEY,false);
                if (isSeclected){
                    initFileUrl(data.getResource_id(),user_id);
                }else {

                    dialog=new CommentDialog(this, R.layout.my_lib_choice, new FullCommonDialog.OnSureClickListener() {
                        @Override
                        public void onClick(View v) {
                            LogUtils.D("确定"+"view-->"+v.toString());
                            dialog.dismiss();
                            initFileUrl(data.getResource_id(),user_id);
                        }
                    });
                    dialog.show();
                }
                }
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(id)) {
            initResourceDetail(id);
//            if (type.equals("doc")) {
//                fileType="doc";
//                initResourceDocDetail(id);
//            } else if (type.equals("image")) {
//                fileType="image";
//                initResourceImageDetail(id);
//            }else if(type.equals("audio")){
//                fileType="audio";
//                initResourceAudioDetail(id);
//            }
        } else {
            showToast("当前无资源");
        }
    }

    /**
     * 得到intent中的数据
     *
     * @return
     */
    private void getIntentData() {
        id = (String) getIntentKey1();
        LogUtils.D("id---->"+id);



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //当版本大于23时
//            bookSv.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
//
////                    LogUtils.D("introductionTopbar.getMeasuredHeight()-->"+introductionTopbar.getMeasuredHeight()+"+introductionTopbar.getTitleHeight()--->"+introductionTopbar.getTitleHeight());
////                      LogUtils.D("bookJieshaoLl.getMeasuredHeight()--->"+bookJieshaoLl.getMeasuredHeight()+"introductionTopbar.getMeasuredHeight()/2+introductionTopbar.getTitleHeight()/2--->"+introductionTopbar.getMeasuredHeight()/2+introductionTopbar.getTitleHeight()/2+"ScrollY--->"+scrollY);
//                    if (!isSetTile) {
//                        if (bookJieshaoLl.getMeasuredHeight() < scrollY) {
//                            LogUtils.D("scrollY--->" + scrollY);
//                            if (topTitle == null) {
//                                height = introductionTopbar.getTitleHeight();
//                                topTitle = introductionTopbar.getTopTitle();
//                                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, introductionTopbar.getMeasuredHeight() + height);
//                                topTitle.setLayoutParams(params);
//                                topTitle.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
//                            }
//                            topTitle.setText(bookTitleTv.getText().toString() + "");
//                            topTitle.scrollTo(0, scrollY - bookJieshaoLl.getMeasuredHeight());
//
//                            if ((scrollY - bookJieshaoLl.getMeasuredHeight()) >= (introductionTopbar.getMeasuredHeight() / 2 + height / 2)) {
//                                topTitle.scrollTo(0, introductionTopbar.getMeasuredHeight() / 2 + height / 2);
//                                LogUtils.D("true");
//                                isSetTile = true;
//                            }
//                        }
//                    } else {
//                        if (bookJieshaoLl.getMeasuredHeight() > scrollY) {
//                            LogUtils.D("bookJieshaoLl.getMeasuredHeight()--->" + bookJieshaoLl.getMeasuredHeight() + "scrollY--->" + scrollY);
//                            topTitle.scrollTo(0, scrollY - bookJieshaoLl.getMeasuredHeight() + introductionTopbar.getMeasuredHeight() / 2 + height / 2);
//                            if ((bookJieshaoLl.getMeasuredHeight() - scrollY) >= (introductionTopbar.getMeasuredHeight() / 2 + height / 2)) {
//                                LogUtils.D("bookJieshaoLl.getMeasuredHeight()--->" + "introductionTopbar.getMeasuredHeight()/2+height/2--->" + introductionTopbar.getMeasuredHeight() / 2 + height / 2);
//                                topTitle.setText(getResources().getString(R.string.book_jieshao));
//                                topTitle.scrollTo(0, introductionTopbar.getMeasuredHeight() / 2 + height / 2);
//                                isSetTile = false;
//                            }
//
//                        }
//                    }
//                }
//            });
//        }
    }


    //获取资源详情页
    private void initResourceDetail(String id) {
        isResource = false;
        Call<BaseResultModel<ResourceModel>> call = HttpAdapter.getService().getResourceDetail(id);
        call.enqueue(new OnResponseListener<BaseResultModel<ResourceModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel<ResourceModel> model) {
                if (model.getCode() == 1 && null != model.getData() &&! "[]".equals(model.getData())) {
                    data = model.getData();
                    initView();
                } else {
                    showToast("无法获取当前资源");
                }
            }
        });
    }

    private void initView() {
           fileType=data.getFile_type();

        if (fileType.equals("audio")){
            introductionTopbar.setTitle("音频介绍");
        }else if(fileType.equals("image")){
            introductionTopbar.setTitle("图片介绍");
        }else if(fileType.equals("video")){
            introductionTopbar.setTitle("视频介绍");
        }else if (fileType.equals("doc")){
            introductionTopbar.setTitle("图书介绍");
        }else{
            introductionTopbar.setTitle("其他");

        }



            isResource = true;
//        Html.fromHtml(data.getCatalog())
            bookTitleTv.setText(nullToSting(data.getResource_name()));
//        <p font='red'>这是测试内容</p>

//
            UtilGlide.loadImg(this, data.getResource_picture(), bookImageIv);
//

            bookPingfenCounTv.setText(nullToSting(data.getScore_num()));
            bookStarbar.setStarMark(Float.parseFloat(data.getScore_num()));
            bookStarbar.setChangMark(false);

            bookCollectionCoumnTv.setText(nullToSting(data.getCollection_num()));
            bookGivThumbCoumnTv.setText(nullToSting(data.getPraise_num()));
//            bookSupplierTv.setText(nullToSting(data.get));book_total_reading_tv
            bookTotalReadingTv.setText(nullToSting(data.getRead_num()));
            //将当前数据传递  点击后收藏
//            addBookBtn.setData(data);
//                        bookSuitYearTv.setText(CommitContent.nullToSting(data.get));
            //将当前数据传递到自定义评论控件中
//            commitView.setModel(data);
            initPager();
    }

    //获取文件
    private void initFile(String user_id, String resource_id) {
        Call<BaseListModel<UrlModel>> call = HttpAdapter.getService().getFileUrl(resource_id, user_id);
        call.enqueue(new OnResponseNoDialogListener<BaseListModel<UrlModel>>() {
            @Override
            protected void onSuccess(BaseListModel<UrlModel> model) {
                if (model.getCode() == 1) {
                    urls = null;
                    urls = model.getItem();
                    if (null != urls) {
                        UrlModel um = urls.get(0);
                        LogUtils.D("urls.get(0)--->" + urls.get(0).toString());
                        LogUtils.D("um.getUrl()--->" + um.getUrl());
                        LogUtils.D("um.getResource_name()--->" + um.getResource_name());

                        if (null != data.getFile_type() && data.getFile_type().equals("doc")) {
                            LogUtils.D("urls.get(0).getUrl()--->" + urls.get(0).getUrl());
                            startRead(urls.get(0).getUrl());
                        } else {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            UrlPath url = new UrlPath();
                            url.setUrlModel(urls);
                            bundle.putSerializable(CommitContent.IMAGE_SCAN, url);
                            intent.putExtras(bundle);
                            launch(ImageScanActivity.class, intent);
                        }
                    }
                }
            }
        });
    }

    private void startRead(String url) {
        LogUtils.D("url-----------f>" + url);
        //判断文件的格式
        if (TextUtils.isEmpty(url)) {
            showToast("当前无文件");
        } else {
            if (data.getFile_type() != null && !data.getFile_type().equals("doc")) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                UrlPath url1 = new UrlPath();
                url1.setUrlModel(urls);
                bundle.putSerializable(CommitContent.IMAGE_SCAN, url1);
                intent.putExtras(bundle);
                launch(ImageScanActivity.class, intent);
                return;
            }
            String s = url.substring(url.lastIndexOf("."));//后缀名
//            if (s.equals(".pdf")){
//                LogUtils.D("pdf跳转");
//                launch(PdfScanActivity.class,url,CommitContent.PDF_SCAN);
//                return;
//            }
            LogUtils.D("s-->" + s.toString());
            String fileName = url.substring(url.lastIndexOf("/") + 1);
            LogUtils.D("fileName-->" + fileName.toString());
            if (null == s || null == fileName) {
                showToast("当前无文件");
            } else {
                if (mFile != null) {
                    File file = searchFileByFilePath(fileName, s);
                    if (file != null) {
                        OpenFileUtils.openFile(context, s, file);
                    } else {
                        LogUtils.D("没有找到文件");
//                           String filePath = mFile + "/" + fileName;
                        LogUtils.D("url--->" + url);
                        String urlPath = url.startsWith("http") ? url : HttpAdapter.BASE_URL.substring(10, HttpAdapter.BASE_URL.indexOf("/")) + url;
                        OpenFileUtils.downloadfile(context, urlPath, s, fileName);
                    }
                } else {
                    showToast("请插入SD卡");
                }
            }
        }

    }

    //根据文件后缀名查找文件是否存在
    private File searchFileByFilePath(final String fileName, final String s) {
        LogUtils.D("fileName--->" + fileName);
//    /判断SD卡是否存在
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            File[] files = mFile.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    return filename.endsWith(s);//根据后缀名
                }
            });

            if (files.length > 0) {
                for (File file : files) {
                    LogUtils.D("files--->" + file.getName());
                    if (fileName.equals(file.getName())) {
                        LogUtils.D("找到文件");
                        return file;
                    }
                }
            }
        }
        ;
        return null;
    }


    private void openReader(String user_id) {
        if (isResource) {
            if (urls != null && !urls.equals("")) {
                LogUtils.D("urls.get(0).getUrl() 00000--->" + urls.get(0).getUrl());
                startRead(urls.get(0).getUrl());
            } else {
                if (!TextUtils.isEmpty(data.getResource_id())) {
                    initFile(user_id, data.getResource_id());
                    LogUtils.I("Resource_id()--->" + data.getResource_id());
                } else {
                    showToast("当前无资源");
                }
            }
        } else {
            showToast("当前无可显示的文件");
        }


    }


}
