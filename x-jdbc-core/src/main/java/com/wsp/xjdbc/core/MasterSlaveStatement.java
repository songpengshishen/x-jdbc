package com.wsp.xjdbc.core;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Title :主从Statement接口
 * Description: 扩展 JDBC {@link DataSource}接口</br>
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/07/19
 */
public interface MasterSlaveStatement extends Statement {
    /**
     * 通过Sql决策真实的Statement
     * @return
     */
    Statement determineRealStatement(String sql) throws SQLException;
}
