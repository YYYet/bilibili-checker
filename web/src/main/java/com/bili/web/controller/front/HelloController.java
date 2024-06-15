package com.bili.web.controller.front;

import cn.hutool.core.date.DateUtil;
import com.bili.common.entity.front.DateMoney;
import com.bili.service.redis.QueryDataServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.*;

@Controller
public class HelloController {

    @Resource
    QueryDataServiceImpl queryDataService;

    public static void main(String[] args) {
        System.out.println(DateUtil.offsetDay(new Date(), 0));
    }
    @GetMapping("/test")
    public String test(Model model, @RequestParam(defaultValue = "24616501") String roomId) {
        // 获取当前日期
        Date currentDate = new Date();

        ArrayList<DateMoney> dateMoneyList = new ArrayList<>();
        // 获取前七日日期
        for (int i = 6; i >= 0; i--) {
            Date sevenDaysAgo = DateUtil.offsetDay(currentDate, -i);
            String format = DateUtil.format(sevenDaysAgo, "yyyy:MM:dd");
            DateMoney dateMoney = new DateMoney();

            String roomALlUnfreeGiftPrice = queryDataService.getRoomALlUnfreeGiftPrice(roomId, format);

            BigDecimal moeny = new BigDecimal(roomALlUnfreeGiftPrice);
            dateMoney.setDate(format.replace("2024:", "").replace(":", "-"));
            dateMoney.setMoney(moeny.divide(BigDecimal.TEN));
            dateMoneyList.add(dateMoney);
        }

//        String theDayBeforYesterday = DateUtil.offsetDay(DateUtil.date(), -2).toDateStr().replace("-", ":");
//        String yesterday = DateUtil.yesterday().toDateStr().replace("-", ":");
//        String today = DateUtil.today().replace("-", ":");
//        ArrayList<HashMap<String, Object>> dieByQiantianDate = redisHelper.getDieByDate(theDayBeforYesterday);
//        ArrayList<HashMap<String, Object>> dieByZuotianDate = redisHelper.getDieByDate(yesterday);
//        ArrayList<HashMap<String, Object>> dieByJintianDate = redisHelper.getDieByDate(today);
//
        model.addAttribute("dateMoneyList", dateMoneyList);
        model.addAttribute("currentRoomId", roomId);
//        model.addAttribute("dieByQiantianDate", dieByQiantianDate);
//        model.addAttribute("dieByZuotianDate", dieByZuotianDate);
//        model.addAttribute("dieByJintianDate", dieByJintianDate);
        return "test"; // 对应模板文件名称
    }

}
