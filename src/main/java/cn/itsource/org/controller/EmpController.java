package cn.itsource.org.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Employee;
import cn.itsource.org.mapper.EmpMapper;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IEmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private IEmpService empService;
    //添加或修改
    @PutMapping
    public AjaxResult saveandupdate(@RequestBody Employee emp){
       // System.out.println(emp.getId());
        try{
            if (emp.getId()!= null){
                empService.update(emp);
                return AjaxResult.me();
            }
            empService.save(emp);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }
    }
    //删除
    @DeleteMapping(value = "/{id}")
    public AjaxResult delete(@PathVariable("id") Long id){
        try{
            empService.remove(id);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }
    }
    //查询所有
    @GetMapping
    public List<Employee> selectAll(){
        return empService.selectAll();
    }
    //分页查询
    @PostMapping
    public PageList<Employee> paging(@RequestBody DepartmentQuery query){
        return empService.paging(query);
    }
    //批量删除
    @PostMapping("/dels")
    public AjaxResult dels(@RequestBody Long[] ids){
        System.out.println(Arrays.toString(ids));
        try{
            for (Long id : ids) {
                //System.out.println(id);
                empService.remove(id);
            }
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }
}
