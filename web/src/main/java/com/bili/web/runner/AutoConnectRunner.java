package com.bili.web.runner;


import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.config.CheckerConfig;
import com.bili.common.entity.mysql.BarrageFlyTask;
import com.bili.dao.mapper.BarrageFlyTaskMapper;
import com.bili.web.controller.ConnectController;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Slf4j
public class AutoConnectRunner implements CommandLineRunner {

    @Resource
    private BarrageFlyTaskMapper mapper;
    @Resource
    private ConnectController connectController;
    @Resource
    private CheckerConfig checker;

    @Override
    public void run(String... args) throws Exception {
        if (checker.isAutoConnect()){
            QueryWrapper<BarrageFlyTask> objectQueryWrapper = new QueryWrapper<BarrageFlyTask>();
            List<BarrageFlyTask> barrageFlyTasks = mapper.selectList(objectQueryWrapper);
            barrageFlyTasks.forEach(task->{
                ThreadUtil.execute(() -> {
                    connectController.subscribe(task.getUuid());
                });
            });
        }
    }
}
