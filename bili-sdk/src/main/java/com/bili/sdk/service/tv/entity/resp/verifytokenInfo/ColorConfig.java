package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ColorConfig {
    private boolean is_dark_mode_aware;
    private Day day;
    private Night night;
}
