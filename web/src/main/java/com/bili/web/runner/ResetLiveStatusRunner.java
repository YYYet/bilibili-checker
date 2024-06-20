package com.bili.web.runner;

import cn.hutool.json.JSONObject;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.mysql.ConfigTypeEnum;
import com.bili.common.util.CommonHttpUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/20
 **/
@Component
@Slf4j
public class ResetLiveStatusRunner implements CommandLineRunner {
    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private RecordServiceImpl recordService;
    @Resource
    private CommonHttpUtil commonHttpUtil;

    @Override
    public void run(String... args) throws Exception {
        /**
         * 每次重启项目获取最新的直播间数据，重置掉旧数据，避免通知重复发送
         */
        List<Config> configList = configService.getConfig(ConfigTypeEnum.WATCH_ROOM);
        ArrayList<String> uids = new ArrayList<>();
        for (Config config : configList) {
            uids.add(config.getValue());
        }
        JSONObject roomInfosData = commonHttpUtil.getRoomInfoByUserIds(uids).getJSONObject("data");
        for (String uid : uids) {
            JSONObject roomInfo = roomInfosData.getJSONObject(uid);
            if (roomInfo == null) {
                continue;
            }
//            0：未开播
//            1：正在直播
//            2：轮播中
            Integer liveStatus = roomInfo.getInt("live_status");
            String roomId = roomInfo.getStr("room_id");
            recordService.recordRoomLiveStatus(roomId, liveStatus);
        }
    }

}
