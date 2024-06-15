package com.bili.sdk.service.web.entity.resp.verifyqrcodeinfo;


import lombok.Data;

@Data
public class VerifyQRcodeInfoResp {

    private int code;
    private String message;
    private int ttl;
    private VerifyQRcodeInfo data;


}
