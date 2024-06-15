package com.bili.service.wechat.factory;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.bili.common.entity.mysql.Config;
import com.bili.common.util.DynamicBeanUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.wechat.command.WxBotCommander;
import com.bili.service.wechat.command.impl.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RootCommandFacatory {

    protected final Map<String, WxBotCommander> commandMap = new ConcurrentHashMap<>();


    @Resource
    private TestImpl testImpl;
    @Resource
    private DanMuScopeImpl danMuScope;
    @Resource
    private ConfigServiceImpl configService;

    @PostConstruct
    public void init() {
        commandMap.put("推送测试", testImpl);
        commandMap.put("批话排名", danMuScope);
        List<Config> analyzeCardConfig = configService.getAnalyzeCardConfig();
        analyzeCardConfig.forEach(config -> {
            AnalyzeCardImpl analyzeCard = new AnalyzeCardImpl();
            analyzeCard.setValue(JSONUtil.parseObj(config.getValue()));
            commandMap.put(config.getName(), analyzeCard);
        });
    }
}
