package com.wsp.xjdbc.core.ds;

import com.wsp.xjdbc.common.domain.Sql;
import com.wsp.xjdbc.common.domain.SqlAnalysisEngine;
import com.wsp.xjdbc.common.enums.SQLType;
import com.wsp.xjdbc.common.exception.XJdbcBaseException;
import com.wsp.xjdbc.config.api.MasterDataSourceConfig;
import com.wsp.xjdbc.config.api.MasterSlaveStrategyConfig;
import com.wsp.xjdbc.config.api.SlaveDataSourceConfig;
import com.wsp.xjdbc.core.XJdbcContext;
import com.wsp.xjdbc.core.connection.StandardMasterSlaveConnection;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 标准的读写多数据源类,本身作为一个数据源提供给应用使用配置,包含当前应用配置的数据源集群类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/20
 */
public class StandardMasterSlaveDataSource extends AbstractMasterSlaveDataSource {


    public StandardMasterSlaveDataSource(MasterDataSourceConfig masterDataSourceConfig,
                                         List<SlaveDataSourceConfig> slaveDataSourceConfigs, MasterSlaveStrategyConfig strategyConfig){
        super(masterDataSourceConfig,slaveDataSourceConfigs,strategyConfig);
    }



    @Override
    public DataSource determineRealDataSource(String sql) {
        if(XJdbcContext.getContext().getOnlyMasterFlag()){
            return masterDataSourceConfig.getTargetDataSource();
        }
        DataSource dataSource = null;
        Sql sqlObj = SqlAnalysisEngine.analysisAndGenerateSql(sql);
        if(sqlObj.getSqlType() == SQLType.DML){
            XJdbcContext.getContext().setOnlyMasterFlag(Boolean.TRUE);
            dataSource =  masterDataSourceConfig.getTargetDataSource();
        }else if(sqlObj.getSqlType() == SQLType.DQL){
            dataSource =  route.route(slaveDataSourceConfigs);
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





}
