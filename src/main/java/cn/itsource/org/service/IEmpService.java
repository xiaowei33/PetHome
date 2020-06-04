package cn.itsource.org.service;

import cn.itsource.basic.util.PageList;

import cn.itsource.org.domain.Employee;
import cn.itsource.org.query.DepartmentQuery;


import java.util.List;

public interface IEmpService {
    //添加
    void save(Employee emp);
    //修改
    void update(Employee emp);
    //删除
    void remove(Long id);
    //查询所有
    List<Employee> selectAll();
    //分页
    PageList<Employee> paging(DepartmentQuery query);

}
