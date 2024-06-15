package com.bili.web.controller;


import com.bili.common.entity.mysql.Config;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.websoket.WebsocketService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@RestController("api")
@Slf4j
public class ConnectController {

    @Resource
    WebsocketService websocketService;
    @Resource
    ConfigServiceImpl configService;

    private static String url = "";

    @PostConstruct
    public void init(){
        Config barrageConfig = configService.getBarrageConfig();
        this.url = barrageConfig.getValue();
    }

    @GetMapping("subscribe/{taskId}")
    public void subscribe(@PathVariable String taskId){
        if (!StringUtils.hasText(taskId)){
            return;
        }
        websocketService.sendSubscribe(url, taskId);
    }

    @GetMapping("unSubscribe/{taskId}")
    public void unSubscribe(@PathVariable String taskId){
        if (!StringUtils.hasText(taskId)){
            return;
        }
        websocketService.sendUnSubscribe(url, taskId);
    }
}
