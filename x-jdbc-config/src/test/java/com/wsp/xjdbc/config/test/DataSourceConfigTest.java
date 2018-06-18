package com.wsp.xjdbc.config.test;

import com.wsp.xjdbc.config.MasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.StandardMasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Set;

/**
 * 数据源配置测试
 * @author wsp
 */
public class DataSourceConfigTest {

    @Test
    public void testSimpleConfig(){
        DataSource master = createMasterDbcpDataSource();
        DataSource slave01 = createslave0DbcpDataSource();
        DataSource slave02 = createslave1DbcpDataSource();
        Set<SlaveDataSourceConfig> slaves = new HashSet<SlaveDataSourceConfig>();

        MasterDataSourceConfig masterDataSourceConfig = new MasterDataSourceConfig();
        masterDataSourceConfig.setName("master");
        masterDataSourceConfig.setTargetDataSource(master);

        SlaveDataSourceConfig slaveDataSourceConfig0 = new SlaveDataSourceConfig();
        slaveDataSourceConfig0.setName("slave01");
        slaveDataSourceConfig0.setTargetDataSource(slave01);
        slaveDataSourceConfig0.setEnable(true);
        slaves.add(slaveDataSourceConfig0);

        SlaveDataSourceConfig slaveDataSourceConfig1 = new SlaveDataSourceConfig();
        slaveDataSourceConfig1.setName("slave02");
        slaveDataSourceConfig1.setTargetDataSource(slave02);
        slaves.add(slaveDataSourceConfig1);

        MasterSlaveDataSourceFactory dataSourceFactory = new StandardMasterSlaveDataSourceFactory();
        dataSourceFactory.getDataSource(masterDataSourceConfig,slaves,null);
    }


    private DataSource createMasterDbcpDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://192.168.195.161:3306/my-test-db-01");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("admin");
        basicDataSource.setInitialSize(0);
        basicDataSource.setMaxActive(15);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxWait(15000);
        return basicDataSource;
    }


    private DataSource createslave0DbcpDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://192.168.195.161:3306/pop_wsp_test_02");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("admin");
        basicDataSource.setInitialSize(0);
        basicDataSource.setMaxActive(15);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxWait(15000);
        return basicDataSource;
    }

    private DataSource createslave1DbcpDataSource() {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl("jdbc:mysql://192.168.195.161:3306/my-test-db-03");
        basicDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        basicDataSource.setUsername("admin");
        basicDataSource.setPassword("admin");
        basicDataSource.setInitialSize(0);
        basicDataSource.setMaxActive(15);
        basicDataSource.setMaxIdle(5);
        basicDataSource.setMinIdle(2);
        basicDataSource.setMaxWait(15000);
        return basicDataSource;
    }

}
