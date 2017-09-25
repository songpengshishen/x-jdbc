package com.jd.jdbc.ds;

/**
 * 数据源包装类,记录了应用所定义的每个数据源的beanID及数据源信息
 * @author wsp
 * @since 2017/09/25
 */
public class DataSourceWrapper {
    private String beanId; /*应用配置的数据源在spring中的beanID*/
    private String deployRoom; /*数据源部署机房*/
    private String role;/*数据源角色 master/slave*/
}
