package com.jd.jdbc.enums;

import com.jd.jdbc.utils.EnumUtils;

/**
 * 数据源所属机房枚举
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public enum DataSourceRoomEnum {

    LF("LF","廊坊"),
    MJQ("MJQ","马驹桥")
    ;
    private String key;
    private String desc;

    DataSourceRoomEnum(String key,String desc) {
        this.key = key;
        this.desc = desc;
        EnumUtils.putCache(DataSourceRoomEnum.class,key,this);
    }


    public static DataSourceRoomEnum valueOfByCode(String key) {
        return EnumUtils.getByCache(DataSourceRoomEnum.class,key);
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
