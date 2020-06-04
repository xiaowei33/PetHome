package cn.itsource.org.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private IDepartmentService departmentService;
    @PutMapping
    public AjaxResult save(@RequestBody Department department){
        try{
            if (department.getId()==null || department.getId()==0){
                departmentService.save(department);

            }else {
                departmentService.update(department);

            }
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }
    //获取请求后面的id，在根据注解@PathVariable传递给Long id
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id){
        departmentService.remove(id);
    }
    @GetMapping("/{id}")
    public AjaxResult selectById(@PathVariable("id") Long id){
        try{
           departmentService.selectById(id);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }
    @GetMapping
    public List<Department> selectAll(){
        return departmentService.selectAll();
    }
    @PostMapping
    public PageList<Department> paging(@RequestBody DepartmentQuery query){
       // System.out.println(query);
        return departmentService.paging(query);
    }

    @PostMapping("/dels")
    public AjaxResult dels(@RequestBody Long[] ids){
        System.out.println(Arrays.toString(ids));
        try{
            for (Long id : ids) {
                System.out.println(id);
                departmentService.remove(id);
            }
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }

}
