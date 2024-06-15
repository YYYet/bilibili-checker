package com.bili.service.redis;

import com.bili.common.constant.RedisConstant;
import com.bili.common.util.CommonHelper;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhuminc
 * @date 2024/6/15
 **/
@Service
public class QueryDataServiceImpl {
    @Resource
    RedisTemplate redisTemplate;
    @Resource
    CommonHelper commonHelper;

    public ArrayList<HashMap<String, Object>> getDieByDate(String date, String roomId){
        String dieKey = String.format(RedisConstant.die, roomId, date);
        String pattern = dieKey + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        Set<String> uid = new HashSet<>();
        for (String key : keys) {
            String dieUid =  key.split(":")[5];
            uid.add(dieUid);
        }
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        for (String id : uid) {
            HashMap<String, Object> map = new HashMap<>();
            String roomdieNameKey = String.format(RedisConstant.roomdieNameKey, roomId, date, id);
            String roomdiePriceKey = String.format(RedisConstant.roomdiePriceListKey, roomId, date, id);
            Object name = redisTemplate.opsForHash().get(roomdieNameKey, "name");

            Object price = redisTemplate.opsForHash().get(roomdiePriceKey, "price");
            if (name == null || price == null){
                continue;
            }
            BigDecimal money = new BigDecimal(price.toString());
            map.put("name", name.toString());
            map.put("price", money.divide(BigDecimal.TEN));
            hashMaps.add(map);
        }
        return hashMaps;
    }

    public ArrayList<HashMap<String, Object>> getDieByDate(String date){
        String dieKey = String.format(RedisConstant.die, "24616501", date);
        String pattern = dieKey + "*";
        Set<String> keys = redisTemplate.keys(pattern);
        Set<String> uid = new HashSet<>();
        for (String key : keys) {
            String dieUid =  key.split(":")[5];
            uid.add(dieUid);
        }
        ArrayList<HashMap<String, Object>> hashMaps = new ArrayList<>();
        for (String id : uid) {
            HashMap<String, Object> map = new HashMap<>();
            String roomDieNameKey = String.format(RedisConstant.roomdieNameKey, "24616501", date, id);
            String roomDiePriceKey = String.format(RedisConstant.roomdiePriceListKey, "24616501", date, id);
            Object name = redisTemplate.opsForHash().get(roomDieNameKey, "name");

            Object price = redisTemplate.opsForHash().get(roomDiePriceKey, "price");
            if (name == null || price == null){
                continue;
            }
            BigDecimal money = new BigDecimal(price.toString());
            map.put("name", name.toString());
            map.put("price", money.divide(BigDecimal.TEN));
            hashMaps.add(map);
        }
        return hashMaps;
    }


    public String getUserNameByUid(String roomId, String uid){
        String today = commonHelper.buildTodayDateStr();
        String roomliveDataKey = String.format(RedisConstant.roomliveUSer, roomId, today, uid);

        return redisTemplate.opsForHash().get(roomliveDataKey, "name").toString();
    }
    public Map<String, Object> getUserNameByUidList(String roomId, String date){

        String pattern = String.format(RedisConstant.roomliveUSer.replace("user:%s", "user"), roomId, date); // 可以设置模式，例如 "user:*" 来匹配以 "user:" 开头的键
        System.out.println(pattern);
        Map<String, Object> hashValuesWithPrefix = getHashValuesWithPrefix(pattern);
        System.out.println(hashValuesWithPrefix);
        return hashValuesWithPrefix;
    }
    public Map<String, Object> getHashValuesWithPrefix(String prefix) {
        Map<String, Object> hashValues = new HashMap<>();

        HashOperations<String, String, Object> hashOps = redisTemplate.opsForHash();
        Cursor<Map.Entry<String, Object>> cursor = hashOps.scan(prefix+"*", ScanOptions.scanOptions().match(prefix+"*").count(1000).build());

        while (cursor.hasNext()) {
            Map.Entry<String, Object> entry = cursor.next();
            hashValues.put(entry.getKey(), entry.getValue());
        }

        cursor.close();
        return hashValues;
    }




    public String getRoomALlUnfreeGiftPrice(String roomId){
        String today = commonHelper.buildTodayDateStr();
        String roomUnfreeGifAllPriceKey = String.format(RedisConstant.roomUnfreeGifAllPriceKey, roomId, today);
        Object price = redisTemplate.opsForHash().get(roomUnfreeGifAllPriceKey, "price");
        return price.toString();
    }
    public String getRoomALlUnfreeGiftPrice(String roomId, String date){
        String today = date;
        String roomUnfreeGifAllPriceKey = String.format(RedisConstant.roomUnfreeGifAllPriceKey, roomId, today);
        Object price = redisTemplate.opsForHash().get(roomUnfreeGifAllPriceKey, "price");
        if (price == null){
            return "0";
        }
        return price.toString();
    }
    public String getRoomALlUnfreeGiftPrice(String roomId, int limitDay){
        String day = commonHelper.buildDateStr(limitDay);
        String roomUnfreeGifAllPriceKey = String.format(RedisConstant.roomUnfreeGifAllPriceKey, roomId, day);
        Object price = redisTemplate.opsForHash().get(roomUnfreeGifAllPriceKey, "price");
        return price.toString();
    }

}
