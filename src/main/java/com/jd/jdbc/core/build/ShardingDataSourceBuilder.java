package com.jd.jdbc.core.build;

import com.jd.jdbc.config.ShardingDataSourceBaseConfig;
import com.jd.jdbc.core.ds.ShardingDataSource;

import javax.sql.DataSource;

/**
 * 分片数据源构造类执行者:用来构造一个分片的数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSourceBuilder extends AbstractDataSourceBuilder<ShardingDataSourceBaseConfig> {

    /**
     * 通过数据源配置类构建数据源
     * @param dataSourceConfig 数据源配置类
     * @return 数据源
     */
    @Override
    public DataSource doBuild(ShardingDataSourceBaseConfig dataSourceConfig) {
        return new ShardingDataSource(dataSourceConfig);
    }
}
