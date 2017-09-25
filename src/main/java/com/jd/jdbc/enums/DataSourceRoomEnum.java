package com.jd.jdbc.enums;

import com.jd.jdbc.utils.EnumUtils;

/**
 * 数据源所属机房枚举
 * @author wsp
 * @since 2017/09/25
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


    /**
     * 根据附件类型获取附件枚举
     * @return 文件后缀
     */
    public static DataSourceRoomEnum valueOfByCode(String key) {
        return EnumUtils.getByCache(DataSourceRoomEnum.class,key);
    }
    @Override
    public String toString() {
        return "DataSourceRoomEnum{" +
                "key='" + key + '\'' +
                '}';
    }
}
