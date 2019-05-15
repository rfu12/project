package com.example.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

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
    //执行线程
    private volatile Thread worker;

    private volatile FutureTask<Map<String, String>> futureTask;
    //线程池
    private final static Executor EXECUTOR = Executors.newFixedThreadPool(10);

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
     * @param apiKey    apiurls.properties文件中配置的拼接接口api
     * @param apiParams 拼接在url后面的组合成url的参数 如：url/api/1/2
     * @param params    拼接在url后面的请求参数 如url/api/1/2?name=a&key=b
     */
    public ApiRequestCall(String apiKey, List<String> apiParams, Map<String, String> params) {
        this("url.keepfit.service", apiKey, apiParams, params, "utf-8");
    }

    @Override
    public Map<String, String> call() throws Exception {
        String url = concatUrl(serviceKey, apiKey, apiParams, params);
        Map<String, String> returnMap = new HashMap<>();
        String responseStr = "ERROR!: thread has been interrupted.";
        if (!Thread.currentThread().isInterrupted())
            responseStr = UrlConnectionUtils.sendGetByHttpUrlConnection(url, charset);
        returnMap.put("detail", responseStr);
        if (responseStr.startsWith("ERROR!: ") || StringUtil.isEmptyStr(responseStr))
            returnMap.put("opcode", "0");
        else returnMap.put("opcode", "1");
        return returnMap;
    }

    /**
     * 获取FutureTask
     *
     * @return FutureTask<Map                               <                               String                               ,                                                               String>>
     */
    public FutureTask<Map<String, String>> getFutureTask() {
        if (futureTask == null) {
            synchronized (ApiRequestCall.class) {
                if (futureTask == null) futureTask = new FutureTask<>(this);
            }
        }
        return futureTask;
    }

    /**
     * 单线程执行
     *
     * @return Map<String                               ,                                                               String>：opcode(1-OK,0-Error) and detail
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<String, String> execute() throws ExecutionException, InterruptedException {
        if (worker == null) {
            synchronized (this) {
                worker = new Thread(getFutureTask());
            }
        }
        worker.start();
        return futureTask.get();
    }

    /**
     * 可交给线程池管理和执行任务
     *
     * @return Map<String                               ,                                                               String>：opcode(1-OK,0-Error) and detail
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public Map<String, String> executeInThreadPool() throws ExecutionException, InterruptedException {
        EXECUTOR.execute(getFutureTask());
        return futureTask.get();
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
        if (StringUtil.isEmptyStr(serviceKey))
            throw new IllegalArgumentException("url can't be empty.");
        StringBuilder url = new StringBuilder(p.getProperty(serviceKey));
        if (StringUtil.isEmptyStr(url.toString())) throw new Exception("url is not exist.");
        if (!StringUtil.isEmptyStr(apiKey)) {
            String api = p.getProperty(apiKey);
            if (StringUtil.isEmptyStr(api)) throw new Exception("api is not exist.");
            url = url.charAt(url.length() - 1) == '/' ? api.charAt(0) == '/' ? url.append(api.substring(1)) : url.append(api) : api.charAt(0) == '/' ? url.append(api) : url.append("/").append(api);
        }
        if (url.charAt(url.length() - 1) == '/') url.deleteCharAt(url.length() - 1);
        if (apiParams != null && !apiParams.isEmpty())
            for (String apiParam : apiParams) url.append("/").append(apiParam);
        if (params == null || params.isEmpty()) return url.toString();
        url.append("?");
        for (Map.Entry<String, String> param : params.entrySet())
            url.append(param.getKey()).append("=").append(param.getValue()).append("&");
        return url.substring(0, url.length() - 1);
    }
}
