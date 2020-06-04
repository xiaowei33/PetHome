package cn.itsource.user.service.impl;

import cn.itsource.basic.util.*;
import cn.itsource.user.constants.SmsConstants;
import cn.itsource.user.service.IVerifycodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class VerifycodeServiceImpl implements IVerifycodeService {
    //发送短信
    @Override
    public AjaxResult sendSmsCode(Map<String, String> params) {
        //获取电话号码并校验手机好是否正确
        String phone = params.get("phone");
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
                 if(phone.length() != 11){
                         return AjaxResult.me().setSuccess(false).setMessage("请输入正确的手机号位数");
                     }else {
                     Pattern p = Pattern.compile(regex);
                     Matcher m = p.matcher(phone);
                     boolean isMatch = m.matches();
                     if (isMatch==false) {
                         return AjaxResult.me().setSuccess(false).setMessage("请输入正确的手机号");
                     }
                 }
                 //手机号验证成功
        //设置key值，将名称抽取到常量类里面
        String Key = SmsConstants.REGISTER_CODE_PREFIX + phone;
         //获取redis里面的值,看是否存在
        String codeForRedis = RedisUtils.INSTANCE.get(Key);
        String code = null;
        if (StringUtils.isNoneBlank(codeForRedis)){
            //获取存入时间
            String time = codeForRedis.split(":")[1];
            //判断是否过了重发时间（获取当前时间-存入时间）
            if (System.currentTimeMillis()-Long.valueOf(time)>=60*1000){
                code = codeForRedis.split(":")[0];
            }
        }
        if (code==null){
            //生产4位验证码
            code = StrUtils.getComplexRandomString(4);
        }
        //放入redis
        RedisUtils.INSTANCE.set(Key,code+":"+System.currentTimeMillis(),5*60);
        //发送验证码，使用httpclient像服务发送http请求
        System.out.println(code);
        String content = "您的验证码为"+code+",请在五分钟之内使用！";
        SmsUtils.sendSmsCode(phone,content);
        return AjaxResult.me();
    }

    @Override
    public String getRegisterSmsCode(String phone) {
        String Key = SmsConstants.REGISTER_CODE_PREFIX + phone;
        //获取redis里面key的数据
        String codeForRedis = RedisUtils.INSTANCE.get(Key);
        if (StringUtils.isNoneBlank(codeForRedis))
            return codeForRedis.split(":")[0];
        return null;
    }

}
