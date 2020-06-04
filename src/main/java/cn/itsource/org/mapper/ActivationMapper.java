package cn.itsource.org.mapper;

import cn.itsource.org.domain.Employee;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.query.DepartmentQuery;

import java.util.List;

public interface ActivationMapper {
    //修改
    void update(Shop shop);
    //删除
    void delete(Long id);
    //查询所有
    List<Shop> selectAll();
    //分页查询
    Long total(DepartmentQuery query);
    List<Shop> paging(DepartmentQuery query);
}
