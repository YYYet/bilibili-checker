package com.bili.service.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.bili.UserInfo;
import com.bili.common.entity.mysql.EnterRoomInfo;
import com.bili.common.util.CommonUtil;
import com.bili.common.util.DanMuUtil;
import com.bili.dao.mapper.EnterRoomInfoMapper;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Component
@Slf4j
public class EnterRoomInfoServiceImpl {
    @Resource
    CommonUtil commonUtil;
    @Resource
    EnterRoomInfoMapper enterRoomInfoMapper;


    public void recordEnterRoomUserInfo2MySql(JsonNode row){
        UserInfo userInfoFromDanMu = DanMuUtil.getUserInfoFromDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        EnterRoomInfo enterRoomInfo = new EnterRoomInfo();
        enterRoomInfo.setRoomId(roomInfoFromRow.getRoomId());
        enterRoomInfo.setUserId(userInfoFromDanMu.getUid());
        enterRoomInfo.setUserName(userInfoFromDanMu.getUserName());
        LocalDateTime currentDateTime = LocalDateTime.now();
        enterRoomInfo.setEnterTime(currentDateTime);
        enterRoomInfoMapper.insert(enterRoomInfo);
    }

    /**
     * 获取指定房间的进房数据
     * @param roomId
     * @param dateString
     * @return
     */
    public HashMap<String, Object> getEnterRoomData(String roomId, String dateString){

        HashMap<String, Object> result = new HashMap<>();
        try {

            List<EnterRoomInfo> dataListByMinute = getEnterDataByMinute(roomId, dateString);
            List<EnterRoomInfo> dataListByHour = getEnterDataByHour(roomId, dateString);
            List<EnterRoomInfo> dataListByDay = getEnterDataByDay(roomId, dateString);
            result.put("dataListByMinute", dataListByMinute);
            result.put("dataListByHour", dataListByHour);
            result.put("dataListByDay", dataListByDay);

        } catch (Exception e) {
            log.error("error {}", e);
        }
        return result;
    }

    /**
     * 获取日进房量
     * @param roomId
     * @param dateString
     * @return
     * @throws ParseException
     */
    public List<EnterRoomInfo> getEnterDataByDay(String roomId, String dateString) throws ParseException {
        LocalDate targetDate = commonUtil.dateStr2LocalDate(dateString);
        QueryWrapper<EnterRoomInfo> queryWrapper = new QueryWrapper<>();
        LocalDateTime startDateTime = LocalDateTime.of(targetDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(targetDate.plusDays(1), LocalTime.MIN);
        queryWrapper.eq("room_id", roomId);
        queryWrapper.ge("enter_time", startDateTime);
        queryWrapper.lt("enter_time", endDateTime);
        return enterRoomInfoMapper.selectList(queryWrapper);
    }

    /**
     * 获取每分钟进房量
     * @param roomId
     * @param dateString
     * @return
     * @throws ParseException
     */
    public List<EnterRoomInfo> getEnterDataByMinute(String roomId, String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        LocalDateTime startTime = LocalDateTime.now().withMinute(minute).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusMinutes(1); // 加一分钟，得到下个分钟的开始时间

        QueryWrapper<EnterRoomInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.ge("enter_time", startTime); // 大于等于开始时间
        queryWrapper.lt("enter_time", endTime); // 小于下个分钟的开始时间

        return enterRoomInfoMapper.selectList(queryWrapper);
    }

    /**
     * 获取每小时进房量
     * @param roomId
     * @param dateString
     * @return
     * @throws ParseException
     */
    public List<EnterRoomInfo> getEnterDataByHour(String roomId, String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY); // 使用24小时制
        LocalDateTime startTime = LocalDateTime.now().withHour(hour).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endTime = startTime.plusHours(1);

        QueryWrapper<EnterRoomInfo> queryWrapper = new QueryWrapper<EnterRoomInfo>();
        queryWrapper.eq("room_id", roomId);
        queryWrapper.ge("enter_time", startTime); // 大于等于开始时间
        queryWrapper.lt("enter_time", endTime); // 小于下个小时的开始时间
        List<EnterRoomInfo> dataListByHour = enterRoomInfoMapper.selectList(queryWrapper);
        return dataListByHour;
    }

}
