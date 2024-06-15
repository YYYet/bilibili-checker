package com.bili.dao.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.bili.common.entity.mysql.BarrageFlyTask;
import org.apache.ibatis.annotations.Mapper;

/**
 * 弹幕服务
 */
@Mapper
@DS("or_barrage_fly")
public interface BarrageFlyTaskMapper extends BaseMapper<BarrageFlyTask> {
}
