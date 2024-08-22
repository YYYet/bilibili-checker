package com.bili.service.bili.strategy.impl.cmd;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.mysql.LiveWechatR;
import com.bili.common.util.DanMuUtil;
import com.bili.dao.mapper.LiveWechatRMapper;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("CUT_OFF")
@Slf4j
public class CutOffStrategyImpl implements CmdStrategy {
    @Resource
    WechatBotServiceImpl wechatBotServiceImpl;
    @Resource
    LiveWechatRMapper liveWechatRMapper;
    @Override
    public JsonNode exec(JsonNode row) {
       RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
       log.info("房间:【{}】 恭喜主播喜提下线：{}", roomInfoFromRow.getRoomId(), row.get(BiliConstant.MSG).get(BiliConstant.MSG).asText());
        QueryWrapper<LiveWechatR> queryWrapper = new QueryWrapper();
        queryWrapper.eq("room_id", roomInfoFromRow.getRoomId());
        List<LiveWechatR> liveWechatRS = liveWechatRMapper.selectList(queryWrapper);

        if (liveWechatRS.size() > 0) {
            LiveWechatR liveWechatR = liveWechatRS.get(0);
            if (liveWechatR != null){
                wechatBotServiceImpl.sendMsgAuto("恭喜主播喜提下线："+row.get(BiliConstant.MSG).get(BiliConstant.MSG).asText(), liveWechatR.getWechatGroupId());
            }
        }


        //wechatBotServiceImpl.sendGroupMessageToSGY("恭喜主播喜提下线："+row.get(BiliConstant.MSG).get(BiliConstant.MSG).asText());

       return null;
    }
}
