package com.jd.jdbc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源分片配置类 : 一个数据源分片配置对象代表一个分片架构配置
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSourceBaseConfig extends DataSourceBaseConfig {


    /**
     * 所有数据源组配置集合,每一个代表一个主从架构
     */
    private List<DataSourceGroupBaseConfig> dataSourceGroupConfigList = new ArrayList<DataSourceGroupBaseConfig>();


    public ShardingDataSourceBaseConfig(List<DataSourceGroupBaseConfig> dataSourceGroupConfigs){
        this(null,dataSourceGroupConfigs);
    }


    public ShardingDataSourceBaseConfig(String name, List<DataSourceGroupBaseConfig> dataSourceGroupConfigs){
        super(name);
        this.dataSourceGroupConfigList = dataSourceGroupConfigs;
    }


    @Override
    protected String generatorName() {
        return SHARDING_NAME_PREFIX + SHARDING_NAME_COUNTER.getAndIncrement();
    }

    public List<DataSourceGroupBaseConfig> getDataSourceGroupConfigList() {
        return dataSourceGroupConfigList;
    }

    public void setDataSourceGroupConfigList(List<DataSourceGroupBaseConfig> dataSourceGroupConfigList) {
        this.dataSourceGroupConfigList = dataSourceGroupConfigList;
    }
}
