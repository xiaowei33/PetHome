package cn.itsource.basic.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class SmsUtils {

    //用户id
    private static  final  String UID = "xiaowei331";
    //秘钥
    private static  final  String SECURITY_KEY = "d41d8cd98f00b204e980";
    public static void sendSmsCode(String phone, String content) {
        HttpClient client = null;
        PostMethod post = null;
        try {
            client = new HttpClient();
            post = new PostMethod("http://utf8.api.smschinese.cn/");
            post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf8");//在头文件中设置转码
            NameValuePair[] data ={ new NameValuePair("Uid", UID)
                    ,new NameValuePair("Key", SECURITY_KEY),
                    new NameValuePair("smsMob",phone),
                    new NameValuePair("smsText",content)};
            post.setRequestBody(data);

            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            System.out.println("statusCode:"+statusCode);
            for(Header h : headers)
            {
                System.out.println(h.toString());
            }
            String result = new String(post.getResponseBodyAsString().getBytes("utf8"));
            System.out.println(result); //打印返回消息状态
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (post != null) {
                post.releaseConnection();
            }
        }
    }
}
