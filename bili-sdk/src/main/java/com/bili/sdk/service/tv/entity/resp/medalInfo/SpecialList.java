
package com.bili.sdk.service.tv.entity.resp.medalInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SpecialList {
    private Medal medal;
    private AnchorInfo anchor_info;
    private Superscript superscript;
    private RoomInfo room_info;
}
