package cn.itsource.org.query;

import lombok.Data;

@Data
public class DepartmentQuery {
    //页数
    private Long currentPage = 1L;
    //条数
    private Long pageSize = 10L;

    //高级查询
    private String keywords;
    //计算查询条数
    public Long getStart(){
        return (currentPage-1)* pageSize;
    }

}
