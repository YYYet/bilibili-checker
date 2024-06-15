package com.bili.sdk.service.tv.entity.resp.applycaptchainfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ApplyCaptchaInfo {
    private String type;
    private String token;
    private Geetest geetest;
    private Tencent tencent;
}
