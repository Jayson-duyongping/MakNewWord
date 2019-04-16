package com.mak.newword.mvp.model;

/**
 * Created by jayson on 2018/6/29.
 * Content:
 */

public class BaseErrorBean {

    /**
     * name : Not Found
     * message : 页面未找到。
     * code : 0
     * status : 404
     * type : yii\web\NotFoundHttpException
     */

    private String name;
    private String message;
    private int code;
    private int status;
    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
