package com.bili.sdk.service.tv.entity.resp.userinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfoResp {
    private int code;
    private String message;
    private int ttl;
    private UserInfo data;
}
