package com.example.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
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
        HttpURLConnection con = null;
        try {
            URL url = new URL(api);
            con = (HttpURLConnection) url.openConnection();
            // set some normal request property
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.setReadTimeout(10000);
            con.setConnectTimeout(15000);
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
            if(con != null) {
                con.disconnect();
                con = null;
            }
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

    public static String sendPostByHttpUrlConnect(String api, String requestBody, String charset) {
        BufferedReader br = null;
        OutputStreamWriter osw = null;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection conn = null;
        try {
            URL url = new URL(api);
            conn = (HttpURLConnection) url.getContent();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            if (requestBody != null && requestBody.length() > 0) {
                osw = new OutputStreamWriter(conn.getOutputStream(),charset);
                osw.write(requestBody, 0, requestBody.length());
                osw.flush();
            }
            int contentLength = 0;
            if(StringUtil.isEmptyStr(conn.getHeaderField("Content-Length")))
                contentLength = Integer.parseInt(conn.getHeaderField("Content-Length"));
            if (contentLength > 0) {
                String temp = null;
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while((temp = br.readLine()) != null) sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            sb.delete(0, sb.length() - 1);
            sb.append("ERROR!: ").append(e.getLocalizedMessage());
        } finally {
            if(osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    osw = null;
                    throw new RuntimeException(e);
                }
            }
            if(br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                    throw new RuntimeException(e);
                }
            }
            if(conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return sb.toString();
    }
}
