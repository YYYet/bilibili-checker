package com.bili.sdk.service.tv.entity.resp.applycaptchainfo;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ApplyCaptchaInfoResp {
    private int code;
    private String message;
    private int ttl;
    private ApplyCaptchaInfo data;
}
