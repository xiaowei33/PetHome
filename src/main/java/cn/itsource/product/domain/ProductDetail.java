package cn.itsource.product.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import java.lang.ref.SoftReference;
@Data
public class ProductDetail extends BaseDomain {
    private Long product_id;
    private String intro;
    private String orderNotice;
}
