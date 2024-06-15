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
@Service("OTHER_CMD")
@Slf4j
public class OtherCmdStrategyImpl implements CmdStrategy {

    @Override
    public JsonNode exec(JsonNode row) {
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        log.info("房间:【{}】 未知的cmd", roomInfoFromRow.getRoomId());
       return null;
    }
}
