package com.bili.service.db;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bili.common.entity.bili.FeedInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.entity.mysql.UserFeedInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.dao.mapper.UserFeedInfoMapper;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Component
public class UserFeedInfoServiceImpl {
    @Resource
    UserFeedInfoMapper userFeedInfoMapper;


    public void recordUserFeedFromGuardData(JsonNode row){
        FeedInfo feedInfo = DanMuUtil.getGiftFromGuardData(row);

        FeedInfo giftFromDanMu = feedInfo;
        UserInfo userInfoFromDanMu = feedInfo.getUserInfo();
        RoomInfo roomInfoFromRow = feedInfo.getRoomInfo();

        UserFeedInfo userFeedInfo = new UserFeedInfo();
        userFeedInfo.setFeedNum((int) giftFromDanMu.getNum());
        userFeedInfo.setGiftName(giftFromDanMu.getGift().getGiftName());
        userFeedInfo.setGiftPrice(BigDecimal.valueOf(giftFromDanMu.getGift().getGiftPrice()));
        userFeedInfo.setGiftType(giftFromDanMu.getGift().isFree()?"免费":"付费");
        LocalDateTime currentDateTime = LocalDateTime.now();
        userFeedInfo.setFeedTime(currentDateTime);
        userFeedInfo.setUserId(userInfoFromDanMu.getUid());
        userFeedInfo.setUserName(userInfoFromDanMu.getUserName());
        userFeedInfo.setTotalPrice(userFeedInfo.getGiftPrice().multiply(BigDecimal.valueOf(userFeedInfo.getFeedNum())));
        userFeedInfo.setRoomId(roomInfoFromRow.getRoomId());
        userFeedInfo.setBoxName(giftFromDanMu.getBoxName());
        BigDecimal actualPrice = BigDecimal.valueOf(giftFromDanMu.getGift().getActualPrice()).multiply(BigDecimal.valueOf(userFeedInfo.getFeedNum()));
        userFeedInfo.setActualPrice(actualPrice);
        userFeedInfo.setBalance(userFeedInfo.getTotalPrice().subtract(actualPrice));
        userFeedInfo.setFeedTimeIndex(DateUtil.today());
        userFeedInfoMapper.insert(userFeedInfo);
    }
    public void recordUserFeed(JsonNode row){
        FeedInfo giftFromDanMu = DanMuUtil.getGiftFromDanMu(row);
        UserInfo userInfoFromDanMu = DanMuUtil.getUserInfoFromDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        UserFeedInfo userFeedInfo = new UserFeedInfo();
        userFeedInfo.setFeedNum((int) giftFromDanMu.getNum());
        userFeedInfo.setGiftName(giftFromDanMu.getGift().getGiftName());
        userFeedInfo.setGiftPrice(BigDecimal.valueOf(giftFromDanMu.getGift().getGiftPrice()));
        userFeedInfo.setGiftType(giftFromDanMu.getGift().isFree()?"免费":"付费");
        LocalDateTime currentDateTime = LocalDateTime.now();
        userFeedInfo.setFeedTime(currentDateTime);
        userFeedInfo.setUserId(userInfoFromDanMu.getUid());
        userFeedInfo.setUserName(userInfoFromDanMu.getUserName());
        userFeedInfo.setTotalPrice(userFeedInfo.getGiftPrice().multiply(BigDecimal.valueOf(userFeedInfo.getFeedNum())));
        userFeedInfo.setRoomId(roomInfoFromRow.getRoomId());
        userFeedInfo.setBoxName(giftFromDanMu.getBoxName());
        BigDecimal actualPrice = BigDecimal.valueOf(giftFromDanMu.getGift().getActualPrice()).multiply(BigDecimal.valueOf(userFeedInfo.getFeedNum()));
        userFeedInfo.setActualPrice(actualPrice);
        userFeedInfo.setBalance(userFeedInfo.getTotalPrice().subtract(actualPrice));
        userFeedInfo.setFeedTimeIndex(DateUtil.today());
        userFeedInfoMapper.insert(userFeedInfo);
    }

    /**
     * 获取指定日期某房间中的所有消费记录
     * @param roomId
     * @param dateStr
     * @return
     */
    public List<UserFeedInfo> getTargetDateFeedInfo(String roomId, String dateStr){
        return userFeedInfoMapper.selectList(Wrappers.<UserFeedInfo>lambdaQuery()
                .eq(UserFeedInfo::getRoomId, roomId).eq(UserFeedInfo::getFeedTimeIndex, dateStr));
    }

    /**
     * 获取某房间指定时间内的指定类型盲盒的盈亏电池情况
     * @param roomId 房间号
     * @param dateString 日期
     * @param boxType 盲盒类型
     * @return
     */
    public List<Map<String, Object>> getEveryOneRoomBalance(String roomId, String dateString, String boxType) {

        QueryWrapper<UserFeedInfo> queryWrapper = Wrappers.query();

        if (StringUtils.hasText(boxType)){
            queryWrapper.select("sum(balance)/100 as balance", "sum(actual_price)/100 as actualPrice", "sum(feed_num) as num", "box_name", "user_id", "user_name")
                    .eq("ROOM_ID", roomId)
                    .eq("box_name", boxType)
                    .eq("feed_time_index", dateString)
                    .groupBy("USER_ID", "box_name");
        }else {
            queryWrapper.select("sum(balance)/100 as balance", "sum(actual_price)/100 as actualPrice", "sum(feed_num) as num", "box_name", "user_id", "user_name")
                    .eq("ROOM_ID", roomId)
                    .eq("feed_time_index", dateString)
                    .like("box_name", "盲盒")
                    .groupBy("USER_ID", "box_name");
        }



        return  userFeedInfoMapper.selectMaps(queryWrapper);

    }
    public List<Map<String, Object>> getEveryOneRoomGift(String roomId, String dateString) {

        QueryWrapper<UserFeedInfo> queryWrapper = Wrappers.query();

        queryWrapper.select("user_name", "sum(feed_num) as num", "gift_name", "box_name", "SUM(total_price)/100 as total_price")
                .eq("ROOM_ID", roomId)
                .eq("feed_time_index", dateString)
                .groupBy("USER_ID", "gift_name");


        return  userFeedInfoMapper.selectMaps(queryWrapper);

    }
    /**
     * 获取直播间指定日期的消费排行
     * @param roomId
     * @param dateString
     * @param boxType
     * @return
     */
    public List<Map<String, Object>> getEveryOneRoomConsumption(String roomId, String dateString, String boxType) {

        QueryWrapper<UserFeedInfo> queryWrapper = Wrappers.query();
        queryWrapper.select("sum(actual_price)/100 as balance", "user_id", "user_name")
                .eq("ROOM_ID", roomId)
                .eq("box_name", boxType)
                .eq("feed_time_index", dateString)
                .groupBy("USER_ID", "user_name");


        return  userFeedInfoMapper.selectMaps(queryWrapper);

    }




}
