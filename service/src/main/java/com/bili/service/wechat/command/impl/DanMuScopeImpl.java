package com.bili.service.wechat.command.impl;


import com.bili.service.wechat.command.WxBotCommander;
import com.bili.common.entity.wx.CommandSign;
import com.bili.service.wechat.bot.WechatBotServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author zhuminc
 * @date 2024/5/28
 **/
@Component
public class DanMuScopeImpl implements WxBotCommander {

    @Resource
    private WechatBotServiceImpl wechatBotServiceImpl;
    @Override
    public String exec(CommandSign arg) {

        if (StringUtils.hasText(arg.getRoomId()) && arg.isGroup()){
            wechatBotServiceImpl.sendMsgAuto("test", arg.getRoomId());
        }
        if (StringUtils.hasText(arg.getWxId()) && !arg.isGroup()){
            wechatBotServiceImpl.sendMsgAuto("test", arg.getWxId());
        }
        return null;
    }
}
