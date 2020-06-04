package cn.itsource.user.query;

import lombok.Data;

import java.util.Date;
@Data
public class UserDto {
    private String phone;
    private String password;
    private String passwordRepeat;
    private String code;
}
