package com.bili.dao.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.common.entity.mysql.UserDataHis;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  用户历史信息记录
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-09
 */
@DS("bili_room_checker")
@Mapper
public interface UserDataHisMapper extends BaseMapper<UserDataHis> {

}
