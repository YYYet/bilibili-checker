package com.bili.sdk.common.api;

public  class Api {
    /** token接口 **/
    public final static  String GET_USERINFO_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-ucenter/v1/user/get_user_info";
    // 获取所有粉丝牌
    public final static  String GET_MEDAL_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-ucenter/v1/fansMedal/panel";
    // 直播间点赞
    public final static  String LIKE_INTERACT_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/web-ucenter/v1/interact/likeInteract";
    // 分享直播间
    public final static  String SHARE_ROOM_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-room/v1/index/TrigerInteract";
    // 发送弹幕
    public final static  String SEND_DANMUKU_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-room/v1/dM/sendmsg";
    // 直播间挂机心跳
    public final static  String MOBILE_HEART_BEAT_URL_WITH_TOKEN = "https://live-trace.bilibili.com/xlive/data-interface/v1/heartbeat/mobileHeartBeat";
    // 进入直播间
    public final static  String MOBILE_ENTRY_ROOM_URL_WITH_TOKEN = "http://live-trace.bilibili.com/xlive/data-interface/v1/heartbeat/mobileEntry";
    // 取消穿戴粉丝牌
    public final  static  String TAKE_OFF_MEDAL_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-ucenter/v1/fansMedal/take_off";
    // 穿戴粉丝牌
    public final static  String WEAR_MEDAL_URL_WITH_TOKEN = "https://api.live.bilibili.com/xlive/app-ucenter/v1/fansMedal/wear";
    // 签到
    public final static  String SIGN_URL_WITH_TOKEN = "https://api.live.bilibili.com/rc/v1/Sign/doSign";
    // accessToken校验
    public final static  String TOKEN_VERIFY_WITH_TOKEN = "https://app.bilibili.com/x/v2/account/mine";
    // web充电接口
    public final static  String CHARGE_WITH_COOKIE = "https://api.bilibili.com/x/ugcpay/web/v2/trade/elec/pay/quick";
    public final static  String RECEIVE_CARD_WITH_COOKIE = "https://api.bilibili.com/x/vip/privilege/receive";
    public final static  String GET_TARGET_UERINFO_WITH_IP_ANDROID = "https://app.bilibili.com/x/v2/space";



    /** cookie接口 **/
    public final static String ENTRY_ROOM_WITH_WEB = "https://live-trace.bilibili.com/xlive/data-interface/v1/x25Kn/E";
    public final static String HEARTBEAT_WITH_WEB = "https://live-trace.bilibili.com/xlive/data-interface/v1/x25Kn/X";

    /** login接口 **/
    public final static String QRCODE_URL = "https://passport.snm0516.aisee.tv/x/passport-tv-login/qrcode/auth_code";
    public final static String WEB_QRCODE_URL = "https://passport.bilibili.com/x/passport-login/web/qrcode/generate";
    public final static String WEB_SCAN_QRCODE_URL = "https://passport.bilibili.com/x/passport-login/web/qrcode/poll";
    public final static String SCAN_QRCODE_URL = "https://passport.snm0516.aisee.tv/x/passport-tv-login/qrcode/poll";
    public final static String VERIFY_QRCODE_URL = "https://passport.bilibili.com/x/passport-tv-login/qrcode/poll";
    public final static String SEND_SMS_URL = "https://passport.bilibili.com/x/passport-login/sms/send";
    public final static String APPLY_CAPTCHA_URL = "https://passport.bilibili.com/x/passport-login/captcha?source=main_web";

    /**
     * 直播接口
     */
    public final static String ROOM_INIT = "https://api.live.bilibili.com/room/v1/Room/room_init";

}
