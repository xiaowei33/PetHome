package cn.itsource.product.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.org.domain.Department;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;
import cn.itsource.product.query.ProductQuery;
import cn.itsource.product.service.IProductDetailService;
import cn.itsource.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductDetailService productDetailService;
    @PostMapping
    public PageList<Product> paging(@RequestBody ProductQuery query){
           return productService.queryPage(query);
    }
    @PutMapping//添加或修改
    public AjaxResult save(@RequestBody Product product){
        //System.out.println(product+"1111111111111111");
        if (product.getId()!=null){
            productService.update(product);
            ProductDetail productDetail = product.getProductDetail();
           // System.out.println(productDetail+"-------");
            productDetail.setProduct_id( product.getId());
            //System.out.println(productDetail.getProduct_id());
            productDetailService.updatedetailById(productDetail);
            return AjaxResult.me();
        }else {
            productService.save(product);
            Long id = product.getId();
            ProductDetail productDetail = product.getProductDetail();
            productDetail.setProduct_id( product.getId());
            productDetailService.save(productDetail);
            return AjaxResult.me();
        }
    }
    @RequestMapping("/productDetail/{productId}")
    public ProductDetail detail(@PathVariable("productId") Long productId){
       return productDetailService.getByProductId(productId);
    }
    @DeleteMapping("/{id}")
    public AjaxResult del(@PathVariable("id") Long id){
        try {
            System.out.println(id);
            productService.delete(id);
            productDetailService.delete(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除失败！");
        }
    }
    @PostMapping("/dels")
    public AjaxResult idels(@RequestBody List<Long> ids){
        try {
            productService.ids(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除失败！");
        }
    }
    @PostMapping("/onsale")
    public AjaxResult onsale(@RequestBody List<Long> ids){
        try {
            productService.onsale(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除失败！");
        }
    }
    @PostMapping("/offsale")
    public AjaxResult offsale(@RequestBody List<Long> ids){
        try {
            productService.offsale(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除失败！");
        }
    }
}
