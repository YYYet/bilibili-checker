package com.bili.service.wechat.bot;


import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bili.common.config.CustomConfig;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.mysql.ConfigTypeEnum;
import com.bili.common.util.CommonHttpUtil;
import com.bili.dao.mapper.LiveWechatRMapper;
import com.bili.service.db.ConfigServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author zhuminc
 * @date 2024/4/26
 **/
@Component
@Slf4j
public class WechatBotServiceImpl {


    @Resource
    private LiveWechatRMapper liveWechatRMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    ConfigServiceImpl configService;
    public static String host = "http://127.0.0.1:7600";
    public static ArrayList<String> receiverUserList = new ArrayList<>();
    public static ArrayList<String> receiverGroupList = new ArrayList<>();

    @PostConstruct
    public void init() {
        Config wcfConfig = configService.getWcfConfig(ConfigTypeEnum.WCF, CustomConfig.WCF_HOST);
        if (wcfConfig != null) {
            this.host = wcfConfig.getValue();
        }
        List<Config> robotConfig = configService.getRobotConfig();
        robotConfig.forEach(item -> {
            if (item.getName().equals(CustomConfig.ROBOT_NOTICE_GROUP)) {
                receiverGroupList.add(item.getValue());
            }
            if (item.getName().equals(CustomConfig.ROBOT_NOTICE_USER)) {
                receiverUserList.add(item.getValue());
            }
        });
    }

    public void sendMsgToSelf(String msg) {
        receiverUserList.forEach(wxid -> {
            sendMsg(host + "/wcf/send_txt", wxid, msg);
        });

    }

    public void sendMsgToSomeBody(String msg, String receiver) {
        sendMsg(host + "/wcf/send_txt", receiver, msg);
    }

    public void sendMsgAuto(String msg, String receiver) {

        if (receiver.endsWith("@chatroom")) {
            this.sendGroupMessage(msg, receiver);
        } else {
            sendMsg(host + "/wcf/send_txt", receiver, msg);
        }
    }

    public void sendGroupMessageToSGY(String msg) {
        this.receiverGroupList.forEach(gourpId -> {
            sendGroupMessage(host + "/wcf/send_txt", gourpId, msg);
        });

    }

    public void sendGroupMessage(String msg, String receiver) {
        sendGroupMessage(host + "/wcf/send_txt", receiver, msg);
    }

    public void sendGroupMessageToAT(String msg, String gourpId, String atUserName, String atUserWxId) {
        sendATMsg(host + "/wcf/send_txt", gourpId, msg + " @" + atUserName + " ", atUserWxId);
    }


    public String getChatGroupName(String chatRoomId) {
        String url = host + "/bot/chatroom/detail";
        JSONObject json = new JSONObject();
        json.set("rd", 0);
        json.set("roomid", chatRoomId);
        String post = HttpUtil.post(url, json.toString());
        JSONObject entries = JSONUtil.parseObj(post);
        return entries.getJSONObject("Payload").getStr("name");
    }

    public String getUserName(String wxId) {
        String url = host + "/wcf/user_info";
        JSONObject json = new JSONObject();
        json.set("wxid", wxId);
        String post = HttpUtil.post(url, json.toString());
        JSONObject entries = JSONUtil.parseObj(post);
        return entries.getJSONObject("Payload").getStr("name");
    }

    public void sendGroupMessage(String url, String receiver, String msg) {
        sendMsg(url, receiver, msg);
    }


    public void sendStartLiveCard(String roomId, String userName) {
        JSONObject roomInfo = new CommonHttpUtil().getRoomInfo(roomId);
        String uid = roomInfo.getJSONObject("data").getStr("uid");
        String userCover = roomInfo.getJSONObject("data").getStr("user_cover");
        String title = roomInfo.getJSONObject("data").getStr("title");
        String digest = "UP主：" + userName + "\n房间号：" + roomId;
        String roomUrl = "https://live.bilibili.com/" + roomId;
        receiverGroupList.forEach(receiver -> {
            sendCard(receiver, digest, userCover, "account", title, roomUrl, "name");
        });

    }

    public void sendStartLiveCard(String roomId, String userName, String receiver) {
        JSONObject roomInfo = new CommonHttpUtil().getRoomInfo(roomId);
        String uid = roomInfo.getJSONObject("data").getStr("uid");
        String userCover = roomInfo.getJSONObject("data").getStr("user_cover");
        String title = roomInfo.getJSONObject("data").getStr("title");
        String digest = "UP主：" + userName + "\n房间号：" + roomId;
        String roomurl = "https://live.bilibili.com/" + roomId;
        sendCard(receiver, digest, userCover, "account", title, roomurl, "name");
    }


    public static void sendCard(String receiver,
                                String digest,
                                String thumbUrl,
                                String account,
                                String title,
                                String roomUrl,
                                String name) {

        try {
            JSONObject json = new JSONObject();
            json.set("account", account);
            json.set("digest", digest);
            json.set("name", name);
            json.set("receiver", receiver);
            json.set("thumburl", thumbUrl);
            json.set("title", title);
            json.set("url", roomUrl);
            HttpUtil.post(host + "/wcf/send_rich_text", json.toString());
        } catch (Exception e) {
           log.error("sendCard error : {}", e);
        }
    }

    public void sendATMsg(String url, String receiver, String msg, String wxid) {

        try {
            JSONObject json = new JSONObject();
            JSONArray objects = new JSONArray();
            objects.add(wxid);
            json.set("aters", objects);
            json.set("msg", msg);
            json.set("receiver", receiver);
            HttpUtil.post(url, json.toString());
        } catch (Exception e) {
            log.error("sendATMsg error : {}", e);
        }
    }

    public void sendMsg(String url, String receiver, String msg) {

        try {
            JSONObject json = new JSONObject();
            JSONArray objects = new JSONArray();
            objects.add("string");
            json.set("aters", objects);
            json.set("msg", msg);
            json.set("receiver", receiver);
            HttpUtil.post(url, json.toString());
        } catch (Exception e) {
            log.error("sendMsg error : {}", e);
        }
    }


}
