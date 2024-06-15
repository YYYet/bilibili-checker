package com.bili.service.bili.strategy.impl.type;


import cn.hutool.core.date.DateUtil;
import com.bili.common.entity.bili.FeedInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.TypeStrategy;
import com.bili.service.db.UserFeedInfoServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("GIFT")
@Slf4j
public class GiftTypeStrategyImpl implements TypeStrategy {

    @Resource
    private UserFeedInfoServiceImpl userFeedInfoService;

    @Resource
    private RecordServiceImpl recordService;

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        FeedInfo giftFromDanMu = DanMuUtil.getGiftFromDanMu(row);
        UserInfo userInfoFromDanMu = DanMuUtil.getUserInfoFromDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        giftFromDanMu.setUserInfo(userInfoFromDanMu);
        giftFromDanMu.setRoomInfo(roomInfoFromRow);

        userFeedInfoService.recordUserFeed(row);


        if (giftFromDanMu.isBox()) {
            log.info("房间:【{}】 用户 {}({}) 投喂了{} 爆出了{}({}) x {}", roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUserName(), userInfoFromDanMu.getUid(),
                    giftFromDanMu.getBoxName(), giftFromDanMu.getGift().getGiftName(), giftFromDanMu.getGift().getGiftPrice(), giftFromDanMu.getNum());
        }else {
            log.info("房间:【{}】 用户 {}({}) 投喂了{}礼物 {}({}) x {}", roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUserName(), userInfoFromDanMu.getUid(),
                    giftFromDanMu.getGift().isFree()?"免费":"付费", giftFromDanMu.getGift().getGiftName(), giftFromDanMu.getGift().getGiftPrice(), giftFromDanMu.getNum());
        }


        recordService.recordGiftPrice2Redis(giftFromDanMu);

        return null;
    }

}
