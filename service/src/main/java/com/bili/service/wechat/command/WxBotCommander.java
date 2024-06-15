package com.bili.service.wechat.command;


import com.bili.common.entity.wx.CommandSign;

public interface WxBotCommander {
    public String exec(CommandSign arg);
}
