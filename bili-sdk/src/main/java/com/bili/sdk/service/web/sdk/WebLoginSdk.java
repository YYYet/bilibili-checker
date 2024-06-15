package com.bili.sdk.service.web.sdk;

import cn.hutool.json.JSONUtil;
import com.bili.sdk.common.util.CommonUtil;
import com.bili.sdk.service.web.api.WebLoginApi;
import com.bili.sdk.service.web.entity.resp.qrcodeinfo.QRcodeInfoResp;
import com.bili.sdk.service.web.entity.resp.verifyqrcodeinfo.VerifyQRcodeInfoResp;
import com.dtflys.forest.http.ForestResponse;
import com.dtflys.forest.utils.URLEncoder;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author zhuminc
 * @date 2024/2/28
 **/
public class WebLoginSdk extends WebLoginApi {


    public static void startWebLogin() throws Exception {
        WebLoginSdk webLoginSdk = new WebLoginSdk();
        QRcodeInfoResp qRcode = webLoginSdk.getWebQRcode();
        System.out.println("qRcode  "+qRcode);
        String url = qRcode.getData().getUrl();

        String format = String.format("https://tool.lu/qrcode/basic.html?text=%s", URLEncoder.createAllUrlEncoder().encode(url, "UTF-8"));
        System.out.println("点击或者复制到下方连接到浏览器，使用移动端b站扫码登录：");
        System.out.println(format);

        //动态的添加定时任务每5秒执行一次
        final int[] count = {0};
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // 每隔10秒执行的操作
                count[0]++;

                try {
                    ForestResponse verifyQRcodeInfoResp = webLoginSdk.verifyQRcode(qRcode.getData().getQrcode_key());
                    System.out.println(verifyQRcodeInfoResp.getContent());
                    VerifyQRcodeInfoResp verifyQRcodeInfoRespObj = JSONUtil.toBean(verifyQRcodeInfoResp.getContent(), VerifyQRcodeInfoResp.class);

                    if(verifyQRcodeInfoRespObj.getData().getCode() == 0){
                        System.out.println("登录完成");
                        System.out.println("cookie："+verifyQRcodeInfoResp.getCookies());
                        CommonUtil.saveCookieOrToken(verifyQRcodeInfoResp.getCookies().toString(), false);
                        timer.cancel();
                    }
                    if(verifyQRcodeInfoRespObj.getData().getCode() == 86038){
                        timer.cancel();
                        throw new RuntimeException("二维码失效");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
                if (count[0] == 24){

                    throw new RuntimeException("过期");
                }
            }
        }, 0, 5000);

    }


    public QRcodeInfoResp getWebQRcode() throws Exception {

        return webLoginReq.getWebQRcode();
    }

    public ForestResponse verifyQRcode(String authCode) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("qrcode_key", authCode);
//        data.put("local_id", "0");
//        data.put("ts", CommonUtil.getTimeStamps());
//        data.put("auth_code", authCode);
//        HashMap<String, String> signature = WebSignUtil.signature(data);
        return webLoginReq.scanQRcode(data);
    }
}
