package com.bili.sdk.service.tv.entity.resp.shareroominfo;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class ShareRoomInfoResp {
    private int code;
    private String message;
    private int ttl;
    private ShareRoomInfo data;
}
