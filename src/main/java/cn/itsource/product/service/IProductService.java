package cn.itsource.product.service;

import cn.itsource.basic.service.IBaseService;
import cn.itsource.product.domain.Product;

import java.util.List;

public interface IProductService extends IBaseService<Product> {
    void ids(List<Long> ids);

    void onsale(List<Long> ids);

    void offsale(List<Long> ids);
}
