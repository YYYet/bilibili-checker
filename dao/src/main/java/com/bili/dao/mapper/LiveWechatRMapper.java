package com.bili.dao.mapper;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bili.common.entity.mysql.LiveWechatR;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  直播间与微信群的绑定关系
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-15
 */

@Mapper
@DS("bili_room_checker")
public interface LiveWechatRMapper extends BaseMapper<LiveWechatR> {

}
