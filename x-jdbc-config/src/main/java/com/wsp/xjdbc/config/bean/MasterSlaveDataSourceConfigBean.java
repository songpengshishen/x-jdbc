package com.wsp.xjdbc.config.bean;

import com.wsp.xjdbc.config.MasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.springframework.beans.factory.FactoryBean;
import javax.sql.DataSource;
import java.util.Set;

/**
 * masterSlave数据源配置SpringBean
 * Date : 2018-06-18
 * @author wsp
 * @since 2.0
 */
public class MasterSlaveDataSourceConfigBean implements FactoryBean<DataSource>{

    /**
     * 数据源创建工厂实例,可以在spring配置文件中配置
     */
    private MasterSlaveDataSourceFactory dataSourceFactory;

    /**
     * master数据源配置
     */
    private MasterDataSourceConfig master;

    /**
     * slave数据源配置
     */
    private Set<SlaveDataSourceConfig> slaves;

    /**
     * master\slave数据源策略配置
     */
    private MasterSlaveStrategyConfig strategyConfig;


    @Override
    public DataSource getObject() throws Exception {
        return dataSourceFactory.getDataSource(master,slaves,strategyConfig);
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }



    public MasterDataSourceConfig getMaster() {
        return master;
    }

    public void setMaster(MasterDataSourceConfig master) {
        this.master = master;
    }

    public Set<SlaveDataSourceConfig> getSlaves() {
        return slaves;
    }

    public void setSlaves(Set<SlaveDataSourceConfig> slaves) {
        this.slaves = slaves;
    }

    public MasterSlaveStrategyConfig getStrategyConfig() {
        return strategyConfig;
    }

    public void setStrategyConfig(MasterSlaveStrategyConfig strategyConfig) {
        this.strategyConfig = strategyConfig;
    }

    public MasterSlaveDataSourceFactory getDataSourceFactory() {
        return dataSourceFactory;
    }

    public void setDataSourceFactory(MasterSlaveDataSourceFactory dataSourceFactory) {
        this.dataSourceFactory = dataSourceFactory;
    }

    @Override
    public String toString() {
        return "MasterSlaveDataSourceConfigBean{" +
                "dataSourceFactory=" + dataSourceFactory +
                ", master=" + master +
                ", slaves=" + slaves +
                ", strategyConfig=" + strategyConfig +
                '}';
    }
}
