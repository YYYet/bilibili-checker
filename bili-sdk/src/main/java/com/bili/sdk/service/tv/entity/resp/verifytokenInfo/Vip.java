package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Vip {
    private int type;
    private int status;
    private int due_date;
    private int vip_pay_type;
    private int theme_type;
    private int themeType;
    private Label label;
    private int avatar_subscript;
    private String nickname_color;
    private int role;
    private String avatar_subscript_url;
}
