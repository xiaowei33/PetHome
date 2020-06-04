package cn.itsource.org.domain;

import lombok.Data;

import java.util.Date;
@Data
public class Shop {
    private Long id;
    private String name;
    private String tel;
    private Date registerTime;
    ///0:正常  -1：禁用 1待激活
    private Integer state = 1;
    private String address;
    private String logo;

    private Employee employee;
}
