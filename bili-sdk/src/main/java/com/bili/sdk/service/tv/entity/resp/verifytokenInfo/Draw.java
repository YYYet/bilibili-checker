package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Draw {
    private int draw_type;
    private int fill_mode;
    private ColorConfig color_config;
}
