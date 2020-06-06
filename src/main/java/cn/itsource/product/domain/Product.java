package cn.itsource.product.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class Product extends BaseDomain {
    private String name;//名称
    private String resources;//资源路径
    private Date offsaletime;//下架时间
    private Date onsaletime;//上架时间
    private Date createtime;//创建时间
    private Integer state;//状态 0下 1上
    private BigDecimal saleprice;//售价
    private BigDecimal costprice;//成本价
    private Long salecount;//销量
    private ProductDetail productDetail = new ProductDetail();
}
