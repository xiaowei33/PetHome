package cn.itsource.user.service.impl;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.basic.util.*;
import cn.itsource.user.constants.WxConstants;
import cn.itsource.user.domain.User;
import cn.itsource.user.domain.WxUser;
import cn.itsource.user.mapper.UserMapper;
import cn.itsource.user.mapper.WxUserMapper;
import cn.itsource.user.query.UserDto;
import cn.itsource.user.query.UserEmail;
import cn.itsource.user.service.IUserService;
import cn.itsource.user.service.IVerifycodeService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.*;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper mapper;
    @Autowired
    private IVerifycodeService verifycodeService;
    @Autowired
    private WxUserMapper wxUserMapper;

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

    //微信登录
    @Override
    public AjaxResult wxLogin(Map<String, String> params, HttpServletResponse response) {
        //获取参数
        String code = params.get("code");
        System.out.println(code);
        String binderUrl = params.get("binderUrl");
        System.out.println(binderUrl);
        //发送请求获取openId
        String url = WxConstants.GET_OPENID_URL.replace("APPID", WxConstants.APPID).replace("SECRET",WxConstants.SECURITY).replace("CODE",code);
        //获取里面的json字符串
        String jsonStr = HttpClientUtils.httpGet(url);
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String accessToken = jsonObject.getString("access_token");
        String openid = jsonObject.getString("openid");
        //判断用户是否以进行绑定，绑定了直接免密登录
        WxUser wxUser = wxUserMapper.loadByOpenId(openid);
        if (wxUser!=null && wxUser.getUser_id()!=null){
            User user = mapper.loadById(wxUser.getUser_id());
            //返回登录成功，在redis存放token，返回对象为token和当前登录用户
            //在redis存放token
            String uToken = UUID.randomUUID().toString();
           //设置存放的key和过期时间
           RedisUtils.INSTANCE.set(uToken,JSONObject.toJSONString(user),30*60);
           //查询后返回的对象为token和当前登录的微信用户
            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("uToken",uToken);
            resultObj.put("user",user);
            return  AjaxResult.me().setResultObj(resultObj);
        }else {
            //未绑定，跳转到绑定页面
            //设置url地址
            binderUrl=binderUrl+"?access_token="+accessToken+"&openId="+openid;
            return AjaxResult.me().setSuccess(false).setMessage("binder").setResultObj(binderUrl);
        }

    }
    //微信绑定
    @Override
    public AjaxResult binder(Map<String, String> params) {
        //获取参数
        String username = params.get("username");
        String password = params.get("password");
        String accessToken = params.get("accessToken");
        String openId = params.get("openId");
        //获取微信用户
        String url = WxConstants.GET_WXUSER_URL.replace("ACCESS_TOKEN", accessToken)
                .replace("OPENID", openId);
        String jsonStr = HttpClientUtils.httpGet(url);
        //将获取的资源转化为wxuser对象
        WxUser wxUser = jsonStr2WxUser(jsonStr);
        //判断用户是否存在
        User user = mapper.loadByUsername(username);
        //有就绑定，没有就创建
        if (user!=null){
            if (!MD5Utils.encrypByMd5(password+user.getSalt()).equals(user.getPassword())){
                return AjaxResult.me().setSuccess(false).setMessage("用户名或密码错误！");
            }
        }else {
            //不存在就创建用户
            User user1 = new User();
            user1.setUsername(username);
            String salt = StrUtils.getComplexRandomString(32);
            user1.setSalt(salt);
            String securityPwd = MD5Utils.encrypByMd5(password+salt);
            user1.setPassword(securityPwd);
            user1.setState(1);
            user1.setCreatetime(new Date());
            user1.setHeadImg(wxUser.getHeadimgurl());
            mapper.save(user1);
            user = user1;
        }
        wxUser.setUser_id(user.getId());
        wxUserMapper.save(wxUser);

        //免密登录 // 返回登录成功，在redis存放token，返回对象为token和当前登录用户
        // 在redis存放token
        String uToken = UUID.randomUUID().toString();
        // 在redis存放java对象使用json格式的字符串，获取到json格式字符串可以换为java对象
        RedisUtils.INSTANCE.set(uToken, JSONObject.toJSONString(user),30*60);
        //返回对象为token和当前登录用户
        Map<String, Object> resultObj = new HashMap<>();
        resultObj.put("uToken",uToken);
        resultObj.put("user",user);
        return AjaxResult.me().setResultObj(resultObj);
    }
    //amail注册
    @Override
    public AjaxResult emailsave(UserEmail userEmail) {
        //前台数据验证
        if (userEmail.getEmail()==null)
            return AjaxResult.me().setSuccess(false).setMessage("请输入email");
        if (!userEmail.getCode().equals(verifycodeService.getRegisterSmsCodeemail(userEmail.getEmail()))){

            System.out.println(verifycodeService.getRegisterSmsCodeemail(userEmail.getCode())+"获取redis");
            return AjaxResult.me().setSuccess(false).setMessage("验证码错误");
        }
        if (!userEmail.getPassword().equals(userEmail.getPasswordRepeat()))
            return AjaxResult.me().setSuccess(false).setMessage("密码不一致");

        //对存入的数据进行处理

        User user1 = initUserEamil(userEmail);
        //保存
        mapper.saveemail(user1);
        return AjaxResult.me();
    }


    private WxUser jsonStr2WxUser(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        WxUser wxUser = new WxUser();
        wxUser.setOpenid(jsonObject.getString("openid"));
        wxUser.setNickname(jsonObject.getString("nickname"));
        wxUser.setSex(jsonObject.getInteger("sex"));
        wxUser.setAddress(null);
        wxUser.setHeadimgurl(jsonObject.getString("headimgurl"));
        wxUser.setUnionid(jsonObject.getString("unionid"));
        return wxUser;
    }

    private User initUserEamil(UserEmail userEmail){
        User user = new User();
        user.setUsername(userEmail.getEmail());
        //密码处理
        String salt = StrUtils.getComplexRandomString(32);
        //保存密码先和盐值结合，在使用md5加密
        String securityPwd = MD5Utils.encrypByMd5(userEmail.getPassword()+salt);
        user.setPassword(securityPwd);
        user.setState(1);
        user.setCreatetime(new Date());
        user.setEmail(user.getEmail());
        user.setSalt(salt);
        return user;
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
