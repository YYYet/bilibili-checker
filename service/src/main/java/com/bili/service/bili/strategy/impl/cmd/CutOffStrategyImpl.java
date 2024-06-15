package com.bili.service.bili.strategy.impl.cmd;



import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("CUT_OFF")
@Slf4j
public class CutOffStrategyImpl implements CmdStrategy {
    @Resource
    WechatBotServiceImpl wechatBotServiceImpl;
    @Override
    public JsonNode exec(JsonNode row) {
       RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
       log.info("房间:【{}】 恭喜主播喜提下线：{}", roomInfoFromRow.getRoomId(), row.get(BiliConstant.MSG).get(BiliConstant.MSG).asText());
       wechatBotServiceImpl.sendGroupMessageToSGY("恭喜主播喜提下线："+row.get(BiliConstant.MSG).get(BiliConstant.MSG).asText());
       return null;
    }
}
