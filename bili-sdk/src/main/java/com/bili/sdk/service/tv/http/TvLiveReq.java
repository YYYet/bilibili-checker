package com.bili.sdk.service.tv.http;


import com.bili.sdk.common.api.Api;
import com.bili.sdk.service.tv.entity.resp.common.Resp.CommonResp;
import com.bili.sdk.service.tv.entity.resp.givelikeInfo.GiveLikeInfoResp;
import com.bili.sdk.service.tv.entity.resp.heartbeatinfo.HeartBeatInfoResp;
import com.bili.sdk.service.tv.entity.resp.medalInfo.MedalInfoResp;
import com.bili.sdk.service.tv.entity.resp.senddanmukuinfo.DanMuKuInfoResp;
import com.bili.sdk.service.tv.entity.resp.shareroominfo.ShareRoomInfoResp;
import com.bili.sdk.service.tv.entity.resp.userinfo.UserInfoResp;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;

import java.util.Map;

public interface TvLiveReq {
    /**
     * 获取当前佩戴粉丝勋章
     * @param query
     * @return
     */
    @Get(
            url = Api.GET_USERINFO_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    UserInfoResp getUserInfo(@Query Map<String, String> query);

    @Get(
            url = Api.GET_TARGET_UERINFO_WITH_IP_ANDROID,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    ForestResponse getUserInfoWithIp(@Query Map<String, String> query);

    /**
     * 获取用户所有粉丝勋章
     * @param query
     * @return
     */
    @Get(
            url = Api.GET_MEDAL_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    MedalInfoResp getMedalInfo(@Query Map<String, String> query);

    /**
     * 直播间点赞
     * @param body
     * @return
     */
    @Post(
            url = Api.LIKE_INTERACT_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    GiveLikeInfoResp giveALike(@Body Map<String, String> body);

    /**
     * 直播间分享
     * @param body
     * @return
     */
    @Post(
            url = Api.SHARE_ROOM_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    ShareRoomInfoResp shareRoom(@Body Map<String, String> body);

    /**
     * 直播间发送弹幕
     * @param query
     * @param body
     * @return
     */
    @Post(
            url = Api.SEND_DANMUKU_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    DanMuKuInfoResp sendDanmuKu(@Query Map<String, String> query, @Body Map<String, String> body);

    /**
     * 直播间心跳
     * @param contentType
     * @param body
     * @return
     */
    @Post(
            url = Api.MOBILE_HEART_BEAT_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    HeartBeatInfoResp heartBeat(@Header(value = "Content-Type",defaultValue = "multipart/form-data") String contentType, @Body Map<String, String> body);

    /**
     * 取消穿戴粉丝勋章
     * @param body
     * @return
     */
    @Post(
            url = Api.TAKE_OFF_MEDAL_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    CommonResp takeOffMedal(@Body Map<String, String> body);

    /**
     * 穿戴粉丝勋章
     * @param body
     * @return
     */
    @Post(
            url = Api.WEAR_MEDAL_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    CommonResp wearMedal(@Body Map<String, String> body);

    /**
     * 签到
     * @param query
     * @return
     */
    @Get(
            url = Api.SIGN_URL_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    String sign(@Query Map<String, String> query);

}
