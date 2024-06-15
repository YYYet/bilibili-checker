package com.bili.sdk.service.web.api;

import com.bili.sdk.common.api.ApiBase;
import com.bili.sdk.service.web.http.WebLiveReq;
import com.dtflys.forest.Forest;

public abstract class WebLiveApi extends ApiBase {
    public WebLiveReq liveReq = Forest.client(WebLiveReq.class);
}
