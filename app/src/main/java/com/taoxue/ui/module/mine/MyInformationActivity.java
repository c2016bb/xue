package com.taoxue.ui.module.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.jaiky.imagespickers.ImageConfig;
import com.jaiky.imagespickers.ImageLoader;
import com.jaiky.imagespickers.ImageSelector;
import com.jaiky.imagespickers.ImageSelectorActivity;
import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.module.login.LoginActivity;
import com.taoxue.ui.view.roundedimageview.RoundedImageView;
import com.taoxue.utils.UtilDate;
import com.taoxue.utils.UtilIntent;
import com.taoxue.utils.UtilToast;
import com.taoxue.utils.UtilTools;
import com.taoxue.utils.Utildialog;
import com.taoxue.utils.glide.UtilGlide;
import com.taoxue.utils.permission.UtilPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class MyInformationActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    RoundedImageView ivHead;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_birthday)
    TextView tvBirthday;
    @BindView(R.id.tv_education)
    TextView tvEducation;
    @BindView(R.id.tv_industry)
    TextView tvIndustry;
    @BindView(R.id.tv_occupation)
    TextView tvOccupation;
    private String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information2);

        UtilPermission.needPermission(this, code,
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);//弹出权限提示(一但被取消,只能通过应用设置找回权限)
        ButterKnife.bind(this);
        netWork();
        intTimePickerView();//选择时间
        setViewData(TaoXueApplication.get().getUserModel());
    }

    /**
     * 初始化数据
     *
     * @param bean
     */
    private void setViewData(UserModel bean) {
        tvNickname.setText(bean.getName() + "");
        tvSex.setText(bean.getSex());
        tvBirthday.setText(bean.getBirth_year());
        tvEducation.setText(bean.getEducation() + "");
        tvIndustry.setText(bean.getHangye());//行业
        tvOccupation.setText(bean.getJob());//职业

        UtilGlide.loadImgForIvHead(this, UtilTools.getStringEND(bean.getPhoto()), ivHead);
    }


    @OnClick({R.id.ll_nickname,
            R.id.ll_sex,
            R.id.iv_head,
            R.id.btn_quit,
            R.id.ll_birthday, R.id.ll_education, R.id.ll_industry, R.id.ll_occupation})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.ll_nickname:
                bundle.putString("flag", "昵称");
                bundle.putInt("code", 101);
                UtilIntent.intentResultDIY(this, NickNameActivity.class, bundle, 101);
                break;
            case R.id.ll_sex:
                intSexPickView(0);//选择性别
                break;
            case R.id.iv_head:
                toPhoto();
                break;
            case R.id.ll_birthday:
                pvTime.show();
                break;
            case R.id.ll_education:
                intSexPickView(1);//选择学历

                break;
            case R.id.ll_industry:
                bundle.putInt("code", 102);
                bundle.putString("flag", "行业");
                UtilIntent.intentResultDIY(this, NickNameActivity.class, bundle, 102);
                break;
            case R.id.ll_occupation:
                bundle.putInt("code", 103);
                bundle.putString("flag", "职业");
                UtilIntent.intentResultDIY(this, NickNameActivity.class, bundle, 103);
                break;
            case R.id.btn_quit:
                Utildialog.show(this, "退出登录将删除用户信息,确认退出登录吗?", new Runnable() {
                    @Override
                    public void run() {
                        TaoXueApplication.get().quitLogin();
                        launch(LoginActivity.class);
                        finish();
                    }
                });

                break;
        }
    }

    /**
     * 得到个人信息
     */
    private void netWork() {
        HttpAdapter.getService().info(TaoXueApplication.get().getUserModel().getUser_id())
                .enqueue(new OnResponseListener<BaseResultModel<UserModel>>(this) {
                    @Override
                    protected void onSuccess(BaseResultModel<UserModel> resultModel) {
                        UserModel bean = resultModel.getData();

                        UserModel userModel = TaoXueApplication.get().getUserModel();
                        userModel.setBirth_year(bean.getBirth_year());
                        userModel.setName(bean.getName());
                        userModel.setEducation(bean.getEducation());
                        userModel.setJob(bean.getJob());
                        userModel.setPhoto(bean.getPhoto());
//                        photoUrl = "http://img5.imgtn.bdimg.com/it/u=51610086,419957093&fm=23&gp=0.jpg";
//                        userModel.setPhoto(photoUrl);
                        TaoXueApplication.get().setUserModel(userModel);
                        setViewData(bean);
                    }
                });
    }

    /**
     * 更改个人信息
     */
    private void netWorkInforChange() {

        HttpAdapter.getService().updUserInfo(
                TaoXueApplication.get().getUserModel().getUser_id(),
                tvSex.getText().toString().trim() + "",
                tvBirthday.getText().toString().trim() + "",
                tvIndustry.getText().toString().trim() + "",
                tvOccupation.getText().toString().trim() + "",
                tvEducation.getText().toString().trim() + ""

        )
                .enqueue(new OnResponseListener<BaseResultModel<BaseModel>>(this) {
                    @Override
                    protected void onSuccess(BaseResultModel<BaseModel> UserModelBaseResultModel) {
                    }
                });
    }

    /**
     * 上传照片
     *
     * @param strPath
     * @param runnable
     */
    private void netWorkInforChange(String strPath, final Runnable runnable) {

        RequestBody typeBody = RequestBody.create(MediaType.parse("text"), "2");
        RequestBody suffixBody = RequestBody.create(MediaType.parse("text"), "jpg");
        RequestBody fileBody = RequestBody.create(MediaType.parse("image/png"), new File(strPath));
        RequestBody userBody = RequestBody.create(MediaType.parse("text"), "");
        HttpAdapter.getService().uploadAndSavePhoto(
                TaoXueApplication.get().getUserModel().getUser_id(),
                typeBody, suffixBody, fileBody, userBody).enqueue(new OnResponseListener<BaseResultModel>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
                runnable.run();

            }
        });
    }


    //********************************选择照片*************************************************
    private void toPhoto() {
        if (!UtilPermission.judgePermisson(this, Manifest.permission.CAMERA)) return;
        if (!UtilPermission.judgePermisson(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            return;
        ImageConfig imageConfig
                = new ImageConfig.Builder(new GlideLoader())
                .steepToolBarColor(getResources().getColor(R.color.colorPrimary))
                .titleBgColor(getResources().getColor(R.color.colorPrimary))
                .titleSubmitTextColor(getResources().getColor(R.color.white))
                .titleTextColor(getResources().getColor(R.color.white))
                // (截图默认配置：关闭    比例 1：1    输出分辨率  500*500)
                .crop(1, 1, 500, 500)
                // 开启单选   （默认为多选）
                .singleSelect()
                // 开启拍照功能 （默认关闭）
                .showCamera()
                // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                .filePath("/ImageSelector/Pictures")
                .build();


        ImageSelector.open(this, imageConfig);   // 开启图片选择器
    }

    /**
     * 得到照片返回的结果
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            // Get Image Path List
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);

            for (final String path : pathList) {
                UtilToast.showText(path);

                netWorkInforChange(path, new Runnable() {
                    @Override
                    public void run() {
                        UtilGlide.loadImgForIvHead(mActivity, path, ivHead);
                        netWork();
                    }
                });
            }

        }

        if (requestCode == 101 || requestCode == 102 || requestCode == 103 && null != data) {
            netWork();
        }
    }

    class GlideLoader implements ImageLoader {

        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context)
                    .load(path)
                    .placeholder(com.jaiky.imagespickers.R.mipmap.imageselector_photo)
                    .centerCrop()
                    .into(imageView);
        }

    }
    //********************************选择照片*************************************************

    //*************************************处理权限******************************************
    private final int code = 3;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        UtilPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
    //*************************************处理权限******************************************


    OptionsPickerView pvOptions;

    public void intSexPickView(final int i) {

        final ArrayList<String> options1Items = new ArrayList<>();
        if (i == 0) {
            options1Items.add("男");
            options1Items.add("女");
        }

//        case R.id.ll_industry:
//        intSexPickView(2);//选择行业
//        break;
//        case R.id.ll_occupation:
//        intSexPickView(3);//选择职业
        if (i == 1) {
            options1Items.add("研究生");
            options1Items.add("本科");
            options1Items.add("大专");
            options1Items.add("中专");
            options1Items.add("高中");
            options1Items.add("初中");
            options1Items.add("小学");
        }
        if (i == 2) {
            options1Items.add("个体户");
            options1Items.add("中小型企业");
            options1Items.add("民企");
            options1Items.add("国企");
            options1Items.add("央企");
            options1Items.add("事业单位");
            options1Items.add("公务员");
            options1Items.add("社会闲散人员");
            options1Items.add("学生");
            options1Items.add("群众");
        }
        if (i == 3) {
            options1Items.add("测试1");
            options1Items.add("测试2");
            options1Items.add("测试3");
            options1Items.add("测试4");
            options1Items.add("测试5");
        }
        pvOptions = new OptionsPickerView(this);
        pvOptions.setPicker(options1Items);
        pvOptions.setCyclic(false, true, true);
        pvOptions.setSelectOptions(0);
        pvOptions.setCancelable(true);
        pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                switch (i) {
                    case 0:
                        tvSex.setText(options1Items.get(options1));
                        break;
                    case 1:
                        tvEducation.setText(options1Items.get(options1));
                        break;
                    case 2:
                        tvIndustry.setText(options1Items.get(options1));
                        break;
                    case 3:
                        tvOccupation.setText(options1Items.get(options1));
                        break;
                }

                netWorkInforChange();

            }
        });
        pvOptions.show();

    }

    TimePickerView pvTime;

    private void intTimePickerView() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 67, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);
        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tvBirthday.setText(UtilDate.getSimpleDateFormatTime(date));
                netWorkInforChange();
            }
        });
    }
}
