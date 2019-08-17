package com.mak.eword.mvp.model;

import java.util.List;

/**
 * 创建人：jayson
 * 创建时间：2019/5/11
 * 创建内容：
 */
public class SentenceListBean {
    private String code;
    private String msg;
    private List<SentenceBean> list;

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

    public List<SentenceBean> getList() {
        return list;
    }

    public void setList(List<SentenceBean> list) {
        this.list = list;
    }
}
