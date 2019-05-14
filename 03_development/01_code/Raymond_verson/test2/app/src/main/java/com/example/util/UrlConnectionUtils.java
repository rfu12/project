package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public abstract class UrlConnectionUtils {

    /**
     * build connection and send get requirement by UrlConnect
     *
     * @param api     api requestment address
     * @param charset requestment charset
     * @return java.lang.String
     */
    public static String sendGetByUrlConnection(String api, String charset) {
        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(api);
            URLConnection con = url.openConnection();
            // set some normal request property
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            bf = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = bf.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.delete(0, sb.length() - 1);
            sb.append("ERROR!: ").append(e.getLocalizedMessage());
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    bf = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * build connection and send get requirement by HttpUrlConnect
     *
     * @param api     api requestment address
     * @param charset requestment charset
     * @return java.lang.String
     */
    public static String sendGetByHttpUrlConnection(String api, String charset) {
        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(api);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // set some normal request property
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            bf = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));
            String temp;
            while ((temp = bf.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.delete(0, sb.length() - 1);
            sb.append("ERROR!: ").append(e.getLocalizedMessage());
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    bf = null;
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }
}
