
package com.bili.sdk.service.tv.entity.resp.medalInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Medal {
    private long uid;
    private long target_id;
    private String target_name;
    private long medal_id;
    private int level;
    private String medal_name;
    private long medal_color;
    private int intimacy;
    private int next_intimacy;
    private int day_limit;
    private int today_feed;
    private long medal_color_start;
    private long medal_color_end;
    private long medal_color_border;
    private int is_lighted;
    private int guard_level;
    private int wearing_status;
    private int medal_icon_id;
    private String medal_icon_url;
    private String guard_icon;
    private String honor_icon;
    private boolean can_delete;
}
