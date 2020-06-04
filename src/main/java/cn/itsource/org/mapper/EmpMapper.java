package cn.itsource.org.mapper;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.query.DepartmentQuery;

import java.util.List;

public interface EmpMapper {
    //添加
    void save(Employee emp);
    //修改
    void update(Employee emp);
    //修改
    void updatename(Employee emp);
    //删除
    void delete(Long id);
    //查询所有
    List<Employee> selectAll();
    Employee selectByName(Employee emp);
   //分页查询
    Long total(DepartmentQuery query);
    List<Employee> paging(DepartmentQuery query);

}
