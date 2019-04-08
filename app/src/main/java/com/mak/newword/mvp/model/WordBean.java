package com.mak.newword.mvp.model;

import com.mak.newword.mvp.model.converter.MeanListConverer;
import com.mak.newword.mvp.model.converter.StringListConverer;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;

import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jayson on 2019/4/8.
 * Content: 一个单词实体
 */
@Entity(nameInDb = "word_table")
public class WordBean {
    @Id(autoincrement = true)
    private Long id;
    @Property
    @NotNull
    private String content;//内容
    @Property
    @Convert(columnType = String.class, converter = MeanListConverer.class)
    private List<MeanBean> means;//不同的词义
    @Property
    private String method;//记忆方法
    @Property
    private String exampleEn;//例句
    @Property
    private String exampleZh;//例句
    @Property
    @Convert(columnType = String.class, converter = StringListConverer.class)
    private List<String> relations;//关联词
    @Property
    private boolean isRemember;//是否已经记忆
    @Property
    private int lookTime;//查看次数

    @Generated(hash = 11040600)
    public WordBean(Long id, @NotNull String content, List<MeanBean> means,
            String method, String exampleEn, String exampleZh,
            List<String> relations, boolean isRemember, int lookTime) {
        this.id = id;
        this.content = content;
        this.means = means;
        this.method = method;
        this.exampleEn = exampleEn;
        this.exampleZh = exampleZh;
        this.relations = relations;
        this.isRemember = isRemember;
        this.lookTime = lookTime;
    }

    @Generated(hash = 1596526216)
    public WordBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean isRemember() {
        return isRemember;
    }

    public void setRemember(boolean remember) {
        isRemember = remember;
    }

    public int getLookTime() {
        return lookTime;
    }

    public void setLookTime(int lookTime) {
        this.lookTime = lookTime;
    }

    public boolean getIsRemember() {
        return this.isRemember;
    }

    public void setIsRemember(boolean isRemember) {
        this.isRemember = isRemember;
    }
}
