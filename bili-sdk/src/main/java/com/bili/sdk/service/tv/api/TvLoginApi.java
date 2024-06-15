package com.bili.sdk.service.tv.api;


import com.bili.sdk.common.api.ApiBase;
import com.bili.sdk.service.tv.http.TvLoginReq;
import com.dtflys.forest.Forest;

public abstract class TvLoginApi extends ApiBase {
    public TvLoginReq tvLoginReq = Forest.client(TvLoginReq.class);
}
