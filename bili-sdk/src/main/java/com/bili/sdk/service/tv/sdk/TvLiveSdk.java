package com.bili.sdk.service.tv.sdk;

import cn.hutool.json.JSONUtil;
import com.bili.sdk.common.constant.BaseConstant;
import com.bili.sdk.common.util.BiliCommonUtil;
import com.bili.sdk.common.util.TvSignUtil;
import com.bili.sdk.service.tv.api.TvLiveApi;
import com.bili.sdk.service.tv.entity.resp.givelikeInfo.GiveLikeInfoResp;
import com.bili.sdk.service.tv.entity.resp.heartbeatinfo.HeartBeatInfoResp;
import com.bili.sdk.service.tv.entity.resp.medalInfo.MedalInfo;
import com.bili.sdk.service.tv.entity.resp.medalInfo.MedalInfoResp;
import com.bili.sdk.service.tv.entity.resp.medalInfo.MedalList;
import com.bili.sdk.service.tv.entity.resp.medalInfo.SpecialList;
import com.bili.sdk.service.tv.entity.resp.senddanmukuinfo.DanMuKuInfoResp;
import com.bili.sdk.service.tv.entity.resp.shareroominfo.ShareRoomInfoResp;
import com.bili.sdk.service.tv.entity.resp.userinfo.UserInfoResp;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Component
public class TvLiveSdk extends TvLiveApi {

    /**
     * 获取用户信息
     * @param accessToken
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public UserInfoResp GetUserInfo(String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap<String, String> map = BiliCommonUtil.initBaseParams(accessToken);
        HashMap<String, String> signature = TvSignUtil.signature(map);

        return tvLiveReq.getUserInfo(signature);
    }
    public ForestResponse GetUserInfoWithIpByAndroid(String accessToken, String uid) throws UnsupportedEncodingException, NoSuchAlgorithmException {
//        HashMap<String, String> map = BiliCommonUtil.initBaseParams(accessToken);
        HashMap<String, String> query = new HashMap<>();
        query.put("access_key", accessToken);
        query.put("appkey", BaseConstant.appTvkey);
        query.put("mobi_app", "android");
        query.put("vmid", uid);
        query.put("ps", "10");
        query.put("build", "1");
        query.put("ts", BiliCommonUtil.getTimeStamps());
        HashMap<String, String> signature = TvSignUtil.signature(query);

        return tvLiveReq.getUserInfoWithIp(signature);
    }

    /**
     * 获取所有粉丝牌 此处需要重写
     * @param accessToken
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public  MedalInfoResp GetMedalInfo(String accessToken) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ArrayList medalList = new ArrayList();
        List specialList = new ArrayList();
        HashMap map =  BiliCommonUtil.initBaseParams(accessToken);
        String page = "0";
        map.put("page", page);
        map.put("page_size", "50");
        boolean isNext = true;
        MedalInfoResp medalInfoAllResp = new MedalInfoResp();
        while (isNext){
            HashMap<String, String> signature = TvSignUtil.signature(map);

            page = String.valueOf(Integer.parseInt(page)+1);
            map.put("page", page);
            map.put("ts", BiliCommonUtil.getTimeStamps());

            MedalInfoResp medalInfoResp = tvLiveReq.getMedalInfo(signature);

            if (medalInfoResp.getData().getList()==null){
                System.out.println("medallist为空");
            }
            specialList.addAll(medalInfoResp.getData().getSpecial_list());
            medalList.addAll(medalInfoResp.getData().getList());
            if (medalInfoResp.getData().getList()==null || medalInfoResp.getData().getList().size() == 0){
                isNext = false;
                break;
            }
        }
        MedalInfo medalData = new MedalInfo();
        medalData.setList(medalList);
        medalData.setSpecial_list(specialList);
        medalInfoAllResp.setData(medalData);
        return medalInfoAllResp;
    }

    /**
     * 直播间点赞
     * @param accessToken
     * @param roomId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public  boolean giveLike(String accessToken, long roomId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initGiveLikeParams(map, roomId);
        TvSignUtil.signature(map);

        return tvLiveReq.giveALike(map).getCode() == 0;
    }
    public GiveLikeInfoResp giveLikeReturnEntity(String accessToken, long roomId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initGiveLikeParams(map, roomId);
        TvSignUtil.signature(map);

        return tvLiveReq.giveALike(map);
    }
    /**
     * 分享直播间
     * @param accessToken
     * @param roomId
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public  boolean shareRoom(String accessToken, long roomId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initShareRoomParams(map, roomId);
        TvSignUtil.signature(map);
        return tvLiveReq.shareRoom(map).getCode() == 0;
    }
    public ShareRoomInfoResp shareRoomReurnEntity(String accessToken, long roomId) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initShareRoomParams(map, roomId);
        TvSignUtil.signature(map);
        return tvLiveReq.shareRoom(map);
    }

    /**
     *  直播间发送弹幕
     * @param accessToken
     * @param roomId
     * @param msg
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean sendDanMuKu(String accessToken, long roomId, String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map =  BiliCommonUtil.initBaseParams(accessToken);
        HashMap<String, String> signature = TvSignUtil.signature(map);
        HashMap<String, String> DanMuData= BiliCommonUtil.initDanMuKuParams(roomId, msg);
        DanMuKuInfoResp danMuKuInfoResp = tvLiveReq.sendDanmuKu(signature, DanMuData);
        return danMuKuInfoResp.getCode() == 0;
    }
    public DanMuKuInfoResp sendDanMuKuReturnEntity(String accessToken, long roomId, String msg) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HashMap map =  BiliCommonUtil.initBaseParams(accessToken);
        HashMap<String, String> signature = TvSignUtil.signature(map);
        HashMap<String, String> DanMuData= BiliCommonUtil.initDanMuKuParams(roomId, msg);
        DanMuKuInfoResp danMuKuInfoResp = tvLiveReq.sendDanmuKu(signature, DanMuData);
        return danMuKuInfoResp;
    }

    /**
     * 直播间心跳
     * @param accessToken
     * @param roomId
     * @param uuids
     * @param upId
     * @return
     * @throws Exception
     */
    public boolean Heartbeat(String accessToken, long roomId, String[] uuids, String upId) throws UnsupportedEncodingException, NoSuchAlgorithmException{

        HashMap<String, String> map  = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initHeartbeatParams(map, roomId, uuids, upId);

        TvSignUtil.signature(map);

        HeartBeatInfoResp execute = tvLiveReq.heartBeat("multipart/form-data",map);

        return execute.getCode() == 0;
    }
    public HeartBeatInfoResp HeartbeatReturnEntity(String accessToken, long roomId, String[] uuids, String upId) throws UnsupportedEncodingException, NoSuchAlgorithmException{

        HashMap<String, String> map  = BiliCommonUtil.initBaseParams(accessToken);
        BiliCommonUtil.initHeartbeatParams(map, roomId, uuids, upId);
        TvSignUtil.signature(map);
        HeartBeatInfoResp execute = tvLiveReq.heartBeat("multipart/form-data",map);

        return execute;
    }

