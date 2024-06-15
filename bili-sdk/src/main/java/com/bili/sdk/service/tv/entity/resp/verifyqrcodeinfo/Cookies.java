package com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Cookies {
    private String name;
    private String value;
    private int http_only;
    private long expires;
    private int secure;
}
