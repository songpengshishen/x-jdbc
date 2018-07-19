package com.wsp.xjdbc.core.ds;

import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import com.wsp.xjdbc.core.MasterSlaveDataSource;
import com.wsp.xjdbc.core.ProxyWrapper;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.Set;


/**
 * 抽象的读写多数据源实现类
 * 负责DataSource中的通用辅助方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public abstract class AbstractMasterSlaveDataSource extends ProxyWrapper implements MasterSlaveDataSource {


    private MasterDataSourceConfig masterDataSourceConfig;

    private Set<SlaveDataSourceConfig> slaveDataSourceConfigs;

    private PrintWriter logWriter = new PrintWriter(System.out);


    public AbstractMasterSlaveDataSource(MasterDataSourceConfig masterDataSourceConfig,Set<SlaveDataSourceConfig> slaveDataSourceConfigs){
         this.masterDataSourceConfig = masterDataSourceConfig;
         this.slaveDataSourceConfigs = slaveDataSourceConfigs;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return logWriter;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        this.logWriter = out;
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported setLoginTimeout(int seconds) By StandardMasterSlaveDataSource");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported getLoginTimeout() By StandardMasterSlaveDataSource");
    }

    public MasterDataSourceConfig getMasterDataSourceConfig() {
        return masterDataSourceConfig;
    }

    public void setMasterDataSourceConfig(MasterDataSourceConfig masterDataSourceConfig) {
        this.masterDataSourceConfig = masterDataSourceConfig;
    }

    public Set<SlaveDataSourceConfig> getSlaveDataSourceConfigs() {
        return slaveDataSourceConfigs;
    }

    public void setSlaveDataSourceConfigs(Set<SlaveDataSourceConfig> slaveDataSourceConfigs) {
        this.slaveDataSourceConfigs = slaveDataSourceConfigs;
    }
}
