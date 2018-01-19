package com.jd.jdbc.core.ds;

import com.jd.jdbc.config.ShardingDataSourceBaseConfig;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 分库分表数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSource extends AbstractDataSource{


    private ShardingDataSourceBaseConfig dataSourceConfig;

    public ShardingDataSource(ShardingDataSourceBaseConfig dataSourceConfig){
          this.dataSourceConfig = dataSourceConfig;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    public ShardingDataSourceBaseConfig getDataSourceConfig() {
        return dataSourceConfig;
    }

    public void setDataSourceConfig(ShardingDataSourceBaseConfig dataSourceConfig) {
        this.dataSourceConfig = dataSourceConfig;
    }
}
