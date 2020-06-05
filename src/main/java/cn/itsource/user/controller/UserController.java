package cn.itsource.user.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.MD5Utils;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.user.domain.User;
import cn.itsource.user.query.UserDto;
import cn.itsource.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @PostMapping("/register/phone")
    public AjaxResult registersave(@RequestBody UserDto userDto){
        return userService.registersave(userDto);

    }
    @PostMapping("/login")
    public AjaxResult login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestBody Map<String,String> params, HttpServletResponse response){
        System.out.println("111111111111111111111111");
        return userService.wxLogin(params,response);
    }

    @PostMapping("/binder")
    public AjaxResult binder(@RequestBody Map<String,String> params){
        return userService.binder(params);
    }
}
