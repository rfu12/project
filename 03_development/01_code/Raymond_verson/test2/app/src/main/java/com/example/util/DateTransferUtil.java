package com.example.util;

import com.example.entity.Credential;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTransferUtil {
    /**
     * default yyyy/MM/dd
     * @param dateStr
     * @param formate
     * @return Date
     * @throws ParseException
     */
    public static Date string2date(String dateStr, String formate) throws ParseException {
        if(StringUtil.isEmptyStr(dateStr)) throw new IllegalArgumentException();
        if(StringUtil.isEmptyStr(formate)) formate = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
        return simpleDateFormat.parse(dateStr);
    }

    /**
     * default yyyy/MM/dd
     * @return String
     */
    public static String date2string(Date date, String formate) {
        if(date == null) throw new IllegalArgumentException();
        if(StringUtil.isEmptyStr(formate)) formate = "yyyy/MM/dd";
        SimpleDateFormat sdf = new SimpleDateFormat(formate);
        return sdf.format(date);
    }

    public static void main(String[] args) {
        String jsonStr = "{\"signUpDate\":\"2019-05-16T00:00:00+08:00\",\"userId\":{\"name\":\"Raymond�\",\"userId\":3},\"username\":\"RJ\"}";
        Gson gson = new Gson();
        Credential credential = null;
        try {
            credential = gson.fromJson(jsonStr, Credential.class);
            System.out.println(credential);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }
}
