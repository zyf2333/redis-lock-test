package com.redislock.utils;

/**
 * @Author zyf
 * @Description
 * @ClassName StringUtils
 * @Date 2020/3/23 23:26
 **/
public class StringUtils {

    public static boolean isNotEmpty(String str) {
        return str != null && !str.equals("");
    }
}
