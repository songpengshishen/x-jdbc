package com.jd.jdbc.core.build;

import com.jd.jdbc.config.DataSourceBaseConfig;

import javax.sql.DataSource;

/**
 * 数据源构造类接口
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public interface DataSourceBuilder {


    /**
     * 通过数据源配置类构建数据源
     * @param dataSourceConfig 数据源配置类
     * @return 数据源
     */
     DataSource build(DataSourceBaseConfig dataSourceConfig);
}
