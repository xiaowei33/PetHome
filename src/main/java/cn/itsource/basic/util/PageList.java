package cn.itsource.basic.util;

import lombok.Data;
import java.util.List;

/**
 * 分页以后给页面返回的数据
 * @param <T>
 */
@Data
public class PageList<T> {
    //总条数
    private Long total;
    //页面展示的数据
    private List<T> data;

    public PageList() {
    }

    public PageList(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }
}
