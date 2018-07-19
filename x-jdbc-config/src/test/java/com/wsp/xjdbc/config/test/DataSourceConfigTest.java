package com.wsp.xjdbc.config.test;

import com.wsp.xjdbc.config.MasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.StandardMasterSlaveDataSourceFactory;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Assert;
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
    public void testCreateDataSource(){
        DataSource master = createMasterDbcpDataSource();
        DataSource slave01 = createslave0DbcpDataSource();
        DataSource slave02 = createslave1DbcpDataSource();
        Set<SlaveDataSourceConfig> slaves = new HashSet<SlaveDataSourceConfig>();

        MasterDataSourceConfig m1 = new MasterDataSourceConfig();
        m1.setName("master");
        m1.setTargetDataSource(master);

        SlaveDataSourceConfig s1 = new SlaveDataSourceConfig();
        s1.setName("slave01");
        s1.setTargetDataSource(slave01);
        slaves.add(s1);

        SlaveDataSourceConfig s2 = new SlaveDataSourceConfig();
        s2.setName("slave02");
        s2.setTargetDataSource(slave02);
        slaves.add(s2);


        MasterSlaveStrategyConfig strategyConfig = new MasterSlaveStrategyConfig();
        strategyConfig.setRoute("aaa");

        MasterSlaveDataSourceFactory dataSourceFactory = new StandardMasterSlaveDataSourceFactory();
        Assert.assertNotNull(dataSourceFactory.getDataSource(m1,slaves,strategyConfig));
    }




    @Test
    public void testMasterDataSourceConfig(){
        DataSource master = createMasterDbcpDataSource();

        MasterDataSourceConfig m1 = new MasterDataSourceConfig();

        m1.setName("master1");
        m1.setTargetDataSource(master);
        m1.setRoomArea("LF1");


        MasterDataSourceConfig m2 = new MasterDataSourceConfig();

        m2.setName("master1");
        m2.setTargetDataSource(master);
        m2.setRoomArea("LF1");


        Assert.assertEquals("Object Not Equals",m1,m2);
        Assert.assertEquals("HashCode Not Equals",m1.hashCode(),m2.hashCode());
    }



    @Test
    public void testSlaveDataSourceConfig(){
        DataSource slave0 = createslave0DbcpDataSource();

        SlaveDataSourceConfig s1 = new SlaveDataSourceConfig();

        s1.setName("s1");
        s1.setTargetDataSource(slave0);
        s1.setRoomArea("LF1");
        s1.setWeight(2);
        s1.setEnable(false);


        SlaveDataSourceConfig s2 = new SlaveDataSourceConfig();

        s2.setName("s1");
        s2.setTargetDataSource(slave0);
        s2.setRoomArea("LF1");
        s2.setWeight(2);
        s2.setEnable(false);


        Assert.assertEquals("Object Not Equals",s1,s2);
        Assert.assertEquals("HashCode Not Equals",s1.hashCode(),s2.hashCode());
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
