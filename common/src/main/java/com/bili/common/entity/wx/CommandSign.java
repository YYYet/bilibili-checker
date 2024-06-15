package com.bili.common.entity.wx;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * @author zhuminc
 * @date 2024/5/28
 **/
@Data
public class CommandSign {
   String roomId;
   String wxId;
   boolean isGroup;
}
