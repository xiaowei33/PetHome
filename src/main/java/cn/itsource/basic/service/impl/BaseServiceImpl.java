package cn.itsource.basic.service.impl;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.basic.query.BaseQuery;
import cn.itsource.basic.service.IBaseService;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.mapper.DepartmentMapper;
import cn.itsource.org.query.DepartmentQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
@Service
public class BaseServiceImpl<T> implements IBaseService<T> {

    @Autowired
    private BaseMapper<T> mapper;
    @Override
    public void save(T t) {
        mapper.save(t);
    }

    @Override
    public void update(T t) {
        mapper.update(t);
    }

    @Override
    public void delete(Serializable id) {
        mapper.remove(id);

    }

    @Override
    public T getById(Serializable id) {
        return mapper.loadById(id);
    }

    @Override
    public List<T> getAll() {
        return mapper.loadAll();
    }

    @Override
    public PageList<T> queryPage(BaseQuery query) {
        Long count = mapper.queryCount(query);
        if (count ==null || count<1){
            return new PageList<T>();
        }
        List<T> data = mapper.queryData(query);

        return new PageList<>(count,data);
    }
}
