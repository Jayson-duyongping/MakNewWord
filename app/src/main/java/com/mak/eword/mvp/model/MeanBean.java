package com.mak.eword.mvp.model;

import java.io.Serializable;

/**
 * Created by jayson on 2019/4/8.
 * Content:
 */
public class MeanBean implements Serializable {
    private Integer id;
    private String class_;
    private String mean_;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClass_() {
        return class_;
    }

    public void setClass_(String class_) {
        this.class_ = class_;
    }

    public String getMean_() {
        return mean_;
    }

    public void setMean_(String mean_) {
        this.mean_ = mean_;
    }
}
