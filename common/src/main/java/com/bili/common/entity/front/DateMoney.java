package com.bili.common.entity.front;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhuminc
 * @date 2024/6/10
 **/
@Data
public class DateMoney {
    private String date;
    private BigDecimal money;
}
