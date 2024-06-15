package com.bili.common.entity.mysql;

public enum UserHisDataTypeEnum {
    HIS_NAME,
    HIS_AVATAR;


    public static UserHisDataTypeEnum getByString(String cmd) {
        try {
            return valueOf(cmd);
        } catch (Exception var2) {
            return null;
        }
    }

    private UserHisDataTypeEnum() {
    }
}
