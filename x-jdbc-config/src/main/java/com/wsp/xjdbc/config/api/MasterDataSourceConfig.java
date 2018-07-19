package com.wsp.xjdbc.config.api;

import javax.sql.DataSource;

/**
 * master的数据源配置
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class MasterDataSourceConfig extends AbstractDataSourceConfig {

    public MasterDataSourceConfig() {
        super();
    }

    public MasterDataSourceConfig(String name, String roomArea, DataSource targetDataSource) {
        super(name, roomArea, targetDataSource);
    }
}
