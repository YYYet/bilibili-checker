package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bili.common.entity.mysql.UserMsgInfo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  捕获所有用户信息
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-14
 */
@DS("bili_room_checker")
@Mapper
public interface UserMsgInfoMapper extends BaseMapper<UserMsgInfo> {

}
