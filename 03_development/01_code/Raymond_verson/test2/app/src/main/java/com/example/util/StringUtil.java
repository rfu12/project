package com.example.util;

public abstract class StringUtil {
    public static boolean isEmptyStr(String str){
        return str == null || "".equals(str);
    }
}
