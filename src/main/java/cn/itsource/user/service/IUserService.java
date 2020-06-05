package cn.itsource.user.service;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.service.IBaseService;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.user.domain.User;
import cn.itsource.user.query.UserDto;

import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IUserService {
    void save(User user);
    void update(User user);
    void delete(Serializable id);
    User getById(Serializable id);
    List<User> getAll();
    //分页的方法
    PageList<User> queryPage(BaseQuery query);
    //注册
    AjaxResult registersave(UserDto userDto);
    //登录
    AjaxResult login(User user);
    //微信登录
    AjaxResult wxLogin(Map<String, String> params, HttpServletResponse response);
    //微信绑定
    AjaxResult binder(Map<String, String> params);
}
