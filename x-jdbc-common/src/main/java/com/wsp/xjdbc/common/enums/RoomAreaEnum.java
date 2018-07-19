package com.wsp.xjdbc.common.enums;


import com.wsp.xjdbc.common.utils.EnumUtils;

/**
 * 数据源所属机房枚举
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public enum RoomAreaEnum {

    LF("LF","廊坊"),
    MJQ("MJQ","马驹桥")
    ;
    private String key;
    private String desc;

    RoomAreaEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
        EnumUtils.putCache(RoomAreaEnum.class,key,this);
    }


    public static RoomAreaEnum valueOfByCode(String key) {
        return EnumUtils.getByCache(RoomAreaEnum.class,key);
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
