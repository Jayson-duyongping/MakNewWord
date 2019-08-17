package com.mak.eword.mvp.model.common;

/**
 * 创建人：jayson
 * 创建时间：2019/4/30
 * 创建内容：通用解析实体
 */
public class CommonBean {
    private String code;                            //响应状态码
    private String msg;                             //响应状态描述
    private Object data;
    private Object list;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getList() {
        return list;
    }

    public void setList(Object list) {
        this.list = list;
    }
}
