package cn.itsource.product.mapper;

import cn.itsource.basic.mapper.BaseMapper;
import cn.itsource.product.domain.Product;

import java.util.List;
import java.util.Map;

public interface ProductMapper extends BaseMapper<Product> {

    void ids(long id);

    void onsale(Map<String, Object> map);

    void offsale(Map<String, Object> map);
}
