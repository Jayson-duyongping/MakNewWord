package com.mak.eword.mvp.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jayson on 2019/4/9.
 * Content: 一个句子实体
 */
@Entity(nameInDb = "sentence_table")
public class SentenceBean implements Serializable {
    @Id(autoincrement = true)
    private Long id;
    @Property
    private String imgUrl;
    @Property
    private String enContent;
    @Property
    private String zhContent;
    public String getZhContent() {
        return this.zhContent;
    }
    public void setZhContent(String zhContent) {
        this.zhContent = zhContent;
    }
    public String getEnContent() {
        return this.enContent;
    }
    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }
    public String getImgUrl() {
        return this.imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 41098980)
    public SentenceBean(Long id, String imgUrl, String enContent, String zhContent) {
        this.id = id;
        this.imgUrl = imgUrl;
        this.enContent = enContent;
        this.zhContent = zhContent;
    }
    @Generated(hash = 1460602456)
    public SentenceBean() {
    }
}
