package com.wsp.xjdbc.common.enums;


import com.wsp.xjdbc.common.utils.EnumUtils;
/**
 * 路由算法枚举
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public enum RouteEnum {

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
