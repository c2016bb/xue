package com.taoxue.ui.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.taoxue.R;
import com.taoxue.app.TaoXueApplication;
import com.taoxue.base.BaseActivity;
import com.taoxue.http.HttpAdapter;
import com.taoxue.http.OnResponseListener;
import com.taoxue.http.OnResponseNoDialogListener;
import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.BindSuceessInfo;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.module.classification.CommitContent;
import com.taoxue.ui.module.mine.SeccessBindCardActivity;
import com.taoxue.ui.view.TopBar;
import com.taoxue.utils.LogUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;


public class ReaderCardActivity extends BaseActivity {

    @BindView(R.id.reader_skip_tv)
    TextView readerSkipTv;
    @BindView(R.id.topbar)
    TopBar topbar;
    @BindView(R.id.reader_code)
    EditText readerCode;
    @BindView(R.id.reader_codeBtn)
    TextView readerCodeBtn;
    @BindView(R.id.reader_xing_Edt)
    EditText readerXingEdt;
    @BindView(R.id.reader_BTN)
    RelativeLayout readerBTN;
    private ReaderCodeModel readerCodeModel; //读者证验证请求结果信息
    /**
     * 注册时用户名
     */
    private  String name;
    /**
     * 返回结果码
     */
    private  int requestCode=1;

    private  String mReaderCode;
    /**
     * 姓
     */
    private  String xing;


    private String user_id;

    private  List<MyLibInfo>myLibInfos;

    //判断是否从我的图书进入验证读者证的 默认false;
    private  boolean isReaderCard=true;

