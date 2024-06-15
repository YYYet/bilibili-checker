package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VerifyTokenInfoResp {
    private int code;
    private String message;
    private int ttl;
    private VerifyTokenInfo data;
}
