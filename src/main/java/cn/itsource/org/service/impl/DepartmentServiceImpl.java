package cn.itsource.org.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.mapper.DepartmentMapper;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class DepartmentServiceImpl implements IDepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public void save(Department department) {
        departmentMapper.save(department);
    }

    @Override
    public void update(Department department) {
        departmentMapper.update(department);
    }

    @Override
    public void remove(Long id) {
        departmentMapper.remove(id);
    }

    @Override
    public Department selectById(Long id) {
        return departmentMapper.selectById(id);
    }

    @Override
    public List<Department> selectAll() {
        return departmentMapper.selectAll();
    }

    @Override
    public PageList<Department> paging(DepartmentQuery query) {
        Long total = departmentMapper.total(query);
        if (total == null){
            return new PageList<>();
        }
        List<Department> data = departmentMapper.paging(query);
        return new PageList<>(total,data);
    }
}
