package com.ocean.common.util;

/**
 */
public class Strings {

    /**
     * 判断字符串是否为null或""
     * @param s 字符串
     * @return 若为null或""返回true，反之false
     */
    public static Boolean isNullOrEmpty(String s){
        return s == null || "".equals(s);
    }
}
