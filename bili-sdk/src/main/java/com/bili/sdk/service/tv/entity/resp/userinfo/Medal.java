package com.bili.sdk.service.tv.entity.resp.userinfo;

import lombok.Data;
import lombok.ToString;


@Data
@ToString
public class Medal {
    private String medal_name;
    private int level;
    private long color;
    private String medal_icon_url;
    private long target_id;
    private long medal_color_start;
    private long medal_color_end;
    private long medal_color_border;
    private int is_lighted;
    private int guard_level;
    private String guard_icon;
    private String honor_icon;
}
