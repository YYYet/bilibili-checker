package com.bili.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class CheckerConfig {

    @Value("${checker.auto_connect}")
    private boolean autoConnect;

}
