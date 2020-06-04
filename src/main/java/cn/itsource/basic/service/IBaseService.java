package cn.itsource.basic.service;

import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepartmentQuery;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;


public interface IBaseService<T> {
    void save(T t);
    void update(T t);
    void delete(Serializable id);
    T getById(Serializable id);
    List<T> getAll();
    //分页的方法
    PageList<T> queryPage(BaseQuery query);
}
