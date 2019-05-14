package com.example.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;

public class ApiRequestCall implements Callable<Map<String, String>> {
    //config file
    private static final Properties p = new Properties();
    //file path
    private static final String APISFILEPATH = "/assets/android_urlapis.properties";

    static {
        try {
            p.load(UrlConnectionUtils.class.getResourceAsStream(APISFILEPATH));
        } catch (Exception ex) {
            throw new RuntimeException(APISFILEPATH + " load Fail.");
        }
    }

    //apiurls.properties文件中配置的请求url
    private String serviceKey;
    //apiurls.properties文件中配置的拼接接口api
    private String apiKey;
    //拼接在url后面的组合成url的参数 如：url/api/1/2
    private List<String> apiParams;
    //拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
    private Map<String, String> params;
    //请求的字符集
    private String charset;

    /**
     * 构造方法初始化接口访问
     *
     * @param serviceKey apiurls.properties文件中配置的请求url
     * @param apiKey     apiurls.properties文件中配置的拼接接口api
     * @param apiParams  拼接在url后面的组合成url的参数 如：url/api/1/2
     * @param params     拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
     * @param charset    请求的字符集
     */
    public ApiRequestCall(String serviceKey, String apiKey, List<String> apiParams, Map<String, String> params, String charset) {
        this.serviceKey = serviceKey;
        this.apiKey = apiKey;
        this.apiParams = apiParams;
        this.params = params;
        this.charset = charset;
    }

    /**
     * 构造方法初始化接口访问，默认KeepfitApp后台服务器地址，默认utf-8编码
     *
     * @param apiKey     apiurls.properties文件中配置的拼接接口api
     * @param apiParams  拼接在url后面的组合成url的参数 如：url/api/1/2
     * @param params     拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
     */
    public ApiRequestCall(String apiKey, List<String> apiParams, Map<String, String> params) {
        this("url.keepfit.service",apiKey,apiParams,params,"utf-8");
    }

    @Override
    public Map<String, String> call() throws Exception {
        String url = concatUrl(serviceKey, apiKey, apiParams, params);
        Map<String, String> returnMap = new HashMap<>();
        String responseStr = "ERROR!: thread has been interrupted.";
        if(!Thread.currentThread().isInterrupted()) responseStr = UrlConnectionUtils.sendGetByHttpUrlConnection(url, "utf-8");
        returnMap.put("retMsg", responseStr);
        if(responseStr.startsWith("ERROR!: ")) returnMap.put("retCode","0");
        else returnMap.put("retCode", "1");
        return returnMap;
    }

    /**
     * 拼接url
     *
     * @param serviceKey
     * @param apiKey
     * @param apiParams
     * @param params
     * @return
     * @throws Exception
     */
    public static String concatUrl(String serviceKey, String apiKey, List<String> apiParams, Map<String, String> params) throws Exception {
        if (serviceKey == null || "".equals(serviceKey))
            throw new Exception("url cann't be empty.");
        StringBuilder url = new StringBuilder(p.getProperty(serviceKey));
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
}
