package com.bili.common.entity.mysql;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 *
 * </p>
 *
 * @author zhuminc
 * @since 2024-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_feed_info")
public class UserFeedInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;
    private String roomId;

    private String userName;

    private Integer feedNum;

    private String giftName;
    private String boxName;
    private String giftType;

    private Integer giftId;

    private BigDecimal giftPrice;

    private BigDecimal totalPrice;
    private BigDecimal actualPrice;
    private BigDecimal balance;

    private LocalDateTime feedTime;
    private String feedTimeIndex;


}
