package com.bili.sdk.service.tv.sdk;

import cn.hutool.core.net.URLEncodeUtil;
import cn.hutool.json.JSONUtil;
import com.bili.sdk.common.util.CommonUtil;
import com.bili.sdk.common.util.QRCodeUtil;
import com.bili.sdk.common.util.TvSignUtil;
import com.bili.sdk.service.tv.api.TvLoginApi;
import com.bili.sdk.service.tv.entity.resp.applycaptchainfo.ApplyCaptchaInfoResp;
import com.bili.sdk.service.tv.entity.resp.qrcodeInfo.QRcodeInfoResp;
import com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo.Cookies;
import com.bili.sdk.service.tv.entity.resp.verifyqrcodeinfo.VerifyQRcodeInfoResp;
import com.dtflys.forest.http.ForestResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class TvLoginSdk extends TvLoginApi {



    public ApplyCaptchaInfoResp applyCaptcha(){
//       return Forest.get(APPLY_CAPTCHA_URL).execute(ApplyCaptchaInfoResp.class);
       return tvLoginReq.applyCaptcha();
    }

//    public static void sendSms(String phoneNumber, String countryCode) throws Exception {
//        HashMap<String, Object> data = new HashMap<String, Object>();
//        data.put("appkey", BaseConstant.appTvkey);
//        data.put("actionKey",BaseConstant.actionKey);
//        data.put("build", 6510400);
//        data.put("channel", "bili");
//        data.put("cid", countryCode);
//        data.put("device", "phone");
//        data.put("mobi_app", "android");
//        data.put("platform", "android");
//        data.put("tel", "+"+phoneNumber);
//        data.put("ts", CommonUtil.getTimeStamps());
//        TvSignUtil.signatureByAndroidWithoutReturn(data);
//
//        System.out.println(Forest.post(SEND_SMS_URL).contentTypeMultipartFormData().addBody(data).execute(String.class));
//
//    }


    /**
     * 获取登录链接
     * @return
     * @throws Exception
     */
    public QRcodeInfoResp getQRcode() throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("local_id", "0");
        data.put("ts", CommonUtil.getTimeStamps());
//        data.put("appkey", BaseConstant.appTvkey);
        HashMap<String, String> signature = TvSignUtil.signature(data);

       return tvLoginReq.getQRcode("multipart/form-data",signature);
    }

    /**
     * 将登录链接转换为base64图片 提供给用户扫码
     * @param url
     * @param needHead
     * @return
     * @throws Exception
     */
    public String getQRcodeBase64(String url,boolean needHead) throws Exception {
        return QRCodeUtil.generateQRCodeBase64(url, needHead);
    }

    /**
     * 获取二维码状态
     * @param authCode
     * @return
     * @throws Exception
     */
    public VerifyQRcodeInfoResp verifyQRcode(String authCode) throws Exception {
        HashMap<String, String> data = new HashMap<String, String>();
        data.put("local_id", "0");
        data.put("ts", CommonUtil.getTimeStamps());
        data.put("auth_code", authCode);
        HashMap<String, String> signature = TvSignUtil.signature(data);
        ForestResponse forestResponse = tvLoginReq.verifyQRcodeWithForestResponse("multipart/form-data", signature);
        return  JSONUtil.toBean(forestResponse.getContent(), VerifyQRcodeInfoResp.class);
    }


    public static void startTvLogin() throws Exception {
        TvLoginSdk tvLoginSdk = new TvLoginSdk();
        QRcodeInfoResp qRcode = tvLoginSdk.getQRcode();
//        QRcodeInfoResp qRcode = tvLoginSdk.getQRcode();
        String auth_code = qRcode.getData().getAuth_code();
        String format = String.format("https://tool.lu/qrcode/basic.html?text=%s", URLEncodeUtil.encode(qRcode.getData().getUrl()));
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
                    VerifyQRcodeInfoResp verifyQRcodeInfoResp = tvLoginSdk.verifyQRcode(auth_code);
                    System.out.println(verifyQRcodeInfoResp.getMessage());
                    if(verifyQRcodeInfoResp.getCode() == 0){
                        System.out.println("登录完成");
//                        System.out.println(JSONUtil.toJsonStr(verifyQRcodeInfoResp.getData()));
//                        System.out.println(verifyQRcodeInfoResp.getData().getCookie_info());
                        String cookie = CommonUtil.list2Cookie(verifyQRcodeInfoResp);
                        System.out.println(cookie);
//                        System.out.println(JSONUtil.toJsonStr(verifyQRcodeInfoResp.getData().getCookie_info()));
                        HashMap<String, Object> result = new HashMap<>();
                        result.put("token", verifyQRcodeInfoResp.getData().getToken_info());
                        result.put("cookie", cookie);
                        for (Cookies cookieSingle : verifyQRcodeInfoResp.getData().getCookie_info().getCookies()) {
                            result.put(cookieSingle.getName(), cookieSingle);
                        }
                        CommonUtil.saveCookieOrToken(JSONUtil.toJsonStr(result), true);
                        timer.cancel();
                    }
                    if(verifyQRcodeInfoResp.getCode() == 86038){
                        timer.cancel();
                        throw new RuntimeException("二维码失效");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (count[0] == 24){
                    System.out.println("过期");
                    throw new RuntimeException();
                }
            }
        }, 0, 5000);

    }
}
