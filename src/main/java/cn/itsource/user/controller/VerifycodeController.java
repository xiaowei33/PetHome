package cn.itsource.user.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.user.query.UserDto;
import cn.itsource.user.service.IVerifycodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/verifycode")
public class VerifycodeController {
    @Autowired
    private IVerifycodeService verifycodeService;
    @PostMapping("/sendSmsCode")
    public AjaxResult sendSmsCode(@RequestBody Map<String,String> params){
    return verifycodeService.sendSmsCode(params);
    }
    @PostMapping("/sendSmsCodeemail")
    public AjaxResult sendSmsCodeemail(@RequestBody Map<String,String> params){
       // System.out.println(params.toString());
        //"email":"XXXX@QQ.COM"
        return verifycodeService.sendSmsCodeemail(params);
    }

}
