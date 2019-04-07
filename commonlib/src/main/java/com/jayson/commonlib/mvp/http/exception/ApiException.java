package com.jayson.commonlib.mvp.http.exception;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

/**
 * api接口错误/异常统一处理类
 * 异常=[程序异常,网络异常,解析异常..]
 * 错误=[接口逻辑错误eg:{code:-101,msg:账号密码错误}]
 *
 * @author ZhongDaFeng
 */
public class ApiException extends Exception {
    private int code;//错误码
    private String message;//错误信息
    private String errorJson; //错误json

    public ApiException(Throwable e, int code) {
        super(e);
        if (e instanceof HttpException) {
            ResponseBody body = ((HttpException) e).response().errorBody();
            this.code = ((HttpException) e).response().code();
            if (this.code == 401) {
                //Token失效，重新登陆
                this.code = -1;
                this.message = "401Token失效";
                return;
            }
            try {
                String errorStr = body.string();
                Log.d("ApiException body ", body.string());
                this.errorJson = errorStr;
                if (TextUtils.isEmpty(this.errorJson)) {
                    this.code = -1;
                    this.message = "请求异常";
                }
            } catch (IOException IOe) {
                IOe.printStackTrace();
            }
        } else if (e instanceof ServerException) {    //服务器返回的错误
            this.code = -1;
            this.message = "服务器错误";
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException || e instanceof MalformedJsonException) {  //解析数据错误
            this.code = -1;
            this.message = "解析出错";
        } else if (e instanceof ConnectException) {//连接网络错误
            this.code = -1;
            this.message = "连接失败";
        } else if (e instanceof SocketTimeoutException) {//网络超时
            this.code = -1;
            this.message = "网络超时";
        } else if (e instanceof UnknownHostException) {
            this.code = -1;
            this.message = "网络链接失败";
        } else {  //未知错误
            this.code = -1;
            this.message = "网络超时";
        }
    }

    public String getErrorString() {
        return errorJson;
    }

    public void setErrorJson(String errorJson) {
        this.errorJson = errorJson;
    }

    public int getStatus() {
        return code;
    }

    public void setStatus(int status) {
        this.code = status;
    }

    public String getMsg() {
        return message;
    }

    public void setMsg(String msg) {
        this.message = msg;
    }
}
