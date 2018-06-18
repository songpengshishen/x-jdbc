package com.wsp.xjdbc.config;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Set;

/**
 * 一个标准的主从数据源工厂
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class StandardMasterSlaveDataSourceFactory extends AbstractMasterSlaveSourceFactory {

    private static final Logger logger = LoggerFactory.getLogger(StandardMasterSlaveDataSourceFactory.class);

    @Override
    protected DataSource createDataSource(MasterDataSourceConfig master, Set<SlaveDataSourceConfig> slaves,
                                          MasterSlaveStrategyConfig strategyConfig)throws IllegalStateException {

        // TODO: 2018/6/17 创建数据源
        return new BasicDataSource();
    }


}
