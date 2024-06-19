package com.bili.service.wechat.command.impl;


import cn.hutool.json.JSONObject;
import com.bili.common.config.CustomConfig;
import com.bili.common.entity.wx.CommandSign;
import com.bili.common.util.CommonHelper;
import com.bili.service.db.ConfigServiceImpl;
import com.bili.service.db.LiveWechatRelationServiceImpl;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import com.bili.service.wechat.command.WxBotCommander;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author zhuminc
 * @date 2024/5/28
 **/
@Component
@Scope("prototype")
@Slf4j
public class AnalyzeCardImpl implements WxBotCommander {

    @Resource
    ConfigServiceImpl configService;
    @Resource
    WechatBotServiceImpl wechatBotServiceImpl;
    @Resource
    CommonHelper commonHelper;
    @Resource
    private LiveWechatRelationServiceImpl liveWechatRelationService;

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

        if (!liveWechatRelationService.hasRelation(arg.getRoomId(), value.getStr("roomId"))){
            log.info("当前直播间和微信群无绑定关系");
            return null;
        }

        if (cardUrl.isEmpty()){
            String netHost = configService.getNetConfig(CustomConfig.NET_FRP_HOST).getValue();
            String pagePath = configService.getNetConfig(CustomConfig.NET_PAGE_ANALYSIS).getValue();
            String roomId = value.getStr("roomId");
            cardUrl = netHost+pagePath+"?roomId="+roomId;
        }
        String name= value.getStr("name");
        if (commonHelper.isGroupMsg(arg)){
            receiver = arg.getRoomId();
            wechatBotServiceImpl.sendCard(receiver, digest, thumbUrl, account, title, cardUrl, name);
        }
        if (commonHelper.isPersonMsg(arg)){
            receiver = arg.getWxId();
            wechatBotServiceImpl.sendCard(receiver, digest, thumbUrl, account, title, cardUrl, name);
        }
        return null;
    }


}
