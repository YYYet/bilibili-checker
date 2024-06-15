package com.bili.sdk.service.web.http;

import com.bili.sdk.common.api.Api;
import com.bili.sdk.service.web.entity.resp.qrcodeinfo.QRcodeInfoResp;
import com.dtflys.forest.annotation.*;
import com.dtflys.forest.http.ForestResponse;

import java.util.Map;

public interface WebLoginReq {


    /**
     * 获取图二维码
     * @param
     * @param
     * @return
     */
//    @Post(
//            url = Api.WEB_QRCODE_URL,
//            headers = {
//                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
//                    "Accept: application/json",
//                    "Accept-Language: en-US,en;q=0.5",
//                    "Connection: keep-alive"
//            }
//    )
//    String getWebQRcode(@Header(value = "Content-Type",defaultValue = "multipart/form-data") String contentType, @Body Map<String, String> body);

    @Get(
            url = Api.WEB_QRCODE_URL,
            headers = {
                    "User-Agent: Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/122.0.0.0 Mobile Safari/537.36 Edg/122.0.0.0"
            }
    )
    QRcodeInfoResp getWebQRcode();


    @Get(
            url = Api.WEB_SCAN_QRCODE_URL,
            headers = {
                    "User-Agent: Mozilla/5.0 BiliDroid/6.73.1 (bbcallen@gmail.com) os/android model/Mi 10 Pro mobi_app/android build/6731100 channel/xiaomi innerVer/6731110 osVer/12 network/2",
                    "Accept: application/json",
                    "Accept-Language: en-US,en;q=0.5",
                    "Connection: keep-alive"
            }
    )
    ForestResponse scanQRcode(@Query Map<String, String> query);
}
