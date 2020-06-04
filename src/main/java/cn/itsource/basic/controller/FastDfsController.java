package cn.itsource.basic.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.FastDfsUtil;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dfs")
public class FastDfsController {
    @PostMapping
    public AjaxResult upload(@RequestParam(required = true,value = "file") MultipartFile file){
        try{
            //获取原始文件名字
            String originalFilename = file.getOriginalFilename();
            //System.out.println(originalFilename);
            //获取文件的后缀名
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
            //文件上传
            String filePath =  FastDfsUtil.upload(file.getBytes(), extName);
            //把返回的地址 ResultObj
            return AjaxResult.me().setResultObj(filePath); //把上传后的路径
        }catch (Exception e){
            e.printStackTrace();
            return  new AjaxResult(e.getMessage());
        }
    }
    @DeleteMapping
    public AjaxResult delete(@RequestParam(value = "path") String path){
       // System.out.println(path+"11111111111111");
        /*分割前台传过来的地址
                分为组名和图片名*/
        String[] split = path.split("/");

        String path1=split[2]+"/"+split[3]+"/"+split[4]+"/"+split[5];
        System.out.println(path1);
        try{
            FastDfsUtil.delete(split[1],path1);
            return AjaxResult.me();
        }catch (Exception e){
            e.printStackTrace();
            return new AjaxResult("操作失败"+e.getMessage());
        }

    }
}
