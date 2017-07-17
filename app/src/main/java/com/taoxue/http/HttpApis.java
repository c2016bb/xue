package com.taoxue.http;

import com.taoxue.ui.model.BaseListModel;
import com.taoxue.ui.model.BaseModel;
import com.taoxue.ui.model.BasePageModel;
import com.taoxue.ui.model.BaseResultModel;
import com.taoxue.ui.model.CheckSignModel;
import com.taoxue.ui.model.ComentModel;
import com.taoxue.ui.model.CommitPageModel;
import com.taoxue.ui.model.FragCollectionBean;
import com.taoxue.ui.model.GysDataBean;
import com.taoxue.ui.model.MyLibInfo;
import com.taoxue.ui.model.PageModel;
import com.taoxue.ui.model.ReaderCodeModel;
import com.taoxue.ui.model.ResourceLibModel;
import com.taoxue.ui.model.ResourceModel;
import com.taoxue.ui.model.ResultModel;
import com.taoxue.ui.model.UrlModel;
import com.taoxue.ui.model.UserModel;
import com.taoxue.ui.model.YzmBean;
import com.taoxue.ui.model.homefrag.ApiOneBean;
import com.taoxue.ui.module.search.bean.ResoureHotSearchKeyBean;
import com.taoxue.ui.module.search.bean.SearchSupplierBean;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by CC on 2016/5/28.
 */

public interface HttpApis {

    /**
     * 测试
     *
     * @return
     */
    @GET("getCatalogType.do")
    Call<BasePageModel<BaseModel>> getCatalogType();

    /**
     * 短信验证
     *
     * @param mobile
     * @return
     */
    @GET("user/login/sendSMS.do")
    Call<BaseResultModel<YzmBean>> sendvalidate(@Query("mobile") String mobile);

    /**
     * 注册
     *
     * @param mobile
     * @param name
     * @param credential
     * @param reader_card_id
     * @param cgs_id
     * @return
     */
    @GET("user/saveRegister.do")
    Call<CheckSignModel> getRegister(@Query("mobile") String mobile, @Query("name") String name, @Query("credential") String credential, @Query("reader_card_id") String reader_card_id, @Query("cgs_id") String cgs_id);

    /**
     * 登录
     *
     * @param identifier
     * @param credential
     * @return
     */
    @GET("user/login/check.do")
    Call<BaseResultModel<UserModel>> getLogin(@Query("identifier") String identifier, @Query("credential") String credential);

    /**
     * 获取分类详情
     *
     * @param type_flag
     * @param keyword
     * @return
     */
    @GET("searchDigitalResources.do")
    Call<BasePageModel<ResultModel>> getDetail(@Query("keyword") String keyword, @Query("type_flag") String type_flag, @Query("pageNo") String pageNo, @Query("pageSize") String pageSize);

    /**
     * 获取分类详情
     *
     * @param resource_id
     * @return
     */
    @GET("dr/mt/view.do")
    Call<BaseResultModel<GysDataBean>> getGys(
            @Query("resource_id") String resource_id,
            @Query("fr") String fr
    );


    /**
     * 获取资源详细接口及二维码扫描接口
     *
     *
     * @param resource_id
     * @return
     */
    @GET("dr/mt/view.do")
    Call<BaseResultModel<ResourceModel>> getResourceDetail(@Query("resource_id") String resource_id);


    /**
     * 获取资源文件Url
     *
     *
     * @param resource_id
     * @return
     */
    @GET("dr/mt/read.do")
    Call<BaseListModel<UrlModel>> getFileUrl(@Query("resource_id") String resource_id,@Query("user_id") String user_id);


    /**
     * 添加收藏
     *
     * @param gys_id
     * @param user_id
     * @param resource_id
     * @return
     */
    @GET("user/dr/collection/save.do")
    Call<CheckSignModel> addCollection(@Query("user_id") String user_id, @Query("resource_id") String resource_id, @Query("gys_id") String gys_id);

    /**
     * 首页接口
     *
     * @return
     */
    @GET("mt/index/dr/1.do")
    Call<BaseResultModel<ApiOneBean>> getHome1();
    /**
     * 我的收藏
     *
     * @return
     */
    @GET("user/dr/collection/query.do")
    Call<BasePageModel<FragCollectionBean>> collection(@Query("user_id") String userId, @Query("pageNo") int page, @Query("pageSize") int pageSize);

    /**
     * 播放记录
     *
     * @return
     */
    @GET("user/dr/read.do")
    Call<BasePageModel<BaseModel>> read(@Query("user_id") String userId, @Query("pageNo") int page, @Query("pageSize") int pageSize);

    /**
     * 查询用户基本信息
     *
     * @return
     */
    @GET("mt/user/info.do")
    Call<BaseResultModel<UserModel>> info(@Query("user_id") String userId);

