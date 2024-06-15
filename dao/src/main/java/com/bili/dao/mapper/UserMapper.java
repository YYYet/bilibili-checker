package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.common.entity.mysql.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  捕捉所有用户
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-09
 */
@DS("bili_room_checker")
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
