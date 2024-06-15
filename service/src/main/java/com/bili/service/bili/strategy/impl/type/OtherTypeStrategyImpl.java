package com.bili.service.bili.strategy.impl.type;



import com.bili.common.constant.BiliConstant;
import com.bili.common.entity.bili.RoomInfo;
import com.bili.common.entity.mysql.Config;
import com.bili.common.util.DanMuUtil;
import com.bili.service.bili.factory.CmdStrategyFactory;
import com.bili.service.bili.strategy.CmdStrategy;
import com.bili.service.bili.strategy.TypeStrategy;
import com.bili.service.db.ConfigServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhuminc
 * @date 2024/6/7
 **/
@Service("OTHER")
@Slf4j
public class OtherTypeStrategyImpl implements TypeStrategy {
    @Resource
    CmdStrategyFactory cmdStrategyFactory;
    @Resource
    ConfigServiceImpl configService;
    @Override
    public JsonNode exec(JsonNode row) throws Exception {
        String msgCmdFromDanMu = DanMuUtil.getMsgCmdFromDanMu(row);
        if (row.has("type")){
            if (row.get("type") == null || row.get("type").isNull()){
                // TODO:暂时跳过人气处理
                if(row.get(BiliConstant.MSG).has("popularity")){
                    return null;
                }
                CmdStrategy strategy = cmdStrategyFactory.getStrategy(msgCmdFromDanMu);
                // 这里加前置日志控制
                String name = strategy.getClass().getSimpleName();
                Config config = configService.getConfig(name);
                if (config != null && config.getStatus() == 1){
                    log.info("实现类：{} 前置数据:{}", name, row);
                }
                strategy.exec(row);
            }
        }else {
            RoomInfo roomInfoFromRow = DanMuUtil.getRoomInfoFromRow(row);
            log.info("房间:【{}】 {}", roomInfoFromRow.getRoomId(), row);
        }
       return null;
    }
}
