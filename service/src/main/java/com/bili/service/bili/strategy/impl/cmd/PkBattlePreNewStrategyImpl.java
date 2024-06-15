package com.bili.service.bili.strategy.impl.cmd;

import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("PK_BATTLE_PRE_NEW")
@Slf4j
public class PkBattlePreNewStrategyImpl implements CmdStrategy {
//    @Resource
//    CommonHttpUtil commonHttpUtil;
    @Override
    public JsonNode exec(JsonNode row) {
        log.info("row:【PkBattlePreNewStrategyImpl】 {}", row);
        RoomInfo roomInfoFromRow = DanMuUtil.getTargetRoomInfoFromPkRow(row);
        log.info("房间:【{}】 预pk 对方房间:【{}】 对方主播 {}({})", roomInfoFromRow.getRoomId(), roomInfoFromRow.getTargetRoomId(), roomInfoFromRow.getUserInfo().getUserName(), roomInfoFromRow.getUserInfo().getUid());

//        QueryWrapper<BarrageFlyTask> objectQueryWrapper = new QueryWrapper<BarrageFlyTask>();
//        objectQueryWrapper.eq("room_id", roomInfoFromRow.getRoomId());
//        BarrageFlyTask barrageFlyTasks = mapper.selectOne(objectQueryWrapper);
//        log.info("barrageFlyTasks {}", barrageFlyTasks);
//        String roomInfo = httpUtil.getRoomInfo(roomInfoFromRow.getTargetRoomId(), barrageFlyTasks.getCookie());
//        log.info("roomInfo "+roomInfo);
//        BiliBotUtil.sendDm(roomInfoFromRow.getRoomId(), "滴"+roomInfo);
        return null;
    }
}
