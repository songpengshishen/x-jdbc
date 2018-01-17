package com.jd.jdbc.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源分片配置类 : 一个数据源分片配置对象代表一个分片架构配置
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSourceConfig extends AbstractDataSourceConfig{


    /**
     * 所有数据源组配置集合,每一个代表一个主从架构
     */
    private List<DataSourceGroupConfig> dataSourceGroupConfigList = new ArrayList<DataSourceGroupConfig>();


    public ShardingDataSourceConfig(List<DataSourceGroupConfig> dataSourceGroupConfigs){
        this(null,dataSourceGroupConfigs);
    }


    public ShardingDataSourceConfig(String name,List<DataSourceGroupConfig> dataSourceGroupConfigs){
        super(name);
        this.dataSourceGroupConfigList = dataSourceGroupConfigs;
    }


    @Override
    protected String generatorName() {
        return SHARDING_NAME_PREFIX + SHARDING_NAME_COUNTER.getAndIncrement();
    }

    public List<DataSourceGroupConfig> getDataSourceGroupConfigList() {
        return dataSourceGroupConfigList;
    }

    public void setDataSourceGroupConfigList(List<DataSourceGroupConfig> dataSourceGroupConfigList) {
        this.dataSourceGroupConfigList = dataSourceGroupConfigList;
    }
}
