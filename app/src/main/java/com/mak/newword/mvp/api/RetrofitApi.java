package com.mak.newword.mvp.api;


import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by jayson on 2019/4/16.
 */

public interface RetrofitApi {

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


}
