package com.bili.sdk.service.web.http;

import com.bili.sdk.common.api.Api;
import com.dtflys.forest.annotation.Body;
import com.dtflys.forest.annotation.Post;

import java.util.Map;

public interface WebLiveReq {
    @Post(
            url = Api.ENTRY_ROOM_WITH_WEB,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json, text/plain, */*",
                    "Accept-Language: zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6",
                    "Sec-Fetch-Dest: empty",
                    "Sec-Fetch-Mode: cors",
                    "Sec-Fetch-Site: same-site",
                    "Connection: keep-alive"
            }
    )
    String entryRoom(@Body Map<String, String> body);
}
