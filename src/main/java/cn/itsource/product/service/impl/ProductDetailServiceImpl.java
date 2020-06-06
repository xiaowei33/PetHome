package cn.itsource.product.service.impl;

import cn.itsource.basic.service.impl.BaseServiceImpl;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;
import cn.itsource.product.mapper.ProductDetailMapper;
import cn.itsource.product.service.IProductDetailService;
import cn.itsource.product.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductDetailServiceImpl extends BaseServiceImpl<ProductDetail> implements IProductDetailService {
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Override
    public ProductDetail getByProductId(Long productId) {
        return productDetailMapper.loadByProductId(productId);
    }

    @Override
    public void updatedetailById(ProductDetail productDetail) {
        System.out.println(productDetail+"sercice");
        productDetailMapper.updatedetailById(productDetail);
    }



}
