package cn.itsource.user.query;

import lombok.Data;

@Data
public class UserEmail {
    private String email;
    private String password;
    private String passwordRepeat;
    private String code;
}
