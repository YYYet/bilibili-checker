package com.bili.common.entity.bili;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Data
public class UserInfo implements Serializable {
    String userName;
    String uid;
    String avatar;
    String wealth;
    double danMuCount;
    double visitCount;
    // 神秘人标识？
    boolean isMystery;
}
