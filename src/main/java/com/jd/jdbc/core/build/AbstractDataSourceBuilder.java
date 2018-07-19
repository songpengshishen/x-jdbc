package com.jd.jdbc.core.build;

import com.google.common.base.Preconditions;
import com.jd.jdbc.config.DataSourceBaseConfig;


import javax.sql.DataSource;

/**
 * 抽象的数据源构造类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public abstract class AbstractDataSourceBuilder<T> implements DataSourceBuilder{


    /**
     * 通过数据源配置类构建数据源
     * @param dataSourceConfig 数据源配置类
     * @return 数据源
     */
    @Override
    public DataSource build(DataSourceBaseConfig dataSourceConfig) {
        Preconditions.checkNotNull(dataSourceConfig,"dataSourceConfig cannot be null.");
        return doBuild((T) dataSourceConfig);
    }




    /**
     * 调用子类实际构建真正的DataSource
     * @param t
     * @return
     */
    protected abstract DataSource doBuild(T t);
}
