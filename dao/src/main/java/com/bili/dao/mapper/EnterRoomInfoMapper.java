package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.common.entity.mysql.EnterRoomInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  用户进房情况记录
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-13
 */
@Mapper
@DS("bili_room_checker")
public interface EnterRoomInfoMapper extends BaseMapper<EnterRoomInfo> {

}
