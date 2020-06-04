package cn.itsource.org.domain;


import lombok.Data;

@Data
public class Department {
    //主键id
    private Long id;
    //部门编号
    private String sn;
    //部门名称
    private String name;

    private String dirPath;
    ///0:正常  -1：禁用 1待激活
    private Integer state;

    private Department parent;

    private Employee emp;
}
