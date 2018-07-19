package com.wsp.xjdbc.core.ds;

import com.jd.jdbc.config.DataSourceGroupBaseConfig;
import com.jd.jdbc.core.SqlAnalysisEngine;
import com.jd.jdbc.core.domain.Sql;
import com.jd.jdbc.core.readwrite.connection.StandardMasterSlaveConnection;
import com.jd.jdbc.enums.SQLType;
import com.jd.jdbc.exception.XJdbcBaseException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 标准的读写多数据源类,本身作为一个数据源提供给应用使用配置,包含当前应用配置的数据源集群类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public class StandardMasterSlaveDataSource extends AbstractMasterSlaveDataSource {


    public StandardMasterSlaveDataSource(DataSourceGroupBaseConfig dataSourceGroupBaseConfig){
        super(dataSourceGroupBaseConfig);
    }

    /*
     * 当前线程是否是Master执行标志
     */
    private final static ThreadLocal<Boolean> ONLY_MASTER_FLAG = new ThreadLocal<Boolean>(){
        @Override
        protected Boolean initialValue() {
            return Boolean.FALSE;
        }
    };

    @Override
    public DataSource determineRealDataSource(String sql) {
        if(ONLY_MASTER_FLAG.get()){
            return getDataSourceGroupBaseConfig().getMasterDataSource().getDataSource();
        }
        DataSource dataSource = null;
        Sql sqlObj = SqlAnalysisEngine.analysisAndGenerateSql(sql);
        if(sqlObj.getSqlType() == SQLType.DML){
            ONLY_MASTER_FLAG.set(Boolean.TRUE);
            dataSource =  getDataSourceGroupBaseConfig().getMasterDataSource().getDataSource();
        }else if(sqlObj.getSqlType() == SQLType.DQL){
            dataSource =  getDataSourceGroupBaseConfig().getRoute().route(getDataSourceGroupBaseConfig().getSlaveDataSources()).getDataSource();
        }else {
             throw new XJdbcBaseException("determineRealDataSource Can't find Right DataSource Sql : " + sql);
        }
        return dataSource;
    }



    @Override
    public Connection getConnection() throws SQLException {
        return new StandardMasterSlaveConnection(this);
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return new StandardMasterSlaveConnection(this,username,password);
    }


    public static void clearOnlyMasterFlag(){
        ONLY_MASTER_FLAG.remove();
    }

}
