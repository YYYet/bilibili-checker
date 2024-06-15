package com.bili.common.entity.bili;

import lombok.Data;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Data
public class GiftInfo {
    String giftName;
    long giftPrice;
    long actualPrice;
    boolean isFree;
}
