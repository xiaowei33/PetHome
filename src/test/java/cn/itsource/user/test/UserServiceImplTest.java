package cn.itsource.user.test;

import cn.itsource.basic.util.MD5Utils;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {
    @Autowired
    private IUserService userService;
    @Test
    public void test1(){
        System.out.println(userService.getById(25L));
    }
    @Test
    public void test2(){
        String a="2";
        String salt = StrUtils.getComplexRandomString(32);
        //System.out.println(salt);
        //保存密码先和盐值结合，在使用md5加密
        String securityPwd = MD5Utils.encrypByMd5(a+salt);
        System.out.println(securityPwd);//873a1e589eb6801de800b77f3823b0d9
        String securityPwd2 = MD5Utils.encrypByMd5(a+salt);
        System.out.println(securityPwd2);//e6b29d20d7b0294af6d597e4cca51fc7//c4ca4238a0b923820dcc509a6f75849b
        System.out.println(securityPwd.equals(securityPwd2));
    }
}