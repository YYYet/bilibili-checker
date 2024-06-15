package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Avatar {
    private ContainerSize container_size;
    private FallbackLayers fallback_layers;
    private String mid;
}
