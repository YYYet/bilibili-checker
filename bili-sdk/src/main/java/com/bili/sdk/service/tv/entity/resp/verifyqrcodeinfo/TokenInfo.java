package com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TokenInfo {

    private long mid;
    private String access_token;
    private String refresh_token;
    private long expires_in;

}
