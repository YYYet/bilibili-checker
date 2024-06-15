package com.bili.common.util;



import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Slf4j
public class DanMuUtil {
    public static UserInfo getUserInfoFromDanMu(JsonNode row){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(row.get(BiliConstant.MSG).get(BiliConstant.USERNAME).asText());
        userInfo.setUid(row.get(BiliConstant.MSG).get(BiliConstant.UID).asText());
        userInfo.setAvatar(row.get(BiliConstant.MSG).get(BiliConstant.USERAVATAR).asText());
        try {
            userInfo.setWealth(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UINFO).get(BiliConstant.WEALTH).get(BiliConstant.LEVEL).asText());
        }catch (Exception e){
//                log.error("缺少荣耀等级数据, 从数组中取:{}", e);
//                try {
//                    userInfo.setWealth(row.get(BiliConstant.MSG).path(BiliConstant.INFO).path(15).path(0).asText());
//                }catch (Exception e2){
//                    log.error("从数组中取失败:{}", e2);
//                }

        }
        return userInfo;
    }
    public static UserInfo getUserInfoFromUinfo(JsonNode row){
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UINFO).get(BiliConstant.BASE).get(BiliConstant.NAME).asText());
        userInfo.setWealth(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UINFO).get(BiliConstant.WEALTH).get(BiliConstant.LEVEL).asText());
        userInfo.setUid(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UINFO).get(BiliConstant.UID).asText());
        userInfo.setAvatar(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UINFO).get(BiliConstant.BASE).get(BiliConstant.FACE).asText());
        return userInfo;
    }
    public static FeedInfo getGiftFromDanMu(JsonNode row){
        GiftInfo giftInfo = new GiftInfo();
        FeedInfo feedInfo = new FeedInfo();

        if (row.get(BiliConstant.MSG).get("cmd") != null){
            if (row.get(BiliConstant.MSG).get("cmd").textValue().equals("COMBO_SEND")){
                giftInfo.setGiftPrice(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.COMBO_TOTAL_COIN).asLong()/row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.COMBO_NUM).asLong());
                giftInfo.setGiftName(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.GIFT_NAMEV2).asText());
                giftInfo.setFree(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.COIN_TYPE).asText().equals(BiliConstant.GOLD)?false:true);
                feedInfo.setGift(giftInfo);
                feedInfo.setNum(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.COMBO_NUM).asLong());
                UserInfo userInfo = new UserInfo();
                userInfo.setUid(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UID).asText());
                userInfo.setUserName(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UNAME).asText());
                feedInfo.setUserInfo(userInfo);
            }
        }else {
            giftInfo.setGiftPrice(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.DISCOUNT_PRICE).asLong());
            giftInfo.setActualPrice(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.DISCOUNT_PRICE).asLong());
            giftInfo.setGiftName(row.get(BiliConstant.MSG).get(BiliConstant.GIFT_NAME).asText());
            giftInfo.setFree(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.COIN_TYPE).asText().equals(BiliConstant.GOLD)?false:true);
            feedInfo.setNum(row.get(BiliConstant.MSG).get(BiliConstant.GIFT_COUNT).asLong());
            UserInfo userInfoFromDanMu = getUserInfoFromDanMu(row);
            feedInfo.setUserInfo(userInfoFromDanMu);
            feedInfo.setBox(false);
            if (row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.BATCH_COMBO_SEND) != null && !row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.BATCH_COMBO_SEND).isNull()){
                JsonNode COMBO = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.BATCH_COMBO_SEND);
                if (!COMBO.get(BiliConstant.BLING_GIFT).isNull()){
                    String BoxName = COMBO.get(BiliConstant.BLING_GIFT).get(BiliConstant.ORIGINAL_GIFT_NAME).asText();
                    giftInfo.setActualPrice(COMBO.get(BiliConstant.BLING_GIFT).get(BiliConstant.ORIGINAL_GIFT_PRICE).asLong());
                    feedInfo.setBox(true);
                    feedInfo.setBoxName(BoxName);
                }
            }

            feedInfo.setGift(giftInfo);
        }
        return feedInfo;
    }
    public static boolean isGuard(){
        return false;
    }
    public static FeedInfo getGiftFromGuardData(JsonNode row){
        FeedInfo feedInfo = new FeedInfo();

        GiftInfo giftInfo = new GiftInfo();
        giftInfo.setGiftPrice(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.PRICE).asLong());
        giftInfo.setGiftName(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UNIT).asText()+row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.ROLE_NAME).asText());
        giftInfo.setFree(false);

        feedInfo.setGift(giftInfo);
        feedInfo.setNum(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.NUM).asLong());

        UserInfo userInfo = new UserInfo();
        userInfo.setUid(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UID).asText());
        userInfo.setUserName(row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.USERNAME).asText());
        feedInfo.setUserInfo(userInfo);



        RoomInfo roomInfoFromRow = getRoomInfoFromRow(row);
        feedInfo.setRoomInfo(roomInfoFromRow);

        return feedInfo;
    }
    public static DanMuInfo getDanMu(JsonNode row){
        DanMuInfo danMuInfo = new DanMuInfo();
        UserInfo userInfoFromDanMu = getUserInfoFromDanMu(row);
        danMuInfo.setUserInfo(userInfoFromDanMu);
        danMuInfo.setMsg(row.get(BiliConstant.MSG).get(BiliConstant.CONTENT).asText());
        return danMuInfo;
    }

    public static RoomInfo getRoomInfoFromRow(JsonNode row){
        RoomInfo roomInfo = new RoomInfo();
        JsonNode roomId = row.get(BiliConstant.ROOMID);
        if (roomId != null){
            roomInfo.setRoomId(roomId.asText());
        }
        return roomInfo;
    }
    public static RoomInfo getTargetRoomInfoFromPkRow(JsonNode row){

        JsonNode targetRoomId = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.ROOM_ID);
        JsonNode selfRoomId = row.get(BiliConstant.MSG).get(BiliConstant.ROOMID_LOWER_I);
        JsonNode uid = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UID);
        JsonNode uname = row.get(BiliConstant.MSG).get(BiliConstant.DATA).get(BiliConstant.UNAME);

        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(uname.asText());
        userInfo.setUid(uid.asText());

        RoomInfo roomInfo = new RoomInfo();
        if (targetRoomId != null){
            roomInfo.setTargetRoomId(targetRoomId.asText());
        }
        if (selfRoomId != null){
            roomInfo.setRoomId(selfRoomId.asText());
        }
        roomInfo.setUserInfo(userInfo);

        return roomInfo;
    }
    public static String getMsgCmdFromDanMu(JsonNode row){
        String result = "";
        JsonNode msg = row.get(BiliConstant.MSG);
        if (msg != null && !msg.isNull()){
            JsonNode cmd = msg.get(BiliConstant.CMD);
            if (cmd != null && !cmd.isNull()){
                result = cmd.asText();
            }
        }

        return result;
    }
}
