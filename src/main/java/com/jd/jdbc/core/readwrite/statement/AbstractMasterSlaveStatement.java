package com.jd.jdbc.core.readwrite.statement;

import com.jd.jdbc.core.ProxyWrapper;
import com.jd.jdbc.core.readwrite.MasterSlaveConnection;
import com.jd.jdbc.core.readwrite.MasterSlaveStatement;
import java.sql.*;

/**
 * Title :抽象的读写Statement执行者实现
 * Description: 实现除了增删改查外的其余方法</br>
 *
 * @author <a href=mailto:wangsongpeng@jd.com>王宋鹏</a>
 * @since 2018/04/09
 */
public abstract class AbstractMasterSlaveStatement extends ProxyWrapper implements MasterSlaveStatement {

    /**
     * 读写数据源连接
     */
    protected MasterSlaveConnection masterSlaveConnection;

    private Statement targetStatement;

    /**
     * 是否是关闭的
     */
    private boolean closed;

    private boolean poolable;

    private int fetchSize;


    @Override
    public void close() throws SQLException {
        closed = true;
        if (null != targetStatement) {
            targetStatement.close();
            targetStatement = null;
        }
    }

    @Override
    public int getMaxFieldSize() throws SQLException {
        return null == targetStatement ? 0 : targetStatement.getMaxFieldSize();
    }

    @Override
    public void setMaxFieldSize(int max) throws SQLException {
        if (null != this.targetStatement) {
            targetStatement.setMaxFieldSize(max);
        } else {
            recordMethodInvocation(Statement.class, "setMaxFieldSize", new Class[]{int.class}, new Object[]{max});
        }
    }

    @Override
    public int getMaxRows() throws SQLException {
        return null == targetStatement ? -1 : targetStatement.getMaxRows();
    }

    @Override
    public void setMaxRows(int max) throws SQLException {
        if (null != this.targetStatement) {
            targetStatement.setMaxRows(max);
        } else {
            recordMethodInvocation(Statement.class, "setMaxRows", new Class[]{int.class}, new Object[]{max});
        }
    }

    @Override
    public void setEscapeProcessing(boolean enable) throws SQLException {
        if (null != this.targetStatement) {
            targetStatement.setEscapeProcessing(enable);
        } else {
            recordMethodInvocation(Statement.class, "setEscapeProcessing", new Class[]{boolean.class}, new Object[]{enable});
        }
    }

    @Override
    public int getQueryTimeout() throws SQLException {
        return null == targetStatement ? 0 : targetStatement.getQueryTimeout();
    }

    @Override
    public void setQueryTimeout(int seconds) throws SQLException {
        if (null != this.targetStatement) {
            targetStatement.setQueryTimeout(seconds);
        } else {
            recordMethodInvocation(Statement.class, "setQueryTimeout", new Class[]{int.class}, new Object[]{seconds});
        }
    }

    @Override
    public void cancel() throws SQLException {
        targetStatement.cancel();
    }

    @Override
    public SQLWarning getWarnings() throws SQLException {
        if (null != targetStatement) {
            return targetStatement.getWarnings();
        }
        throw new SQLFeatureNotSupportedException("unsupported getWarnings() By AbstractMasterSlaveStatement");
    }

    @Override
    public void clearWarnings() throws SQLException {
        if (null != targetStatement) {
            targetStatement.clearWarnings();
        }
        throw new SQLFeatureNotSupportedException("unsupported clearWarnings() By AbstractMasterSlaveStatement");
    }

    @Override
    public void setCursorName(String name) throws SQLException {
        if (null != targetStatement) {
            targetStatement.setCursorName(name);
        }
        throw new SQLFeatureNotSupportedException("unsupported  setCursorName(String name) By AbstractMasterSlaveStatement");
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        return targetStatement.getResultSet();
    }

    @Override
    public int getUpdateCount() throws SQLException {
        return targetStatement.getUpdateCount();
    }

    @Override
    public boolean getMoreResults() throws SQLException {
        if (null != targetStatement) {
            return targetStatement.getMoreResults();
        }
        throw new SQLFeatureNotSupportedException("unsupported  getMoreResults() By AbstractMasterSlaveStatement");
    }

    @Override
    public void setFetchDirection(int direction) throws SQLException {
        if (null != targetStatement) {
            targetStatement.setFetchDirection(direction);
        }
        throw new SQLFeatureNotSupportedException("unsupported setFetchDirection(int direction) By AbstractMasterSlaveStatement");
    }

    @Override
    public int getFetchDirection() throws SQLException {
        if (null != targetStatement) {
            targetStatement.getFetchDirection();
        }
        throw new SQLFeatureNotSupportedException("unsupported getFetchDirection() By AbstractMasterSlaveStatement");
    }

    @Override
    public void setFetchSize(int rows) throws SQLException {
        this.fetchSize = rows;
        if (null != this.targetStatement) {
            targetStatement.setFetchSize(rows);
        } else {
            recordMethodInvocation(Statement.class, "setFetchSize", new Class[]{int.class}, new Object[]{rows});
        }
    }

    @Override
    public int getFetchSize() throws SQLException {
        return fetchSize;
    }

    @Override
    public int getResultSetConcurrency() throws SQLException {
        if (null != targetStatement) {
            return targetStatement.getResultSetConcurrency();
        }
        throw new SQLFeatureNotSupportedException("unsupported getResultSetConcurrency() By AbstractMasterSlaveStatement");
    }

    @Override
    public int getResultSetType() throws SQLException {
        if (null != targetStatement) {
            return targetStatement.getResultSetType();
        }
        throw new SQLFeatureNotSupportedException("unsupported getResultSetType() By AbstractMasterSlaveStatement");
    }

    @Override
    public void addBatch(String sql) throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported addBatch(String sql) By AbstractMasterSlaveStatement");
    }

    @Override
    public void clearBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported clearBatch() By AbstractMasterSlaveStatement");
    }

    @Override
    public int[] executeBatch() throws SQLException {
        throw new SQLFeatureNotSupportedException("unsupported executeBatch() By AbstractMasterSlaveStatement");
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public boolean getMoreResults(int current) throws SQLException {
        return false;
    }

    @Override
    public ResultSet getGeneratedKeys() throws SQLException {
        return null;
    }


    @Override
    public int getResultSetHoldability() throws SQLException {
        return 0;
    }

    @Override
    public boolean isClosed() throws SQLException {
        return false;
    }

    @Override
    public void setPoolable(boolean poolable) throws SQLException {

    }

    @Override
    public boolean isPoolable() throws SQLException {
        return false;
    }

    @Override
    public void closeOnCompletion() throws SQLException {

    }

    @Override
    public boolean isCloseOnCompletion() throws SQLException {
        return false;
    }
}
