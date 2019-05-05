package com.mak.eword.mvp.model.request;

/**
 * 创建人：jayson
 * 创建时间：2019/5/1
 * 创建内容：
 */
public class LoginReq {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
