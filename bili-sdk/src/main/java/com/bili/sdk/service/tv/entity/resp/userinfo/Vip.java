package com.bili.sdk.service.tv.entity.resp.userinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Vip {
    private int vip;
    private int svip;
    private String vip_time;
    private String svip_time;
}
