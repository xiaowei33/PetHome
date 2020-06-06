package cn.itsource.product.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.product.domain.Product;
import cn.itsource.product.mapper.ProductDetailMapper;
import cn.itsource.product.mapper.ProductMapper;
import cn.itsource.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Override
    public void ids(List<Long> ids) {
        for (Long id : ids) {
            productMapper.ids(id);
            productDetailMapper.ids(id);
        }

    }
        //上架
    @Override
    public void onsale(List<Long> ids) {
        Map<String,Object> map=new HashMap<>();
        map.put("onsaleTime",new Date());
        map.put("ids",ids);
        map.put("state",1);
        productMapper.onsale(map);
    }
        //下架
    @Override
    public void offsale(List<Long> ids) {
        Map<String,Object> map=new HashMap<>();
        map.put("offsaleTime",new Date());
        map.put("ids",ids);
        map.put("state",0);
        productMapper.offsale(map);
    }
}
