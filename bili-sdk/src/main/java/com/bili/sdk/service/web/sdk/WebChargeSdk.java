package com.bili.sdk.service.web.sdk;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bili.sdk.service.web.api.WebMainStationApi;

import java.util.HashMap;

/**
 * @author zhuminc
 * @date 2024/3/9
 **/
public class WebChargeSdk extends WebMainStationApi {



    public void AutoChargeUser(String line, long userId){
        HashMap lineMap = JSONUtil.parseObj(line).toBean(HashMap.class);
        HashMap biliJctMap = JSONUtil.parseObj(lineMap.get("bili_jct")).toBean(HashMap.class);
        ChargeUser(5, false, userId, "up", userId, lineMap.get("cookie").toString(), biliJctMap.get("value").toString());
    }
    public boolean reciveBpCard(String line){
        HashMap lineMap = JSONUtil.parseObj(line).toBean(HashMap.class);
        HashMap biliJctMap = JSONUtil.parseObj(lineMap.get("bili_jct")).toBean(HashMap.class);
        return reciveCard(1, lineMap.get("cookie").toString(), biliJctMap.get("value").toString());
    }

    public boolean reciveCard(int type, String cookie, String bilJct){
        //1：B币券
        //2：会员购优惠券
        //3：漫画福利券
        //4：会员购包邮券
        //5：漫画商城优惠券
        //6：装扮体验卡
        //7：课堂优惠券
        HashMap<String, Object> body = new HashMap<>();
        body.put("type", type);
        body.put("csrf", bilJct);

        HashMap<String, String> head = new HashMap<>();
        head.put("Cookie", cookie);
        head.put("Content-Type", "multipart/form-data");
        String s = webMainStationReq.reciveCard(head, body);
        JSONObject entries = JSONUtil.parseObj(s);

        System.out.println(entries);
        return Integer.parseInt(entries.get("code").toString()) == 0;
    }
    public void ChargeUser(int bpNum, boolean isBpFirst, long upMid, String otype, long oid, String cookie, String bilJct){
        HashMap<String, Object> body = new HashMap<>();
        body.put("bp_num", bpNum);
        body.put("is_bp_remains_prior", isBpFirst);
        body.put("up_mid", upMid);
        body.put("otype", otype);
        body.put("oid", oid);
        body.put("csrf", bilJct);

        HashMap<String, String> head = new HashMap<>();
        head.put("Cookie", cookie);
        head.put("Content-Type", "multipart/form-data");
        String s = webMainStationReq.charge2User(head, body);
        JSONObject entries = JSONUtil.parseObj(s);
        System.out.println(entries);
    }


}
