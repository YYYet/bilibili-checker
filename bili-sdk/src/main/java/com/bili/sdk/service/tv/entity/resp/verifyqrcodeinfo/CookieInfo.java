package com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class CookieInfo {
    private List<Cookies> cookies;
    private List<String> domains;
}
