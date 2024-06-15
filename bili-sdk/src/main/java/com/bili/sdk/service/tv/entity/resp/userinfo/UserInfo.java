package com.bili.sdk.service.tv.entity.resp.userinfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserInfo {
    private long uid;
    private int silver;
    private int gold;
    private Medal medal;
    private Vip vip;
    private WearTitle wear_title;
    private Exp exp;
    private int room_id;
    private boolean vip_view_status;
    private int guard_count;
}
