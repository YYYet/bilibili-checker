package com.bili.sdk.service.tv.entity.resp.senddanmukuinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DanMuKuInfoResp {
    private int code;
    private DanMuInfo data;
    private String message;
    private String msg;
}
