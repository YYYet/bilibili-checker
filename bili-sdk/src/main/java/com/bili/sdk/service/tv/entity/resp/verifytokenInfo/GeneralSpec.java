package com.bili.sdk.service.tv.entity.resp.verifytokenInfo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class GeneralSpec {
    private PosSpec pos_spec;
    private SizeSpec size_spec;
    private RenderSpec render_spec;
}
