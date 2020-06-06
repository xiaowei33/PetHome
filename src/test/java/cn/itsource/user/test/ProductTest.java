package cn.itsource.user.test;

import cn.itsource.basic.util.MD5Utils;
import cn.itsource.basic.util.StrUtils;
import cn.itsource.product.domain.Product;
import cn.itsource.product.service.IProductService;
import cn.itsource.user.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ProductTest {
   @Autowired
   private IProductService productService;
    @Test
    public void test1(){
        Product byId = productService.getById(8L);
        System.out.println(byId);
    }

}