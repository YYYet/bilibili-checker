package com.bili.web.controller;

import cn.hutool.core.date.DateUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.db.EnterRoomInfoServiceImpl;
import com.bili.service.db.UserFeedInfoServiceImpl;
import com.bili.service.redis.QueryDataServiceImpl;
import com.bili.service.redis.RecordServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuminc
 * @date 2024/6/8
 **/
@RestController()
@Slf4j
@RequestMapping("analyze")
public class AnalyzeController {

    @Resource
    QueryDataServiceImpl queryDataService;
    @Resource
    EnterRoomInfoServiceImpl enterRoomInfoService;
    @Resource
    UserFeedInfoServiceImpl userFeedInfoService;
    /**
     * 获取大爹榜数据
     * @param date
     * @param roomId
     * @return
     */
    @GetMapping("getDieData")
    public ArrayList<HashMap<String, Object>> geta(@RequestParam String date, @RequestParam(defaultValue = "") String roomId){
        date = date.replace("-", ":");
        ArrayList<HashMap<String, Object>> dieByDate = queryDataService.getDieByDate(date, roomId);
        return dieByDate;
    }

    @GetMapping("getUserFeedInfoByDb")
    public List<Map<String, Object>> getUserFeedInfoByDb(@RequestParam String date, @RequestParam(defaultValue = "") String roomId){
//        date = date.replace("-", ":");
        return userFeedInfoService.getUserFeedInfoByDb(roomId, date);
    }

    /**
     * 获取进房数据
     * @param roomId
     * @return
     */
    @GetMapping("getEnterRoomData")
    public HashMap<String, Object> getEnterRoomDataLineOption(@RequestParam(defaultValue = "") String roomId){
        String date = DateUtil.now();
        return enterRoomInfoService.getEnterRoomData(roomId, date);
    }

    /**
     * 获取每一位用户在直播间指定日期的盲盒盈亏数
     * @param roomId 房间号
     * @param date 日期
     * @param boxType 盲盒类型
     * @return
     */
    @GetMapping("getEveryOneRoomBalance")
    public List<Map<String, Object>> getEveryOneRoomBalance(@RequestParam String roomId,
                                                         @RequestParam String date,
                                                         @RequestParam(required = false) String boxType){

        return userFeedInfoService.getEveryOneRoomBalance(roomId, date, boxType);
    }
    @GetMapping("getEveryOneRoomGift")
    public List<Map<String, Object>> getEveryOneRoomGift(@RequestParam String roomId,
                                                         @RequestParam String date){

        return userFeedInfoService.getEveryOneRoomGift(roomId, date);
    }



    @GetMapping("getTargetRoomBoxInfo")
    public void getTargetRoomBoxInfo(){}

    @GetMapping("userdmaz/{roomId}/{date}")
    public Map<String, Object> danMuAnalyze3(@PathVariable String roomId, @PathVariable String date){
        return queryDataService.getUserNameByUidList(roomId, date);
    }

    @GetMapping("ping")
    public void danMuAnalyze2(){

    }
    @GetMapping("pingaz")
    public String ping(){

        return "pong";
    }
}
