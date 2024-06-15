package com.bili.sdk.service.web.api;

import com.bili.sdk.common.api.ApiBase;
import com.bili.sdk.service.web.http.WebLoginReq;
import com.dtflys.forest.Forest;

/**
 * @author zhuminc
 * @date 2024/2/28
 **/
public abstract class WebLoginApi extends ApiBase {
    public WebLoginReq webLoginReq = Forest.client(WebLoginReq.class);
}
