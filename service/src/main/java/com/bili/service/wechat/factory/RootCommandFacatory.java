package com.bili.service.wechat.factory;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bili.common.entity.mysql.Config;
import com.bili.common.util.DynamicBeanUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.wechat.command.WxBotCommander;
import com.bili.service.wechat.command.impl.*;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
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
    @Resource
    private DynamicBeanUtil dynamicBeanUtil;
    @Resource
    private ApplicationContext applicationContext;
    @PostConstruct
    public void init() {
        commandMap.put("推送测试", testImpl);
        commandMap.put("批话排名", danMuScope);
        List<Config> analyzeCardConfig = configService.getAnalyzeCardConfig();
        for (Config config : analyzeCardConfig) {
            AnalyzeCardImpl analyzeCard = applicationContext.getBean(AnalyzeCardImpl.class);
            JSONObject entries = JSONUtil.parseObj(config.getValue());
            analyzeCard.setValue(entries);
            this.commandMap.put(config.getName(), analyzeCard);
        }
    }
}
