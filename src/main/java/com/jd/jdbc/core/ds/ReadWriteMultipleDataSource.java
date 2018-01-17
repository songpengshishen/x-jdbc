package com.jd.jdbc.core.ds;

import com.jd.jdbc.exception.XJdbcNoAliveDataSourceException;
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
     * 存放数据源的线程本地私有对象,存放了当前线程的数据源beanId
     */
    private ThreadLocal<String> currentDataSource = new ThreadLocal<String>();


    /**
     * 存放当前数据源的模式
     */
    private ThreadLocal<Boolean> onlyMaster = new ThreadLocal<Boolean>();

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
            throw new XJdbcNoAliveDataSourceException();
        }
        return dataSource;
    }


    /**
     * 获取ThreadLocal中的数据源
     * @return
     */
    private DataSource getDataSource(){
        String beanId =  getDataSourceBeanId();
        if(null == beanId || beanId.isEmpty()){
            //如果当前线程没有设置过数据源则默认使用主库数据源
            setDataSourceBeanId(dataSourceCluterConfig.getMaster().getId());
            setMasterRouteOnly(Boolean.TRUE);
            return getBean(dataSourceCluterConfig.getMaster().getId(),DataSource.class);
        }
        return getBean(beanId, DataSource.class);
    }



    /**
     * 获取ThreadLocal中的数据源beanId
     * @return
     */
    public String getDataSourceBeanId(){
        return currentDataSource.get();
    }


    /**
     * 设置ThreadLocal中的数据源beanId
     * @return
     */
    public void setDataSourceBeanId(String dataSourceBeanId){
         currentDataSource.set(dataSourceBeanId);
    }

    /**
     * 清空ThreadLocal中的数据源
     * @return
     */
    public void clearDataSource(){
        currentDataSource.remove();
    }


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public DataSourceCluterConfig getDataSourceCluterConfig() {
        return dataSourceCluterConfig;
    }

    public void setDataSourceCluterConfig(DataSourceCluterConfig dataSourceCluterConfig) {
        this.dataSourceCluterConfig = dataSourceCluterConfig;
    }

    public void setMasterRouteOnly(Boolean mode) {
       this.onlyMaster.set(mode);
    }

    public Boolean isOnlyMaster () {
        return this.onlyMaster.get();
    }
}
