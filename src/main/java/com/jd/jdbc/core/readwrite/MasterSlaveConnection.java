package com.jd.jdbc.core.readwrite;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Title :主从数据源连接接口
 * Description: 扩展 JDBC {@link Connection}接口</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public interface MasterSlaveConnection extends Connection {

    /**
     * 决定真实的数据源连接
     * @return
     */
    Connection determineRealConnection(String sql) throws SQLException;
}
