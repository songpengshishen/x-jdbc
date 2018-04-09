package com.jd.jdbc.core.readwrite;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * Title :主从Statement接口
 * Description: 扩展 JDBC {@link DataSource}接口</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/03/28
 */
public interface MasterSlaveStatement extends Statement {
    /**
     * 决定真实的Statement
     * @return
     */
    Connection determineRealStatement(String sql) throws SQLException;
}
