package cn.itsource.product.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.product.domain.Product;
import cn.itsource.product.domain.ProductDetail;

import java.io.Serializable;
import java.util.List;

public interface ProductDetailMapper extends BaseMapper<ProductDetail> {
    ProductDetail loadByProductId(Long productId);
    void removeByProductId(Serializable id);
    void updatedetailById(ProductDetail productDetail);
    void ids(long id);

}
