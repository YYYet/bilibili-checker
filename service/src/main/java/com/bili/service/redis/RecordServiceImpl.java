package com.bili.service.redis;

import com.bili.common.constant.RedisConstant;
import com.bili.common.entity.bili.*;
import com.bili.common.util.CommonUtil;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;



/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Service
public class RecordServiceImpl {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    CommonUtil commonUtil;

    /**
     * 计算礼物价格并存入redis
     * @param feedInfo
     */
    public void recordGiftPrice2Redis(FeedInfo feedInfo){

        String today = commonUtil.nowDate2SelfStyle();
        GiftInfo gift = feedInfo.getGift();
        UserInfo userInfo = feedInfo.getUserInfo();
        long num = feedInfo.getNum();
        String roomId = feedInfo.getRoomInfo().getRoomId();

        String roomUnfreeGiftKey = String.format(RedisConstant.roomUnfreeGiftKey, roomId, today);
        String roomUnfreeGifAllPriceKey = String.format(RedisConstant.roomUnfreeGifAllPriceKey, roomId, today);
        String roomFreeGiftKey = String.format(RedisConstant.roomfreeGiftKey, roomId, today);

        String roomDieGiftListKey = String.format(RedisConstant.roomdieGiftListKey, roomId, today, userInfo.getUid());
        String roomDiePriceListKey = String.format(RedisConstant.roomdiePriceListKey, roomId, today, userInfo.getUid());
        String roomDieNameKey = String.format(RedisConstant.roomdieNameKey, roomId, today, userInfo.getUid());

        if (!gift.isFree()) {
            long price = gift.getGiftPrice();
            redisTemplate.opsForHash().increment(roomUnfreeGiftKey, gift.getGiftName(), price * num / 100);
            redisTemplate.opsForHash().increment(roomUnfreeGifAllPriceKey, "price", price * num / 100);

            redisTemplate.opsForHash().increment(roomDieGiftListKey, gift.getGiftName(), num);
            redisTemplate.opsForHash().increment(roomDiePriceListKey, "price", price * num / 100);
            redisTemplate.opsForHash().put(roomDieNameKey, "name", userInfo.getUserName());

        }
        if (gift.isFree()) {
            long price = gift.getGiftPrice();
            redisTemplate.opsForHash().increment(roomFreeGiftKey, gift.getGiftName(), price * num);
        }
    }
    public void recordDanMuScore2Redis(String roomId, String uid){
        String today = commonUtil.buildTodayDateStr();
        String roomLiveDanMuScore = String.format(RedisConstant.roomliveDanMuScore, roomId, today);
        redisTemplate.opsForZSet().incrementScore(roomLiveDanMuScore, uid, 1);
    }

    public void recordVisitScore2Redis(String roomId, String uid){
        String today = commonUtil.buildTodayDateStr();
        String roomLiveVisitScore = String.format(RedisConstant.roomliveVisitScore, roomId, today);
        redisTemplate.opsForZSet().incrementScore(roomLiveVisitScore, uid, 1);
    }
    public void recordUserName2Redis(String roomId, String uid, String userName){
        String today = commonUtil.buildTodayDateStr();
        String roomLiveDataKey = String.format(RedisConstant.roomliveUSer, roomId, today, uid);
        Boolean hasName = redisTemplate.opsForHash().hasKey(roomLiveDataKey, "name");
        if (!hasName){
            redisTemplate.opsForHash().put(roomLiveDataKey, "name", userName);

        }
    }






}
