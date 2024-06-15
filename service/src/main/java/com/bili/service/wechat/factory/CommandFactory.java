package com.bili.service.wechat.factory;


import com.bili.service.wechat.command.WxBotCommander;
import org.springframework.stereotype.Service;

@Service
public class CommandFactory extends RootCommandFacatory {

    public WxBotCommander getWxBotCommander(String type) {
        return super.commandMap.get(type);
    }

}
