/**
  * Copyright 2024 json.cn
  */
package com.bili.sdk.service.web.entity.resp.qrcodeinfo;

import lombok.Data;

@Data
public class QRcodeInfoResp {

    private int code;
    private String message;
    private int ttl;
    private QRcodeInfo data;


}
