package com.mak.eword.mvp.model;
import java.io.Serializable;

/**
 * Created by jayson on 2019/4/9.
 * Content: 一个句子实体
 */
public class SentenceBean implements Serializable {

    private int id;

    private String imgUrl;

    private String enContent;

    private String zhContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getEnContent() {
        return enContent;
    }

    public void setEnContent(String enContent) {
        this.enContent = enContent;
    }

    public String getZhContent() {
        return zhContent;
    }

    public void setZhContent(String zhContent) {
        this.zhContent = zhContent;
    }
}
