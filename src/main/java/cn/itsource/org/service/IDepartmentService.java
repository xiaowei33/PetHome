package cn.itsource.org.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepartmentQuery;

import java.util.List;

public interface IDepartmentService {
    //添加
    void save(Department department);
    //修改
    void update(Department department);
    //删除
    void remove(Long id);
    //根据id查询一个
    Department selectById(Long id);
    //查询所有
    List<Department> selectAll();
    //分页
    PageList<Department> paging(DepartmentQuery query);

}
