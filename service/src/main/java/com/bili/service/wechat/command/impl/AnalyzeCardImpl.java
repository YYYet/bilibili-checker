package com.bili.service.wechat.command.impl;


import cn.hutool.json.JSONObject;
import com.bili.common.config.CustomConfig;
import com.bili.common.entity.wx.CommandSign;
import com.bili.common.util.CommonUtil;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import com.bili.service.wechat.command.WxBotCommander;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * @author zhuminc
 * @date 2024/5/28
 **/
@Component
public class AnalyzeCardImpl implements WxBotCommander {

    @Resource
    private ConfigServiceImpl configService;
    @Resource
    private WechatBotServiceImpl wechatBotServiceImpl;
    @Resource
    private CommonUtil commonUtil;
    private JSONObject value;

    public void setValue(JSONObject value) {
        this.value = value;
    }

    @Override
    public String exec(CommandSign arg) {

        String receiver = value.getStr("receiver");
        String digest = value.getStr("digest");
        String thumbUrl = value.getStr("thumbUrl");
        String account = value.getStr("account");
        String title = value.getStr("title");
        String cardUrl = value.getStr("cardUrl");
        if (cardUrl.isEmpty()){
            String netHost = configService.getNetConfig(CustomConfig.NET_FRP_HOST).getValue();
            String pagePath = configService.getNetConfig(CustomConfig.NET_PAGE_ANALYSIS).getValue();
            String roomId = value.getStr("roomId");
            cardUrl = netHost+pagePath+"?roomId="+roomId;
        }
        String name= value.getStr("name");
        return getString(arg, digest, thumbUrl, account, title, cardUrl, name, commonUtil, wechatBotServiceImpl);
    }

    @Nullable
    static String getString(CommandSign arg, String digest, String thumbUrl, String account, String title, String cardUrl, String name, CommonUtil commonUtil, WechatBotServiceImpl wechatBotServiceImpl) {
        String receiver;
        if (commonUtil.isGroupMsg(arg)){
            receiver = arg.getRoomId();
            wechatBotServiceImpl.sendCard(receiver, digest, thumbUrl, account, title, cardUrl, name);
        }
        if (commonUtil.isPersonMsg(arg)){
            receiver = arg.getWxId();
            wechatBotServiceImpl.sendCard(receiver, digest, thumbUrl, account, title, cardUrl, name);
        }

        return null;
    }
}
