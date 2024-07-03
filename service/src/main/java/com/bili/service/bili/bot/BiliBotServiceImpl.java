package com.bili.service.bili.bot;

import com.bili.common.config.CustomConfig;
import com.bili.common.entity.bili.DanMuInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.mysql.Config;
import com.bili.common.util.DanMuUtil;
import com.bili.sdk.service.tv.sdk.TvLiveSdk;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.redis.QueryDataServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Component
@Slf4j
public class BiliBotServiceImpl {
    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private QueryDataServiceImpl queryDataService;

    @Resource
    TvLiveSdk tvLiveSdk;

    public static String masterBotAccessToken;
    public static ArrayList<String> botAccessTokenList = new ArrayList<>();

    @PostConstruct
    public void init() {
        List<Config> tokenConfig = configService.getTokenConfig(CustomConfig.ACCESS_TOKEN);
        tokenConfig.forEach(config -> this.botAccessTokenList.add(config.getValue()));
        this.masterBotAccessToken = this.botAccessTokenList.get(0);
    }

    public void sendMsg(String roomId, String msg) {
        try {
            boolean b = tvLiveSdk.sendDanMuKu(masterBotAccessToken, Long.parseLong(roomId), msg);
        } catch (Exception e) {
            log.error("error {}", e);
        }
    }

    public HashMap<String, String> checkMsg(String roomId, String content) {
        HashMap<String, String> result = new HashMap<>();
        String dm = content;

        result.put("send", "false");
        if (content.equals("今日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomId);
                BigDecimal todayPrice = new BigDecimal(price.toString());
                dm = "今日截至目前流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";
            } catch (Exception e) {
                dm = "主播今日还没圈到米，加油加油加油";
            }
            result.put("send", "true");
        }
        if (content.equals("昨日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomId, -1);
                BigDecimal todayPrice = new BigDecimal(price);
                dm = "昨日流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";
            } catch (Exception e) {
                dm = "主播昨日没圈到米，加油加油加油";
            }
            result.put("send", "true");
        }
        if (content.equals("前日流水")) {
            try {
                String price = queryDataService.getRoomALlUnfreeGiftPrice(roomId, -2);
                BigDecimal todayPrice = new BigDecimal(price);
                dm = "前日流水合计:" + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆";

            } catch (Exception e) {
                dm = "主播前日没圈到米，加油加油加油";
            }
            result.put("send", "true");
        }

        if (content.equals("明日流水")) {
            List<Config> robotConfig = configService.getRobotConfig(CustomConfig.TOMORROW_REPLAY_KEY);
            Collections.shuffle(robotConfig);
            Config config = robotConfig.get(0);
            dm = config.getValue();
            result.put("send", "true");
        }

        result.put("dm", dm);
        return result;
    }
}
