package cn.itsource.user.domain;

import cn.itsource.basic.domain.BaseDomain;
import lombok.Data;

@Data
public class WxUser extends BaseDomain {
    private String openid;
    private String nickname;
    private Integer sex;
    private String address;
    private String headimgurl;
    private String unionid;
    private Long user_id;

}
