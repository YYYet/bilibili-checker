package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Label {
    private String path;
    private String text;
    private String label_theme;
    private String text_color;
    private int bg_style;
    private String bg_color;
    private String border_color;
    private String image;
}
