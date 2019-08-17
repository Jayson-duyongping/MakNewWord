package com.mak.eword.mvp.api;


import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by jayson on 2019/4/16.
 */

public interface RetrofitApi {

    /**
     * 用户注册
     */
    @POST(UrlConst.USER_REGISTER)
    Observable<ResponseBody> userRegister(@Body RequestBody route);

    /**
     * 用户登录
     */
    @POST(UrlConst.USER_LOGIN)
    Observable<ResponseBody> userLogin(@Body RequestBody route);

    /**
     * 单词查询
     */
    @GET(UrlConst.GET_QUERY_WORD)
    Observable<ResponseBody> getQueryWord(@Query("w") String word);

    /**
     * 信息查询
     */
    @GET
    Observable<ResponseBody> messageInfo(@Url String url);

    /**
     * 单词计划查询
     */
    @GET(UrlConst.GET_WORD_PLAN)
    Observable<ResponseBody> getWordPlan();

    /**
     * 单词计划修改
     */
    @POST(UrlConst.ALTER_WORD_PLAN)
    Observable<ResponseBody> alterWordPlan(@Body RequestBody route);

    /**
     * 单词列表
     */
    @GET(UrlConst.Word_List)
    Observable<ResponseBody> getWordList(@Query("page") int page,
                                         @Query("page_size") int page_size);

    /**
     * 单词新增
     */
    @POST(UrlConst.Add_Word)
    Observable<ResponseBody> addWord(@Body RequestBody route);

    /**
     * 单词修改
     */
    @POST(UrlConst.Alter_Word)
    Observable<ResponseBody> alterWord(@Body RequestBody route);

    /**
     * 单词删除
     */
    @POST(UrlConst.Delete_Word)
    Observable<ResponseBody> deleteWord(@Body RequestBody route);

    /**
     * 句子列表
     */
    @GET(UrlConst.Sentence_List)
    Observable<ResponseBody> getSentenceList(@Query("page") int page,
                                             @Query("page_size") int page_size);
}
