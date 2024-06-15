package com.bili.sdk.service.tv.entity.resp.common.Resp;

import lombok.Data;
import lombok.ToString;


@ToString
@Data
public class CommonResp {
    private int code;
    private String message;
    private int ttl;
    private CommonData data;
}
