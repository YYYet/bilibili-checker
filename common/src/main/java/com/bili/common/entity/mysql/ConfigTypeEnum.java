package com.bili.common.entity.mysql;

public enum ConfigTypeEnum {
    DM_LOG,
    NET,
    MIKUFANS_RECIVE_ROOM,
    WCF,
    MIKUFANS,
    BILI_SDK,
    ANALYZE_CARD,
    BARRAGE,
    USER_IP,
    WATCH_ROOM,
    ROBOT, LIVE_NOTICE;


    public static ConfigTypeEnum getByString(String cmd) {
        try {
            return valueOf(cmd);
        } catch (Exception var2) {
            return null;
        }
    }

    private ConfigTypeEnum() {
    }
}
