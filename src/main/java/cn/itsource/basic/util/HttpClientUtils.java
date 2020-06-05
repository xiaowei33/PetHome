package cn.itsource.basic.util;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.HttpClient;

public class HttpClientUtils {
    public static String httpGet(String url){
        try {
            //创建发起请求的客户端
            HttpClient client = new HttpClient();
            //创建要发起的请求
            GetMethod getMethod = new GetMethod(url);
            //通过传入的请求，获取响应对象
            client.executeMethod(getMethod);
            //提取json字符串
            String result = new String(getMethod.getResponseBodyAsString().getBytes("utf8"));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
