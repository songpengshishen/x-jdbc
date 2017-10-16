package com.jd.jdbc.enums;

import com.jd.jdbc.utils.EnumUtils;

/**
 * 路由算法枚举
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public enum RouteEnum {
    SIMPLE("SIMPLE","简单的读写分离"),
    RANDOM("RANDOM","权重随机路由"),
    LOCALROOM("LOCALROOM","本地机房调用")
    ;
    private String key;
    private String desc;

    RouteEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
        EnumUtils.putCache(RouteEnum.class,key,this);
    }


    public static RouteEnum valueOfByCode(String key) {
        return EnumUtils.getByCache(RouteEnum.class,key);
    }

    @Override
    public String toString() {
        return "RouteEnum{" +
                "key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
