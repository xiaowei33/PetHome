package cn.itsource.org.domain;

import lombok.Data;

@Data
public class Employee {
    private Long  id;
    private String username;
    private String email;
    private String phone;
    private String password;
    private Integer age;
    ///0:正常  -1：禁用 1待激活
    private Integer state;
    private Department department;




}
