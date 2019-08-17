package com.mak.eword.mvp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jayson on 2019/4/8.
 * Content: 一个单词实体
 */
public class WordBean implements Serializable {

    private Integer id;
    private String content;//内容
    private List<MeanBean> means;//不同的词义
    private String method;//记忆方法
    private String exampleEn;//例句
    private String exampleZh;//例句
    private List<String> relations;//关联词
    private int isRemember;//是否已经记忆
    private int lookTime;//查看次数

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<MeanBean> getMeans() {
        return means;
    }

    public void setMeans(List<MeanBean> means) {
        this.means = means;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getExampleEn() {
        return exampleEn;
    }

    public void setExampleEn(String exampleEn) {
        this.exampleEn = exampleEn;
    }

    public String getExampleZh() {
        return exampleZh;
    }

    public void setExampleZh(String exampleZh) {
        this.exampleZh = exampleZh;
    }

    public List<String> getRelations() {
        return relations;
    }

    public void setRelations(List<String> relations) {
        this.relations = relations;
    }

    public int getIsRemember() {
        return isRemember;
    }

    public void setIsRemember(int isRemember) {
        this.isRemember = isRemember;
    }

    public int getLookTime() {
        return lookTime;
    }

    public void setLookTime(int lookTime) {
        this.lookTime = lookTime;
    }
}
