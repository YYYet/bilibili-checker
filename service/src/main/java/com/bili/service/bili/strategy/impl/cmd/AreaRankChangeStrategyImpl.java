package com.bili.service.bili.strategy.impl.cmd;



import com.bili.common.constant.BiliConstant;
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
@Service("AREA_RANK_CHANGED")
@Slf4j
public class AreaRankChangeStrategyImpl implements CmdStrategy {

    @Override
    public JsonNode exec(JsonNode row) throws Exception{
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        String rankName = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.RANK_NAME).asText();
        String rank = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.RANK).asText();
        log.info("房间:【{}】 登上{}第{}名", roomInfoFromRow.getRoomId(), rankName,  rank);
        return null;
    }
}
