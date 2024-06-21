package com.bili.service.task;
 
import cn.hutool.json.JSONObject;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.mysql.ConfigTypeEnum;
import com.bili.common.util.CommonHttpUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling//开启定时任务
@Component
@Slf4j
public class WatchRoomTask {
    private static DateTimeFormatter pattern=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private CommonHttpUtil commonHttpUtil;
    @Resource
    private RecordServiceImpl recordService;

    @Scheduled(cron="0/3 * * * * ?")//每秒钟执行一次，以空格分隔
    public void cron(){
        LocalDateTime now=LocalDateTime.now();
//        log.info("spring task 这是定时任务，时间是："+pattern.format(now));
        List<Config> configList = configService.getConfig(ConfigTypeEnum.WATCH_ROOM);
        ArrayList<String> uids = new ArrayList<>();
        for (Config config : configList) {
            uids.add(config.getValue());
        }
        JSONObject roomInfosData = commonHttpUtil.getRoomInfoByUserIds(uids).getJSONObject("data");

        for (String uid : uids) {
            JSONObject roomInfo = roomInfosData.getJSONObject(uid);
//            0：未开播
//            1：正在直播
//            2：轮播中
            Integer liveStatus = roomInfo.getInt("live_status");
            String roomId = roomInfo.getStr("room_id");
            // 表示之前未记录过开播信息或者未开播
            if (recordService.getRoomLiveStatus(roomId) == 0){
                if (liveStatus == 1){
                    // 表示当前轮询直播间已开播
                    // 执行开播通知
                    log.info("房间[{}]【自检测开播】 {}", roomId, roomInfo);
                }
                if (liveStatus == 0){
                    // 表示当前轮询的直播间仍未开播
                }
            }

            // 表示当前轮询的直播间已经开播或者程序崩溃未记录下播
            if (recordService.getRoomLiveStatus(roomId) == 1){
                if (liveStatus == 1){
                    // 表示当前轮询直播间仍然在直播
                }
                if (liveStatus == 0){
                    // 表示当前轮询的直播间已经下播
                    // 可以执行下播通知
                    log.info("房间[{}]【自检测下播】 {}", roomId, roomInfo);
                }
            }
            recordService.recordRoomLiveStatus(roomId, liveStatus);

        }


    }
}