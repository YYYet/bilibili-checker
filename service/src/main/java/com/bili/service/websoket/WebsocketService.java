package com.bili.service.websoket;


import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.PayloadData;
import com.bili.service.bili.factory.TypeStrategyFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.stereotype.Service;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service
@Slf4j
public class WebsocketService {

    @Resource
    RSocketRequester.Builder builder;
    @Resource
    ObjectMapper objectMapper;
    @Resource
    TypeStrategyFactory typeStrategyFactory;

    @PostConstruct
    public void init() {
        builder.rsocketConnector(connector ->
                connector.reconnect(Retry.fixedDelay(10, Duration.ofSeconds(5)))); // 每次重连间隔5秒

    }



    // 这个要做个任务池存储 多用户多任务的话
//    private RSocketRequester rSocketRequester;
    Map<String,RSocketRequester> rSocketRequesterMap = Collections.synchronizedMap(new Hashtable<String,RSocketRequester>());
    private JsonNode buildPayloadData(String taskId, String cmd){
       return objectMapper.valueToTree(new PayloadData(Arrays.asList(taskId), cmd));
    }

    private RSocketRequester buildConnect(String url){
        return builder.websocket(URI.create(url));
    }

    public void sendSubscribe(String url, String taskId){
        JsonNode subscribe = this.buildPayloadData(taskId, BiliConstant.SUBSCRIBE);
        RSocketRequester rSocketRequester = this.buildConnect(url);
        rSocketRequester.route(BiliConstant.EMPTY)
                .data(subscribe)
                .retrieveFlux(JsonNode.class)
                .subscribe(response->{
                    typeStrategyFactory.getStrategyAndExec(response);
                }, error->{
                    log.error("rSocketRequester error {}", error);
                });
        rSocketRequesterMap.put(taskId, rSocketRequester);
    }
    public void sendUnSubscribe(String url, String taskId){
        JsonNode subscribe = this.buildPayloadData(taskId, "UNSUBSCRIBE");
//        rSocketRequester = this.buildConnect(url);
        RSocketRequester rSocketRequester = rSocketRequesterMap.get(taskId);
        rSocketRequester.route("")
                .data(subscribe)
                .retrieveFlux(JsonNode.class)
                .subscribe(item->{
                 System.out.println("UNSUBSCRIBE "+item);
        }).dispose();

    }


}