    /**
     * 进入直播间（无效）
     * @param accessToken
     * @param roomId
     * @param uuids
     * @param upId
     * @return
     * @throws Exception
     */
    public static String EntryRoom(String accessToken, long roomId, String[] uuids, String upId) throws Exception {
        HashMap data  = BiliCommonUtil.initBaseParams(accessToken);

        BiliCommonUtil.initEntryRoomParams(data, roomId, uuids, upId);

        TvSignUtil.signatureWithoutReturn(data);

//        return Forest.post(TvLiveSdk.MOBILE_ENTRY_ROOM_URL).contentTypeMultipartFormData().addBody(data).execute(String.class);
        return "";
    }

    /**
     * 取消穿戴粉丝牌
     * @param accessToken
     * @param medalId
     * @return
     * @throws Exception
     */
    public boolean TakeOffMedal(String accessToken, long medalId) throws Exception {

        HashMap data  = BiliCommonUtil.initBaseParams(accessToken);

        data.put("medal_id", String.valueOf(medalId));
        data.put("platform", "android");
        data.put("type", "1");
        data.put("version", "0");

        TvSignUtil.signatureWithoutReturn(data);

        return tvLiveReq.takeOffMedal(data).getCode() == 0;
    }

    /**
     * 穿戴粉丝牌
     * @param accessToken
     * @param medalId
     * @return
     * @throws Exception
     */
    public boolean WearMedal(String accessToken, long medalId) throws Exception {
        HashMap<String, String> data  = BiliCommonUtil.initBaseParams(accessToken);

        data.put("medal_id", String.valueOf(medalId));
        data.put("platform", "android");
        data.put("type", "1");
        data.put("version", "0");

        TvSignUtil.signatureWithoutReturn(data);

        return tvLiveReq.wearMedal(data).getCode() == 0;
    }

    /**
     * 签到
     * @param accessToken
     * @return
     * @throws Exception
     */
    public String SignIn(String accessToken) throws Exception {
        HashMap<String, String> data  = BiliCommonUtil.initBaseParams(accessToken);

        TvSignUtil.signatureWithoutReturn(data);

        return  tvLiveReq.sign(data);
    }
    /**
     * 签到
     * @param accessToken
     * @return
     * @throws Exception
     */
    public boolean VerifyToken(String accessToken) throws Exception {
        HashMap<String, String> data  = BiliCommonUtil.initBaseParams(accessToken);

        TvSignUtil.signatureWithoutReturn(data);

        return tvInfoReq.verifyToken(data).get("code").equals(0);
    }

    /**
     * 直播间是否开播
     * @param accessToken
     * @param roomId
     * @return
     * @throws Exception
     */
    public boolean isLiving(String accessToken, String roomId) throws Exception {
        MedalInfo medalInfo = this.GetMedalInfo(accessToken).getData();
        boolean flag = false;
        for (MedalList medal : medalInfo.getList()) {
            if (medal.getRoom_info().getLiving_status() == 1){
                flag = true;
                break;
            }
        }

        return flag;
    }

    /**
     * 根据房间号获取房间参数
     * @param accessToken
     * @param roomId
     * @return
     * @throws Exception
     */
    public MedalList GetOneMedalInfoByRoomId(String accessToken, String roomId) throws UnsupportedEncodingException, NoSuchAlgorithmException{
        MedalInfo medalInfo = this.GetMedalInfo(accessToken).getData();
        MedalList medal = new MedalList();
        for (MedalList item : medalInfo.getList()) {
            if (String.valueOf(item.getRoom_info().getRoom_id()).equals(roomId)){
                medal = item;
                break;
            }
        }
        for (SpecialList item : medalInfo.getSpecial_list()) {
            if (String.valueOf(item.getRoom_info().getRoom_id()).equals(roomId)){
                medal.setRoom_info(item.getRoom_info());
                medal.setMedal(item.getMedal());
                medal.setAnchor_info(item.getAnchor_info());
                medal.setSuperscript(JSONUtil.toJsonStr(item.getSuperscript()));
                break;
            }
        }
        return medal;
    }
}
