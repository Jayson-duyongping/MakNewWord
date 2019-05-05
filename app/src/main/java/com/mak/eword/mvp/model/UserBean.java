package com.mak.eword.mvp.model;


import java.io.Serializable;

/**
 * Created by jayson on 2019/4/9.
 * Content:用户实体类
 */
public class UserBean implements Serializable {
    //网络字段
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String nick_name;
    private String real_name;
    private Integer age;
    private Integer sex;
    private String address;
    private String phone;
    private String qq;
    private String wx;
    //本地字段
    private int recordDayNum;
    private int rememberDayNum;
    private int recordTotalNum;
    private int rememberTotalNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWx() {
        return wx;
    }

    public void setWx(String wx) {
        this.wx = wx;
    }

    public int getRecordDayNum() {
        return recordDayNum;
    }

    public void setRecordDayNum(int recordDayNum) {
        this.recordDayNum = recordDayNum;
    }

    public int getRememberDayNum() {
        return rememberDayNum;
    }

    public void setRememberDayNum(int rememberDayNum) {
        this.rememberDayNum = rememberDayNum;
    }

    public int getRecordTotalNum() {
        return recordTotalNum;
    }

    public void setRecordTotalNum(int recordTotalNum) {
        this.recordTotalNum = recordTotalNum;
    }

    public int getRememberTotalNum() {
        return rememberTotalNum;
    }

    public void setRememberTotalNum(int rememberTotalNum) {
        this.rememberTotalNum = rememberTotalNum;
    }
}