    private  boolean isVerificationReadCard=false;//验证读者证是否成功

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_reader_activity);
        ButterKnife.bind(this);
        getIntentData();
        onInit();
        topbar.setBackButtonListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isReaderCard) {
                    setResult(CommitContent.UNGETREADERID, "");
                }
                ((BaseActivity)ReaderCardActivity.this).onBackPressed();
            }
        });
        readerBTN.setEnabled(false);
    }
    @Override
    protected void onInit() {
        super.onInit();
        xing = readerXingEdt.getText().toString();
        mReaderCode = readerCode.getText().toString();

      TextChange textChange=new TextChange();
        readerXingEdt.addTextChangedListener(textChange);
        readerCode.addTextChangedListener(textChange);
    }
    //EditText的监听器
    class TextChange implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isReaderCard){
                    //当读者证号中修改
                    readerCodeBtn.setEnabled(true);
            }
            if(readerCode.length()>0&&
                    readerXingEdt.length()>0){
//                LogUtils.
                readerBTN.setFocusable(true);
                readerBTN.setEnabled(true);
//                registerBTN.setPressed(true);
            }else{
                readerBTN.setEnabled(false);
            }
        }
    }



    private void setResult(String reader_card_id,String cgs_id){
        Intent intent=new Intent();
        if (!TextUtils.isEmpty(reader_card_id)) {
            Bundle bundle = new Bundle();
            bundle.putString("reader_card_id", reader_card_id);
            bundle.putString("cgs_id", cgs_id);
            intent.putExtras(bundle);
        }
        this.setResult(requestCode,intent);
        finish();
    }


    /**
     * 获取传递用户名
     */
    private  void getIntentData(){
        Intent intent=getIntent();
             name=intent.getStringExtra(CommitContent.REGISTER_READER);
        if (name!=null) {
            isReaderCard = false;
        }else if (CommitContent.MY_LIB_CANSHU.equals(intent.getStringExtra(CommitContent.MY_LIB))){
            LogUtils.D("获取参数");
            readerSkipTv.setVisibility(View.GONE);
            isReaderCard=true;
//            String user_id=CommitContent.isLogin(this);
//            if (user_id!=null){
//                queryMyLib(user_id);
//            }
        }
        LogUtils.D("isReaderCard--->"+isReaderCard);
}
    /**
     * 验证读者证
     *
     * @param mReaderCode
     */
    private void initReaderCodeByUser(String user_id,String mReaderCode) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(mReaderCode);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(this) {
            @Override
            protected void onSuccess(BaseResultModel baseResultModel) {
//                             showToast(baseResultModel.getMsg());
                readerCodeModel = (ReaderCodeModel) baseResultModel.getData();
                LogUtils.D("readerCodeModel.getRealname()" + readerCodeModel.getRealname()+",name-->"+name);
                if (!TextUtils.isEmpty(name) & readerCodeModel != null & !readerCodeModel.equals("")) {
                    //判断注册时的姓名 与通过读者证获取的姓名是否相同
                    if (name.equals(readerCodeModel.getRealname())) {
//                                     setResult(readerCodeModel.getReader_id(),readerCodeModel.getCgs_id());
                        readerXingEdt.setText(readerCodeModel.getRealname());
                        readerXingEdt.setFocusable(false);
                    } else {
                        showToast("您输入的注册真实性别名与读者证上真实性名不一致，请输入您的姓");
//                                     readerCode.setFocusable(false);
                        readerXingEdt.setFocusable(true);
                    }
                }
            }
        });
    }


    //查询我的图书馆信息
    private  void queryMyLib(String user_id){
        Call<BaseListModel<MyLibInfo>> call = HttpAdapter.getService().queryMyLibInfo(user_id);
       call.enqueue(new OnResponseNoDialogListener<BaseListModel<MyLibInfo>>() {
           @Override
           protected void onSuccess(BaseListModel<MyLibInfo> myLibInfoBaseListModel) {
               if (myLibInfoBaseListModel!=null){
                   if (myLibInfoBaseListModel.getItem().get(0).getReader_card_id()!=null){
                    showToast("您已绑定过读者证，请返回");
                       readerCode.setText(CommitContent.nullToSting(myLibInfoBaseListModel.getItem().get(0).getReader_card_id()));
                       readerCodeBtn.setEnabled(false);
//                       myLibInfos=myLibInfoBaseListModel.getItem();
                   }else{
                    myLibInfos=myLibInfoBaseListModel.getItem();
                   }
               }
           }
       });
    }
    //绑定读者证    "1555232","125222","1201431098" 测试时使用
    private  void bindReaderCardIdByUserId(){
        Call<CheckSignModel> call = HttpAdapter.getService().bindReaderCardIdByUserId(user_id,myLibInfos.get(0).getCgs_id(),mReaderCode);
       call.enqueue(new OnResponseListener<CheckSignModel>(this) {
           @Override
           protected void onSuccess(CheckSignModel checkSignModel) {
               showToast("绑定"+checkSignModel.getMsg());
               isVerificationReadCard=true;
               readerXingEdt.setText(CommitContent.nullToSting(TaoXueApplication.get().getUserModel().getName()));
           }
       });
    }

    /**
     * 验证读者证
     *
     * @param mReaderCode
     */
    private void initReaderCode(String mReaderCode) {
        Call<BaseResultModel<ReaderCodeModel>> call = HttpAdapter.getService().testReaderCode(mReaderCode);
        call.enqueue(new OnResponseListener<BaseResultModel<ReaderCodeModel>>(this) {
                         @Override
                         protected void onSuccess(BaseResultModel baseResultModel) {
//                             showToast(baseResultModel.getMsg());
                             readerCodeModel = (ReaderCodeModel) baseResultModel.getData();
                             LogUtils.D("readerCodeModel.getRealname()" + readerCodeModel.getRealname()+",name-->"+name);
                             if (!TextUtils.isEmpty(name) & readerCodeModel != null & !readerCodeModel.equals("")) {
                                 //判断注册时的姓名 与通过读者证获取的姓名是否相同
                                 if (name.equals(readerCodeModel.getRealname())) {
//                                     setResult(readerCodeModel.getReader_id(),readerCodeModel.getCgs_id());
                                     readerXingEdt.setText(readerCodeModel.getRealname());
                                     isVerificationReadCard=true;
                                     readerXingEdt.setFocusable(false);
                                 } else {
                                     showToast("您输入的注册真实性别名与读者证上真实性名不一致，请输入您的姓");
//                                     readerCode.setFocusable(false);
                                     readerXingEdt.setFocusable(true);
                                 }
                             }
                         }
                     });
            }
    @OnClick({R.id.reader_skip_tv, R.id.reader_BTN,R.id.reader_codeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.reader_skip_tv:
                LogUtils.D("点击了跳转");
                    setResult(CommitContent.ZHIJIEZHUCE, "0");
                break;
            case R.id.reader_BTN:
                LogUtils.I("点击了确定");
                if (!isVerificationReadCard){
                    showToast("您的读者证未绑定成功,请先绑定读者证");
                    return;
                }
            xing =readerXingEdt.getText().toString();

                if (isReaderCard){
                    if (myLibInfos!=null) {
                        BindSuceessInfo info = new BindSuceessInfo();
                        info.setReaderName(TaoXueApplication.get().getUserModel().getName());
                        info.setMyLib_name(myLibInfos.get(0).getName());
                        info.setReader_card_id(mReaderCode);
                        launch(SeccessBindCardActivity.class, info, CommitContent.BIND_READER_CARD_ID_SUCCESS);
                        finish();
                    }else{
                        showToast("未获取信息");
                    }
                    return;
                }
                if(!TextUtils.isEmpty(xing)){
                        LogUtils.D("通过读者证获取的姓-->"+readerCodeModel.getRealname().trim().substring(0,1));
                        if (xing.trim().substring(0,1).equals(readerCodeModel.getRealname().trim().substring(0,1))){
                                setResult(readerCodeModel.getReader_id(), readerCodeModel.getCgs_id());
                        }else{
                            showToast("您输入的姓不正确，请重新输入");
                        }
                }else{
                    showToast("请输入您的姓");
                }
                break;
            case  R.id.reader_codeBtn:

                    mReaderCode = readerCode.getText().toString();
                if (!TextUtils.isEmpty(mReaderCode)) {
                  showToast("请先输入读者证号");
                    return;
                };
                if (isReaderCard){
                  bindReaderCardIdByUserId();
                }else {
                        initReaderCode(mReaderCode);
                }
                break;
        }
    }
}
