package com.jayson.commonlib.mvp.http.function;

import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 服务器结果处理函数
 *
 * @author
 */
public class ServerResultFunction implements Function<ResponseBody, String> {

    @Override
    public String apply(ResponseBody responseBody) throws Exception {
        String json = new String(responseBody.bytes());
        return json;
    }
}