    /**
     * 验证读者证
     *
     * @param reader_id
     * @return
     */
    @GET("user/register/getTempReaderInfo.do")
    Call<BaseResultModel<ReaderCodeModel>> testReaderCode(@Query("reader_id") String reader_id);
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoName(
            @Query("user_id") String reader_id,
            @Query("name") String str
    );
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfo(
            @Query("user_id") String reader_id,
            @Query("sex") String sex,
            @Query("birth_year") String birth_year,
            @Query("hangye") String hangye,
            @Query("job") String job,
            @Query("education") String education
    );
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoBirth(
            @Query("user_id") String reader_id,
            @Query("birth_year") String birth_year
    );
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoHangye(
            @Query("user_id") String reader_id,
            @Query("hangye") String hangye
    );
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoJob(
            @Query("user_id") String reader_id,
            @Query("job") String job
    );
    /**
     * 修改用户信息接口
     *
     * @param reader_id
     * @return
     */
    @GET("mt/user/updUserInfo.do")
    Call<BaseResultModel<BaseModel>> updUserInfoEducation(
            @Query("user_id") String reader_id,
            @Query("education") String education
    );

//    /**
//     * 上传头像
//     *
//     * @param userid
//     * @param file_fmimg
//     * @return
//     */
//    @Multipart
//    @POST("mt/user/uploadAndSavePhoto.do")
//    Call<BaseResultModel<BaseModel>> uploadAndSavePhoto(
//            @Query("user_id") String reader_id,
//            @Part MultipartBody.Part file_fmimg);


    /**
     * 上传图片
     *
     * @param type
     * @param suffix
     * @param file
     * @param usercode
     * @return
     */
    @Multipart
    @POST("mt/user/uploadAndSavePhoto.do")
    Call<BaseResultModel> uploadAndSavePhoto(
            @Query("user_id") String reader_id,
            @Part("type") RequestBody type,
            @Part("suffix") RequestBody suffix,
            @Part("file\"; filename=\"image.png") RequestBody file,
            @Part("usercode") RequestBody usercode
    );

    /**
     * 查询我的图书馆信息
     *
     * @param user_id
     * @return
     */
    @GET("mt/user/qryReaderCard.do")
    Call<BaseListModel<MyLibInfo>> queryMyLibInfo(@Query("user_id") String user_id);

    /**
     * 通过user_id绑定读者证
     *
     * @param user_id
     * @param cgs_id
     * @param reader_card_id
     * @return
     */
    @GET("mt/user/bindingReaderCardId.do")
    Call<CheckSignModel> bindReaderCardIdByUserId(@Query("user_id") String user_id,@Query("cgs_id") String cgs_id,@Query("reader_card_id") String reader_card_id);

    /**
     * 查询评论内容
     *@param resource_id
     * @param pageNo
     * @param pageSize
     * @return
     */
    @GET("dr/qryResourceComment.do")
    Call<CommitPageModel<PageModel<ComentModel>>> queryResourceComment(@Query("resource_id") String resource_id, @Query("pageSize") String pageSize, @Query("pageNo") String pageNo);

    /**
     * 添加评论内容
     *@param resource_id
     * @param content
     * @param user_id
     * @return
     */
    @GET("dr/saveResourceComment.do")
    Call<CheckSignModel> addResourceComment(@Query("resource_id") String resource_id, @Query("user_id") String user_id, @Query("content") String content);


    /**
     * 点赞
     *@param resource_id
     * @param user_id
     * @param gys_id
     * @return
     */
    @GET("dr/saveResourcePraise.do")
    Call<CheckSignModel> giveThumb(@Query("resource_id") String resource_id, @Query("user_id") String user_id, @Query("gys_id") String gys_id);

    /**
     * 根据所在城市查询对应的采购商（图书馆）
     * @param city
     * @return
     */
    @GET("/mt/city/cgs.do")
    Call<BaseModel> searchCgs(@Query("city") String city);
    /**
     * 资源库详情接口
     * @return
     */
    @GET("/mt/drdata/info.do")
    Call<BaseModel> searchInfo();
    /**
     * 手机端显示所有的图书馆：输入名称时，可进行搜索
     * @param city
     * @return
     */
    @GET("mt/city/cgs.do")
    Call<SearchSupplierBean> searchList(@Query("city") String city);
    /**
     * 供应商已经采购的所有数字资源
     * @return
     */
    @GET("mt/gys/list.do")
    Call<SearchSupplierBean> gysList(@Query("name") String name);

    /**
     * 手机端推荐资源库关键词接口：搜索资源库时使用
     * @return
     */
    @GET("/mt/commend/keyword/resource.do")
    Call<BaseModel> searchResource();
    /**
     * 手机端推荐关键词接口：搜索资源时使用
     * @return
     */
    @GET("/mt/commend/keyword/resource.do")
    Call<BaseModel> searchResource2();
    /**
     * 图书馆已经采购的所有数字资源
     * @return
     */
    @GET("/mt/cgs/resource/list")
    Call<BaseModel> searchList2();
    /**
     * 资源库详情
     * @return
     */
    @GET("/DRIS_frontend_V1.0.1/mt/drdata/info.do")
    Call<BaseResultModel<ResourceLibModel>> searchResourceDeatail();
    /**
     * 资源搜索,热门搜索关键词
     * @return
     */
    @GET("mt/commend/keyword/resource.do")
    Call<ResoureHotSearchKeyBean> resource();
    /**
     * 资源库 热门搜索
     * @return
     */
    @GET("mt/commend/keyword/drdata.do")
    Call<ResoureHotSearchKeyBean> keywordDrdata();
}
