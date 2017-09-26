package com.jd.jdbc.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类,提供缓存枚举数据,在创建枚举对象时写入,根据相应KEY进行读出,加速枚举访问
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
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
