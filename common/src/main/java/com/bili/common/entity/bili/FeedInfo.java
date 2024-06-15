package com.bili.common.entity.bili;

import lombok.Data;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Data
public class FeedInfo {
    GiftInfo gift;
    UserInfo userInfo;
    RoomInfo roomInfo;
    boolean isBox;
    String boxName;
    long num;
}
