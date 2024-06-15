package com.bili.sdk.service.web.entity.resp.verifyqrcodeinfo;

import lombok.Data;

@Data
public class VerifyQRcodeInfo {

    private String url;
    private String refresh_token;
    private int timestamp;
    private long code;
    private String message;

}
