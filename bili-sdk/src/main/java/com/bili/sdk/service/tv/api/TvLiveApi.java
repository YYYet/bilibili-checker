package com.bili.sdk.service.tv.api;


import com.bili.sdk.common.api.ApiBase;
import com.bili.sdk.service.tv.http.TvInfoReq;
import com.bili.sdk.service.tv.http.TvLiveReq;
import com.dtflys.forest.Forest;

public abstract class TvLiveApi extends ApiBase {
    public TvLiveReq tvLiveReq = Forest.client(TvLiveReq.class);
    public TvInfoReq tvInfoReq = Forest.client(TvInfoReq.class);
}
