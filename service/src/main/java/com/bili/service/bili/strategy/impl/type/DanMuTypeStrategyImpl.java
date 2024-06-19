package com.bili.service.bili.strategy.impl.type;


import com.bili.common.entity.bili.DanMuInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.bot.BiliBotServiceImpl;
import com.bili.service.bili.strategy.TypeStrategy;
import com.bili.service.db.UserMsgInfoServiceImpl;
import com.bili.service.redis.QueryDataServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("DANMU")
@Slf4j
public class DanMuTypeStrategyImpl implements TypeStrategy {

    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private QueryDataServiceImpl queryDataService;
    @Resource
    private UserMsgInfoServiceImpl userMsgInfoService;
    @Resource
    private BiliBotServiceImpl botService;

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        DanMuInfo danMu = DanMuUtil.getDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        recordService.recordDanMuScore2Redis(roomInfoFromRow.getRoomId(), danMu.getUserInfo().getUid());
        recordService.recordUserName2Redis(roomInfoFromRow.getRoomId(), danMu.getUserInfo().getUid(), danMu.getUserInfo().getUserName());
        userMsgInfoService.recordUserMsg(row);

        log.info("房间:【{}】 用户 {}({}) 发送弹幕 【{}】", roomInfoFromRow.getRoomId(), danMu.getUserInfo().getUserName(), danMu.getUserInfo().getUid(), danMu.getMsg());
        String content = danMu.getMsg();
        HashMap<String, String> result = botService.checkMsg(roomInfoFromRow.getRoomId(), content);
        if (result.get("send").equals("true")){
            botService.sendMsg(roomInfoFromRow.getRoomId(), result.get("dm"));
        }

        return null;
    }
}
