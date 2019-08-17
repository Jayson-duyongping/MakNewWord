package com.mak.eword.mvp.model.request;

/**
 * 创建人：jayson
 * 创建时间：2019/5/1
 * 创建内容：
 */
public class WordPlanReq {
    private Integer recordDayNumber;
    private Integer rememberDayNumber;

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
