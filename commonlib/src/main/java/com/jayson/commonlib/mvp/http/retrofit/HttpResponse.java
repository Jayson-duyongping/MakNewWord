package com.jayson.commonlib.mvp.http.retrofit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

/**
 * http响应参数实体类
 * 通过Gson解析属性名称需要与服务器返回字段对应,或者使用注解@SerializedName
 * 备注:这里与服务器约定返回格式
 *
 * @author ZhongDaFeng
 */
public class HttpResponse {

    /**
     * 描述信息
     */
    @SerializedName("message")
    private String message;

    /**
     * 状态码
     */
    @SerializedName("code")
    private int code;

    /**
     * 数据对象[成功返回对象,失败返回错误说明]
     */
    @SerializedName("data")
    private Object data;

    /**
     * 是否成功(这里约定200)
     *
     * @return
     */
    public boolean isSuccess() {
        return code == 1 ? true : false;
    }

    public String toString() {
        String response = "[http response]" + "{\"code\": " + code + ",\"message\":" + message + ",\"data\":" + new Gson().toJson(data) + "}";
        return response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
