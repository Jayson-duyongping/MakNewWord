package com.mak.eword.mvp.model;

import java.util.List;

/**
 * 创建人：jayson
 * 创建时间：2019/5/11
 * 创建内容：
 */
public class WordListBean {
    private String code;
    private String msg;
    private List<WordBean> list;

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

    public List<WordBean> getList() {
        return list;
    }

    public void setList(List<WordBean> list) {
        this.list = list;
    }
}
