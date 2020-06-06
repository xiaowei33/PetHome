package cn.itsource.user.service;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.user.query.UserDto;

import java.util.Map;

public interface IVerifycodeService {
    //发送短信
    AjaxResult sendSmsCode(Map<String, String> params);
    //获取验证码
    String getRegisterSmsCode(String phone);
    //发送邮件
    AjaxResult sendSmsCodeemail(Map<String, String> params);
    //获取验证码
    String getRegisterSmsCodeemail(String email);
}
