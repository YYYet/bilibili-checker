package com.bili.sdk.service.tv.entity.resp.heartbeatinfo;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class HeartBeatInfo {
    private int heartbeat_interval;
    private long timestamp;
    private List<Integer> secret_rule;
    private String secret_key;
}
