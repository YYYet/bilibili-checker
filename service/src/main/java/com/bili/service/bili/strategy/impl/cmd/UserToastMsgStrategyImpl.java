package com.bili.service.bili.strategy.impl.cmd;



import com.bili.common.entity.bili.FeedInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.db.UserFeedInfoServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("USER_TOAST_MSG")
@Slf4j
public class UserToastMsgStrategyImpl implements CmdStrategy {
    @Resource
    RecordServiceImpl recordService;
    @Resource
    private UserFeedInfoServiceImpl userFeedInfoService;

    @Override
    public JsonNode exec(JsonNode row) {
        FeedInfo feedInfo = DanMuUtil.getGiftFromGuardData(row);
        log.info("房间:【{}】 用户 {}({}) 投喂了{}礼物 {}({}) x {}", feedInfo.getRoomInfo().getRoomId(), feedInfo.getUserInfo().getUserName(),
                feedInfo.getUserInfo().getUid(), "付费",feedInfo.getGift().getGiftName(), feedInfo.getGift().getGiftPrice(), feedInfo.getNum());
        recordService.recordGiftPrice2Redis(feedInfo);
        userFeedInfoService.recordUserFeedFromGuardData(row);
        return null;
    }
}
