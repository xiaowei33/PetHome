package cn.itsource.org.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.query.DepartmentQuery;

import java.util.List;

public interface IActivationService {

    //修改
    void update(Shop shop);
    //删除
    void remove(Long id);
    //查询所有
    List<Shop> selectAll();
    //分页
    PageList<Shop> paging(DepartmentQuery query);

}
