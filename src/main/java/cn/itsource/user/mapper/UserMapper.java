package cn.itsource.user.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.basic.query.BaseQuery;
import cn.itsource.user.domain.User;

import java.io.Serializable;
import java.util.List;

public interface UserMapper{
    void save(User user);
    void update(User user);
    void remove(Serializable id);
    User loadById(Serializable id);
    List<User> loadAll();
    //分页相关的方法
    //查询总条数
    Long queryCount(BaseQuery query);
    //查询数据
    List<User> queryData(BaseQuery query);
    //通过电话查询
    User loadByphone(String phone);
    User loadByUsername(String username);
    //邮件注册保存
    void saveemail(User user1);
<<<<<<< HEAD
    //邮箱查询
    User loadByEmail(String email);
=======
>>>>>>> 1292f466079b1d5a0d1df4f431ddacc4738f6b6e
}
