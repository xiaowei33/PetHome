package cn.itsource.user.mapper;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.user.domain.User;
import cn.itsource.user.domain.WxUser;

import java.io.Serializable;
import java.util.List;

public interface WxUserMapper {
    void save(WxUser wxUser);
    void update(User user);
    void remove(Serializable id);
    User loadById(Serializable id);
    List<User> loadAll();
    //分页相关的方法
    //查询总条数
    Long queryCount(BaseQuery query);
    //查询数据
    List<User> queryData(BaseQuery query);
    //查询表里面是否由该openid
    WxUser loadByOpenId(String openid);
}
