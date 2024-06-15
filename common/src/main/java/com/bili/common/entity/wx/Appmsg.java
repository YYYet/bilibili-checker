/**
  * Copyright 2024 bejson.com
  */
package com.bili.common.entity.wx;

import lombok.Data;

@Data
public class Appmsg {

    private String action;
    private Appattach appattach;
    private String appid;
    private String commenturl;
    private String content;
    private String dataurl;
    private String des;
    private String extinfo;
    private String laninfo;
    private String lowdataurl;
    private String lowurl;
    private String messageaction;
    private String recorditem;
    private Refermsg refermsg;
    private String sdkver;
    private String showtype;
    private String sourcedisplayname;
    private String sourceusername;
    private String thumburl;
    private String title;
    private String type;
    private String url;
    private Weappinfo weappinfo;
    private String websearch;
    private Webviewshared webviewshared;

}
