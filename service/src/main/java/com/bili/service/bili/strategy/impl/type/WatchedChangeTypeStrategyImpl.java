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
@Service("WATCHED_CHANGE")
@Slf4j
public class WatchedChangeTypeStrategyImpl implements TypeStrategy {
    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        log.info("房间:【{}】 {}", roomInfoFromRow.getRoomId(), row.get("data").get("text_large").asText());
        return null;
    }
}
