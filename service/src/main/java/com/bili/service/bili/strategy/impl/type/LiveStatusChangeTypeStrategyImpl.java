package com.bili.service.bili.strategy.impl.type;


import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.TypeStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("LIVE_STATUS_CHANGE")
@Slf4j
public class LiveStatusChangeTypeStrategyImpl implements TypeStrategy {
    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        if (row.get("data") != null){
            JsonNode cmd = row.get("data").get("cmd");
            RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
            if (cmd != null){
                String cmdStr = cmd.asText();
                if (cmdStr.equals("STOP_LIVE_ROOM_LIST")){
//                    log.info("房间:【{}】 pk倒计时 {}", roomInfoFromRow.getRoomId(), row.get("data").get("online_count"));
                    log.info("房间:【{}】 {}", roomInfoFromRow.getRoomId(), "可能是全区下播主播");
                }
            }

        }

        return null;
    }
}
