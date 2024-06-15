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
        String dm = "";
        if (content.equals("今日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomInfoFromRow.getRoomId());
                BigDecimal todayPrice = new BigDecimal(price.toString());
                dm = "今日截至目前流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";
                if (roomInfoFromRow.getRoomId().equals("22594587")){
                    dm = "今日流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆加800";
                }
            } catch (Exception e) {
                dm = "主播今日还没圈到米，加油加油加油";
            }
            botService.sendMsg(roomInfoFromRow.getRoomId(), dm);
        }

        if (content.equals("昨日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomInfoFromRow.getRoomId(), -1);
                BigDecimal todayPrice = new BigDecimal(price);
                dm = "昨日流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";
            } catch (Exception e) {
                dm = "主播昨日没圈到米，加油加油加油";
            }
            botService.sendMsg(roomInfoFromRow.getRoomId(), dm);
        }
        if (content.equals("前日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomInfoFromRow.getRoomId(), -2);
                BigDecimal todayPrice = new BigDecimal(price);
                dm = "前日流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";

            } catch (Exception e) {
               dm = "主播前日没圈到米，加油加油加油";

            }
            botService.sendMsg(roomInfoFromRow.getRoomId(), dm);
        }

        return null;
    }
}
