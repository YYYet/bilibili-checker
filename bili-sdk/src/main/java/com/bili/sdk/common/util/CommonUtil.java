package com.bili.sdk.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.json.JSONUtil;
import com.bili.sdk.common.constant.BaseConstant;
import com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo.Cookies;
import com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo.VerifyQRcodeInfoResp;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CommonUtil {
    public static String list2Cookie( VerifyQRcodeInfoResp verifyQRcodeInfoResp){
        StringBuffer sb = new StringBuffer();
        for (Cookies cookie : verifyQRcodeInfoResp.getData().getCookie_info().getCookies()) {
           String stringTemp = "%s=%s;";
           sb.append(String.format(stringTemp, cookie.getName(), cookie.getValue()));
        }
        String headTemp = "mid=%s;expires_in=%s;";
        sb.append(String.format(headTemp, verifyQRcodeInfoResp.getData().getMid(), verifyQRcodeInfoResp.getData().getExpires_in()));
        return sb.toString();
    }

    public static ArrayList<HashMap> readCookieOrToken(boolean isTv){
        String path = isTv?"tv.txt":"web.txt";
        ArrayList<HashMap> list = new ArrayList<>();
        if(isTv){
            List<String> cookies = FileUtil.readLines(new File(path), "UTF-8");
            for (String cookie : cookies) {
                cookie = cookie.split("】")[1];
                HashMap lineMap = JSONUtil.parseObj(cookie).toBean(HashMap.class);
                list.add(lineMap);
            }
        }

        return list;

    }
    public static void saveCookieOrToken(String content, boolean isToken){
        //将String写入文件，覆盖模式，字符集为UTF-8

        String path = isToken?"tv.txt":"web.txt";
        //path指定路径下的文件如不存在，则创建

        try {
            String result = "【"+DateUtil.now() + "】  "+content;
            File file = FileUtil.appendUtf8String(result, new File(path));
        }catch (IORuntimeException e){
            //抛出一个运行时异常(直接停止掉程序)
            throw new RuntimeException("运行时异常",e);
        }
    }
    public static String getTimeStamps(){
        return String.valueOf(Instant.now().getEpochSecond());
    }
    public static long getTimeStampsWithLong(){
        return Instant.now().getEpochSecond();
    }
    public static String randomString(int length) {
        String source = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        char[] sourceChars = source.toCharArray();
        Random random = new Random();
        for (int i = sourceChars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = sourceChars[i];
            sourceChars[i] = sourceChars[j];
            sourceChars[j] = temp;
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            result.append(sourceChars[i]);
        }
        return result.toString();
    }
    public static HashMap<String, String> initBaseParams(String accessToken,HashMap<String, String> query){
        query.put("access_key", accessToken);
        query.put("actionKey", BaseConstant.actionKey);
        query.put("appkey", BaseConstant.appTvkey);
        query.put("ts", CommonUtil.getTimeStamps());
        return query;
    }


    public static HashMap<String, String> initDanMuKuParams(Long roomId, String danMuKu){
        HashMap<String, String> DanMu = new HashMap<>();
        DanMu.put("cid", String.valueOf(roomId));
        DanMu.put("msg", danMuKu);
        DanMu.put("rnd", CommonUtil.getTimeStamps());
        DanMu.put("color", "16777215");
        DanMu.put("fontsize", "25");
        return DanMu;
    }

    public static HashMap<String, String> initBaseParams(String accessToken){
        HashMap<String, String> query = new HashMap<>();
        query.put("access_key", accessToken);
        query.put("actionKey", BaseConstant.actionKey);
        query.put("appkey", BaseConstant.appTvkey);
        query.put("ts", CommonUtil.getTimeStamps());
        return query;
    }
    public static HashMap<String, String> initGiveLikeParams(HashMap<String, String> map,long roomId){
        map.put("roomid", String.valueOf(roomId));
        return map;
    }
    public static HashMap<String, String> initShareRoomParams(HashMap<String, String> map,long roomId){
        map.put("interact_type", "3");
        map.put("roomid", String.valueOf(roomId));
        return map;
    }
    public static HashMap<String, String> initHeartbeatParams(HashMap<String, String> map,long roomId, String[] uuids, String upId){
        map.put("platform", "android");
        map.put("uuid", uuids[0]);
        map.put("buvid", CommonUtil.randomString(37).toUpperCase());
        map.put("seq_id", "1");
        map.put("room_id", String.valueOf(roomId));
        map.put("parent_id", "6");
        map.put("area_id", "283");
        map.put("timestamp", String.valueOf(Instant.now().getEpochSecond()-60));
        map.put("secret_key", "axoaadsffcazxksectbbb");
        map.put("watch_time", "60");
        map.put("up_id", upId);
        map.put("up_level", "40");
        map.put("jump_from", "30000");
        map.put("gu_id", CommonUtil.randomString(43).toUpperCase());
        map.put("play_type", "0");
        map.put("play_url", "");
        map.put("s_time", "0");
        map.put("data_behavior_id", "");
        map.put("data_source_id", "");
        map.put("up_session", String.format("l:one:live:record:%d:%d", roomId,  Instant.now().getEpochSecond() - 88888));
        map.put("visit_id", CommonUtil.randomString(32).toUpperCase());
        map.put("watch_status", "%7B%22pk_id%22%3A0%2C%22screen_status%22%3A1%7D");
        map.put("click_id", uuids[1]);
        map.put("session_id", "");
        map.put("player_type", "0");
        map.put("client_ts", String.valueOf(Instant.now().getEpochSecond()));

        String dataStr = buildClientSignData(map);
        map.put("client_sign", TvSignUtil.clientSign(dataStr));
        return map;
    }
    public static HashMap<String, String> initEntryRoomParams(HashMap<String, String> map,long roomId, String[] uuids, String upId){
        map.put("platform", "android");
        map.put("uuid", uuids[0]);
        map.put("buvid", CommonUtil.randomString(37).toUpperCase());
        map.put("seq_id", "1");
        map.put("room_id", String.valueOf(roomId));
        map.put("parent_id", "6");
        map.put("area_id", "283");
        map.put("timestamp", String.valueOf(CommonUtil.getTimeStampsWithLong()-60));
        map.put("up_id", upId);
        map.put("watch_time", "60");
        map.put("up_level", "40");
        map.put("jump_from", "30000");
        map.put("gu_id", CommonUtil.randomString(43).toUpperCase());
        map.put("visit_id", CommonUtil.randomString(32).toUpperCase());
        map.put("click_id", uuids[1]);
        map.put("heart_beat", "[]");
        map.put("client_ts", CommonUtil.getTimeStamps());
        return map;
    }

    public static String buildClientSignData(HashMap<String, String> map){
        String dataStr = String.format("{\"platform\":\"%s\",\"uuid\":\"%s\",\"buvid\":\"%s\",\"seq_id\":\"%s\",\"room_id\":\"%s\",\"parent_id\":\"%s\",\"area_id\":\"%s\",\"timestamp\":\"%s\",\"secret_key\":\"%s\",\"watch_time\":\"%s\",\"up_id\":\"%s\",\"up_level\":\"%s\",\"jump_from\":\"%s\",\"gu_id\":\"%s\",\"play_type\":\"%s\",\"play_url\":\"%s\",\"s_time\":\"%s\",\"data_behavior_id\":\"%s\",\"data_source_id\":\"%s\",\"up_session\":\"%s\",\"visit_id\":\"%s\",\"watch_status\":\"%s\",\"click_id\":\"%s\",\"session_id\":\"%s\",\"player_type\":\"%s\",\"client_ts\":\"%s\"}",
                map.get("platform"),
                map.get("uuid"),
                map.get("buvid"),
                map.get("seq_id"),
                map.get("room_id"),
                map.get("parent_id"),
                map.get("area_id"),
                map.get("timestamp"),
                map.get("secret_key"),
                map.get("watch_time"),
                map.get("up_id"),
                map.get("up_level"),
                map.get("jump_from"),
                map.get("gu_id"),
                map.get("play_type"),
                map.get("play_url"),
                map.get("s_time"),
                map.get("data_behavior_id"),
                map.get("data_source_id"),
                map.get("up_session"),
                map.get("visit_id"),
                map.get("watch_status"),
                map.get("click_id"),
                map.get("session_id"),
                map.get("player_type"),
                map.get("client_ts"));
        return dataStr;
    }
}
