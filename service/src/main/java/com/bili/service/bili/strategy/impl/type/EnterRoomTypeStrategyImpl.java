package com.bili.service.bili.strategy.impl.type;




import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.TypeStrategy;
import com.bili.service.db.EnterRoomInfoServiceImpl;
import com.bili.service.db.UserInfoServiceImpl;
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
@Service("ENTER_ROOM")
@Slf4j
public class EnterRoomTypeStrategyImpl implements TypeStrategy {

    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private EnterRoomInfoServiceImpl enterRoomInfoService;
    @Resource
    private UserInfoServiceImpl userInfoService;

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        UserInfo userInfoFromDanMu = DanMuUtil.getUserInfoFromDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);

        log.info("房间:【{}】 用户 {}({}) 进入房间", roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUserName(), userInfoFromDanMu.getUid());

        recordService.recordVisitScore2Redis(roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUid());
        recordService.recordUserName2Redis(roomInfoFromRow.getRoomId(), userInfoFromDanMu.getUid(), userInfoFromDanMu.getUserName());
        userInfoService.recordUser2MySql(userInfoFromDanMu);
        enterRoomInfoService.recordEnterRoomUserInfo2MySql(row);
        return null;
    }
}
