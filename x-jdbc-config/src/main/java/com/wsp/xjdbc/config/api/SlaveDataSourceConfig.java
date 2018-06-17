package com.wsp.xjdbc.config.api;

import javax.sql.DataSource;

/**
 * slave的数据源配置
 * Date : 2018-06-17
 * @author wsp
 * @since 2.0
 */
public class SlaveDataSourceConfig extends AbstractDataSourceConfig {

    public SlaveDataSourceConfig() {
        super();
    }

    public SlaveDataSourceConfig(String name, String roomArea, DataSource targetDataSource, int weight) {
        super(name, roomArea, targetDataSource);
        this.weight = weight;
    }

    /**
     * 权重值
     */
    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}
