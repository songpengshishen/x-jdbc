package com.jd.jdbc.core.build;

import com.jd.jdbc.config.DataSourceConfig;

import javax.sql.DataSource;

/**
 * 数据源组构建类执行者:构建一个数据源组的DataSource
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class DataSourceGroupBuilder implements DataSourceBuilder {



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
