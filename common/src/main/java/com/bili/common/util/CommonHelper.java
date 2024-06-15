package com.bili.common.util;

import cn.hutool.core.date.DateUtil;
import com.bili.common.entity.wx.CommandSign;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;

/**
 * @author zhuminc
 * @date 2024/6/14
 **/
@Component
public class CommonHelper {
    public LocalDate dateStr2LocalDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(dateString);
        int year = DateUtil.year(date);
        YearMonth yearMonth = YearMonth.now();
        int javaMonth = yearMonth.getMonthValue();
        int month = javaMonth;
        int day = DateUtil.dayOfMonth(date);
        return LocalDate.of(year, month, day);
    }

    public String nowDate2SelfStyle(){
        return DateUtil.today().replace("-", ":");
    }

    public String buildDateStr(int limit){
        return DateUtil.offsetDay(DateUtil.date(), limit).toDateStr().replace("-", ":");
    }
    public String buildTodayDateStr(){
        return DateUtil.today().replace("-", ":");
    }

    public boolean isGroupMsg(CommandSign arg){
        return StringUtils.hasText(arg.getRoomId())
                && arg.isGroup();
    }
    public boolean isPersonMsg(CommandSign arg){
        return StringUtils.hasText(arg.getWxId()) && !arg.isGroup();
    }
}
