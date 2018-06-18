package com.wsp.xjdbc.config;

import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;

import javax.sql.DataSource;
import java.util.Set;

/**
 * master\slave 数据源创建工厂接口
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public interface MasterSlaveDataSourceFactory {

    /**
     * 根据 master,slave数据源及数据源策略创建DataSource
     * @param master master配置
     * @param slaves slaves配置集合
     * @param strategyConfig 数据源策略配置
     * @return
     */
    DataSource getDataSource(MasterDataSourceConfig master, Set<SlaveDataSourceConfig> slaves,
                             MasterSlaveStrategyConfig strategyConfig);

}
