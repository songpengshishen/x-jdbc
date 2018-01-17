package com.jd.jdbc.core.build;

import com.jd.jdbc.config.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 分片数据源构造类执行者:用来构造一个分片的数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSourceBuilder implements DataSourceBuilder {



    /**
     * 通过数据源配置类构建数据源
     * @param dataSourceConfig 数据源配置类
     * @return 数据源
     */
    @Override
    public DataSource build(DataSourceConfig dataSourceConfig) {
        return null;
    }
}
