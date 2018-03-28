package com.jd.jdbc.core.readwrite.ds;
import com.jd.jdbc.config.DataSourceGroupBaseConfig;
import com.jd.jdbc.core.ProxyWrapper;
import com.jd.jdbc.core.SqlAnalysisEngine;
import com.jd.jdbc.core.domain.Sql;
import com.jd.jdbc.core.readwrite.MasterSlaveDataSource;
import com.jd.jdbc.core.readwrite.connection.ReadWriteMultipleConnection;
import com.jd.jdbc.enums.SQLType;
import com.jd.jdbc.exception.XJdbcBaseException;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.List;
import java.util.logging.Logger;

/**
 * 读写多数据源类,本身作为一个数据源提供给应用使用配置,包含当前应用配置的数据源集群类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public class ReadWriteMultipleDataSource extends ProxyWrapper implements MasterSlaveDataSource {


    /**
     * 数据源组配置,一般读写分离使用该配置
     */
    private DataSourceGroupBaseConfig dataSourceGroupBaseConfig;

    /*
     * 当前线程是否是Master执行标志
     */
    private final static ThreadLocal<Boolean> ONLY_MASTER_FLAG = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };


    private PrintWriter logWriter = new PrintWriter(System.out);


    @Override
    public DataSource determineRealDataSource(String sql) {
        if(ONLY_MASTER_FLAG.get()){
            return dataSourceGroupBaseConfig.getMasterDataSource().getDataSource();
        }
        DataSource dataSource = null;
        Sql sqlObj = SqlAnalysisEngine.analysisAndGenerateSql(sql);
        if(sqlObj.getSqlType() == SQLType.DML){
            ONLY_MASTER_FLAG.set(Boolean.TRUE);
            dataSource =  dataSourceGroupBaseConfig.getMasterDataSource().getDataSource();
        }else if(sqlObj.getSqlType() == SQLType.DQL){
            dataSource =  dataSourceGroupBaseConfig.getRoute().route(dataSourceGroupBaseConfig.getSlaveDataSources()).getDataSource();
        }else {
             throw new XJdbcBaseException("determineRealDataSource Can't find Right DataSource Sql : " + sql);
        }
        return dataSource;
    }

    @Override
    public List<DataSource> getAllTargetDataSource() {
       return dataSourceGroupBaseConfig.getAllDataSource();
    }

    @Override
    public Connection getConnection() throws SQLException {
        return new ReadWriteMultipleConnection(this);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new ReadWriteMultipleConnection(this,username,password);
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
}
