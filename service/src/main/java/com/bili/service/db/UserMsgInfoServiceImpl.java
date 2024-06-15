package com.bili.service.db;

import com.bili.common.entity.bili.DanMuInfo;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.mysql.UserMsgInfo;
import com.bili.common.util.DanMuUtil;
import com.bili.dao.mapper.UserMsgInfoMapper;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Service
public class UserMsgInfoServiceImpl {
    @Resource
    UserMsgInfoMapper userMsgInfoMapper;

    public void recordUserMsg(JsonNode row){
        DanMuInfo danMu = DanMuUtil.getDanMu(row);
        RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
        UserMsgInfo userMsgInfo = new UserMsgInfo();
        userMsgInfo.setUserMsg(danMu.getMsg());
        userMsgInfo.setUserId(danMu.getUserInfo().getUid());
        userMsgInfo.setUserName(danMu.getUserInfo().getUserName());
        userMsgInfo.setRoomId(roomInfoFromRow.getRoomId());
        LocalDateTime currentDateTime = LocalDateTime.now();
        userMsgInfo.setSendTime(currentDateTime);
        userMsgInfoMapper.insert(userMsgInfo);
    }
}
