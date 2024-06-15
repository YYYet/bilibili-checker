package com.bili.service.bili.strategy.impl.cmd;


import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.strategy.CmdStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("DM_INTERACTION")
@Slf4j
public class DmInteractionStrategyImpl implements CmdStrategy {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        String data = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.DATA).asText();
        JsonNode jsonNode = objectMapper.readTree(data);
        log.info("房间:【{}】 {}", roomInfoFromRow.getRoomId(), jsonNode.get(BiliConstant.SUFFIX_TEXT));
        return null;
    }
}
