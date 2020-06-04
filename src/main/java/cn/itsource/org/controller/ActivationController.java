package cn.itsource.org.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Shop;
import cn.itsource.org.query.DepartmentQuery;
import cn.itsource.org.service.IActivationService;
import cn.itsource.org.service.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/activation")
public class ActivationController {
    @Autowired
    private IActivationService activationService;
    //添加或修改
    @PutMapping
    public AjaxResult saveandupdate(@RequestBody Shop shop){
       // System.out.println(emp.getId());
        try{
            activationService.update(shop);
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
            activationService.remove(id);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }
    }
    //查询所有
    @GetMapping
    public List<Shop> selectAll(){
        return activationService.selectAll();
    }
    //分页查询
    @PostMapping
    public PageList<Shop> paging(@RequestBody DepartmentQuery query){
        return activationService.paging(query);
    }
    //批量删除
    @PostMapping("/dels")
    public AjaxResult dels(@RequestBody Long[] ids){

        try{
            for (Long id : ids) {
                //System.out.println(id);
                activationService.remove(id);
            }
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }

}
