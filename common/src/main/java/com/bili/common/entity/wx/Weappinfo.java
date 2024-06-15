/**
  * Copyright 2024 bejson.com
  */
package com.bili.common.entity.wx;

import lombok.Data;

@Data
public class Weappinfo {

    private String appid;
    private String appservicetype;
    private String pagepath;
    private String username;
    public void setAppid(String appid) {
         this.appid = appid;
     }
     public String getAppid() {
         return appid;
     }

    public void setAppservicetype(String appservicetype) {
         this.appservicetype = appservicetype;
     }
     public String getAppservicetype() {
         return appservicetype;
     }

    public void setPagepath(String pagepath) {
         this.pagepath = pagepath;
     }
     public String getPagepath() {
         return pagepath;
     }

    public void setUsername(String username) {
         this.username = username;
     }
     public String getUsername() {
         return username;
     }

}
