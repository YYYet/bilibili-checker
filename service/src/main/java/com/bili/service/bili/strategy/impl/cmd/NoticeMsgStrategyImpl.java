package com.bili.service.bili.strategy.impl.cmd;


import com.bili.service.bili.strategy.CmdStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("NOTICE_MSG")
@Slf4j
public class NoticeMsgStrategyImpl implements CmdStrategy {

    @Override
    public JsonNode exec(JsonNode row) {
        log.info("全区飘屏 {}", row.get("msg").get("msg_self").asText().replace("<%", "").replace("%>", ""));
        return null;
    }
}
