package com.bili.service.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.entity.mysql.LiveWechatR;
import com.bili.dao.mapper.LiveWechatRMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author zhuminc
 * @date 2024/6/17
 **/
@Component
@Slf4j
public class LiveWechatRelationServiceImpl {
    @Resource
    LiveWechatRMapper liveWechatRMapper;

    public boolean hasRelation(String wechatGroup, String LiveRoomId){
        return liveWechatRMapper.exists(new QueryWrapper<LiveWechatR>().eq("wechat_group_id", wechatGroup).eq("live_room_id", LiveRoomId));
    }

}
