package com.bili.web.controller.front;

import com.bili.common.entity.mysql.Config;
import com.bili.service.db.ConfigServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/17
 **/
@RestController()
@RequestMapping("manage")
public class ConfigController {

    @Resource
    ConfigServiceImpl configService;

    @GetMapping("getAllConfig")
    public List<Config> getAllConfig() {
        return configService.getAllConfig();
    }

    @PostMapping("modifyConfig")
    public int modifyConfig(Config config) {
        return configService.modifyConfig(config);
    }

    @PostMapping("addConfig")
    public void addConfig(Config config) {
        configService.insertConfig(config);
    }

}
