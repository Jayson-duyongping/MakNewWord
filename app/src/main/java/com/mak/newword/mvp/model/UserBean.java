package com.mak.newword.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jayson on 2019/4/9.
 * Content:用户实体类
 */
@Entity(nameInDb = "user_table")
public class UserBean implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    @Property
    private String name;
    @Property
    private String headUrl;
    @Property
    private int recordDayNum;
    @Property
    private int rememberDayNum;
    @Property
    private int recordTotalNum;
    @Property
    private int rememberTotalNum;
    public int getRememberTotalNum() {
        return this.rememberTotalNum;
    }
    public void setRememberTotalNum(int rememberTotalNum) {
        this.rememberTotalNum = rememberTotalNum;
    }
    public int getRecordTotalNum() {
        return this.recordTotalNum;
    }
    public void setRecordTotalNum(int recordTotalNum) {
        this.recordTotalNum = recordTotalNum;
    }
    public int getRememberDayNum() {
        return this.rememberDayNum;
    }
    public void setRememberDayNum(int rememberDayNum) {
        this.rememberDayNum = rememberDayNum;
    }
    public int getRecordDayNum() {
        return this.recordDayNum;
    }
    public void setRecordDayNum(int recordDayNum) {
        this.recordDayNum = recordDayNum;
    }
    public String getHeadUrl() {
        return this.headUrl;
    }
    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1468110573)
    public UserBean(Long id, String name, String headUrl, int recordDayNum,
            int rememberDayNum, int recordTotalNum, int rememberTotalNum) {
        this.id = id;
        this.name = name;
        this.headUrl = headUrl;
        this.recordDayNum = recordDayNum;
        this.rememberDayNum = rememberDayNum;
        this.recordTotalNum = recordTotalNum;
        this.rememberTotalNum = rememberTotalNum;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
}
