package com.bili.service.bili.factory;



import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.mysql.Config;
import com.bili.service.bili.strategy.TypeStrategy;
import com.bili.service.db.ConfigServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Component
@Slf4j
public class TypeStrategyFactory {
    @Resource
    ConfigServiceImpl configService;
    public Map<String, TypeStrategy> strategyHashMap = new ConcurrentHashMap<>();

    public TypeStrategyFactory(Map<String, TypeStrategy> strategyHashMap) {
        this.strategyHashMap.clear();
        strategyHashMap.forEach(this.strategyHashMap::put);
    }
    public void getStrategyThenExec(JsonNode command){
        TypeStrategy strategy = null;
        JsonNode type = command.get(BiliConstant.TYPE);
        if (type == null){
            strategy = this.strategyHashMap.get(BiliConstant.OTHER);
        }else {
            strategy = this.strategyHashMap.get(command.asText());
        }

        if (strategy == null){
            strategy = this.strategyHashMap.get(BiliConstant.OTHER);
        }
        try {
            strategy.exec(command);
        }catch (Exception e){
            log.error("ERROR JsonNode: {} exception: {}", command, e);
        }
    }
    public void getStrategyAndExec(JsonNode response){
        JsonNode command = response.get(BiliConstant.TYPE);
        TypeStrategy strategy = null;
        if (command == null){
            strategy = this.strategyHashMap.get(BiliConstant.OTHER);
        }else {
            strategy = this.strategyHashMap.get(command.asText());
        }
        if (strategy == null){
            strategy = this.strategyHashMap.get(BiliConstant.OTHER);
        }
        // 这里加前置日志控制
        String name = strategy.getClass().getSimpleName();
        // 暂时这样去除cglib对类名的代理加强
        name = name.replace("$$SpringCGLIB$$0", "");
        Config config = configService.getConfig(name);
        if (config != null && config.getStatus() == 1 && !config.getValue().equals("OtherTypeStrategyImpl")){
            log.info("实现类：{} 前置数据:{}", name, response);
        }
        try {
            strategy.exec(response);
        }catch (Exception e){
            log.error("ERROR JsonNode: {}", response);
            log.error("ERROR: {}", e);
        }
    }
    public TypeStrategy getStrategy(JsonNode command){
        if (command == null){
            return this.strategyHashMap.get(BiliConstant.OTHER);
        }
        TypeStrategy strategy = this.strategyHashMap.get(command.asText());
        if (strategy == null){
            return this.strategyHashMap.get(BiliConstant.OTHER);
        }

        return strategy;
    }
    public TypeStrategy getStrategy(String command){
        if (command == null){
            return this.strategyHashMap.get(BiliConstant.OTHER);
        }
        TypeStrategy strategy = this.strategyHashMap.get(command);
        if (strategy == null){
            return this.strategyHashMap.get(BiliConstant.OTHER);
        }
        return strategy;
    }
}
