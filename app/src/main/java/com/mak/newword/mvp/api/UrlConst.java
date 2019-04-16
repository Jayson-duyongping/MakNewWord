package com.mak.newword.mvp.api;

/**
 * Created by jayson on 2019/4/16.
 * Content: 网络接口API
 */
public class UrlConst {

    //金山词霸key
    public static final String CibaKey = "F555626571D49D435A11145B93A32787";
    //获取词霸查询单词
    public static final String GET_QUERY_WORD = "http://dict-co.iciba.com/api/dictionary.php?type=json&key=" + CibaKey;
}
