package com.bili.common.entity.wx.notice;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Data
public class NoticeDTO {
    String tip;
    String receiver;
    String atUserWxId;
    String atUserName;
    String endNoticePrefix;
    boolean endNotice;

    public boolean isAt(){
        return StringUtils.hasText(atUserWxId);
    }
}
