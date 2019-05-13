package com.rf.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class UrlConnectionUtils {
    private static final Properties p = new Properties();
    private static final String APISFILEPATH = "/assets/android_urlapis.properties";

    static {
        try {
            p.load(UrlConnectionUtils.class.getResourceAsStream(APISFILEPATH));
        } catch (Exception ex) {
            throw new RuntimeException(APISFILEPATH + " load Fail.");
        }
    }

    /**
     * 通过UrlConnect建立连接并发送get请求
     *
     * @param urlKey    apiurls.properties文件中配置的请求url
     * @param apiKey    apiurls.properties文件中配置的拼接接口api
     * @param apiParams 拼接在url后面的组合成url的参数 如：url/api/1/2
     * @param params    拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
     * @param charset   请求的字符集
     * @return java.lang.String
     */
    public static String sendGetByUrlConnection(String urlKey, String apiKey, List<String> apiParams, Map<String, String> params, String charset) {
        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder();
        try {
            String urlStr = concatUrl(urlKey, apiKey, apiParams, params);
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            // 设置请求属性
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
            sb.append("ERROR!");
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
     * 通过HttpUrlConnect建立连接并发送get请求
     *
     * @param urlKey    apiurls.properties文件中配置的请求url
     * @param apiKey    apiurls.properties文件中配置的拼接接口api
     * @param apiParams 拼接在url后面的组合成url的参数 如：url/api/1/2
     * @param params    拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
     * @param charset   请求的字符集
     * @return java.lang.String
     */
    public static String sendGetByHttpUrlConnection(String urlKey, String apiKey, List<String> apiParams, Map<String, String> params, String charset) {
        BufferedReader bf = null;
        StringBuilder sb = new StringBuilder();
        try {
            String urlStr = concatUrl(urlKey, apiKey, apiParams, params);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            // 设置请求属性
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
            sb.append("ERROR!");
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
     * 拼接url
     *
     * @param urlKey
     * @param apiKey
     * @param apiParams
     * @param params
     * @return
     * @throws Exception
     */
    public static String concatUrl(String urlKey, String apiKey, List<String> apiParams, Map<String, String> params) throws Exception {
        if (urlKey == null || "".equals(urlKey)) throw new Exception("url cann't be empty.");
        StringBuilder url = new StringBuilder(p.getProperty(urlKey));
        if (apiKey == null || "".equals(apiKey)) return url.toString();
        String api = p.getProperty(apiKey);
        url = url.charAt(url.length() - 1) == '/' ? api.charAt(0) == '/' ? url.append(api.substring(1)) : url.append(api) : api.charAt(0) == '/' ? url.append(api) : url.append("/").append(api);
        if (apiParams == null || apiParams.isEmpty()) return url.toString();
        if (url.charAt(url.length() - 1) == '/') url.deleteCharAt(url.length() - 1);
        for (String apiParam : apiParams) url.append("/").append(apiParam);
        if (params == null || params.isEmpty()) return url.toString();
        url.append("?");
        for (Map.Entry<String, String> param : params.entrySet())
            url.append(param.getKey()).append("=").append(param.getValue()).append("&");
        return url.substring(0, url.length() - 1);
    }

    public static void main(String[] arg) {
        //System.out.println(new File(".").getAbsolutePath());
        List<String> list = new ArrayList<>();
        list.add("1");
        String a = sendGetByHttpUrlConnection("url.keepfit.service", "api.keepfit.login", list, null, "utf-8");
        System.out.println(a);
    }
}
