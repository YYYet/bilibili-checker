package com.bili.sdk.service.web.http;

import com.bili.sdk.common.api.Api;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Header;
import com.dtflys.forest.annotation.Post;

import java.util.Map;

public interface WebMainStationReq {
    @Post(
            url = Api.CHARGE_WITH_COOKIE,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    String charge2User(@Header Map<String, String> head, @Body Map<String, Object> body);

    @Post(
            url = Api.RECEIVE_CARD_WITH_COOKIE,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    String reciveCard(@Header Map<String, String> head, @Body Map<String, Object> body);

}
