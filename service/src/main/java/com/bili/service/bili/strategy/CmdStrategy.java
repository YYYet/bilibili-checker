package com.bili.service.bili.strategy;

import com.fasterxml.jackson.databind.JsonNode;

public interface CmdStrategy {
    JsonNode exec(JsonNode row) throws Exception;
}
