package com.bili.sdk.service.web.api;

import com.bili.sdk.common.api.ApiBase;
import com.bili.sdk.service.web.http.WebMainStationReq;
import com.dtflys.forest.Forest;

/**
 * @author zhuminc
 * @date 2024/3/9
 **/
public class WebMainStationApi extends ApiBase {
    public static WebMainStationReq webMainStationReq = Forest.client(WebMainStationReq.class);
}
