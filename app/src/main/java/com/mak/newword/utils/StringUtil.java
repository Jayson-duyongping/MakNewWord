package com.mak.newword.utils;

/**
 * Created by jayson on 2019/4/16.
 * Content:
 */
public class StringUtil {
    /**
     * 获取标签内部数据
     *
     * @param val
     * @return
     */
    public static String getBodySym(String val) {
        String start = "[";
        String end = "]";
        int s = val.indexOf(start) + start.length();
        int e = val.indexOf(end);
        return val.substring(s, e);
    }

    /**
     * 获取某个字符串后面的字符串
     *
     * @param content
     * @param val
     * @return
     */
    public static String getAfterAddr(String content, String val) {
        return content.substring(content.indexOf(val) + val.length());
    }
}
