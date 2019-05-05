package com.mak.eword.mvp.api;


/**
 * 接口工具类
 *
 * Created by jayson on 2019/4/16.
 */

public class ApiUtils {

    private static RetrofitApi retrofitApi;

    public static RetrofitApi getretrofitApi() {
        if (retrofitApi == null) {
            retrofitApi = RetrofitUtils.get().retrofit().create(RetrofitApi.class);
        }
        return retrofitApi;
    }

}
