package com.bili.common.constant;

/**
 * @author zhuminc
 * @date 2024/5/23
 **/
public class RedisConstant {
    public static String roomRoot = "%s:%s";
    public static String roomGiftRoot = "%s:%s:gift";
    public static String roomUnfreeGiftKey = roomGiftRoot+":unfreeGift:list";
    public static String roomUnfreeGifListKey = roomGiftRoot+":unfreeGift:unfreeGiftList";
    public static String roomUnFreeGiftObjectListKey = roomGiftRoot+":unfreeGift:unfreeGiftObjectList";
    public static String roomUnfreeGifAllPriceKey = roomGiftRoot+":unfreeGift:price";

    public static String roomfreeGiftKey = roomGiftRoot+":freeGift:list";
    public static String roomfreeGifListKey = roomGiftRoot+":freeGift:freeGiftList";
    public static String roomliveDataKey = roomRoot+":liveData";
    public static String roomliveUSer = roomRoot+":user:%s";
    public static String roomliveVisitScore = roomRoot+":visitScore";
    public static String roomliveDanMuScore = roomRoot+":danMuScore";

    public static String roomFreeGiftObjectListKey = roomGiftRoot+":freeGift:freeGiftObjectList";
    public static String roomfreeGifAllPriceKey = roomGiftRoot+":freeGift:price";
    public static String die = "%s:%s:die";
    public static String dieRoot = "%s:%s:die:%s";

    public static String roomdieGiftListKey = dieRoot+":nums";
    public static String roomdiePriceListKey = dieRoot+":price";
    public static String roomdieNameKey = dieRoot+":name";

    public static String word = "%s:%s";
    public static String biliroom = word+":biliroom";
    public static String wxroom =  word+":wxroom";
}
