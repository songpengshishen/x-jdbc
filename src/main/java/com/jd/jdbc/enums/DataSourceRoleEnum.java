package com.jd.jdbc.enums;

import com.jd.jdbc.utils.EnumUtils;

/**
 * 数据源角色枚举类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public enum DataSourceRoleEnum {

    MASTER("MASTER","主库"),
    SLAVE("SLAVE","从库")
    ;
    private String key;
    private String desc;

    DataSourceRoleEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
        EnumUtils.putCache(DataSourceRoleEnum.class,key,this);
    }


    public static DataSourceRoleEnum valueOfByCode(String key) {
        return EnumUtils.getByCache(DataSourceRoleEnum.class,key);
    }

    @Override
    public String toString() {
        return "DataSourceRoleEnum{" +
                "key='" + key + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
