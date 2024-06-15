package com.bili.common.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 弹幕转发任务实体类
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-07
 */
@Data
@ToString
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("barrage_fly_task")
public class BarrageFlyTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 主键UUID
     */
    private String uuid;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 平台
     */
    private String platform;

    /**
     * 房间号
     */
    private String roomId;

    /**
     * 备注
     */
    private String remark;

    /**
     * Cookie
     */
    private String cookie;

    /**
     * 消息前置处理规则引擎脚本
     */
    private String msgPreMapExpress;

    /**
     * 消息过滤规则引擎脚本
     */
    private String msgFilterExpress;

    /**
     * 消息后置处理规则引擎脚本
     */
    private String msgPostMapExpress;

    /**
     * Json序列化后的Client配置类
     */
    private String clientConfigJson;


}
