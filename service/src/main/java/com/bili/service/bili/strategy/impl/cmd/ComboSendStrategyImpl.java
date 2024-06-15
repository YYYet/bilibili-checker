package com.bili.service.bili.strategy.impl.cmd;


import com.bili.common.entity.bili.FeedInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("COMBO_SEND")
@Slf4j
public class ComboSendStrategyImpl implements CmdStrategy {

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        FeedInfo giftFromDanMu = DanMuUtil.getGiftFromDanMu(row);
        UserInfo userInfo = giftFromDanMu.getUserInfo();
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        giftFromDanMu.setUserInfo(userInfo);
        giftFromDanMu.setRoomInfo(roomInfoFromRow);
        log.info("房间:【{}】 用户 {}({}) 投喂了{}礼物 {}({}) x {}", roomInfoFromRow.getRoomId(), userInfo.getUserName(), userInfo.getUid(),
                giftFromDanMu.getGift().isFree()?"免费":"付费", giftFromDanMu.getGift().getGiftName(), giftFromDanMu.getGift().getGiftPrice(), giftFromDanMu.getNum());
        return null;
    }
}
