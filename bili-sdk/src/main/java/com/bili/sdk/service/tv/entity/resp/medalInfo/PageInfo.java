package com.bili.sdk.service.tv.entity.resp.medalInfo;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class PageInfo {
    private int number;
    private int current_page;
    private boolean has_more;
    private int next_page;
    private int next_light_status;
    private int total_page;
}
