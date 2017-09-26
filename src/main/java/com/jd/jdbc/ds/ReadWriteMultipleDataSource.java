package com.jd.jdbc.ds;

import com.jd.jdbc.route.Route;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 读写多数据源类,本身作为一个数据源提供给应用使用配置,包含当前应用配置的数据源集群类
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public class ReadWriteMultipleDataSource extends ProxyDataSource {

    /**
     * 数据源集群配置
     */
    private DataSourceCluterConfig dataSourceCluterConfig;

    /**
     * 读写分离读库路由算法
     */
    private Route route;

    /**
     * 存放数据源的线程本地私有对象,存放了当前线程的数据源
     */
    private ThreadLocal<DataSource> currentDataSource = new ThreadLocal<DataSource>();



    /**
     * DataSource接口中获取连接
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection() throws SQLException {
        return getCurrentDataSource().getConnection();
    }

    /**
     * DataSource接口中获取连接
     * @return
     * @throws SQLException
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return getCurrentDataSource().getConnection(username,password);
    }

    /**
     * 获取当前要实用的数据源
     * @return
     */
    @Override
    protected DataSource getCurrentDataSource() {
        DataSource dataSource = getDataSource();
        if(null == dataSource){
            throw new NullPointerException("dataSource is Empty!");
        }
        return dataSource;
    }


    /**
     * 获取ThreadLocal中的数据源
     * @return
     */
    public DataSource getDataSource(){
        return currentDataSource.get();
    }

    /**
     * 设置ThreadLocal中的数据源
     * @return
     */
    public void setDataSource(DataSource dataSource){
         currentDataSource.set(dataSource);
    }

    /**
     * 清空ThreadLocal中的数据源
     * @return
     */
    public void clearDataSource(DataSource dataSource){
        currentDataSource.remove();
    }

    public DataSourceCluterConfig getDataSourceCluterConfig() {
        return dataSourceCluterConfig;
    }

    public void setDataSourceCluterConfig(DataSourceCluterConfig dataSourceCluterConfig) {
        this.dataSourceCluterConfig = dataSourceCluterConfig;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
