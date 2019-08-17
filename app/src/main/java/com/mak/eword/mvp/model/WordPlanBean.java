package com.mak.eword.mvp.model;

import java.io.Serializable;

/**
 * 创建人：jayson
 * 创建时间：2019/5/8
 * 创建内容：单词计划实体
 */
public class WordPlanBean implements Serializable {
    private Integer id;
    private Integer recordDayNumber;
    private Integer rememberDayNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecordDayNumber() {
        return recordDayNumber;
    }

    public void setRecordDayNumber(Integer recordDayNumber) {
        this.recordDayNumber = recordDayNumber;
    }

    public Integer getRememberDayNumber() {
        return rememberDayNumber;
    }

    public void setRememberDayNumber(Integer rememberDayNumber) {
        this.rememberDayNumber = rememberDayNumber;
    }
}
