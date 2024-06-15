package com.bili.service.db;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bili.common.entity.mysql.Config;
import com.bili.common.entity.mysql.ConfigTypeEnum;
import com.bili.dao.mapper.ConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Service
public class ConfigServiceImpl {
    @Resource
    ConfigMapper configMapper;

    /**
     * 根据配置类别和配置key查询配置项
     * @param key
     * @return
     */
    public Config getConfig(String key) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", ConfigTypeEnum.DM_LOG.name()).eq("name", key));
        return configs.size() == 0 ? null : configs.get(0);
    }

    /**
     * 通过key查询bot
     * @param key
     * @return
     */
    public List<Config> getRobotConfig(String key) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", ConfigTypeEnum.ROBOT.name()).eq("name", key).eq("status", 1));
        return configs;
    }

    /**
     * 查询所有type为bot的机器人配置项
     * @return
     */
    public List<Config> getRobotConfig() {
        return getConfig(ConfigTypeEnum.ROBOT);
    }

    /**
     * 查询所有bilisdk相关的配置项
     * @return
     */
    public List<Config> getBiliSdkConfig() {
        return getConfig(ConfigTypeEnum.BILI_SDK);
    }

    /**
     * 查询biliSdk相关参数
     * @param name
     * @return
     */
    public Config getBiliSdkConfig(String name) {
        return getConfig(ConfigTypeEnum.BILI_SDK, name).get(0);
    }

    /**
     * 查询网络相关参数
     * @param name
     * @return
     */
    public Config getNetConfig(String name) {
        return getConfig(ConfigTypeEnum.NET, name).get(0);
    }

    /**
     * 获取弹幕服务地址
     * @return
     */
    public Config getBarrageConfig() {
        return getConfig(ConfigTypeEnum.BARRAGE).get(0);
    }

    /**
     * 获取所有wx卡片的配置
     * @return
     */
    public List<Config> getAnalyzeCardConfig() {
        return getConfig(ConfigTypeEnum.ANALYZE_CARD);
    }
    /**
     * 查询直播通知相关参数
     * @return
     */
    public List<Config> getLiveNoticeConfig() {
        return getConfig(ConfigTypeEnum.LIVE_NOTICE);
    }

    /**
     * 查询token相关参数
     * @param name
     * @return
     */
    public List<Config> getTokenConfig(String name) {
        return getConfig(ConfigTypeEnum.BILI_SDK, name);
    }

    /**
     * 查询所有mikuFans相关参数
     * @return
     */
    public List<Config> getMikuFansConfig() {
        return getConfig(ConfigTypeEnum.MIKUFANS);
    }

    /**
     * 通过type查询所有配置
     * @param configTypeEnum
     * @return
     */
    public List<Config> getConfig(ConfigTypeEnum configTypeEnum) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", configTypeEnum.name()).eq("status", 1));
        return configs;
    }

    /**
     * 通过key和type查询所有启用的配置
     * @param configTypeEnum
     * @param key
     * @return
     */
    public List<Config> getConfig(ConfigTypeEnum configTypeEnum, String key) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", configTypeEnum.name()).eq("name", key).eq("status", 1));
        return configs;
    }

    /**
     * 通过key查询wcf相关配置
     * @param key
     * @return
     */
    public Config getWcfConfig(String key) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", ConfigTypeEnum.WCF.name()).eq("name", key).eq("status", 1));
        return configs.size() == 0 ? null : configs.get(0);
    }

    public Config getWcfConfig(ConfigTypeEnum configTypeEnum, String value) {
        List<Config> configs = configMapper.selectList(new QueryWrapper<Config>().eq("type", ConfigTypeEnum.WCF.name()).eq("name", value).eq("status", 1));
        return configs.size() == 0 ? null : configs.get(0);
    }
}
