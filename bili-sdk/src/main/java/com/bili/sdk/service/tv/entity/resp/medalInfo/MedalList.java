package com.bili.sdk.service.tv.entity.resp.medalInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MedalList {
    private Medal medal;
    private AnchorInfo anchor_info;
    private String superscript;
    private RoomInfo room_info;
}
