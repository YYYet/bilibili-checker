package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Layers {
    private boolean visible;
    private GeneralSpec general_spec;
    private LayerConfig layer_config;
    private Resource resource;
}
