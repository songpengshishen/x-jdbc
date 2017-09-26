package com.jd.jdbc.ds;


import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * 代理数据源抽象类,实现JDBC规范DataSource接口,并提供通用实现
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 1.0.0.Alpha
 */
public abstract class ProxyDataSource implements DataSource{

    protected Class<DataSource> dataSourceClass = DataSource.class;

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        if(!isDataSource(iface)) {
            throw new SQLDataException("DataSource of type [" + this.getClass().getName() + "] can only be unwrapped as [javax.sql.DataSource], not as [" + iface.getName());
        } else {
            return (T) this;
        }
    }


    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return isDataSource(iface);
    }


    @Override
    public PrintWriter getLogWriter() throws SQLException {
        throw new UnsupportedOperationException("Not supported getLogWriter by ProxyDataSource");
    }


    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        throw new UnsupportedOperationException("Not supported setLogWriter by ProxyDataSource");
    }


    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        throw new UnsupportedOperationException("Not supported setLoginTimeout by ProxyDataSource");
    }


    @Override
    public int getLoginTimeout() throws SQLException {
         return 0;
    }


    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return Logger.getLogger(this.getClass().getName());
    }

    private <T>  boolean isDataSource(Class<T> tclass){
        return DataSource.class.equals(dataSourceClass);
    }

    protected abstract DataSource getCurrentDataSource();
}
