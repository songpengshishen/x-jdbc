package com.jd.jdbc.core.build;

import com.google.common.base.Preconditions;
import com.jd.jdbc.config.DataSourceGroupBaseConfig;
import com.jd.jdbc.core.readwrite.ds.ReadWriteMultipleDataSource;
import com.jd.jdbc.enums.RouteEnum;
import com.jd.jdbc.route.RouteFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;

/**
 * 数据源组构建类执行者:构建一个数据源组的DataSource
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class DataSourceGroupBuilder extends AbstractDataSourceBuilder<DataSourceGroupBaseConfig> {

    private static Logger logger = LoggerFactory.getLogger(DataSourceGroupBuilder.class);

    @Override
    protected DataSource doBuild(DataSourceGroupBaseConfig dataSourceGroupConfig) {
        Preconditions.checkNotNull(dataSourceGroupConfig.getMasterDataSource(),"MasterDataSource cannot be null.");
        Preconditions.checkArgument(CollectionUtils.isEmpty(dataSourceGroupConfig.getSlaveDataSources()),"SlaveDataSource cannot be null.");
        if(null == dataSourceGroupConfig.getRoute()){
            logger.warn("DataSourceGroupBaseConfig.getRoute() Is Null,Use Simple Route");
            dataSourceGroupConfig.setRoute(RouteFactory.getInstance(RouteEnum.SIMPLE.name()));
        }

        return new ReadWriteMultipleDataSource(dataSourceGroupConfig);
    }


}
