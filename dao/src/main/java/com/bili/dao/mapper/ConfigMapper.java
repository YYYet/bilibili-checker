package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.bili.common.entity.mysql.Config;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  cheker项目的配置
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-09
 */
@DS("bili_room_checker")
@Mapper
public interface ConfigMapper extends BaseMapper<Config> {

}
