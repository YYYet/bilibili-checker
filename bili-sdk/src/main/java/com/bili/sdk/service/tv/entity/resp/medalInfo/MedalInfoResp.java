package com.bili.sdk.service.tv.entity.resp.medalInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MedalInfoResp {
    private int code;
    private String message;
    private int ttl;
    private MedalInfo data;
}
