package cn.itsource.basic.query;

import lombok.Data;

@Data
public class BaseQuery {
    //当前页
    private Long currentPage=1L;
    private Long  pageSize=10L;
    //关键字查询
    private String keywords;
    //计算开始索引
    public Long getStart(){
        return (currentPage-1)*pageSize;
    }
}
