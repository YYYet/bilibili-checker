package com.bili.service.bili.bot;

import com.bili.common.config.CustomConfig;
import com.bili.common.entity.mysql.Config;
import com.bili.sdk.service.tv.sdk.TvLiveSdk;
import com.bili.service.db.ConfigServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Component
@Slf4j
public class BiliBotServiceImpl {
    public static String masterBotAccessToken;
    public static ArrayList<String> botAccessTokenList = new ArrayList<>();

    @Resource
    private ConfigServiceImpl configService;

    @PostConstruct
    public void init(){
        List<Config> tokenConfig = configService.getTokenConfig(CustomConfig.ACCESS_TOKEN);
        tokenConfig.forEach(config -> this.botAccessTokenList.add(config.getValue()));
        this.masterBotAccessToken = this.botAccessTokenList.get(0);
    }

    @Resource
    TvLiveSdk tvLiveSdk;
    public void sendMsg(String roomId, String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        tvLiveSdk.sendDanMuKu(masterBotAccessToken, Long.parseLong(roomId), msg);
    }
}
