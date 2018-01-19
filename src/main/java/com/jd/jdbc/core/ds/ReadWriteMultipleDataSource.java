package com.jd.jdbc.core.ds;
import com.jd.jdbc.config.DataSourceGroupBaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 读写多数据源类,本身作为一个数据源提供给应用使用配置,包含当前应用配置的数据源集群类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ReadWriteMultipleDataSource extends AbstractDataSource {

    private DataSourceGroupBaseConfig dataSourceGroupConfig;

    public ReadWriteMultipleDataSource(DataSourceGroupBaseConfig dataSourceGroupConfig){
           this.dataSourceGroupConfig = dataSourceGroupConfig;
    }

    public Connection getConnection() throws SQLException {
        return null;
    }


    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public DataSourceGroupBaseConfig getDataSourceGroupConfig() {
        return dataSourceGroupConfig;
    }

    public void setDataSourceGroupConfig(DataSourceGroupBaseConfig dataSourceGroupConfig) {
        this.dataSourceGroupConfig = dataSourceGroupConfig;
    }



}
