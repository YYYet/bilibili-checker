package com.bili.web.runner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.mysql.ConfigTypeEnum;
import com.bili.dao.mapper.ConfigMapper;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.bili.strategy.TypeStrategy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class ConfigInitRunner implements CommandLineRunner {

    @Resource
    private ConfigMapper mapper;
    @Resource
    private ApplicationContext applicationContext;
    @Override
    public void run(String... args) throws Exception {
        initConfig(TypeStrategy.class, false);
        initConfig(CmdStrategy.class, true);
    }
    /**
     * 初始化配置
     *
     * @throws IllegalAccessException 非法访问异常
     */
    public void initConfig(Class beanClass, boolean used) throws IllegalAccessException{
        Map<String, ?> beans = applicationContext.getBeansOfType(beanClass);
        for (String beanName : beans.keySet()) {
            Object bean = beans.get(beanName);
            // 暂时这样取消cglib对类名的加强
            String className = bean.getClass().getSimpleName().replace("$$SpringCGLIB$$0", "");
            try {
                initConfigData(className, className, used);
            }catch (Exception e){
                log.error("error {}", e);
            }
        }
    }


    public void initConfigData(String key, String value, boolean used) throws NoSuchFieldException, IllegalAccessException {
        LambdaQueryWrapper<Config> lambdaQueryWrapper = Wrappers.<Config>lambdaQuery().eq(Config::getValue, value);
        List<Config> configs = mapper.selectList(lambdaQueryWrapper);
        if (configs.size() == 0){
            Config config = new Config();
            config.setName(key);
            config.setValue(value);
            config.setStatus(used?1:0);
            config.setType(ConfigTypeEnum.DM_LOG.name());
            mapper.insert(config);
        }
    }
}
