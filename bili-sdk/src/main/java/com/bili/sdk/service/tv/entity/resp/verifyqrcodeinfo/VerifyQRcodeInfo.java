/**
  * Copyright 2023 bejson.com
  */
package com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;


@Data
@ToString
public class VerifyQRcodeInfo {

    private boolean is_new;
    private long mid;
    private String access_token;
    private String refresh_token;
    private long expires_in;
    private TokenInfo token_info;
    private CookieInfo cookie_info;
    private List<String> sso;
    private String hint;

}
