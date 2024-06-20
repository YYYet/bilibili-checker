package com.bili.common.util;

import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Component
public class CommonHttpUtil {
    public JSONObject getRoomInfo(String roomId){
        String res = cn.hutool.http.HttpUtil.get("https://api.live.bilibili.com/room/v1/Room/get_info?room_id=" + roomId);
        return JSONUtil.parseObj(res);
    }

    public JSONObject getRoomInfoByUserIds(ArrayList<String> uids){
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        map.put("uids", uids);
        HttpRequest request = cn.hutool.http.HttpUtil.createPost("https://api.live.bilibili.com/room/v1/Room/get_status_info_by_uids");
        request.body(JSONUtil.toJsonStr(map));
        request.header(Header.USER_AGENT, "Mozilla/5.0 (Linux; Android 13; Pixel 7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Mobile Safari/537.36 Edg/126.0.0.0");
        String body = request.execute().body();
        return JSONUtil.parseObj(body);
    }

    public String getRoomInfo(String roomId, String cookie){
        JSONObject roomInfo = getRoomInfo(roomId);
        String uid = roomInfo.getJSONObject("data").getStr("uid");
        HttpRequest request = cn.hutool.http.HttpUtil.createGet("https://api.live.bilibili.com/xlive/app-room/v2/guardTab/topListNew?roomid="+roomId+"&page=1&ruid="+uid+"&page_size=20&typ=0&platform=web");
        request.cookie(cookie);
        String result = request.execute().body();
        JSONObject entries = JSONUtil.parseObj(result);
        String num = entries.getJSONObject("data").getJSONObject("info").getStr("num");
        return num+"位舰长";
    }

//    public static void main(String[] args) {
//        CommonHttpUtil commonHttpUtil = new CommonHttpUtil();
//        String roomInfo = commonHttpUtil.getRoomInfo("1758157493", "i-wanna-go-back=-1; buvid_fp_plain=undefined; CURRENT_FNVAL=4048; buvid4=04B5C146-29FB-8A27-250D-45350BEE971729002-022082415-HRBpZZfDi%2FEl%2F0TnFs%2BuGw%3D%3D; b_ut=5; _uuid=7743AD55-586A-F10CA-3EE9-58199FCFA61393434infoc; buvid3=4D06771D-7CD8-7246-D73C-1631113D032970262infoc; b_nut=1697004670; enable_web_push=DISABLE; header_theme_version=CLOSE; rpdid=|(umRJ|RRllm0J'u~|YRmkJ~k; FEED_LIVE_VERSION=V_WATCHLATER_PIP_WINDOW3; LIVE_BUVID=AUTO1817132359479120; fingerprint=547248f90ab2b5be7b9cfebe4e56d495; buvid_fp=547248f90ab2b5be7b9cfebe4e56d495; bp_t_offset_22148666=936383448446140435; DedeUserID=3493135149697066; DedeUserID__ckMd5=3d91e131d59bb055; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTc4OTYxOTksImlhdCI6MTcxNzYzNjkzOSwicGx0IjotMX0.1MwdGP6KvoOstbWhO-RijLpnv6sHj5QZOlVWBwAV6fE; bili_ticket_expires=1717896139; PVID=1; SESSDATA=92e48357%2C1733317716%2Cc4d54%2A62CjBimNO7YxaagEQtT3MMmzhFaMk28ItyEdW5WskbPKmxi7E2DeF1jvaKl6dPJiYgUDgSVmJpTlQzQlYtaXNoTmg1a2Y3THdFNl9EcXRUU2VzUVpUNWJJQkdSUGV0VzZWNlA5UWJpdWdsNHBfQjd0aFdyT1V3ZGpIX29DSDBvZUNSckRSemtSbEN3IIEC; bili_jct=85169357418c7335a1603bcec84e1d07; sid=5w3ypybo; b_lsid=E487414B_18FF3311CFB; bp_t_offset_3493135149697066=940309714289295433; home_feed_column=4; browser_resolution=285-507");
//        System.out.println(roomInfo);
//    }
}
