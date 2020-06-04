package cn.itsource.org.mapper;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepartmentQuery;

import java.util.List;

public interface DepartmentMapper {
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
    //查询总条数
    Long total(DepartmentQuery query);
    //分页
    List<Department> paging(DepartmentQuery query);

}
