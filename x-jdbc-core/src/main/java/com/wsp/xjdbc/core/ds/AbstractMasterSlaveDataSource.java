package com.wsp.xjdbc.core.ds;

import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import com.wsp.xjdbc.core.MasterSlaveDataSource;
import com.wsp.xjdbc.core.ProxyWrapper;
import com.wsp.xjdbc.strategy.route.Route;
import com.wsp.xjdbc.strategy.route.RouteFactory;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;


/**
 * 抽象的读写多数据源实现类
 * 负责DataSource中的通用辅助方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public abstract class AbstractMasterSlaveDataSource extends ProxyWrapper implements MasterSlaveDataSource {


    protected MasterDataSourceConfig masterDataSourceConfig;

    protected List<SlaveDataSourceConfig> slaveDataSourceConfigs = new CopyOnWriteArrayList<SlaveDataSourceConfig>();

    protected MasterSlaveStrategyConfig strategyConfig;

    protected Route route = null;

    private List<DataSource> slaveDataSources = new CopyOnWriteArrayList<DataSource>();

    private PrintWriter logWriter = new PrintWriter(System.out);


    protected AbstractMasterSlaveDataSource(MasterDataSourceConfig masterDataSourceConfig,
                                            List<SlaveDataSourceConfig> slaveDataSourceConfigs,MasterSlaveStrategyConfig strategyConfig){
         this.masterDataSourceConfig = masterDataSourceConfig;
         this.slaveDataSourceConfigs.addAll(slaveDataSourceConfigs);
         this.strategyConfig = strategyConfig;
         for(SlaveDataSourceConfig dataSourceConfig : slaveDataSourceConfigs){
             this.slaveDataSources.add(dataSourceConfig.getTargetDataSource());
         }
         route = RouteFactory.getInstance(strategyConfig.getRoute());
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

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    @Override
    public List<DataSource> getSlaveTargetDataSources() {
        return slaveDataSources;
    }

    @Override
    public DataSource getMasterDataSource() {
        return masterDataSourceConfig.getTargetDataSource();
    }

    public MasterDataSourceConfig getMasterDataSourceConfig() {
        return masterDataSourceConfig;
    }

    public List<SlaveDataSourceConfig> getSlaveDataSourceConfigs() {
        return slaveDataSourceConfigs;
    }
}
