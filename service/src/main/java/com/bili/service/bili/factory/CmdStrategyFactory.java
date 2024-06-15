package com.bili.service.bili.factory;


import com.bili.common.constant.BiliConstant;
import com.bili.service.bili.strategy.CmdStrategy;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Component
public class CmdStrategyFactory {
    public Map<String, CmdStrategy> strategyHashMap = new ConcurrentHashMap<>();

    public CmdStrategyFactory(Map<String, CmdStrategy> strategyHashMap) {
        this.strategyHashMap.clear();
        strategyHashMap.forEach(this.strategyHashMap::put);
    }

    public CmdStrategy getStrategy(JsonNode command){
        if (command == null){
            return this.strategyHashMap.get(BiliConstant.OTHER_CMD);
        }
        CmdStrategy strategy = this.strategyHashMap.get(command.asText());
        if (strategy == null){
            return this.strategyHashMap.get(BiliConstant.OTHER_CMD);
        }
        return strategy;
    }
    public CmdStrategy getStrategy(String command){
        if (command == null){
            return this.strategyHashMap.get(BiliConstant.OTHER_CMD);
        }
        CmdStrategy strategy = this.strategyHashMap.get(command);
        if (strategy == null){
            return this.strategyHashMap.get(BiliConstant.OTHER_CMD);
        }
        return strategy;
    }

}
