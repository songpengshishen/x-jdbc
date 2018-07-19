package com.wsp.xjdbc.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 枚举工具类
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public class EnumUtils {
    //并发控制HashMap
    private final static Map<Class<?>, Map<String, Object>> CACHE = new ConcurrentHashMap<>(100);

    public static <T extends Enum<T>> void putCache(Class<T> c, String key, T value){
        Map<String, Object> map = CACHE.get(c);
        if(null == map){
            synchronized (CACHE) {
                map = CACHE.get(c);
                if(null == map){
                    map = new ConcurrentHashMap<>();
                    CACHE.put(c, map);
                }
            }
        }
        map.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Enum<T>> T getByCache(Class<T> c, String key){
        Map<String, Object> map = CACHE.get(c);
        return (T) (map==null?null:map.get(key));
    }

}
