package com.bili.service.bili.strategy.impl.cmd;



import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.redis.RecordServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("ENTRY_EFFECT")
@Slf4j
public class EntryEffectStrategyImpl implements CmdStrategy {

    @Resource
    private RecordServiceImpl recordService;

    @Override
    public JsonNode exec(JsonNode row) {
       RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
       UserInfo userInfoFromDanMu = DanMuUtil.getUserInfoFromUinfo(row);
       log.info("房间:【{}】 用户 {}({}) 飘屏进入房间", roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUserName(), userInfoFromDanMu.getUid());
        recordService.recordVisitScore2Redis(roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUid());
        recordService.recordUserName2Redis(roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUid(), userInfoFromDanMu.getUserName());
//       recordBiliRoomDataUtil.recordUser(userInfoFromDanMu);
//        recordBiliRoomDataUtil.recordEnterRoomUserInfo(row);
       return null;
    }
}
