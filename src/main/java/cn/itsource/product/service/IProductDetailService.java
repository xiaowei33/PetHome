package cn.itsource.product.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;

public interface IProductDetailService extends IBaseService<ProductDetail> {
    ProductDetail getByProductId(Long productId);
   void updatedetailById(ProductDetail productDetail);
}
