package com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class VerifyQRcodeInfoResp {

    private int code;
    private String message;
    private int ttl;
    private VerifyQRcodeInfo data;

}
