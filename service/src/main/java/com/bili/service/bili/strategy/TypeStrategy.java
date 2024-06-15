package com.bili.service.bili.strategy;

import com.fasterxml.jackson.databind.JsonNode;

public interface TypeStrategy {
    JsonNode exec(JsonNode row) throws Exception;
}
