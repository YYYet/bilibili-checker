/**
  * Copyright 2024 bejson.com
  */
package com.bili.common.entity.wx;

import lombok.Data;

@Data
public class Msgsource {

    private String membercount;
    private String pua;
    private Sec_msg_node sec_msg_node;
    private String signature;
    private String silence;
    private Tmp_node tmp_node;

}
