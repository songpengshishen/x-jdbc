package com.jd.jdbc.core.ds;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 分库分表数据源
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2.0.0.Alpha
 */
public class ShardingDataSource extends AbstractDataSource{
    /**
     * <p>Attempts to establish a connection with the data source that
     * this <code>DataSource</code> object represents.
     *
     * @return a connection to the data source
     * @throws SQLException if a database access error occurs
     */
    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    /**
     * <p>Attempts to establish a connection with the data source that
     * this <code>DataSource</code> object represents.
     *
     * @param username the database user on whose behalf the connection is
     *                 being made
     * @param password the user's password
     * @return a connection to the data source
     * @throws SQLException if a database access error occurs
     * @since 1.4
     */
    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }
}
