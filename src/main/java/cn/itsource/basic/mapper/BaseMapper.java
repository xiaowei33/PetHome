package cn.itsource.basic.mapper;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepartmentQuery;

import java.io.Serializable;
import java.util.List;

public interface BaseMapper<T> {
    void save(T t);
    void update(T t);
    void remove(Serializable id);
    T loadById(Serializable id);
    List<T> loadAll();
    //分页相关的方法
    //查询总条数
    Long queryCount(BaseQuery query);
    //查询数据
    List<T> queryData(BaseQuery query);
}