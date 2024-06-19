package com.bili.web.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bili.common.config.CustomConfig;
import com.bili.common.constant.RedisConstant;
import com.bili.common.entity.mikufans.RecordEventDTO;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.wx.CommandSign;
import com.bili.common.entity.wx.notice.NoticeDTO;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.db.LiveWechatRelationServiceImpl;
import com.bili.service.wechat.command.WxBotCommander;
import com.bili.service.wechat.factory.CommandFactory;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/recordWebHook")
public class RecordWebHook {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private CommandFactory commandFactory;
    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private WechatBotServiceImpl wechatBotServiceImpl;

    public static ArrayList<String> recordRoomList = new ArrayList<>();


    @PostConstruct
    public void init() {
        List<Config> mikuFansConfig = configService.getMikuFansConfig();
        mikuFansConfig.forEach(config -> {
            recordRoomList.add(config.getValue());
        });

    }

    @PostMapping("/bot/webhook/{token}/{app}")
    public void botWebHook(@PathVariable("token") String token, @PathVariable("app") String app, @RequestBody JSONObject wxMessageInfo) {
        log.info("wxMessageInfo {}", JSONUtil.toJsonStr(wxMessageInfo));

        String content = "";
        int type = wxMessageInfo.getInt("type");
        if (type == 1) {
            content = wxMessageInfo.getStr("content");
        }
        if (type == 49) {
            content = wxMessageInfo.getJSONObject("content").getJSONObject("msg").getJSONObject("appmsg").getStr("title");
        }
        if (type == 3) {
            // 图片
            content = "图片";
        }
        if (type == 47) {
            // 表情包
            content = "表情包";
        }
        if (type == 10002) {
            content = "";
        }
        try {
            if (wxMessageInfo.containsKey("sender") && wxMessageInfo.containsKey("roomid")) {
                log.info("收到微信id来自 {}({}) 的信息 [{}]", wechatBotServiceImpl.getUserName(wxMessageInfo.get("sender").toString()), wechatBotServiceImpl.getChatGroupName(wxMessageInfo.get("roomid").toString()), wxMessageInfo.get("content"));
            }
            if (wxMessageInfo.containsKey("sender") && !wxMessageInfo.containsKey("roomid")) {
                log.info("收到微信id来自 {}({}) 的信息 [{}]", wechatBotServiceImpl.getUserName(wxMessageInfo.get("sender").toString()), wxMessageInfo.get("sender").toString(), wxMessageInfo.get("content"));
            }
        } catch (Exception e) {
            log.error("微信消息有参数为空 {}", e);
        }

        boolean isGroup = false;
        if (wxMessageInfo.containsKey("is_group")) {
            isGroup = wxMessageInfo.getBool("is_group");
        }

        if (content.startsWith("@王一狗")) {
//            String user = configService.getRobotConfig(CustomConfig.ROBOT_NOTICE_USER).get(0).getValue();
//            String group = configService.getRobotConfig(CustomConfig.ROBOT_NOTICE_GROUP).get(0).getValue();
            CommandSign commandSignDTO = new CommandSign();
            commandSignDTO.setGroup(isGroup);
            if (isGroup) {
                String roomId = wxMessageInfo.getStr("roomid");
                commandSignDTO.setRoomId(roomId);
            }
            if (!isGroup) {
                String wxId = wxMessageInfo.getStr("sender");
                commandSignDTO.setWxId(wxId);
            }

            String replace = content.replace("@王一狗", "").trim();
            replace = replace.replaceAll("\\s+", "");
            replace = replace.replaceAll("\\p{Zs}", "");
            WxBotCommander wxBotCommander = commandFactory.getWxBotCommander(replace);
            if (wxBotCommander != null) {
                wxBotCommander.exec(commandSignDTO);
            }
        }
    }


    @PostMapping
    public void processing(@RequestBody RecordEventDTO recordEvent) {
        log.info("收到录播姬的推送信息==> {}", JSONUtil.toJsonStr(recordEvent));

        List<Config> liveNoticeConfig = configService.getLiveNoticeConfig();

        liveNoticeConfig.forEach(config -> {
            boolean isJson = JSONUtil.isTypeJSON(config.getValue());
            if (!isJson){
                return;
            }
            NoticeDTO noticeConfig = JSONUtil.parseObj(config.getValue()).toBean(NoticeDTO.class);
            if (recordEvent.getEventData().getRoomId().equals(config.getName().trim()) && recordEvent.getEventType().equals("StreamStarted")) {
                log.info("config {} {}", config.getName(), recordEvent.getEventData().getRoomId());
                wechatBotServiceImpl.sendMsgAuto(noticeConfig.getTip(), noticeConfig.getReceiver());
                wechatBotServiceImpl.sendStartLiveCard(recordEvent.getEventData().getRoomId(), recordEvent.getEventData().getName(), noticeConfig.getReceiver());
            }

            if (recordEvent.getEventData().getRoomId().equals(config.getName().trim())
                    && recordEvent.getEventType().equals("StreamEnded")
                    && noticeConfig.isEndNotice()) {
                log.info("config {} {}", config.getName(), recordEvent.getEventData().getRoomId());
                String roomUnfreeGifAllPriceKey = String.format(RedisConstant.roomUnfreeGifAllPriceKey, config.getName(), DateUtil.today().replace("-", ":"));
                Object price = redisTemplate.opsForHash().get(roomUnfreeGifAllPriceKey, "price");
                if (price == null) {
                    if (noticeConfig.isAt()) {
                        wechatBotServiceImpl.sendGroupMessageToAT(noticeConfig.getTip(), noticeConfig.getReceiver(),
                                noticeConfig.getAtUserName(), noticeConfig.getAtUserWxId());
                    }
                    if (!noticeConfig.isAt()) {
                        wechatBotServiceImpl.sendMsgAuto(noticeConfig.getEndNoticePrefix() + "0圆", noticeConfig.getReceiver());
                    }

                } else {
                    BigDecimal todayPrice = new BigDecimal(price.toString());
                    wechatBotServiceImpl.sendMsgAuto(noticeConfig.getEndNoticePrefix() + (todayPrice.divide(BigDecimal.valueOf(10))) + "圆", noticeConfig.getReceiver());
                }
            }
        });
    }


    @GetMapping
    public String processing() {
        return "这里是录播姬推送的接口地址，把当前地址复制到录播姬WebHookV2里即可(前提是录播姬网络环境也可访问)";
    }
}
