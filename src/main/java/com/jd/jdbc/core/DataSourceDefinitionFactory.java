package com.jd.jdbc.core;

import com.google.common.base.Preconditions;
import com.jd.jdbc.enums.DataSourceRoleEnum;
import com.jd.jdbc.enums.DataSourceRoomEnum;

import javax.sql.DataSource;


/**
 * 数据源定义类简单工厂类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public final class DataSourceDefinitionFactory {


    /**
     * 创建数据源定义类
     * @return
     */
    public DataSourceDefinition createDataSourceDefinition(String name, String deployRoom, String role, int weight, DataSource dataSource){
        Preconditions.checkNotNull(dataSource,"dataSource cannot be null.");
        Preconditions.checkNotNull(name,"name cannot be null.");
        Preconditions.checkNotNull(deployRoom,"deployRoom cannot be null.");
        Preconditions.checkNotNull(role,"role cannot be null.");
        Preconditions.checkArgument(null == DataSourceRoomEnum.valueOfByCode(deployRoom),"deployRoom can only be Lf Or Mjq!");
        Preconditions.checkArgument(null == DataSourceRoleEnum.valueOfByCode(role),"role can only be Master Or Slave!");
        return new DataSourceDefinition(name,deployRoom,role,weight,dataSource);
    }

    /**
     * 创建数据源定义类
     * @return
     */
    public DataSourceDefinition createDataSourceDefinition(String name, String deployRoom, String role, DataSource dataSource){
        Preconditions.checkNotNull(dataSource,"dataSource cannot be null.");
        Preconditions.checkNotNull(name,"name cannot be null.");
        Preconditions.checkNotNull(deployRoom,"deployRoom cannot be null.");
        Preconditions.checkArgument(null == DataSourceRoomEnum.valueOfByCode(deployRoom),"deployRoom can only be Lf Or Mjq!");
        Preconditions.checkNotNull(role,"role cannot be null.");
        Preconditions.checkArgument(null == DataSourceRoleEnum.valueOfByCode(role),"role can only be Master Or Slave!");
        return new DataSourceDefinition(name,deployRoom,role,dataSource);
    }




}
