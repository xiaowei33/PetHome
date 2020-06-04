package cn.itsource.user.service.impl;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.util.*;
import cn.itsource.user.domain.User;
import cn.itsource.user.mapper.UserMapper;
import cn.itsource.user.query.UserDto;
import cn.itsource.user.service.IUserService;
import cn.itsource.user.service.IVerifycodeService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private IVerifycodeService verifycodeService;
    @Override
    public void save(User user) {
        mapper.save(user);
    }

    @Override
    public void update(User user) {
        mapper.update(user);
    }

    @Override
    public void delete(Serializable id) {
        mapper.remove(id);

    }

    @Override
    public User getById(Serializable id) {
        return mapper.loadById(id);
    }

    @Override
    public List<User> getAll() {
        return mapper.loadAll();
    }

    @Override
    public PageList<User> queryPage(BaseQuery query) {
        Long count = mapper.queryCount(query);
        if (count ==null || count<1){
            return new PageList<User>();
        }
        List<User> data = mapper.queryData(query);

        return new PageList<>(count,data);
    }
    //注册
    @Override
    public AjaxResult registersave(UserDto userDto) {
        //前端传过来非null判断
        if (StringUtils.isBlank(userDto.getPhone())|| StringUtils.isBlank(userDto.getCode())
                || StringUtils.isBlank(userDto.getPassword()) || StringUtils.isBlank(userDto.getPasswordRepeat()))
            return AjaxResult.me().setSuccess(false).setMessage("请输入相关参数");

        //密码和确认密码校验
        if (!userDto.getPassword().equals(userDto.getPasswordRepeat()))
            return AjaxResult.me().setSuccess(false).setMessage("两次密码不一致");
        //短信验证码校验
        String code = verifycodeService.getRegisterSmsCode(userDto.getPhone());
        if (StringUtils.isBlank(code)) {
            return AjaxResult.me().setSuccess(false).setMessage("请输入短信验证码！");
        }
        if (!userDto.getCode().equals(code))
            return AjaxResult.me().setSuccess(false).setMessage("验证码错误");
        //用户是否存在校验
        User user = mapper.loadByphone(userDto.getPhone());
        if (user != null)
            return AjaxResult.me().setSuccess(false).setMessage("用户已存在！");

        //校验完成进行保存
        User user2 = initUser(userDto);
        mapper.save(user2);
        return AjaxResult.me();
    }
    //登录
    @Override
    public AjaxResult login(User user) {
        //通过用户名查询用户
        User user1 = mapper.loadByUsername(user.getUsername());
        //System.out.println(user1.toString());
        //判断是否存在
        if (user1.getUsername()== null)
            return AjaxResult.me().setSuccess(false).setMessage("请输入正确的用户名！");
        //判断状态是否为1
        if (user1.getState()==0)
            return AjaxResult.me().setSuccess(false).setMessage("联系管理员激活！");
        //加密传进来的密码
        //System.out.println(user.getPassword()+"pwd");
        //System.out.println(user1.getSalt());
        String pwd = MD5Utils.encrypByMd5(user.getPassword()+user1.getSalt());
        /*System.out.println(pwd+"传");
        System.out.println(user1.getPassword());*/
        if (!user1.getPassword().equals(pwd))
            return AjaxResult.me().setSuccess(false).setMessage("请输入正确的密码！");
        //在redis上存放token，返回为token和当前登录用户
        //存放redis在token中
        String uToken = UUID.randomUUID().toString();
        //将字符串转为json格式
        RedisUtils.INSTANCE.set(uToken, JSONObject.toJSONString(user1),30*60);
        //返回为token和当前登录用户，存放在map中
        Map<String,Object> resultObj = new HashMap<>();
        resultObj.put("uToken",uToken);
        resultObj.put("user",user1);
        return AjaxResult.me().setResultObj(resultObj);
    }

    //将UserDto转成user
    private User initUser(UserDto userDto) {
        User user = new User();
        //转换
        //设置默认值为phone
        user.setUsername(userDto.getPhone());
        //1设置默认激活
        user.setState(1);
        user.setPhone(userDto.getPhone());
        user.setCreatetime(new Date());
        //生成盐值
        String salt = StrUtils.getComplexRandomString(32);
        //保存密码先和盐值结合，在使用md5加密
        String securityPwd = MD5Utils.encrypByMd5(userDto.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(securityPwd);
        return user;
    }
}
