package com.mak.eword.mvp.api;

/**
 * Created by jayson on 2019/4/16.
 * Content: 网络接口API
 */
public class UrlConst {

    //金山词霸key
    public static final String CibaKey = "F555626571D49D435A11145B93A32787";
    //获取词霸查询单词
    public static final String GET_QUERY_WORD = "http://dict-co.iciba.com/api/dictionary.php?type=json&key=" + CibaKey;
    //用户注册
    public static final String USER_REGISTER = "/user/register";
    //用户登录
    public static final String USER_LOGIN = "/user/login";

    //单词计划
    public static final String GET_WORD_PLAN = "/word/getWordPlan";
    public static final String ALTER_WORD_PLAN = "/word/alterWordPlan";
    //单词
    public static final String Word_List = "/word/getWordList";
    public static final String Add_Word = "/word/addWord";
    public static final String Alter_Word = "/word/alterWord";
    public static final String Delete_Word = "/word/deleteWord";
    //句子
    public static final String Sentence_List = "/word/getSentenceList";
}
