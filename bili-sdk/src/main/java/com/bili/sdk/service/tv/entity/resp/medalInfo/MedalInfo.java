package com.bili.sdk.service.tv.entity.resp.medalInfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MedalInfo {
    private List<MedalList> list;
    private List<SpecialList> special_list;
    private String bottom_bar;
    private PageInfo page_info;
    private int total_number;
    private int has_medal;
}
