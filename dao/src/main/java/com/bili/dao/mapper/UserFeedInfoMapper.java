package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bili.common.entity.mysql.UserFeedInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  用户投喂情况记录
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-14
 */
@DS("bili_room_checker")
@Mapper
public interface UserFeedInfoMapper extends BaseMapper<UserFeedInfo> {

}
