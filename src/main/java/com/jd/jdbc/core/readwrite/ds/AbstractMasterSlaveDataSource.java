package com.jd.jdbc.core.readwrite.ds;
import com.jd.jdbc.config.DataSourceGroupBaseConfig;
import com.jd.jdbc.core.ProxyWrapper;
import com.jd.jdbc.core.readwrite.MasterSlaveDataSource;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

/**
 * 抽象的读写多数据源实现类
 * 负责DataSource中的通用辅助方法
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public abstract class AbstractMasterSlaveDataSource extends ProxyWrapper implements MasterSlaveDataSource {
    /**
     * 数据源组配置,一般读写分离使用该配置
     */
    protected DataSourceGroupBaseConfig dataSourceGroupBaseConfig;


    private PrintWriter logWriter = new PrintWriter(System.out);


    public AbstractMasterSlaveDataSource(DataSourceGroupBaseConfig dataSourceGroupBaseConfig){
         this.dataSourceGroupBaseConfig = dataSourceGroupBaseConfig;
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
        throw new SQLFeatureNotSupportedException("unsupported setLoginTimeout(int seconds) By ReadWriteMultipleDataSource");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported getLoginTimeout() By ReadWriteMultipleDataSource");
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    @Override
    public List<DataSource> getAllTargetDataSource() {
        return dataSourceGroupBaseConfig.getAllDataSource();
    }

    public DataSourceGroupBaseConfig getDataSourceGroupBaseConfig() {
        return dataSourceGroupBaseConfig;
    }

    public void setDataSourceGroupBaseConfig(DataSourceGroupBaseConfig dataSourceGroupBaseConfig) {
        this.dataSourceGroupBaseConfig = dataSourceGroupBaseConfig;
    }
}
