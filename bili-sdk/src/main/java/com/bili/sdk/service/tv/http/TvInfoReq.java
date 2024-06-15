package com.bili.sdk.service.tv.http;

import cn.hutool.json.JSONObject;

import com.bili.sdk.common.api.Api;
import com.dtflys.forest.annotation.Get;
import com.dtflys.forest.annotation.Query;

import java.util.Map;

public interface TvInfoReq {
    /**
     * 访问个人信息以校验token
     * @param query
     * @return
     */
    @Get(
            url = Api.TOKEN_VERIFY_WITH_TOKEN,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    JSONObject verifyToken(@Query Map<String, String> query);
}
