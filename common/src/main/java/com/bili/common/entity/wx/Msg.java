/**
  * Copyright 2024 bejson.com
  */
package com.bili.common.entity.wx;

import lombok.Data;

@Data
public class Msg {

    private Appinfo appinfo;
    private Appmsg appmsg;
    private String commenturl;
    private String fromusername;
    private String scene;

}
