package cn.itsource.org.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.mapper.EmpMapper;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmpServiceImpl implements IEmpService {
    @Autowired
    private EmpMapper empMapper;

    @Override
    public void save(Employee emp) {
        empMapper.save(emp);
    }

    @Override
    public void update(Employee emp) {
        empMapper.update(emp);
    }

    @Override
    public void remove(Long id) {
        empMapper.delete(id);
    }
    @Override
    public List<Employee> selectAll() {
        return empMapper.selectAll();
    }

    @Override
    public PageList<Employee> paging(DepartmentQuery query) {
        //System.out.println(query.getCurrentPage()+"-------------------");
        Long total = empMapper.total(query);
        if (total==null){
            return new PageList<>();
        }
        List<Employee> data = empMapper.paging(query);
        return new PageList<>(total,data);
    }
}
