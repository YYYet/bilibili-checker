/**
  * Copyright 2023 bejson.com
  */
package com.bili.sdk.service.tv.entity.resp.givelikeInfo;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class GiveLikeInfoResp {
    private int code;
    private String message;
    private int ttl;
    private GiveLikeInfo data;
}
