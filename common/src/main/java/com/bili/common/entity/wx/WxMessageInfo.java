/**
  * Copyright 2024 bejson.com
  */
package com.bili.common.entity.wx;

import lombok.Data;

@Data
public class WxMessageInfo {

    private String sender;
    private Xml xml;
    private String extra;
    private String sign;
    private long id;
    private int type;
    private String roomid;
    private Content content;
    private boolean is_group;
    private long ts;


}
